package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

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
}
