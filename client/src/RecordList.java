import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import server.Record;

@SuppressWarnings("rawtypes")
public class RecordList extends JList implements ListSelectionListener {

	private static final long serialVersionUID = 1L;

	private DefaultListModel model;
	private LinkedList<Observer> observers;
	private Monitor monitor;

	@SuppressWarnings("unchecked")
	public RecordList(DefaultListModel model, Monitor monitor) {
		super();
		this.model = model;
		this.monitor = monitor;
		setModel(model);
		addListSelectionListener(this);
		observers = new LinkedList<Observer>();
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setLayoutOrientation(JList.VERTICAL);
		setVisibleRowCount(-1);
	}

	public void addObserver(Observer o) {
		observers.add(o);
	}

	public void updateList(String name) {
		List<Record> records = monitor.getRecords(name);

		model.clear();
		for (Record record : records) {
			model.addElement(record);
		}
		this.setModel(model);

	}

	public void updateAll(Record record) {
		for (Observer o : observers) {
			o.update(record);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		if(getSelectedIndex() < 0) return;
		updateAll((Record)model.get(getSelectedIndex()));
	}

}
