import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JTextField;

import server.Record;

public class SearchField extends JTextField implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Monitor monitor;
	private DefaultListModel<Record> model;

	public SearchField(Monitor monitor, DefaultListModel<Record> model) {
		this.monitor = monitor;
		this.model = model;
		setLayout(new BorderLayout());
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		List<Record> records = monitor.getRecords(getText());

		for (Record r : records) {
			model.addElement(r);
		}
	}

}
