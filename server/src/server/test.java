package server;

import java.sql.SQLException;
import com.google.gson.Gson;

public class test {
	public static void main(String[] args) {
		DatabaseManager dbm;
		Gson gson = new Gson();
		try {
			dbm = new DatabaseManager();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
