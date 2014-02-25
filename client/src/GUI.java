import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import resources.HospitalMember;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private Monitor monitor;
	private HospitalConnection hc;

	public GUI(final Monitor monitor, HospitalConnection hc) {
		this.monitor = monitor;
		this.hc = hc;

		DefaultListModel model = new DefaultListModel();
		NameList list = new NameList(model, monitor);

		JPanel container = new JPanel();
		container.setPreferredSize(new Dimension(600, 600));
		container.setBorder(new EmptyBorder(10, 10, 10, 10));

		SearchField search = new SearchField(monitor, list);
		container.setLayout(new BorderLayout());
		container.add(search, BorderLayout.NORTH);

		JPanel main = new JPanel();
		main.setBorder(new EmptyBorder(10, 0, 0, 0));

		JPanel leftBar = new JPanel();
		leftBar.setLayout(new BorderLayout());
		leftBar.setBorder(new EmptyBorder(0, 0, 0, 10));

		JScrollPane listScroller = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		leftBar.add(listScroller);

		JPanel leftFooter = new JPanel();
		leftFooter.setLayout(new BorderLayout());
		JPanel rightFooter = new JPanel();
		rightFooter.setLayout(new BorderLayout());
		FileArea textArea = new FileArea();
		
//		int temp = 3;
		
		String name = hc.getName();
		
		if (name.equals(HospitalMember.PATIENT)) {
//		if (temp == 0) {
			
			// Patient : no buttons, records not editable
			textArea.setEditable(false);
			search.setVisible(false);
			leftFooter.setVisible(false);
			rightFooter.setVisible(false);
		
		} else if (name.equals(HospitalMember.NURSE)) {
//		} else if (temp == 1) {	
			
			// Nurse : Delete and Save
			leftFooter.add(new DeleteButton());
			rightFooter.add(new SaveButton());
			
		} else if (name.equals(HospitalMember.DOCTOR)) {
//		} else if (temp == 2) {
			
			// Doctor : Create, Delete, Save
			leftFooter.setLayout(new GridLayout(2, 1));
			leftFooter.add(new CreateButton(monitor));
			leftFooter.add(new DeleteButton());
			rightFooter.add(new SaveButton());
			
		} else if (name.equals(HospitalMember.GOV)) {
//		} else if (temp == 3) {

			// Government : Delete only
			leftFooter.add(new DeleteButton());
			textArea.setEditable(false);
			
		}


		leftBar.add(leftFooter, BorderLayout.SOUTH);


		JPanel rightBar = new JPanel();
		rightBar.setLayout(new BorderLayout());

		Header header = new Header();
		rightBar.add(header, BorderLayout.NORTH);

		JScrollPane textScroller = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		textArea.setMargin(new Insets(2, 4, 2, 4));
		rightBar.add(textScroller);
		
		rightBar.add(rightFooter, BorderLayout.SOUTH);

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
