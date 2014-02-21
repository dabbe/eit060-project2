import java.awt.Insets;

import javax.swing.JTextArea;

public class Header extends JTextArea implements Observer {

	private static final long serialVersionUID = 1L;

	public Header() {
		setEditable(false);
		setMargin(new Insets(2, 4, 2, 4));
		setText("Name:\nDoctor:\nNurse:\nDistrict:");
	}

	@Override
	public void update(ListEntry le) {
		String[] temp = le.getHeader().split("\n");
		if (temp.length < 3) System.out.println("Need 3 lines in header");
		else setText("Name: " + le.toString() + "\nDoctor: " + temp[0]
				+ "\nNurse: " + temp[1] + "\nDistrict: " + temp[2]);
	}

}
