package controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import db.AfdelingDb;
import db.ImageDb;
import db.SessieDb;
import domain.Afdeling;
import domain.OpenClassSession;
import domain.Opleiding;
import domain.SimpleMail;

@WebServlet("/Controller")
@MultipartConfig
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ImageDb imageDb;
	private SimpleMail mail;
	private AfdelingDb afdelingDb;
	ArrayList<Afdeling> afdelingen = new ArrayList<>();
	private SessieDb sessieDb = new SessieDb();

	public Controller() throws ClassNotFoundException, SQLException {
		super();
		imageDb = new ImageDb();
		mail = new SimpleMail();
		afdelingDb = new AfdelingDb();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		handleAction(request, response);
	}

	private void handleAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String destination = "";
		String action = request.getParameter("action");
		if (action == null) {
			action = "";
		}
		switch (action) {
		case "uploadimage":
			uploadImage(request, response);
			break;
		case "downloadimage":
			downloadImage(request, response);
			break;
		case "imageoverview":
			destination = imageOverview(request, response);
			break;
		case "sendMail":
			destination = sendMail(request, response);
			break;
		case "sessionoverview":
			destination = sessionOverview(request, response);
			break;
		case "overviewOpendays":
			destination = openDayOverview(request, response);
			break;
		case "getOpleidingenOverzicht":
			destination = getOpleidingenOverzicht(request, response);
			break;
		case "register":
			destination = register(request, response);
			break;
		default:
			destination = "index.jsp";
		}
		if (destination != "") {
			RequestDispatcher rd = request.getRequestDispatcher(destination);
			rd.forward(request, response);
		}
	}

	private String openDayOverview(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));

		String a = request.getParameter("afdeling");

		for (Afdeling afd : afdelingen) {
			if (a.equals(afd.getNaam())) {
				Afdeling af = afd;
				Opleiding o = af.getOpleiding(id);
				request.setAttribute("openDays", o.getOpenLesDagen());
			}
		}
		return "overviewOpenDays.jsp";
	}

	private String sendMail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String email = request.getParameter("email");
			mail.sendMail(email);
		} catch (Exception e) {
			throw new ServletException(e.getMessage(), e);
		}

		return "Controller?action=";

	}

	private void uploadImage(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Part file = request.getPart("image");
		this.imageDb.addNewImage(file);
		response.sendRedirect("Controller?action=imageoverview");
	}

	private void downloadImage(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String fileName = request.getParameter("fileName");
		String fileFormat = fileName.substring(fileName.indexOf(".") + 1);

		response.setHeader("Content-Disposition", "attachment; filename=\"download." + fileFormat + "\"");
		ImageIO.write(this.imageDb.getImage(fileName), fileFormat, response.getOutputStream());
		response.sendRedirect("Controller?action=imageoverview");
	}

	// TODO: Voorlopig nog niet nodig, maar werkt nog niet.
	private String imageOverview(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		ArrayList<String> images = new ArrayList<>();
		for (String key : imageDb.getImages().keySet()) {
			BufferedImage image = imageDb.getImages().get(key);

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(image, key.substring(key.indexOf(".")), os);
			os.flush();
			byte[] imageIntByteArray = os.toByteArray();
			os.close();
			String b64 = javax.xml.bind.DatatypeConverter.printBase64Binary(imageIntByteArray);
			images.add(b64);
		}
		request.getSession().setAttribute("images", images);
		return "imageOverview.jsp";
	}

	private String sessionOverview(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		int columns = 3;

		ArrayList<OpenClassSession> sessions = sessieDb.getAll();

		ArrayList<ArrayList<OpenClassSession>> dividedSessions = new ArrayList<>();
		for (int i = 0; i < sessions.size(); i += columns) {
			ArrayList<OpenClassSession> rowSessions = new ArrayList<>();
			for (int j = 0; j < columns; j++) {
				if (sessions.size() % columns == 0 || i + j < sessions.size()) {
					rowSessions.add(sessions.get(i + j));
				}
			}
			dividedSessions.add(rowSessions);
		}

		request.setAttribute("sessions", dividedSessions);
		return "sessionOverview.jsp";
	}

	private String getOpleidingenOverzicht(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("afdelingen", afdelingDb.getAfdelingen());

		Afdeling a1 = new Afdeling("Lerarenopleiding");
		a1.addOpleiding(new Opleiding("Kleuteronderwijs", 1));
		a1.addOpleiding(new Opleiding("Lager onderwijs", 2));

		Afdeling a2 = new Afdeling("Gezondheid");
		a2.addOpleiding(new Opleiding("Mondzorg", 3));
		a2.addOpleiding(new Opleiding("Vroedkunde", 4));

		Afdeling a3 = new Afdeling("Welzijn");
		a3.addOpleiding(new Opleiding("Sociaal werk", 5));

		afdelingen.add(a1);
		afdelingen.add(a2);
		afdelingen.add(a3);

		request.setAttribute("afdelingen", afdelingen);
		request.setAttribute("afdelingen", afdelingDb.getAfdelingen());
		return "opleidingOverzicht.jsp";
	}

	private String register(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("sessionId");
		int sessionId = Integer.valueOf(id);
		request.setAttribute("session", sessieDb.get(sessionId));
		return "registration.jsp";
	}
}
