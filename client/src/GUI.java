import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;

	public GUI() {

		JPanel container = new JPanel();
		container.setPreferredSize(new Dimension(600, 600));
		container.setBorder(new EmptyBorder(10, 10, 10, 10));

		SearchField search = new SearchField();
		container.setLayout(new BorderLayout());
		container.add(search, BorderLayout.NORTH);

		JPanel main = new JPanel();
		main.setBorder(new EmptyBorder(10, 0, 0, 0));

		JPanel leftBar = new JPanel();
		leftBar.setLayout(new BorderLayout());
		leftBar.setBorder(new EmptyBorder(0, 0, 0, 10));

		ListEntry[] data = {
				new ListEntry("Elliot Jalgard",
						"Dr. Freeman\nModer Jord\nBornholm",
						"medical records\n\nand data osv"),
				new ListEntry("Mattias \"MSI\" Simonsson",
						"Dr. Daniel\nJurgina\nAzeroth",
						"medical records\n\nhej jag heter matiz\nhej") };

		NameList list = new NameList(data);
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

		Header header = new Header();
		rightBar.add(header, BorderLayout.NORTH);

		FileArea textArea = new FileArea();
		JScrollPane textScroller = new JScrollPane(textArea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		textArea.setMargin(new Insets(2, 4, 2, 4));
		rightBar.add(textScroller);

		JButton save = new SaveButton();
		rightBar.add(save, BorderLayout.SOUTH);

		main.add(rightBar);
		container.add(main);

		add(container);
		pack();

		list.addObserver(header);
		list.addObserver(textArea);

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
