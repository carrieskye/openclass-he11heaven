package controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import org.apache.xalan.xsltc.dom.LoadDocument;
import org.joda.time.LocalDate;

import db.AfdelingDb;
import db.ImageDb;
import db.InschrijvingenDb;
import db.OpenLesdagDb;
import db.SessieDb;
import db.StudentDb;
import domain.Afdeling;
import domain.DomainException;
import domain.OpenClassSession;
import domain.OpenLesDag;
import domain.Opleiding;
import domain.SimpleMail;
import domain.Student;
import javafx.util.converter.LocalDateTimeStringConverter;

@WebServlet("/Controller")
@MultipartConfig
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ImageDb imageDb;
	private SimpleMail mail;
	private AfdelingDb afdelingDb;
	ArrayList<Afdeling> afdelingen;
	private SessieDb sessieDb;
	private OpenLesdagDb openLesdagDb;
	private StudentDb studentDb;
	private InschrijvingenDb inschrijvingenDb;

	public Controller() throws ClassNotFoundException, SQLException {
		super();
		imageDb = new ImageDb();
		mail = new SimpleMail();
		afdelingDb = new AfdelingDb();
		afdelingen = new ArrayList<>();
		sessieDb = new SessieDb(this);
		openLesdagDb = new OpenLesdagDb();
		studentDb = new StudentDb();
		inschrijvingenDb = new InschrijvingenDb();
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
		case "removeSessionStudent":
			destination = removeSessionStudent(request, response);

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
		String id = request.getParameter("id");
		List<OpenLesDag> lesdagen = openLesdagDb.getLesdagen(id);
		
		if (lesdagen == null) {
			request.setAttribute("message", "Er zijn nog geen openlesdagen voor deze opleiding.");
		}
		else {
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
			mail.sendMail(student, sessie);
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

	private String toonVoegSessieToe(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.setAttribute("afdelingen", afdelingDb.getAfdelingen());
		return "voegSessieToe.jsp";
	}

	
	private String voegSessieToe(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		List<String> errors = new ArrayList<String>();
		OpenClassSession sessie = new OpenClassSession();
		setTitel(sessie,errors,request.getParameter("sessionName"));
		setdescription(sessie,errors,request.getParameter("content"));
		setStartDate(sessie,errors,request.getParameter("beginTime"));
		setEndDate(sessie,errors, request.getParameter("endTime"));
		setmaxEntries(sessie,errors, request.getParameter("maxaantal"));
		setClassroom(sessie,errors, request.getParameter("classroom"));

		if(errors.size() == 0){
			System.out.println("alles ok");
		}
		
		else{
			for(String a: errors){
				System.out.println(a);
			}
			return toonVoegSessieToe(request, response);
		}

		return null;
		
		
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
			if(e instanceof DomainException){
				errors.add(e.getMessage());
			}
			else{
				errors.add("Max entries is not correct!");
			}
		}
		
	}

	private void setEndDate(OpenClassSession sessie, List<String> errors, String endDate) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
			LocalDateTime time = LocalDateTime.parse(endDate, formatter);
		} catch (Exception e) {
			errors.add(e.getMessage());
		}
		
	}

	private void setStartDate(OpenClassSession sessie, List<String> errors, String startDate) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
			LocalDateTime time = LocalDateTime.parse(startDate, formatter);
		} catch (Exception e) {
			if( e instanceof DomainException){
				errors.add(e.getMessage());
			}
			else{
				errors.add("Startdate is not correct.");
			}
		}		
	}

	private void setdescription(OpenClassSession sessie, List<String> errors, String inhoud) {
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
		request.setAttribute("firstNamePreviousValue", studentDb.get(studentId).getFirstName());
		request.setAttribute("lastNamePreviousValue", studentDb.get(studentId).getLastName());
		request.setAttribute("emailPreviousValue", studentDb.get(studentId).getEmail());
		return "registration.jsp";
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

}
