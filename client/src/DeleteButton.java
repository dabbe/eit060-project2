
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import resources.Record;

public class DeleteButton extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private RecordList list;
	private Monitor monitor;
	private FileArea area;
	private Header header;

	public DeleteButton(Monitor monitor, RecordList list, FileArea area, Header header) {
		super("Delete");
		this.list = list;
		this.monitor = monitor;
		this.area = area;
		this.header = header;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int index = list.getSelectedIndex();
		System.out.println(index);
		Record record = list.getCurrentSelected(); 
		if(record == null) return;

		try {
			if (Boolean.parseBoolean(monitor.deleteRecord(record))) {
				JOptionPane.showMessageDialog(null, "Record deleted.");
				list.remove(index);
				list.refreshList();
				area.setText("");
				header.clear();
			} else {
				JOptionPane.showMessageDialog(null, "There was an error.");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
