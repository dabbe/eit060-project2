import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

public class GUI extends JFrame {

	// public static final int PATIENT = 0, NURSE = 1, DOCTOR = 2, GOV = 3;

	public GUI() {

		JPanel container = new JPanel();
		container.setPreferredSize(new Dimension(500, 400));
		container.setBorder(new EmptyBorder(10, 10, 10, 10));

		SearchField search = new SearchField();
		container.setLayout(new BorderLayout());
		container.add(search, BorderLayout.NORTH);

		JPanel main = new JPanel();
		main.setBorder(new EmptyBorder(10, 0, 0, 0));

		JPanel leftBar = new JPanel();
		leftBar.setLayout(new BorderLayout());
		leftBar.setBorder(new EmptyBorder(0, 0, 0, 10));

		String[] data = { "Mister cool", "danne bogdan olsson pyssling",
				"ptsuw", "iou789ah8" };
		JList list = new JList(data);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(list,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		leftBar.add(listScroller);

		// TODO: switch (user) {

		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(3, 1));

		JButton create = new CreateButton();
		buttons.add(create);

		JButton delete = new DeleteButton();
		buttons.add(delete);

		JButton edit = new EditButton();
		buttons.add(edit);

		leftBar.add(buttons, BorderLayout.SOUTH);

		main.setLayout(new GridLayout(1, 2));

		main.add(leftBar);

		JPanel rightBar = new JPanel();
		rightBar.setLayout(new BorderLayout());

		JTextArea textArea = new JTextArea();
		rightBar.add(textArea);

		JButton save = new SaveButton();
		rightBar.add(save, BorderLayout.SOUTH);

		main.add(rightBar);
		container.add(main);

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
