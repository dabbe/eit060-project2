import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import resources.Record;

public class SaveButton extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JTextArea textArea;
	private Monitor monitor;
	private RecordList list;

	public SaveButton(JTextArea textArea, Monitor monitor, RecordList list) {
		super("Save");
		this.textArea = textArea;
		this.monitor = monitor;
		this.list = list;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Record record = list.getCurrentSelected();
		record.setData(textArea.getText());

		try {
			if (Boolean.parseBoolean(monitor.updateRecord(record))) {
				JOptionPane.showConfirmDialog(null, "Patient medical data updated.");
				list.refreshList();
			} else {
				JOptionPane.showConfirmDialog(null, "There was an error.");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

}
