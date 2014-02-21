import javax.swing.JTextArea;

public class FileArea extends JTextArea implements Observer {
	
	private static final long serialVersionUID = 1L;

	public FileArea() {
		setLineWrap(true);
		setWrapStyleWord(true);
	}

	@Override
	public void update(ListEntry le) {
		setText(le.getRecords());
	}
}
