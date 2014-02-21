import java.awt.Insets;

import javax.swing.JTextArea;

import server.Record;

public class Header extends JTextArea implements Observer {

	private static final long serialVersionUID = 1L;

	public Header() {
		setEditable(false);
		setMargin(new Insets(2, 4, 2, 4));
		setText("Name:\nDoctor:\nNurse:\nDistrict:");
	}

	@Override
	public void update(Record record) {
		setText("Name: " + record.getPatient() + "\nDoctor: " + record.getDoctor() + "\nNurse: " + record.getNurse()
				+ "\nDivision: " + record.getDivision());
	}

}
