package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import domain.DomainException;
import domain.OpenClassSession;
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
			// voor elke openlesdag van die opleiding
			while (result.next()) {
				int id = result.getInt("id");
				int opleidingid = result.getInt("opleiding");
				LocalDateTime begin = (LocalDateTime) result.getObject("begin");
				LocalDateTime einde = (LocalDateTime) result.getObject("einde");
				String titel = result.getString("titel");
				String locatie = result.getString("locatie");
				
				OpenLesDag lesdag = new OpenLesDag(id, titel, locatie, begin, einde);
				lesdag.setSessies(getSessies(id));
				lesdagen.add(lesdag);
			}
			return lesdagen;
		}catch (SQLException e) {
			throw new DomainException(e.getMessage());
		}
	}
	
	private List<OpenClassSession> getSessies(int openlesdagid) {
		List<OpenClassSession> sessies = new ArrayList<>();
		String query = "SELECT * FROM sessie WHERE openlesdagid = ?";
		
		try(
			Connection connection = DriverManager.getConnection(url, properties);	
			PreparedStatement statement = connection.prepareStatement(query);
		) {
			statement.setInt(1, openlesdagid);
			ResultSet result = statement.executeQuery();
			
			while (result.next()) {
				String naam = result.getString("naam");
				String beschrijving = result.getString("beschrijving");
				LocalDateTime begin = (LocalDateTime) result.getObject("begin");
				LocalDateTime einde = (LocalDateTime) result.getObject("einde");
				int sessieid = result.getInt("sessieid");
				int max_inschrijvingen = result.getInt("max_inschrijvingen");
				String klaslokaal = result.getString("klaslokaal");
				
				OpenClassSession sessie = new OpenClassSession(sessieid, naam, beschrijving, begin, einde, max_inschrijvingen, openlesdagid);
				sessies.add(sessie);
			}
			
			return sessies;
		} 
		catch (SQLException e) {
			throw new DbException();
		}
		
	}
}
