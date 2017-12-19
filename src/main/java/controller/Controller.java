package controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import db.OpenLesdagDb;
import db.SessieDb;
import db.StudentDb;
import domain.Afdeling;
import domain.OpenClassSession;
import domain.Opleiding;
import domain.SimpleMail;
import domain.Student;

@WebServlet("/Controller")
@MultipartConfig
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ImageDb imageDb;
	private SimpleMail mail;
	private AfdelingDb afdelingDb;
<<<<<<< HEAD
	ArrayList<Afdeling> afdelingen;
	private SessieDb sessieDb;
	private OpenLesdagDb openLesdagDb;
=======
	ArrayList<Afdeling> afdelingen = new ArrayList<>();
	private SessieDb sessieDb = new SessieDb();
	private StudentDb studentDb;
>>>>>>> 22d7e389c0c0d9fd5a29230bdca89782a5890785

	public Controller() throws ClassNotFoundException, SQLException {
		super();
		imageDb = new ImageDb();
		mail = new SimpleMail();
		afdelingDb = new AfdelingDb();
<<<<<<< HEAD
		afdelingen = new ArrayList<>();
		sessieDb = new SessieDb();
		openLesdagDb = new OpenLesdagDb();
=======
		studentDb = new StudentDb();
>>>>>>> 22d7e389c0c0d9fd5a29230bdca89782a5890785
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
		case "registerForm":
			destination = registerForm(request, response);
			break;
		case "registerStudent":
			destination = registerStudent(request, response);
			System.out.println(destination);
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
				request.setAttribute("openDays", openLesdagDb.getLesdagen(o));
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
		return "opleidingOverzicht.jsp";
	}

	private String registerForm(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("sessionId");
		int sessionId = Integer.valueOf(id);
		request.setAttribute("session", sessieDb.get(sessionId));
		return "registration.jsp";
	}

	private String registerStudent(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		List<String> result = new ArrayList<String>();
		Student student = new Student();
		result = getFirstName(student, request, result);
		result = getLastName(student, request, result);
		result = getEmail(student, request, result);
		if (!result.isEmpty()) {
			request.setAttribute("errormessage", result);
			return "registration.jsp";
		} else {
			studentDb.add(student);
			return sessionOverview(request, response);
		}
	}

	private List<String> getFirstName(Student student, HttpServletRequest request, List<String> result) {
		String firstName = request.getParameter("firstName");
		try {
			student.setFirstName(firstName);
			request.setAttribute("firstNameClass", "has-success");
			request.setAttribute("firstNamePreviousValue", firstName);
		} catch (Exception exc) {
			request.setAttribute("firstNameClass", "has-error");
			result.add(exc.getMessage());
		}
		return result;
	}

	private List<String> getLastName(Student student, HttpServletRequest request, List<String> result) {
		String lastName = request.getParameter("lastName");
		try {
			student.setLastName(lastName);
			request.setAttribute("lastNameClass", "has-success");
			request.setAttribute("lastNamePreviousValue", lastName);
		} catch (Exception exc) {
			request.setAttribute("lastNameClass", "has-error");
			result.add(exc.getMessage());
		}
		return result;
	}

	private List<String> getEmail(Student student, HttpServletRequest request, List<String> result) {
		String email = request.getParameter("email");
		try {
			student.setEmail(email);
			request.setAttribute("emailClass", "has-success");
			request.setAttribute("emailPreviousValue", email);
		} catch (Exception exc) {
			request.setAttribute("emailClass", "has-error");
			result.add(exc.getMessage());
		}
		return result;
	}

}
