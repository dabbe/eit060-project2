import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import server.Record;

import com.google.gson.Gson;


public class Monitor {
	
	private HospitalConnection c;
	private final int port = 9999;
	private final String host = "localhost";
	
	private Gson gson;

	private Monitor(){
		this.gson = new Gson();
		new GUI(this);
		try {
			c = new HospitalConnection(host, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized List<Record> getRecords(String name){
		return Arrays.asList(gson.fromJson(c.getRecords(), Record[].class));
	}
	
	public synchronized void createRecord(Record record){
		c.createRecord(record);
		
	}
	
	public static void main(String[] args){
		new Monitor();
	}

	public synchronized void closeConnection() {
		c.close();
	}
}
