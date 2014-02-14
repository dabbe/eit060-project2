package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBUtil {

	// public static

	// Metod för att hämta patient
	// Hitta hans id och sen hämta alla hans record

	// Change info (update)

	//

	private Connection c = null;

	public DBUtil() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		c = DriverManager.getConnection("jdbc:sqlite:eit060.db");
	}
	
	public void closeConnection() throws SQLException{
		c.close();
	}

	public void createRecord(String patientName, String nurseName, String doctorName, String division, String medicalData) throws SQLException {
		String query = "INSERT INTO records VALUES(?,?,?,?,?)";
		PreparedStatement prepStmt = c.prepareStatement(query);
		prepStmt.setString(1, patientName);
		prepStmt.setString(2, nurseName);
		prepStmt.setString(3, doctorName);
		prepStmt.setString(4, division);
		prepStmt.setString(5, medicalData);
		prepStmt.executeUpdate();
		prepStmt.close();
	}

	public void deleteRecord(int id) throws SQLException {
		String query = "DELETE FROM records WHERE id='?'";
		PreparedStatement prepStmt = c.prepareStatement(query);
		prepStmt.setInt(1, id);
		prepStmt.executeUpdate();
		prepStmt.close();
	}
	
	public void getPatient(String name) throws SQLException{
		String query = "SELECT * FROM records WHERE name='?'";
		PreparedStatement prepStmt = c.prepareStatement(query);
		prepStmt.setString(1, name);
		ResultSet rs = prepStmt.executeQuery();
		
		ArrayList<Record> records = new ArrayList<Record>();
	
		while(rs.next()){
		}
		
		prepStmt.close();		
	}
	
	
	
}
