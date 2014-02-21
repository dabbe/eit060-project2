import java.util.LinkedList;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("rawtypes")
public class NameList extends JList implements ListSelectionListener {

	private static final long serialVersionUID = 1L;

	private ListEntry[] data;
	private LinkedList<Observer> observers;

	@SuppressWarnings("unchecked")
	public NameList(ListEntry[] data) {
		super(data);
		this.data = data;
		addListSelectionListener(this);
		observers = new LinkedList<Observer>();
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setLayoutOrientation(JList.VERTICAL);
		setVisibleRowCount(-1);
	}

	public void addObserver(Observer o) {
		observers.add(o);
	}

	public void updateAll(ListEntry le) {
		for (Observer o : observers) {
			o.update(le);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
//		System.out.println("asdasd"); // why double print ;(
		updateAll(data[arg0.getFirstIndex()]);
	}

}
