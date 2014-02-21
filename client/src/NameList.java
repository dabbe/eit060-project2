import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import server.Record;

@SuppressWarnings("rawtypes")
public class NameList extends JList<Record> implements ListSelectionListener {

	private static final long serialVersionUID = 1L;

	private DefaultListModel<Record> model;
	private LinkedList<Observer> observers;

	@SuppressWarnings("unchecked")
	public NameList(DefaultListModel model) {
		super();
		this.model = model;
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

	public void updateAll(Record record) {
		for (Observer o : observers) {
			o.update(record);
		}
	}

	public void replaceList(ArrayList<Record> data) {
		model.clear();
		for (Record entry : data) {
			model.addElement(entry);
		}
		this.setModel(model);
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		
		updateAll(model.get(getMinSelectionIndex()));
	}

}
