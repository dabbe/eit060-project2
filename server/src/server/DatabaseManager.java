package server;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import resources.Identity;
import resources.Record;

public class DatabaseManager {

	private Connection c = null;

	public DatabaseManager() throws ClassNotFoundException, SQLException, IOException {
		Class.forName("org.sqlite.JDBC");	
		c = DriverManager.getConnection("jdbc:sqlite:" + new File(new File(".").getAbsolutePath()).getCanonicalPath() + "/Desktop/EIT060/eit060.db");
	}

	public void closeConnection() throws SQLException {
		c.close();
	}

	public void createRecord(Record record, Identity identity) throws SQLException {
		String query = "INSERT INTO records (patient, nurse, doctor, division, data) VALUES(?,?,?,?,?)";
		PreparedStatement prepStmt = c.prepareStatement(query);
		prepStmt.setString(1, record.getPatient());
		prepStmt.setString(2, record.getNurse());
		prepStmt.setString(3, record.getDoctor());
		prepStmt.setString(4, getDivisionFromName(identity.getCN()));
		prepStmt.setString(5, record.getData());
		prepStmt.executeUpdate();
		prepStmt.close();
		String logString = "Created new record for patient " + record.getPatient() + " with nurse " + record.getNurse(); 
		log(identity, logString);
		
	}
	
	public void deleteRecord(Record record, Identity identity) throws SQLException {
		String query = "DELETE FROM records WHERE id=?";
		PreparedStatement prepStmt = c.prepareStatement(query);
		prepStmt.setInt(1, record.getId());
		prepStmt.executeUpdate();
		prepStmt.close();
		String logString = "Deleted record with ID " + record.getId();
		log(identity, logString);
	}
	
	public void updatePatientRecord(Record record, Identity identity) throws SQLException{
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
		log(identity, logString);
	}
	
	public ArrayList<Record> getRecordsOfPatient(Identity identity, String patientName) throws SQLException {
		String query = "SELECT * FROM records WHERE patient=?";
		
		String logString = "Recieved all records associated with patient: " + patientName;
		log(identity, logString);
		return getRecordFromName(query, patientName);
	}
	
	public ArrayList<Record> getRecordsOfPatient(Identity identity, String patientName, String division) throws SQLException {
		String query = "SELECT * FROM records WHERE patient=? AND division=?";
		
		String logString = "Recieved all records associated with patient: " + patientName;
		log(identity, logString);
		return getRecordFromName(query, patientName, division);
	}

	public ArrayList<Record> getRecordWithPatient(Identity identity) throws SQLException {
		String query = "SELECT * FROM records WHERE patient=?";
		
		String logString = "Recieved all records associated with patient: " + identity.getCN();
		log(identity, logString);
		return getRecordFromName(query, identity.getCN());
	}
	
	public ArrayList<Record> getRecordsWithNurse(Identity identity) throws SQLException {
		String query = "SELECT * FROM records WHERE nurse=?";
		String logString = "Recieved all records associated with nurse: " + identity.getCN();
		log(identity, logString);
		return getRecordFromName(query, identity.getCN());
	}
	public ArrayList<Record> getRecordsWithDoctor(Identity identity) throws SQLException {
		String query = "SELECT * FROM records WHERE doctor=?";
		String logString = "Recieved all records associated with doctor: " + identity.getCN();
		log(identity, logString);
		return getRecordFromName(query, identity.getCN());
	}
	public String getDivisionFromName(String name) throws SQLException {
		String query = "SELECT * FROM divisions WHERE name=?";
		PreparedStatement prepStmt = c.prepareStatement(query);
		prepStmt.setString(1, name);
		ResultSet rs = prepStmt.executeQuery();
		
		String div = "";
		if (rs.next()) {
			div = rs.getString(2);
		}
		return div;
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
	
	private ArrayList<Record> getRecordFromName(String query, String name, String division) throws SQLException {
		PreparedStatement prepStmt = c.prepareStatement(query);
		prepStmt.setString(1, name);
		prepStmt.setString(2, division);
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
	
	private void log(Identity identity, String action) throws SQLException
	{
		String query = "INSERT INTO log (title, name, time, action) VALUES(?,?,?,?)";
		PreparedStatement prepStmt = c.prepareStatement(query);
		prepStmt.setString(1, identity.getOU());
		prepStmt.setString(2, identity.getCN());
		prepStmt.setLong(3, System.currentTimeMillis());
		prepStmt.setString(4, action);
		prepStmt.executeUpdate();
		prepStmt.close();
		
	}
	
}
