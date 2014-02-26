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
	
	public synchronized ArrayList<Record> getRecordsOfPatient(Identity identity, String patientName) {
		try {
			//access control här
			return dbm.getRecordsOfPatient(identity, patientName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// TODO: Gov?
	public synchronized ArrayList<Record> getRecords(Identity identity) {
		try {
			if (identity.getOU().equals(HospitalMember.DOCTOR)) {
				return dbm.getRecordsWithDoctor(identity);
			} else if (identity.getOU().equals(HospitalMember.NURSE)) {
				return dbm.getRecordsWithNurse(identity);
			} else if (identity.getOU().equals(HospitalMember.PATIENT)) {
				return dbm.getRecordWithPatient(identity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean createRecord(Identity identity, Record record) {
		try {
			if (identity.getOU().equals(HospitalMember.DOCTOR)) {
				record.setDoctor(identity.getCN());
				dbm.createRecord(record, identity);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void updateRecord(Identity identity, Record record) {
		String CN = identity.getCN();
		String OU = identity.getOU();

		try {
			if ((OU.equals(HospitalMember.DOCTOR) && CN.equals(record.getDoctor())) || (OU.equals(HospitalMember.NURSE) && CN.equals(record.getNurse()))) {
				dbm.updatePatientRecord(record, identity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteRecord(Identity identity, Record record) {
		String CN = identity.getCN();
		String OU = identity.getOU();

		try {
			if ((OU.equals(HospitalMember.DOCTOR) && CN.equals(record.getDoctor())) || (OU.equals(HospitalMember.NURSE) && CN.equals(record.getNurse()))
					|| OU.equals(HospitalMember.GOV)) {
				dbm.deleteRecord(record, identity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
