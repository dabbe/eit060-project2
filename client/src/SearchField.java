import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

public class SearchField extends JTextField implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Monitor monitor;
	private RecordList recordList;

	public SearchField(Monitor monitor, RecordList recordList) {
		this.monitor = monitor;
		this.recordList = recordList;
		setLayout(new BorderLayout());
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		recordList.updateList(getText());
	}

}
