package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import db.ImageDb;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ImageDb imageDb;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controller() {
		super();
		imageDb = new ImageDb();
		// TODO Auto-generated constructor stub
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
		case "uploadeimage":
			destination = uploadImage(request, response);
			break;
		case "downloadimage":
			//destination = downloadImage(request, response);
			break;
		default:
			break;
		}
	}

	private String uploadImage(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Part file = request.getPart("file");
		
		return null;
	}

}
