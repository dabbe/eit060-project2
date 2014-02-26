import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import server.Record;

import com.google.gson.Gson;

public class Monitor {

	private HospitalConnection c;
	private final int port = 9999;
	private final String host = "localhost";
	private Gson gson;

	private JPasswordField textField;
	private JLabel label;
	private JFrame frame;

	private ActionListener listener = new ActionListener() {

		private int incorrect = 0;

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				c = new HospitalConnection(host, port, textField.getPassword());
				new GUI(Monitor.this, c);
				frame.dispose();
			} catch (IOException ex) {
				incorrect++;
				label.setText("<HTML><CENTER>Welcome to DataJournal.<BR>Please enter your password:<BR><font color='red'>Incorrect password! (" + incorrect
						+ ")</font></CENTER></HTML>");
				frame.pack();

				if (incorrect >= 5) {
					frame.dispose();
					JOptionPane.showMessageDialog(null, "Entered incorrect password too many times!");
					System.exit(1);
				}
			}
		}
	};

	private Monitor() {
		this.gson = new Gson();
		createLoginWindow();
	}

	private void createLoginWindow() {
		frame = new JFrame();
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));
		frame.add(panel);

		label = new JLabel();
		label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		label.setText("<HTML><CENTER>Welcome to DataJournal.<BR>Please enter your password:</CENTER></HTML>");
		panel.add(label, BorderLayout.NORTH);

		textField = new JPasswordField(12);
		panel.add(textField, BorderLayout.CENTER);
		textField.addActionListener(listener);

		frame.pack();

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public synchronized List<Record> getRecordsOfPatient(String patientName) {
		return Arrays.asList(gson.fromJson(c.getRecordsOfPatient(patientName), Record[].class));
	}

	public synchronized void createRecord(Record record) {
		c.createRecord(record);

	}

	public static void main(String[] args) {
		new Monitor();
	}

	public synchronized void closeConnection() {
		c.close();
	}
}
