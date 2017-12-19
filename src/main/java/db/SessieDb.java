package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Properties;

import domain.OpenClassSession;

public class SessieDb {

	Properties properties = new Properties();
	String url = "jdbc:postgresql://databanken.ucll.be:51718/hakkaton?currentSchema=he11heaven";

	public SessieDb() {
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
				PreparedStatement statement = connection.prepareStatement(sql);
		) {
			statement.setInt(1, sessieId);
			ResultSet result = statement.executeQuery();
			result.next();
			int sessionId = Integer.parseInt(result.getString("sessieid"));
			String title = result.getString("naam");
			String description = result.getString("beschrijving");
			String begintijd = result.getString("begin");
			String eindtijd = result.getString("einde");
			int maxInschrijvingen = Integer.parseInt(result.getString("max_inschrijvingen"));

			LocalDateTime begin = LocalDateTime.of(2020, 1, 1, Integer.parseInt(begintijd.substring(0, 2)),
					Integer.parseInt(begintijd.substring(3, 5)));
			LocalDateTime einde = LocalDateTime.of(2020, 1, 1, Integer.parseInt(eindtijd.substring(0, 2)),
					Integer.parseInt(eindtijd.substring(3, 5)));

			OpenClassSession sessie = new OpenClassSession(sessionId, title, description, begin, einde,
					maxInschrijvingen);
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
				String begintijd = result.getString("begin");
				String eindtijd = result.getString("einde");
				int maxInschrijvingen = Integer.parseInt(result.getString("max_inschrijvingen"));

				LocalDateTime begin = LocalDateTime.of(2020, 1, 1, Integer.parseInt(begintijd.substring(0, 2)),
						Integer.parseInt(begintijd.substring(3, 5)));
				LocalDateTime einde = LocalDateTime.of(2020, 1, 1, Integer.parseInt(eindtijd.substring(0, 2)),
						Integer.parseInt(eindtijd.substring(3, 5)));

				OpenClassSession sessie = new OpenClassSession(sessionId, title, description, begin, einde,
						maxInschrijvingen);
				sessies.add(sessie);
			}

			return sessies;
		} catch (SQLException e) {
			throw new DbException(e.getMessage(), e);
		}

	}

}
