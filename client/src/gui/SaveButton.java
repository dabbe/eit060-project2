package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import client.Client;
import resources.Record;

public class SaveButton extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextArea textArea;
	private Client monitor;
	private RecordList list;

	public SaveButton(JTextArea textArea, Client client, RecordList list) {
		super("Save");
		this.textArea = textArea;
		this.monitor = client;
		this.list = list;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Record record = list.getCurrentSelected();
		if(record == null) return;

		try {
			String oldData = record.getData();
			record.setData(textArea.getText());
			if (Boolean.parseBoolean(monitor.updateRecord(record))) {
				//record.setData(textArea.getText());
				JOptionPane.showMessageDialog(null, "Patient medical data updated.");
				list.refreshList();
			} else {
				record.setData(oldData);
				textArea.setText(oldData);
				list.refreshList();
				JOptionPane.showMessageDialog(null, "There was an error.");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

}
