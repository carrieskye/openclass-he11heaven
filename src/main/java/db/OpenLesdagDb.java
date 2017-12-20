package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import domain.DomainException;
import domain.OpenClassSession;
import domain.OpenLesDag;

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
			ResultSet result = statement.executeQuery( "SELECT * FROM openlesdag WHERE opleiding = "+ opleiding +"" );
			// als er openlesdagen zijn voor deze opleiding:
			if (result.isBeforeFirst()) {
				// alle openlesdagen ophalen voor die opleiding
				while (result.next()) {
					int id = result.getInt("id");
					String titel = result.getString("titel");
					String locatie = result.getString("locatie");
					LocalDate datum = result.getDate("datum").toLocalDate();
					
					OpenLesDag lesdag = new OpenLesDag(id, titel, locatie, datum);
					lesdag.addAllSessies(getSessies(id));
					lesdagen.add(lesdag);
				}
				return lesdagen;
			}
			else {
				return null;
			}

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
				LocalTime begin = result.getTimestamp("begin").toLocalDateTime().toLocalTime();
				LocalTime einde = result.getTimestamp("einde").toLocalDateTime().toLocalTime();
				int sessieid = result.getInt("sessieid");
				int max_inschrijvingen = result.getInt("max_inschrijvingen");
				String klaslokaal = result.getString("klaslokaal");
				
				OpenClassSession sessie = new OpenClassSession(sessieid, naam, beschrijving, begin, einde, max_inschrijvingen, klaslokaal);
				sessies.add(sessie);
			}
			
			return sessies;
		} 
		catch (SQLException e) {
			throw new DbException();
		}
		
	}
}
