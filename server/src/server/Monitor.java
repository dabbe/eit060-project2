package server;

import java.sql.SQLException;
import java.util.ArrayList;

import resources.HospitalMember;

public class Monitor {

	private DatabaseManager dbm;

	public Monitor() {
		try {
			dbm = new DatabaseManager();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public synchronized void closeConnection() {
		try {
			dbm.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// TODO: Gov?
	public synchronized ArrayList<Record> getRecords(String CN, String OU) {
		System.out.println(CN + " " + OU);
		try {
			if(OU.equals(HospitalMember.DOCTOR)) {
				return dbm.getRecordsWithDoctor(CN);
			} else if(OU.equals(HospitalMember.NURSE)) {
				return dbm.getRecordsWithNurse(CN);
			} else if(OU.equals(HospitalMember.PATIENT)) {
				return dbm.getRecordWithPatient(CN);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean createRecord(String CN, String OU, Record record) {
		try {
			if(OU.equals(HospitalMember.DOCTOR)) {
				record.setDoctor(CN);
				dbm.createRecord(record);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
