package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class GUI extends JFrame {

	// public static final int PATIENT = 0, NURSE = 1, DOCTOR = 2, GOV = 3;

	public GUI() {

		JPanel container = new JPanel();
		container.setPreferredSize(new Dimension(350, 400));

		SearchField search = new SearchField();
		container.add(search);

		String[] data = { "Mister cool", "danne bogdan olsson pyssling",
				"ptsuw", "iou789ah8" };
		JList<String> list = new JList<String>(data);
		list.setPreferredSize(new Dimension(300, 300));

		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);

		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 80));

		container.add(list, BorderLayout.CENTER);

		// TODO: switch (user) {

		JButton create = new CreateButton();
		container.add(create);

		JButton delete = new DeleteButton();
		container.add(delete);

		JButton edit = new EditButton();
		container.add(edit);

		add(container);
		pack();

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2
				- getSize().height / 2);

		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	public static void main(String[] args) {
		new GUI();
	}

}
