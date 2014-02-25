package server;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import resources.HospitalMember;

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
		String logString = "Created new record for patient " + record.getPatient() + " with nurse " + record.getNurse(); 
		log(HospitalMember.DOCTOR, record.getDoctor(), logString);
		
	}

	public void deleteRecord(Record record) throws SQLException {
		String query = "DELETE FROM records WHERE id=?";
		PreparedStatement prepStmt = c.prepareStatement(query);
		prepStmt.setInt(1, record.getId());
		prepStmt.executeUpdate();
		prepStmt.close();
		String logString = "Deleted records with ID " + record.getId();
		log(HospitalMember.DOCTOR, record.getDoctor(), logString);
	}
	
	public void updatePatientRecord(Record record, String OU, String CN) throws SQLException{
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
		String logString = "Updated record for patient " + record.getPatient();
		log(OU, CN, logString);
	}

	public ArrayList<Record> getRecordWithPatient(String patient) throws SQLException {
		String query = "SELECT * FROM records WHERE patient=?";
		return getRecordFromName(query, patient);
	}
	
	public ArrayList<Record> getRecordsWithNurse(String nurse) throws SQLException {
		String query = "SELECT * FROM records WHERE nurse=?";
		return getRecordFromName(query, nurse);
	}
	public ArrayList<Record> getRecordsWithDoctor(String doctor) throws SQLException {
		String query = "SELECT * FROM records WHERE doctor=?";
		return getRecordFromName(query, doctor);
	}
	
	private ArrayList<Record> getRecordFromName(String query, String name) throws SQLException {
		PreparedStatement prepStmt = c.prepareStatement(query);
		prepStmt.setString(1, name);
		ResultSet rs = prepStmt.executeQuery();

		ArrayList<Record> records = new ArrayList<Record>();

		while (rs.next()) {
			int id = rs.getInt(1);
			String pat = rs.getString(2);
			String nur = rs.getString(3);
			String doc = rs.getString(4);
			String div = rs.getString(5);
			String data = rs.getString(6);

			records.add(new Record(id, pat, nur, doc, div, data));
		}
		prepStmt.close();

		return records;
	}
	
	public void log(String title, String name, String action) throws SQLException
	{
		String query = "INSERT INTO log (title, name, time, action) VALUES(?,?,?,?)";
		PreparedStatement prepStmt = c.prepareStatement(query);
		prepStmt.setString(1, title);
		prepStmt.setString(2, name);
		prepStmt.setLong(3, System.currentTimeMillis());
		prepStmt.setString(4, action);
		prepStmt.executeUpdate();
		prepStmt.close();
		
	}
}
