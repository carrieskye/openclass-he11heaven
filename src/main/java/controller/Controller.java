package controller;

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
import db.ImageDb;
<<<<<<< HEAD
=======
import domain.Afdeling;
>>>>>>> 5e749a85f08d92a79be587868971e530b0e6f603
import domain.Opleiding;
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
<<<<<<< HEAD
		case "overviewOpendays":
			destination = openDayOverview(request,response);
=======
		case "getOpleidingenOverzicht":
			destination = getOpleidingenOverzicht(request, response);
			break;
>>>>>>> 5e749a85f08d92a79be587868971e530b0e6f603
		default:
			destination = "index.jsp";
		}
		if (destination != "") {
			RequestDispatcher rd = request.getRequestDispatcher(destination);
			rd.forward(request, response);
		}
	}

	private String openDayOverview(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		int id = Integer.parseInt(request.getParameter("id"));
		Opleiding o = afdelingen.getOpleiding(id);
		request.setAttribute("openDays", o.getOpenLesDagen());
		return null;
	}

	private String sendMail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String email = request.getParameter("email");
			mail.sendMail(email);
		} catch(Exception e) {
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

	private String imageOverview(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.setAttribute("images", imageDb.getImages());
		return "imageOverview.jsp";
	}
	
	private String getOpleidingenOverzicht(HttpServletRequest request, HttpServletResponse response) {
		ArrayList<Afdeling> afdelingen = new ArrayList<>();
		
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
		
		return "opleidingOverzicht.jsp";
	}
}
