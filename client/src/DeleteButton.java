
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class DeleteButton extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;

	public DeleteButton() {
		super("Delete");
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO: delete selected file
		System.out.println("Your level of clearance is not sufficient for this action.");
	}

}
