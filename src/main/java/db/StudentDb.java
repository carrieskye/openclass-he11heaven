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

public class StudentDb {

	Properties properties = new Properties();
	String url = "jdbc:postgresql://databanken.ucll.be:51718/hakkaton?currentSchema=he11heaven";

	public StudentDb() {
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

	public int add(Student student) {
		if (student == null) {
			throw new DbException("No student given");
		}
		String sql = "INSERT INTO student(voornaam, naam, email) VALUES (?,?,?)";
		try (Connection connection = DriverManager.getConnection(url, properties);
				PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
			statement.setString(1, student.getFirstName());
			statement.setString(2, student.getLastName());
			statement.setString(3, student.getEmail());
			statement.executeUpdate();

			ResultSet rs = statement.getGeneratedKeys();
			int key = -1;
			if (rs.next()) {
				// Retrieve the auto generated key(s).
				key = rs.getInt("studentid");
			}

			return key;

		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	public Student get(int studentId) {
		if (studentId < 0) {
			throw new DbException("no studentId given.");
		}
		String sql = "SELECT * from student WHERE studentid = ?";
		try (Connection connection = DriverManager.getConnection(url, properties);
				PreparedStatement statement = connection.prepareStatement(sql);) {
			statement.setInt(1, studentId);
			ResultSet result = statement.executeQuery();
			result.next();
			int id = Integer.parseInt(result.getString("studentid"));
			String voorNaam = result.getString("voornaam");
			String naam = result.getString("naam");
			String email = result.getString("email");

			Student student = new Student(id, voorNaam, naam, email);
			return student;

		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}

	public ArrayList<Student> getAll() {
		ArrayList<Student> studenten = new ArrayList<Student>();
		try (Connection connection = DriverManager.getConnection(url, properties);
				Statement statement = connection.createStatement();) {
			ResultSet result = statement.executeQuery("SELECT * FROM student");

			while (result.next()) {
				int id = Integer.parseInt(result.getString("studentid"));
				String voorNaam = result.getString("voornaam");
				String naam = result.getString("naam");
				String email = result.getString("email");

				Student student = new Student(id, voorNaam, naam, email);

				studenten.add(student);
			}

			return studenten;
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}
	
	
}
