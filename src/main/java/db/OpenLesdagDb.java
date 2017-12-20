package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import domain.DomainException;
import domain.OpenLesDag;
import domain.Opleiding;

public class OpenLesdagDb {
	String url = "jdbc:postgresql://databanken.ucll.be:51718/hakkaton?currentSchema=he11heaven";
	String user = "hakkaton_11";
	Properties properties;
	public OpenLesdagDb() throws ClassNotFoundException, SQLException {
		String p = "IeS5nahweitohwaa";
		Properties properties = new Properties();
		properties.setProperty("user", user);
		properties.setProperty("password", p);
		properties.setProperty("ssl", "true");
		properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
		Class.forName("org.postgresql.Driver");
		this.properties = properties;
	}
	
	public List<OpenLesDag> getLesdagen(String opleiding){
		try(
			Connection connection = DriverManager.getConnection(url, properties);	
			Statement statement = connection.createStatement();
		) {
			ArrayList<OpenLesDag> lesdagen = new ArrayList<>();
			ResultSet result = statement.executeQuery( "SELECT * FROM openlesdag WHERE opleiding = '"+ opleiding +"'" );
			while (result.next()) {
				Date date = result.getDate("datum");
				
				//OpenLesDag lesdag = new OpenLesDag(date);
				//lesdagen.add(lesdag);
			}
			return lesdagen;
		}catch (SQLException e) {
			throw new DomainException(e.getMessage());
		}
	}
}
