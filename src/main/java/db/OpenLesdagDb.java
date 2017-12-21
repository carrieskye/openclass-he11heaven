package db;

import java.sql.Connection;
import java.sql.Date;
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

	public List<OpenLesDag> getLesdagen(int opleiding){
		String query = "SELECT * FROM openlesdag WHERE opleiding = ?";
		try(
			Connection connection = DriverManager.getConnection(url, properties);	
			PreparedStatement statement = connection.prepareStatement(query);
		) {
			statement.setInt(1, opleiding);
			ArrayList<OpenLesDag> lesdagen = new ArrayList<>();
			ResultSet result = statement.executeQuery();

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
			} else {
				return null;
			}

		} catch (SQLException e) {
			throw new DomainException(e.getMessage());
		}
	}

	private List<OpenClassSession> getSessies(int openlesdagid) {
		List<OpenClassSession> sessies = new ArrayList<>();
		String query = "SELECT * FROM sessie WHERE openlesdagid = ?";

		try (Connection connection = DriverManager.getConnection(url, properties);
				PreparedStatement statement = connection.prepareStatement(query);) {
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

				OpenClassSession sessie = new OpenClassSession(sessieid, naam, beschrijving, begin, einde,
						max_inschrijvingen, klaslokaal);
				sessies.add(sessie);
			}

			return sessies;
		} catch (SQLException e) {
			throw new DbException();
		}
	}

	public void addOpenDay(OpenLesDag openDay) {

		String sql = "INSERT into openlesdag (datum, opleiding, titel, locatie) VALUES (?,?,?,?)";

		try (Connection connection = DriverManager.getConnection(url, properties);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setDate(1, Date.valueOf((openDay.getDatum())));
			statement.setInt(2, openDay.getOpleidingID());

			statement.setString(3, openDay.getTitel());
			statement.setString(4, openDay.getLocatie());

			statement.executeUpdate();
			connection.close();

		} catch (Exception e) {
			System.out.println("werkt niet: " + e.getMessage());
		}
	}

	public int getOpenlesdagID(String date, int opleidingID) {
		int id = -1;

		try (Connection connection = DriverManager.getConnection(url, properties);
			Statement statement = connection.createStatement();) 
		{
			ResultSet aantal = statement.executeQuery(
					"SELECT COUNT (*) FROM openlesdag WHERE opleiding =" + opleidingID + "AND datum = '" + date + "'");
			boolean checkaantal = false;
			if (aantal.next()) {
				if (aantal.getInt(1) >0) {
					checkaantal = true;
				}
			}
			ResultSet result = statement.executeQuery(
					"SELECT * FROM openlesdag WHERE opleiding =" + opleidingID + "AND datum = '" + date + "'");
			if (checkaantal) {
				if (result.next()) {
					id = result.getInt("id");
				}
			}

		} catch (Exception e) {
			System.out.println("getopenlesdagid werkt niet: " + e.getMessage());
		}

		return id;
	}
	
	public OpenLesDag getOpenlesdagVanSessie(int sessieId) throws SQLException {
		String query = "SELECT O.id FROM sessie S INNER JOIN openlesdag O ON (S.openlesdagid = O.id)  WHERE S.sessieid = ?";
		
		try(
			Connection connection = DriverManager.getConnection(url, properties);	
			PreparedStatement statement = connection.prepareStatement(query);
		) {
			statement.setInt(1, sessieId);
			ResultSet result = statement.executeQuery();
			
			while (result.next()) {
				int openlesdagId = result.getInt("id");
				return getOpenlesdag(openlesdagId);
			}
			return null;
		}
	}
		
	public OpenLesDag getOpenlesdag(int openlesdagId) throws SQLException {
		String query = "SELECT * FROM openlesdag WHERE id = ?"; 
		
		try(
			Connection connection = DriverManager.getConnection(url, properties);	
			PreparedStatement statement = connection.prepareStatement(query);
		) {
			statement.setInt(1, openlesdagId);
			OpenLesDag lesdag = null;
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				int id = result.getInt("id");
				String titel = result.getString("titel");
				String locatie = result.getString("locatie");
				LocalDate datum = result.getDate("datum").toLocalDate();
				int opleidingId = result.getInt("opleiding");
				lesdag = new OpenLesDag(id, titel, locatie, datum);
				lesdag.setOpleidingID(opleidingId);
			}
			return lesdag;
		}

	}
}
