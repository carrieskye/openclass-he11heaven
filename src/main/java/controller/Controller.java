package controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

import org.joda.time.DateTime;

import db.ImageDb;
import domain.OpenClassSession;
import domain.SimpleMail;

@WebServlet("/Controller")
@MultipartConfig
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ImageDb imageDb;
	private SimpleMail mail;

	public Controller() {
		super();
		imageDb = new ImageDb();
		mail = new SimpleMail();
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
		default:
			destination = "index.jsp";
		}
		if (destination != "") {
			RequestDispatcher rd = request.getRequestDispatcher(destination);
			rd.forward(request, response);
		}
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
		ArrayList<OpenClassSession> sessions = new ArrayList<>();
		sessions.add(new OpenClassSession("Bomen en Grafen",
				"Gaat over bomen- en grafen structuren in de wiskunde, wordt aanzien als het gemakkelijkste examen van het tweede semester.",
				new DateTime(), new DateTime(), 20));
		sessions.add(new OpenClassSession("OOP", "Programmeren in Java voor gevorderden.", new DateTime(),
				new DateTime(), 20));
		sessions.add(new OpenClassSession("Scripttalen", "Het aanleren van een scripttaal, in dit geval is dat Python.",
				new DateTime(), new DateTime(), 20));

		ArrayList<ArrayList<OpenClassSession>> dividedSessions = new ArrayList<>();
		for (int i = 0; i < sessions.size(); i += 2) {
			ArrayList<OpenClassSession> twoSessions = new ArrayList<>();
			twoSessions.add(sessions.get(i));
			if (sessions.size() % 2 == 0 || i + 1 < sessions.size()) {
				twoSessions.add(sessions.get(i + 1));
			}
			dividedSessions.add(twoSessions);
		}

		request.setAttribute("sessions", dividedSessions);
		return "sessionOverview.jsp";
	}
}
