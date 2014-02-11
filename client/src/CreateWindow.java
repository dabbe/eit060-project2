package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateWindow extends JFrame {
	
	private JTextField patientName, division, nurse, data;

	public CreateWindow() {
		
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(4, 2));
		
		container.add(new JLabel("Patient name:"));
		patientName = new JTextField();
		container.add(patientName);
		
		container.add(new JLabel("Division:"));
		division = new JTextField();
		container.add(division);

		container.add(new JLabel("Nurse:"));
		nurse = new JTextField();
		container.add(nurse);
		
		container.add(new JLabel("Medical data:"));
		data = new JTextField();
		container.add(data);
		
		
		add(container);
		add(new OKButton(), BorderLayout.SOUTH);

		pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2
				- getSize().height / 2);
		
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

	}
	
	public class OKButton extends JButton implements ActionListener {
		
		public OKButton() {
			super("Create");
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(patientName.getText());
		}
		
	}

}
