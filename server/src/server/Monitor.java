package server;

import java.sql.SQLException;
import java.util.ArrayList;

import resources.HospitalMember;
import resources.Identity;

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
	public synchronized ArrayList<Record> getRecords(Identity identity) {
		try {
			if(identity.getOU().equals(HospitalMember.DOCTOR)) {
				return dbm.getRecordsWithDoctor(identity);
			} else if(identity.getOU().equals(HospitalMember.NURSE)) {
				return dbm.getRecordsWithNurse(identity);
			} else if(identity.getOU().equals(HospitalMember.PATIENT)) {
				return dbm.getRecordWithPatient(identity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean createRecord(Identity identity, Record record) {
		try {
			if(identity.getOU().equals(HospitalMember.DOCTOR)) {
				record.setDoctor(identity.getCN());
				dbm.createRecord(record, identity);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
