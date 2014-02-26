import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import resources.HospitalMember;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;

	public GUI(final Monitor monitor, HospitalConnection hc) {

		@SuppressWarnings("rawtypes")
		DefaultListModel model = new DefaultListModel();
		RecordList list = new RecordList(model, monitor);

		JPanel container = new JPanel();
		container.setPreferredSize(new Dimension(600, 600));
		container.setBorder(new EmptyBorder(10, 10, 10, 10));
		container.setLayout(new BorderLayout());

		SearchField search = new SearchField(monitor, list);
		container.add(search, BorderLayout.NORTH);

		JPanel leftBar = new JPanel();
		leftBar.setLayout(new BorderLayout());
		leftBar.setBorder(new EmptyBorder(0, 0, 0, 10));

		JPanel rightBar = new JPanel();
		rightBar.setLayout(new BorderLayout());

		JScrollPane listScroller = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		leftBar.add(listScroller);

		JPanel leftFooter = new JPanel();
		leftFooter.setLayout(new BorderLayout());
		JPanel rightFooter = new JPanel();
		rightFooter.setLayout(new BorderLayout());
		FileArea textArea = new FileArea();

		Header header = new Header();
		rightBar.add(header, BorderLayout.NORTH);

		String ou = hc.getOU();

		if (ou.equals(HospitalMember.PATIENT)) {

			// Patient : no buttons, records not editable
			textArea.setEditable(false);
			search.setVisible(false);
			leftFooter.setVisible(false);
			rightFooter.setVisible(false);

		} else if (ou.equals(HospitalMember.NURSE)) {

			// Nurse : Delete and Save
			leftFooter.setVisible(false);
			rightFooter.add(new SaveButton());

		} else if (ou.equals(HospitalMember.DOCTOR)) {

			// Doctor : Create, Delete, Save
			leftFooter.add(new CreateButton(monitor));
			rightFooter.add(new SaveButton());

		} else if (ou.equals(HospitalMember.GOV)) {

			// Government : Delete only
			leftFooter.add(new DeleteButton());
			textArea.setEditable(false);

		}

		leftBar.add(leftFooter, BorderLayout.SOUTH);

		JScrollPane textScroller = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,

		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		textArea.setMargin(new Insets(2, 4, 2, 4));
		rightBar.add(textScroller);

		rightBar.add(rightFooter, BorderLayout.SOUTH);

		JPanel main = new JPanel();
		main.setBorder(new EmptyBorder(10, 0, 0, 0));
		main.setLayout(new GridLayout(1, 2));
		main.add(leftBar);
		main.add(rightBar);

		container.add(main);

		add(container);
		pack();

		list.addObserver(header);
		list.addObserver(textArea);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);

		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				monitor.closeConnection();
				GUI.this.dispose();
				System.exit(1);
			}
		});

	}

}
