import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class SaveButton extends JButton implements ActionListener {

	public SaveButton() {
		super("Save");
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println();
		// TODO: if changes are made and authority is ok, save file to database
	}

}
