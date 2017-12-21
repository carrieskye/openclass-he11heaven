package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import domain.Student;

public class InschrijvingenDb {

	StudentDb studentDb;

	Properties properties = new Properties();
	String url = "jdbc:postgresql://databanken.ucll.be:51718/hakkaton?currentSchema=he11heaven";

	public InschrijvingenDb() {

		properties.setProperty("user", "hakkaton_11");
		properties.setProperty("password", "IeS5nahweitohwaa");
		properties.setProperty("ssl", "true");
		properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
		studentDb = new StudentDb();
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new DbException(e.getMessage(), e);
		}

	}

	public void add(Student student, int sessieId) {
		if (student == null)
			throw new DbException("no student given.");
		if (sessieId == 0)
			throw new DbException("no session given.");
		String sql = "INSERT INTO inschrijving(studentid, sessieid) " + "VALUES (?,?)";
		try (Connection connection = DriverManager.getConnection(url, properties);
			PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setInt(1, student.getId());
			statement.setInt(2, sessieId);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	public ArrayList<Student> get(int sessieId) {
		if (sessieId < 0) {
			throw new DbException("no sessionId given.");
		}
		String query = "SELECT * FROM inschrijving WHERE sessieid = ?";
		
		try (Connection connection = DriverManager.getConnection(url, properties);
			PreparedStatement statement = connection.prepareStatement(query))
		{
			statement.setInt(1, sessieId);
			ResultSet result = statement.executeQuery();
			ArrayList<Student> students = new ArrayList<Student>();
			while (result.next()) {
				students.add(studentDb.get(result.getInt("studentid")));
			}
			return students;
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	public void remove(int studentId, int sessieId) {
		if (studentId == 0 || sessieId == 0) {
			throw new DbException("No student or session given.");
		}
		String sql = "DELETE FROM inschrijving WHERE sessieid = ? AND studentid = ?";
		
		try (Connection connection = DriverManager.getConnection(url, properties);
				PreparedStatement statement = connection.prepareStatement(sql);) 
		{
			statement.setInt(1, sessieId);
			statement.setInt(2, studentId);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}
	
	public int telIngeschrevenStudenten(int sessieId) {
		// aantal ingeschreven studenten toevoegen aan sessie
		String sql = "SELECT COUNT(*) AS aantal FROM inschrijving I INNER JOIN sessie S ON (I.sessieid = S.sessieid) WHERE S.sessieid = ?";
		try (Connection connection = DriverManager.getConnection(url, properties);
			PreparedStatement statement = connection.prepareStatement(sql);) 
		{
			statement.setInt(1, sessieId);
			ResultSet result = statement.executeQuery();
			int aantalIngeschreven = 0;
			
			while (result.next()) {
				aantalIngeschreven = result.getInt("aantal");
			}
			
			return aantalIngeschreven;
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}
}
