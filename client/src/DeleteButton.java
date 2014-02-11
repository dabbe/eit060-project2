package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class DeleteButton extends JButton implements ActionListener {

	public DeleteButton() {
		super("Delete");
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO: delete selected file
		System.out.println("no such file");
	}

}
