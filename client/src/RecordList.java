import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import resources.Record;

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
	
	public Record getCurrentSelected() {
		if (getSelectedIndex() < 0)
			return null;
		return (Record) model.get(getSelectedIndex());
	}

	public void addObserver(Observer o) {
		observers.add(o);
	}
	
	public void removeWithIndex(int index){
		model.remove(index);
	}

	public void refreshList() {
		setModel(model);
	}

	public void updateList(String name) {
		List<Record> records = monitor.getRecordsOfPatient(name);

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
		if (getSelectedIndex() < 0)
			return;
		updateAll((Record) model.get(getSelectedIndex()));
	}

}
