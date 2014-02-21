package server;

import java.sql.SQLException;

public class test {
	public static void main(String[] args) {
		DatabaseManager dbm;
		try {
			dbm = new DatabaseManager();
			//dbm.createTable();
	
//			Record record = new Record(3, "Kalle", "Nörse", "Dåktor", "Divishon", "Dataspela");
//			dbm.updatePatientRecord(record);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
