package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import client.Client;


public class CreateButton extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Client monitor;

	public CreateButton(Client monitor) {
		super("Create new record");
		this.monitor = monitor;
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		new CreateWindow(monitor);
	}
}
