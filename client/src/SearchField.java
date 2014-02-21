import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JTextField;

import server.Record;

public class SearchField extends JTextField implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Monitor monitor;
	private NameList nameList;

	public SearchField(Monitor monitor, NameList nameList) {
		this.monitor = monitor;
		this.nameList = nameList;
		setLayout(new BorderLayout());
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		nameList.updateList(getText());
	}

}
