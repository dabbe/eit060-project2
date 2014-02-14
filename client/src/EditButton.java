
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class EditButton extends JButton implements ActionListener {

	public EditButton() {
		super("Edit");
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO: server request for selected file
		System.out.println("Your level of clearance is not sufficient for this action.");
	}

}
