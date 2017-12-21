package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import domain.Afdeling;
import domain.DomainException;
import domain.Opleiding;

public class AfdelingDb {
	String url = "jdbc:postgresql://databanken.ucll.be:51718/hakkaton?currentSchema=he11heaven";
	String user = "hakkaton_11";
	Properties properties;
	public AfdelingDb() throws ClassNotFoundException, SQLException {
		String p = "IeS5nahweitohwaa";
		Properties properties = new Properties();
		properties.setProperty("user", user);
		properties.setProperty("password", p);
		properties.setProperty("ssl", "true");
		properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
		Class.forName("org.postgresql.Driver");
		this.properties = properties;
	}
	
	public List<Afdeling> getAfdelingen(){
		try(
			Connection connection = DriverManager.getConnection(url, properties);	
			Statement statement = connection.createStatement();
		) {
			ArrayList<Afdeling> afdelingen = new ArrayList<>();
			ResultSet result = statement.executeQuery( "SELECT * FROM afdeling" );
			while (result.next()) {
				String naam = result.getString("naam");
				Afdeling afdeling = new Afdeling(naam);
				List<Opleiding> opleidingen = getOpleidingen(naam);
				afdeling.setOpleidingen(opleidingen);
				afdelingen.add(afdeling);
			}
			return afdelingen;
		}catch (SQLException e) {
			throw new DomainException(e.getMessage());
		}
	}
	
	public List<Opleiding> getOpleidingen(String afdeling){
		String query = "SELECT * FROM opleiding WHERE afdeling = ?";
		try(
				Connection connection = DriverManager.getConnection(url, properties);	
				PreparedStatement statement = connection.prepareStatement(query);
			) {
				statement.setString(1, afdeling);
				ResultSet result = statement.executeQuery();
				
				ArrayList<Opleiding> opleidingen = new ArrayList<>();
				while (result.next()) {
					String naam = result.getString("naam");
					int id = result.getInt("id");
					Opleiding opleiding = new Opleiding(naam, id);
					opleidingen.add(opleiding);
				}
				return opleidingen;
			}catch (SQLException e) {
				throw new DomainException(e.getMessage());
			}
	}
}


