package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.Properties;

import controller.Controller;
import domain.OpenClassService;
import domain.OpenClassSession;
import domain.Student;

public class SessieDb {

	private OpenClassService service;
	private Properties properties = new Properties();
	private String url = "jdbc:postgresql://databanken.ucll.be:51718/hakkaton?currentSchema=he11heaven";

	public SessieDb(OpenClassService service) {
		this.service = service;

		properties.setProperty("user", "hakkaton_11");
		properties.setProperty("password", "IeS5nahweitohwaa");
		properties.setProperty("ssl", "true");
		properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new DbException(e.getMessage(), e);
		}

	}

	public OpenClassSession get(int sessieId) {
		if (sessieId == 0) {
			throw new DbException("no sessieId given.");
		}
		String sql = "SELECT * from sessie WHERE sessieid = ?";
		try (Connection connection = DriverManager.getConnection(url, properties);
				PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setInt(1, sessieId);
			ResultSet result = statement.executeQuery();
			result.next();
			int sessionId = Integer.parseInt(result.getString("sessieid"));
			String title = result.getString("naam");
			String description = result.getString("beschrijving");
			LocalTime begin = result.getTimestamp("begin").toLocalDateTime().toLocalTime();
			LocalTime einde = result.getTimestamp("einde").toLocalDateTime().toLocalTime();
			int maxInschrijvingen = Integer.parseInt(result.getString("max_inschrijvingen"));
			String klaslokaal = result.getString("klaslokaal");

			OpenClassSession sessie = new OpenClassSession(sessionId, title, description, begin, einde,
					maxInschrijvingen, klaslokaal, service.telAantalInschrijvingen(sessionId));
			return sessie;

		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	public ArrayList<OpenClassSession> getAll() {
		ArrayList<OpenClassSession> sessies = new ArrayList<OpenClassSession>();
		try (Connection connection = DriverManager.getConnection(url, properties);
				Statement statement = connection.createStatement();) {
			ResultSet result = statement.executeQuery("SELECT * FROM sessie");

			while (result.next()) {
				int sessionId = Integer.parseInt(result.getString("sessieid"));
				String title = result.getString("naam");
				String description = result.getString("beschrijving");

				LocalTime begin = result.getTimestamp("begin").toLocalDateTime().toLocalTime();
				LocalTime einde = result.getTimestamp("einde").toLocalDateTime().toLocalTime();
				int maxInschrijvingen = Integer.parseInt(result.getString("max_inschrijvingen"));
				String klaslokaal = result.getString("klaslokaal");

				OpenClassSession sessie = new OpenClassSession(sessionId, title, description, begin, einde,
						maxInschrijvingen, klaslokaal, service.telAantalInschrijvingen(sessionId));
				int openLesDagId = result.getInt("openlesdagid");
				sessie.setOpenlesdagid(openLesDagId);

				sessies.add(sessie);
			}

			return sessies;
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}

	}

	public void addNewSession(OpenClassSession sessie) {

		String sql = "INSERT into sessie (naam, beschrijving,max_inschrijvingen,klaslokaal,begin,einde, openlesdagid) VALUES (?,?,?,?,?,?,?)";

		try (Connection connection = DriverManager.getConnection(url, properties);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, sessie.getTitle());
			statement.setString(2, sessie.getDescription());

			statement.setInt(3, sessie.getMaxEntries());
			statement.setString(4, sessie.getClassroom());

			statement.setTime(5, Time.valueOf(sessie.getStart()));
			statement.setTime(6, Time.valueOf(sessie.getEnd()));

			statement.setInt(7, sessie.getOpenlesdagid());

			statement.executeUpdate();
			connection.close();

		} catch (Exception e) {
			System.out.println(sessie.getStart().toString());
			System.out.println("werkt niet: " + e.getMessage());
		}
	}

	public void schrijfIn(Student student, OpenClassSession sessie) {
		if (student == null)
			throw new DbException("no student given.");
		if (sessie == null)
			throw new DbException("no session given.");
		String sql = "INSERT INTO inschrijving(studentid, sessieid) VALUES (?,?)";
		try (Connection connection = DriverManager.getConnection(url, properties);
				PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setInt(1, student.getId());
			statement.setInt(2, sessie.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	public ArrayList<OpenClassSession> getSessiesOpenLesDag(int openLesDagId) {
		String query = "SELECT * FROM sessie WHERE openlesdagid = ?";

		ArrayList<OpenClassSession> sessies = new ArrayList<OpenClassSession>();
		try (Connection connection = DriverManager.getConnection(url, properties);
				PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setInt(1, openLesDagId);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				int sessionId = Integer.parseInt(result.getString("sessieid"));
				String title = result.getString("naam");
				String description = result.getString("beschrijving");

				LocalTime begin = result.getTimestamp("begin").toLocalDateTime().toLocalTime();
				LocalTime einde = result.getTimestamp("einde").toLocalDateTime().toLocalTime();
				int maxInschrijvingen = Integer.parseInt(result.getString("max_inschrijvingen"));
				String klaslokaal = result.getString("klaslokaal");

				OpenClassSession sessie = new OpenClassSession(sessionId, title, description, begin, einde,
						maxInschrijvingen, klaslokaal, service.telAantalInschrijvingen(sessionId));
				sessies.add(sessie);
			}

			return sessies;
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}
}
