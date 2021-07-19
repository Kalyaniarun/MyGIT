package tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

public class JDBC {

	public static Logger log = LogManager.getLogger(JDBC.class.getName());

	@Test
	public void connectionToDB() throws SQLException {
		String host = "localhost";
		String port = "3306";
		Connection con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/qadbt", "root",
				"mysql123");
		Statement s = con.createStatement();
		ResultSet rs = s.executeQuery("select * from employeeInfo where name='Paul';");
		while (rs.next()) {
			log.info(rs.getString("Location"));
		}
	}

}
