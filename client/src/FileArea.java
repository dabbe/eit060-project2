import javax.swing.JTextArea;

import server.Record;

public class FileArea extends JTextArea implements Observer {
	
	private static final long serialVersionUID = 1L;

	public FileArea() {
		setLineWrap(true);
		setWrapStyleWord(true);
	}

	@Override
	public void update(Record record) {
		setText(record.getData());
	}
}
