package gui;
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

import client.Client;
import client.HospitalConnection;
import resources.HospitalMember;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;

	public GUI(final Client client, HospitalConnection hc) {

		setTitle("DataJournal - " + hc.getCN());

		DefaultListModel model = new DefaultListModel();
		RecordList list = new RecordList(model, client);

		JPanel container = new JPanel(new BorderLayout());
		container.setPreferredSize(new Dimension(600, 600));
		container.setBorder(new EmptyBorder(10, 10, 10, 10));

		SearchField search = new SearchField(list);
		container.add(search, BorderLayout.NORTH);

		JPanel leftBar = new JPanel(new BorderLayout());
		leftBar.setBorder(new EmptyBorder(0, 0, 0, 10));

		JPanel rightBar = new JPanel(new BorderLayout());

		JScrollPane listScroller = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		leftBar.add(listScroller);

		JPanel leftFooter = new JPanel(new BorderLayout());
		JPanel rightFooter = new JPanel(new BorderLayout());
		FileArea textArea = new FileArea();

		Header header = new Header();
		rightBar.add(header, BorderLayout.NORTH);

		String ou = hc.getOU();

		if (ou.equals(HospitalMember.PATIENT)) {

			textArea.setEditable(false);
			search.setVisible(false);
			leftFooter.setVisible(false);
			rightFooter.setVisible(false);
			list.updateList(hc.getCN());

		} else if (ou.equals(HospitalMember.NURSE)) {
			
			leftFooter.setVisible(false);
			rightFooter.add(new SaveButton(textArea, client, list));

		} else if (ou.equals(HospitalMember.DOCTOR)) {

			leftFooter.add(new CreateButton(client));
			rightFooter.add(new SaveButton(textArea, client, list));

		} else if (ou.equals(HospitalMember.GOV)) {

			leftFooter.add(new DeleteButton(client, list, textArea, header));
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
				client.closeConnection();
				GUI.this.dispose();
				System.exit(1);
			}
		});

	}

}
