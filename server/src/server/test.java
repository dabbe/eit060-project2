package server;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

public class test {
	public static void main(String[] args) {
		DatabaseManager dbm;
		Gson gson = new Gson();
		try {
			dbm = new DatabaseManager();
			// dbm.createTable();

//			 Record record = new Record(4, "Kalle", "Nörse", "Dåktor",
//			 "Divishon", "Dataspela");
//			 dbm.updatePatientRecord(record);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
