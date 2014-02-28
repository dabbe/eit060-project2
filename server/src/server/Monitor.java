package server;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import resources.HospitalMember;
import resources.Identity;
import resources.Record;

public class Monitor {

	private DatabaseManager dbm;

	public Monitor() {
		try {
			dbm = new DatabaseManager();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
		String CN = identity.getCN();
		String OU = identity.getOU();

		try {
			if (OU.equals(HospitalMember.GOV)) {
				return dbm.getRecordsOfPatient(identity, patientName);
			} else if (OU.equals(HospitalMember.PATIENT) && CN.equals(patientName)) {
				return dbm.getRecordsOfPatient(identity, patientName);
			} else if (OU.equals(HospitalMember.NURSE) || OU.equals(HospitalMember.DOCTOR)) {
				String division = dbm.getDivisionFromName(CN);
				return dbm.getRecordsOfPatient(identity, patientName, division);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

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

	public synchronized boolean createRecord(Identity identity, Record record) {
		try {
			if (identity.getOU().equals(HospitalMember.DOCTOR)) {
				record.setDoctor(identity.getCN());
				record.setDivision(dbm.getDivisionFromName(identity.getCN()));
				dbm.createRecord(record, identity);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public synchronized boolean updateRecord(Identity identity, Record record) {
		String CN = identity.getCN();
		String OU = identity.getOU();

		try {
			Record dbRecord = dbm.getRecordFromId(record.getId());
			System.out.println("Updating record with id " + record.getId());
			System.out.println("CN: " + CN + " OU: " + OU);
			System.out.println("DB RECORD " + dbRecord.toLongString());
			System.out.println("CLIENT RECORD " + record.toLongString());
			if ((OU.equals(HospitalMember.DOCTOR) && CN.equals(dbRecord.getDoctor())) || (OU.equals(HospitalMember.NURSE) && CN.equals(dbRecord.getNurse()))) {
				
				dbRecord.setData(record.getData());
				System.out.println("Updating");
				dbm.updatePatientRecord(dbRecord, identity);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public synchronized boolean deleteRecord(Identity identity, Record record) {
		String OU = identity.getOU();

		try {
			if (OU.equals(HospitalMember.GOV)) {
				dbm.deleteRecord(record, identity);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}