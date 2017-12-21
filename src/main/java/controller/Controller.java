package controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import db.InschrijvingenDb;
import db.OpenLesdagDb;
import db.OpleidingDb;
import db.SessieDb;
import db.StudentDb;
import domain.Afdeling;
import domain.DomainException;
import domain.OpenClassService;
import domain.OpenClassSession;
import domain.OpenLesDag;
import domain.Student;

@WebServlet("/Controller")
@MultipartConfig
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OpenClassService service;
	private ImageDb imageDb;
	private AfdelingDb afdelingDb;
	ArrayList<Afdeling> afdelingen;
	private SessieDb sessieDb;
	private OpenLesdagDb openLesdagDb;
	private StudentDb studentDb;
	private InschrijvingenDb inschrijvingenDb;
	private OpleidingDb opleidingDb;

	public Controller() throws ClassNotFoundException, SQLException {
		super();
		imageDb = new ImageDb();
		afdelingDb = new AfdelingDb();
		opleidingDb = new OpleidingDb();
		afdelingen = new ArrayList<>();
		sessieDb = new SessieDb(this);
		openLesdagDb = new OpenLesdagDb();
		studentDb = new StudentDb();
		inschrijvingenDb = new InschrijvingenDb();
		
		service = new OpenClassService();
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
			break;
		case "registrationOverview":
			destination = registrationOverview(request, response);
			break;
		case "toonVoegSessieToe":
			destination = toonVoegSessieToe(request, response);
			break;
		case "voegSessieToe":
			destination = voegSessieToe(request, response);
			break;
		case "updateSessionStudent":
			destination = updateSessionStudent(request, response);
			break;
		case "updateStudent":
			destination = updateStudent(request, response);
			break;
		case "removeSessionStudent":
			destination = removeSessionStudent(request, response);
			break;
		case "toonAlleInschrijvingen":
			destination = toonAlleInschrijvingen(request, response);
			break;
		case "toonInschrijvingenOpenlesdagen":
			destination = toonInschrijvingenOpenlesdagen(request, response);
			break;
		case "toonInschrijvingenSessies":
			destination = toonInschrijvingenSessies(request,response);
			break;
		case "inschrijvingen":
			destination = inschrijvingen(request, response);
			break;
		case "showAddOpenDay":
			destination = showAddOpenDay(request, response);
			break;
		case "addOpenDay":
			destination = addOpenDay(request, response);
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
		List<OpenLesDag> lesdagen = openLesdagDb.getLesdagen(id);

		if (lesdagen == null) {
			request.setAttribute("message", "Er zijn nog geen openlesdagen voor deze opleiding.");
		} else {
			request.setAttribute("openDays", lesdagen);
		}

		return "overviewOpenDays.jsp";
	}

	private void sendMail(HttpServletRequest request, HttpServletResponse response, int studentID)
			throws ServletException, IOException {
		try {
			Student student = studentDb.get(studentID);
			int sessieID = Integer.parseInt(request.getParameter("sessionId"));
			OpenClassSession sessie = sessieDb.get(sessieID);
			service.sendMail(student, sessie);
		} catch (Exception e) {
			throw new ServletException(e.getMessage(), e);
		}

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

		ArrayList<OpenClassSession> sessions;

		if (request.getParameter("openlesdagId") == null || request.getParameter("openlesdagId").isEmpty()) {
			sessions = sessieDb.getAll();
		} else {
			int openLesDagId = Integer.parseInt(request.getParameter("openlesdagId"));
			sessions = sessieDb.getSessiesOpenLesDag(openLesDagId);
		}

		if (sessions.isEmpty()) {
			request.setAttribute("noSessionsMessage", "Er zijn nog geen sessies voor deze openlesdag.");
		} else {

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
		}
		return "sessionOverview.jsp";
	}

	private String getOpleidingenOverzicht(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("afdelingen", afdelingDb.getAfdelingen());
		return "opleidingOverzicht.jsp";
	}

	private String registerForm(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String id = request.getParameter("sessionId");
		int sessionId = Integer.valueOf(id);
		OpenClassSession sessie = sessieDb.get(sessionId);
		
		if (sessie.isVolzet()) {
			request.setAttribute("infoMessage", "De sessie waarvoor je probeerde in te schrijven is volzet. Je kan hiervoor niet meer inschrijven.");
			return sessionOverview(request, response);
		}
		
		request.setAttribute("session", sessieDb.get(sessionId));
		return "registration.jsp";
	}

	private String toonVoegSessieToe(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.setAttribute("afdelingen", afdelingDb.getAfdelingen());
		return "voegSessieToe.jsp";
	}

	private String showAddOpenDay(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("afdelingen", afdelingDb.getAfdelingen());
		return "addOpenDay.jsp";
	}

	private String addOpenDay(HttpServletRequest request, HttpServletResponse response) {

		List<String> errors = new ArrayList<String>();
		OpenLesDag openDay = new OpenLesDag();

		setTitel(openDay, errors, request.getParameter("title"));
		setLocation(openDay, errors, request.getParameter("location"));
		setDatum(openDay, errors, request.getParameter("date"));
		setOpenlesdagOpleidingID(openDay, errors, request.getParameter("opleiding"));

		if (errors.size() == 0) {
			openLesdagDb.addOpenDay(openDay);
			return "index.jsp";
		} else {
			request.setAttribute("errorMessage", errors);
			return "addOpenDay.jsp";
		}
	}

	private void setOpenlesdagOpleidingID(OpenLesDag openDay, List<String> errors, String opleiding) {
		try {
			openDay.setOpleidingID(Integer.parseInt(opleiding));
		} catch (Exception e) {
			errors.add(e.getMessage());
		}

	}

	private void setDatum(OpenLesDag openDay, List<String> errors, String date) {
		try {
			LocalDate localDate = LocalDate.parse(date);
			openDay.setDatum(localDate);
		} catch (Exception e) {
			errors.add(e.getMessage());
		}
	}

	private void setLocation(OpenLesDag openDay, List<String> errors, String location) {
		try {
			openDay.setLocatie(location);
		} catch (Exception e) {
			errors.add(e.getMessage());
		}
	}

	private void setTitel(OpenLesDag openDay, List<String> errors, String title) {
		try {
			openDay.setTitel(title);
		} catch (Exception e) {
			errors.add(e.getMessage());
		}

	}

	private String voegSessieToe(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		List<String> errors = new ArrayList<String>();
		OpenClassSession sessie = new OpenClassSession();

		setTitel(sessie, errors, request.getParameter("sessionName"));
		setDescription(sessie, errors, request.getParameter("content"));
		setStartDate(sessie, errors, request.getParameter("date"), request.getParameter("beginTime"));
		setEndDate(sessie, errors, request.getParameter("date"), request.getParameter("endTime"));
		setmaxEntries(sessie, errors, request.getParameter("maxaantal"));
		setClassroom(sessie, errors, request.getParameter("classroom"));
		setOpleidingsid(sessie, errors, request.getParameter("opleiding"));
		setOpenlesdagid(sessie, errors, request.getParameter("date"), request.getParameter("opleiding"));

		 if (sessie.getOpenlesdagid() < 1) {
			request.setAttribute("afdelingen", afdelingDb.getAfdelingen());
			request.setAttribute("sessie", sessie);
			request.setAttribute("datePreviousValue", request.getParameter("date"));
			request.setAttribute("opleidingId", Integer.parseInt(request.getParameter("opleiding")));
			request.setAttribute("opleidingPreviousValue", opleidingDb.getOpleiding(Integer.parseInt(request.getParameter("opleiding"))));
			System.out.println(request.getAttribute("opleidingPreviousValue"));
			return "addOpenDay.jsp";
		} else if (errors.size() == 0) {
			sessieDb.addNewSession(sessie);
			return "index.jsp";
		} else {

			request.setAttribute("errormessage", errors);
			request.setAttribute("afdelingen", afdelingDb.getAfdelingen());

			return "voegSessieToe.jsp";
		}
	}

	private void setOpenlesdagid(OpenClassSession sessie, List<String> errors, String date, String opleiding) {
		try {
			int id = openLesdagDb.getOpenlesdagID(date, Integer.parseInt(opleiding));
			sessie.setOpenlesdagid(id);
		} catch (Exception e) {
			errors.add(e.getMessage());
		}

	}

	private void setOpleidingsid(OpenClassSession sessie, List<String> errors, String id) {
		try {
			sessie.setOpleidingid(Integer.parseInt(id));
		} catch (Exception e) {
			errors.add("Invalid education.");
		}

	}

	private void setClassroom(OpenClassSession sessie, List<String> errors, String klaslokaal) {
		try {
			sessie.setClassroom(klaslokaal);
		} catch (Exception e) {
			errors.add(e.getMessage());
		}
	}

	private void setmaxEntries(OpenClassSession sessie, List<String> errors, String maxEntries) {
		try {
			int maximum = Integer.parseInt(maxEntries);
			sessie.setMaxEntries(maximum);
		} catch (Exception e) {
			if (e instanceof DomainException) {
				errors.add(e.getMessage());
			} else if (e instanceof NumberFormatException) {
				errors.add("Max entries moet een getal zijn");
			}
		}
	}

	private void setEndDate(OpenClassSession sessie, List<String> errors, String date, String endDate) {
		try {
			LocalTime time = LocalTime.parse(endDate);
			sessie.setEnd(time);
		} catch (Exception e) {
			errors.add(e.getMessage());
		}
	}

	private void setStartDate(OpenClassSession sessie, List<String> errors, String date, String startDate) {
		try {
			LocalTime time = LocalTime.parse(startDate);
			sessie.setStart(time);
		} catch (Exception e) {
			if (e instanceof DomainException) {
				errors.add(e.getMessage());
			} else {
				errors.add("Startdate is not correct.");
			}
		}
	}

	private void setDescription(OpenClassSession sessie, List<String> errors, String inhoud) {
		try {
			sessie.setDescription(inhoud);
		} catch (Exception e) {
			errors.add(e.getMessage());
		}
	}

	private void setTitel(OpenClassSession sessie, List<String> errors, String name) {
		try {
			sessie.setTitle(name);
		} catch (Exception e) {
			errors.add(e.getMessage());
		}

	}

	private String registerStudent(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// als de sessie volzet is, niet meer inschrijven
		int sessieId = Integer.valueOf(request.getParameter("sessionId"));
		OpenClassSession sessie = sessieDb.get(sessieId);
		
		if (sessie.isVolzet()) {
			request.setAttribute("infoMessage", "De sessie waarvoor je probeerde in te schrijven is volzet. Je kan hiervoor niet meer inschrijven.");
		}
		else {
			List<String> result = new ArrayList<String>();
			Student student = new Student();
			result = getFirstName(student, request, result);
			result = getLastName(student, request, result);
			result = getEmail(student, request, result);
			if (!result.isEmpty()) {
				request.setAttribute("errormessage", result);
				return "registration.jsp";
			} else {
				int studentId = studentDb.add(student);
				inschrijvingenDb.add(studentDb.get(studentId), Integer.valueOf(request.getParameter("sessionId")));
				sendMail(request, response, studentId);
				request.setAttribute("infoMessage", String.format("Je bent ingeschreven voor de volgende sessie: %s (%s - %s)", sessie.getTitle(), sessie.getStart(), sessie.getEnd()));
			}
		}
		return sessionOverview(request, response);
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

	private String registrationOverview(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("sessionId");
		int sessionId = Integer.valueOf(id);
		request.setAttribute("session", sessieDb.get(sessionId));
		request.setAttribute("students", inschrijvingenDb.get(sessionId));
		return "registrationOverview.jsp";
	}

	private String updateSessionStudent(HttpServletRequest request, HttpServletResponse response) {
		int studentId = Integer.valueOf(request.getParameter("studentId"));
		int sessionId = Integer.valueOf(request.getParameter("sessionId"));
		request.setAttribute("session", sessieDb.get(sessionId));
		request.setAttribute("sessionId", sessionId);
		
		Student student = studentDb.get(studentId);
		request.setAttribute("studentid", studentId);
		request.setAttribute("firstNamePreviousValue", student.getFirstName());
		request.setAttribute("lastNamePreviousValue", student.getLastName());
		request.setAttribute("emailPreviousValue", student.getEmail());
		
		request.setAttribute("wijzigen", true);
		
		return "registration.jsp";
	}
	
	private String updateStudent(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int studentId = Integer.parseInt(request.getParameter("id"));
		int sessionId = Integer.parseInt(request.getParameter("sessionId"));
		List<String> result = new ArrayList<>();
		
		Student student = new Student();
		student.setId(studentId);
		result = getFirstName(student, request, result);
		result = getLastName(student, request, result);
		result = getEmail(student, request, result);
		
		if (!result.isEmpty()) {
			request.setAttribute("errormessage", result);
			request.setAttribute("wijzigen", true);
			request.setAttribute("sessionId", sessionId);
			
			return "registration.jsp";
		} else {
			service.updateStudent(student, sessionId);
			// inschrijvingenDb.add(studentDb.get(studentId), Integer.valueOf(request.getParameter("sessionId")));
			request.setAttribute("infoMessage", "Inschrijving voor " + student.getFirstName() + " " + student.getLastName() + " aangepast.");
		}		
		return sessionOverview(request, response);
	}

	private String removeSessionStudent(HttpServletRequest request, HttpServletResponse response) {
		int studentId = Integer.valueOf(request.getParameter("studentId"));
		int sessionId = Integer.valueOf(request.getParameter("sessionId"));
		inschrijvingenDb.remove(studentId, sessionId);
		request.setAttribute("session", sessieDb.get(sessionId));
		request.setAttribute("studentId", studentId);
		return registrationOverview(request, response);
	}

	public int telAantalInschrijvingen(int sessieId) {
		return inschrijvingenDb.telIngeschrevenStudenten(sessieId);
	}

	private String toonAlleInschrijvingen(HttpServletRequest request, HttpServletResponse response) {
		Map<OpenClassSession, ArrayList<Student>> inschrijvingen = new HashMap<>();
		
		for (OpenClassSession sessie : sessieDb.getAll()) {
			inschrijvingen.put(sessie, inschrijvingenDb.get(sessie.getId()));
		}
		request.setAttribute("afdelingen", afdelingDb.getAfdelingen());
		request.setAttribute("opleidingen", opleidingDb.getOpleidingen());
		request.setAttribute("inschrijvingen", inschrijvingen);
		return "inschrijvingen.jsp";

	}

	private String toonInschrijvingenOpenlesdagen(HttpServletRequest request, HttpServletResponse response) {
		int opleidingId = Integer.parseInt(request.getParameter("opleidingId"));
		request.setAttribute("openlesdagen", openLesdagDb.getLesdagen(opleidingId));
		return "inschrijvingen.jsp";
	}

	private String toonInschrijvingenSessies(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("sessies", null);
		return "inschrijvingen.jsp";
	}

	private String inschrijvingen(HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

}
