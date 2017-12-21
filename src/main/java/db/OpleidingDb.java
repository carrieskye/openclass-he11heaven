package db;

import java.sql.Connection;
import java.sql.DriverManager;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import domain.Afdeling;
import domain.DomainException;
import domain.OpenLesDag;
import domain.Opleiding;

public class OpleidingDb {
	
	
	String url = "jdbc:postgresql://databanken.ucll.be:51718/hakkaton?currentSchema=he11heaven";
	String user = "hakkaton_11";
	Properties properties;
	public OpleidingDb() throws ClassNotFoundException, SQLException {
		String p = "IeS5nahweitohwaa";
		Properties properties = new Properties();
		properties.setProperty("user", user);
		properties.setProperty("password", p);
		properties.setProperty("ssl", "true");
		properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
		Class.forName("org.postgresql.Driver");
		this.properties = properties;
	}
	
	public String getOpleiding(int opleidingId) {
		try(
				Connection connection = DriverManager.getConnection(url, properties);	
				Statement statement = connection.createStatement();
			) {
			String sql = "Select * from opleiding where id= " + opleidingId;
			ResultSet rs = statement.executeQuery(sql);
			
			if(rs.next()) {
				return rs.getString("naam");
			}else {
				throw new DbException("opleiding not found");
			}
			
			
			
		}catch(Exception e) {
			throw new DbException("fout");
		}
	}
	public List<Opleiding> getOpleidingen(){
		try(
			Connection connection = DriverManager.getConnection(url, properties);	
			Statement statement = connection.createStatement();
		) {
			ArrayList<Opleiding> opleidingen = new ArrayList<>();
			ResultSet result = statement.executeQuery( "SELECT * FROM opleiding" );
			while (result.next()) {
				String naam = result.getString("naam");
				int id = result.getInt("id");
				Opleiding opleiding = new Opleiding( naam, id);
				ArrayList<OpenLesDag> openlesdagen = this.getOpenLesDagen(id);
				opleiding.setOpenLesDagen(openlesdagen);
				opleidingen.add(opleiding);
			}
			return opleidingen;
		}catch (SQLException e) {
			throw new DomainException(e.getMessage());
		}
	}

	public ArrayList<OpenLesDag> getOpenLesDagen(int opleiding) {
		String query = "SELECT * FROM openlesdag WHERE opleiding = ?";
		try (Connection connection = DriverManager.getConnection(url, properties);
				PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setInt(1, opleiding);
			ArrayList<OpenLesDag> openlesdagen = new ArrayList<>();
			ResultSet result = statement.executeQuery();
			while (result.next()) {
				String titel = result.getString("titel");
				String locatie = result.getString("locatie");
				LocalDate datum = result.getTimestamp("datum").toLocalDateTime().toLocalDate();
				int id = result.getInt("id");
				OpenLesDag openlesdag = new OpenLesDag(id, titel, locatie, datum);
				openlesdagen.add(openlesdag);
			}
			return openlesdagen;
		} catch (SQLException e) {
			throw new DomainException(e.getMessage());
		}
	}
}
