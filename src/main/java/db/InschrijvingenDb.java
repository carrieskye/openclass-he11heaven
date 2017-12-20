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
		if(sessieId == 0) 
			throw new DbException("no session given.");
		String sql = "INSERT INTO inschrijving(studentid, sessieid) "
				+ "VALUES (?,?)";
		try (Connection connection = DriverManager.getConnection(url, properties);
				PreparedStatement statement = connection.prepareStatement(sql);
		) {
			statement.setInt(1, student.getId());
			statement.setInt(2, sessieId);
			statement.executeUpdate();
		}catch(SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}
	
	public ArrayList<Student> get(int sessieId){
		if (sessieId < 0) {
			throw new DbException("no sessionId given.");
		}
		try (Connection connection = DriverManager.getConnection(url, properties);
				Statement statement = connection.createStatement();) {
			ResultSet result = statement.executeQuery("SELECT * FROM inschrijving WHERE sessieid = " + sessieId);
			ArrayList<Student> students = new ArrayList<Student>();
			while (result.next()) {
				students.add(studentDb.get(result.getInt("studentid")));
			}
			return students;
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}
	}
}
