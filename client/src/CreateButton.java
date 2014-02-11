package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class CreateButton extends JButton implements ActionListener {

	
	public CreateButton() {
		super("Create new record");
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		new CreateWindow();
	}

}
