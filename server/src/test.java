import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class test {
	public static void main(String[] args) {

		Connection c = null;
		Statement stmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:eit060.db");
			stmt = c.createStatement();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		System.out.println("Opened database successfully");

		/*
		 * ResultSet rs; try { DatabaseMetaData md = c.getMetaData(); rs =
		 * md.getTables(null, null, "%", null); while (rs.next()) {
		 * System.out.println(rs.getString(3)); }
		 * 
		 * } catch (SQLException e) { e.printStackTrace(); }
		 */

		String query = "CREATE TABLE records (id int AUTO_INCREMENT, patient_name varchar(255), nurse_name varchar(255), doctor_name varchar(255), division varchar(255), data varchar(8000), PRIMARY KEY (id));";

		try {
			stmt.executeUpdate(query);
			stmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// PreparedStatement prepStmt = c.prepareStatement("SELECT * from ?");

	}
}
