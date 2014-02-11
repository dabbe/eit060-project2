package client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class SearchField extends JTextField implements ActionListener {

	public SearchField() {
		setPreferredSize(new Dimension(300, 20));
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println(getText());
		// TODO: send request to server based on text
	}

}
