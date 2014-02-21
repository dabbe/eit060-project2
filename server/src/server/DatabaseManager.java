package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseManager {

	// public static

	// Metod för att hämta patient
	// Hitta hans id och sen hämta alla hans record

	// Change info (update)

	//

	private Connection c = null;

	public DatabaseManager() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		c = DriverManager.getConnection("jdbc:sqlite:eit060.db");
	}

	public void closeConnection() throws SQLException {
		c.close();
	}

	public void createTable() throws SQLException{
		Statement sta = c.createStatement(); 
	      int count = sta.executeUpdate(
	        "CREATE TABLE records ("
	        + " id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
	        + " patient VARCHAR(255) NOT NULL,"
	        + " nurse VARCHAR(255) NOT NULL,"
	        + " doctor VARCHAR(255) NOT NULL,"
	        + " division VARCHAR(255) NOT NULL,"
	        + " data VARCHAR(8000) NOT NULL)");
	      System.out.println("Table created.");
	      sta.close();        

	}

	public void createRecord(Record record) throws SQLException {
		String query = "INSERT INTO records (patient, nurse, doctor, division, data) VALUES(?,?,?,?,?)";
		PreparedStatement prepStmt = c.prepareStatement(query);
		prepStmt.setString(1, record.getPatient());
		prepStmt.setString(2, record.getNurse());
		prepStmt.setString(3, record.getDoctor());
		prepStmt.setString(4, record.getDivision());
		prepStmt.setString(5, record.getData());
		prepStmt.executeUpdate();
		prepStmt.close();
	}

	public void deleteRecord(int id) throws SQLException {
		String query = "DELETE FROM records WHERE id=?";
		PreparedStatement prepStmt = c.prepareStatement(query);
		prepStmt.setInt(1, id);
		prepStmt.executeUpdate();
		prepStmt.close();
	}
	
	public void updatePatientRecord(Record record) throws SQLException{
		String query = "UPDATE records SET patient=?,nurse=?,doctor=?,division=?,data=? WHERE id=?";
		PreparedStatement prepStmt = c.prepareStatement(query);
		prepStmt.setString(1, record.getPatient());
		prepStmt.setString(2, record.getNurse());
		prepStmt.setString(3, record.getDoctor());
		prepStmt.setString(4, record.getDivision());
		prepStmt.setString(5, record.getData());
		prepStmt.setInt(6, record.getId());
		
		prepStmt.executeUpdate();
		prepStmt.close();
	}

	public ArrayList<Record> getPatientRecords(String name) throws SQLException {
		String query = "SELECT * FROM records WHERE patient=?";
		PreparedStatement prepStmt = c.prepareStatement(query);
		prepStmt.setString(1, name);
		ResultSet rs = prepStmt.executeQuery();

		ArrayList<Record> records = new ArrayList<Record>();

		while (rs.next()) {
			int id = rs.getInt(1);
			String patient = rs.getString(2);
			String nurse = rs.getString(3);
			String doctor = rs.getString(4);
			String division = rs.getString(5);
			String data = rs.getString(6);

			records.add(new Record(id, patient, nurse, doctor, division, data));
		}
		prepStmt.close();

		return records;
	}

}
