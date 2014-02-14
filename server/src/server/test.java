package server;

import java.sql.SQLException;
import java.util.ArrayList;

public class test {
	public static void main(String[] args) {
		DatabaseManager dbm;
		try {
			dbm = new DatabaseManager();
//dbm.createTable();
			dbm.createRecord("Alfred", "nurseman", "dr dre", "C22", "Har ont som fan i halsen");
			dbm.createRecord("Alfred", "nurseman2", "dr jallan", "C52", "Kan inte spela data");
			dbm.createRecord("Alfred", "nurseman2", "dj broder", "C32", "Vill inte äta");
			
			ArrayList<Record> records = dbm.getPatientRecords("Alfred");
			System.out.println(records.size());
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
