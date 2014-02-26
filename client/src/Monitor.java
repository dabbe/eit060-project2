import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import server.Record;

import com.google.gson.Gson;


public class Monitor {
	
	private HospitalConnection c;
	private final int port = 9999;
	private final String host = "localhost";
	
	private Gson gson;

	private Monitor(){
		this.gson = new Gson();
		try {
			c = new HospitalConnection(host, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		createLoginWindow();
//		new GUI(this, c);
	}
	
	private void createLoginWindow(){
		JFrame frame = new JFrame();
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		frame.add(panel);
		
		JLabel label = new JLabel();
		label.setText("<HTML>Welcome " + c.getCN() + "<BR>Please enter your password:</HTML>");
		panel.add(label);
		
		JTextField textField = new JTextField(12);
		panel.add(textField);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
	}
	
	public synchronized List<Record> getRecordsOfPatient(String patientName){
		return Arrays.asList(gson.fromJson(c.getRecordsOfPatient(patientName), Record[].class));
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
