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
import javax.swing.border.EmptyBorder;

import resources.Record;

public class CreateWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField patientName, nurse, division, data;

	private Monitor monitor;

	public CreateWindow(Monitor monitor) {
		this.monitor = monitor;

		JPanel container = new JPanel();
		container.setLayout(new GridLayout(3, 2));
		container.setBorder(new EmptyBorder(10, 10, 10, 10));

		container.add(new JLabel("Patient name:"));
		patientName = new JTextField();
		container.add(patientName);

		container.add(new JLabel("Nurse:"));
		nurse = new JTextField();
		container.add(nurse);

		container.add(new JLabel("Medical data:      "));
		data = new JTextField();
		container.add(data);

		add(container);
		add(new OKButton(), BorderLayout.SOUTH);

		pack();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);

		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private Record getRecord() {
		String p = patientName.getText();
		String n = nurse.getText();
		String doc = "";
		String div = "";
		String dat = data.getText();
		return new Record(-1, p, n, doc, div, dat);
	}

	public class OKButton extends JButton implements ActionListener {

		private static final long serialVersionUID = 1L;

		public OKButton() {
			super("Create");
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			monitor.createRecord(getRecord());
		}

	}

}
