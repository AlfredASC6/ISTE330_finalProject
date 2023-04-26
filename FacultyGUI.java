/*
Fennell, Kayla
Chen, Steven
Franco, Alfred
Conte, Jacob
Foley, Ben
Chuhi, Reg

Group 2
ISTE 330 
Group Project Week 3 HW
4/24/23
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class FacultyGUI {

	private static final Font DEFAULT_FONT = new Font("Apple Casual", Font.PLAIN, 24);
	private static final Font HEADING_FONT = new Font("Apple Casual", Font.BOLD, 36);
	private static final Font FONT_SMALL = new Font("Apple Casual", Font.PLAIN, 18);
	private static final Insets DEFAULT_INSETS = new Insets(20, 20, 20, 20);
	private static final Border DEFAULT_PADDING = BorderFactory.createEmptyBorder(40, 40, 40, 40);

	private JFrame frame;
	private JLabel heading;
	private JPanel abstracts;
	private JPanel buttons;

	private JButton addAbstract;
	private JButton editAbstract;
	private JButton deleteAbstract;

	private JTextField abstractName;
	private JTextField abstractBody;

	private DataLayer dl;
	private final int ID = 1;

	// default values
	public FacultyGUI() {
		new FacultyGUI("root", "Student-2023", "researchinterests");
	}
	
	public FacultyGUI(String username, String password, String database) {
		// Initialize data layer
		dl = new DataLayer(username, password, database);

		// Set up frame
		frame = new JFrame();
		frame.setSize(400, 800);
		frame.setLocation(125, 75);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		// Heading element
		heading = new JLabel("Faculty Home", SwingConstants.CENTER);
		heading.setFont(DEFAULT_FONT);
		heading.setBorder(DEFAULT_PADDING);
		frame.add(heading, BorderLayout.PAGE_START);
				
		// Abstracts view
		abstracts = new JPanel();
		abstracts.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createLineBorder(Color.black, 2)));
		String abstractData = "[abstracts go here]"; //FIX THIS: needs a d.lgetAbstractsByFaculty(int facId) method 
		JLabel abstractLabel = new JLabel(abstractData);
		abstractLabel.setFont(FONT_SMALL);
		abstractLabel.setAlignmentX(0);
		abstractLabel.setAlignmentY(1);
		abstracts.add(abstractLabel);
		frame.add(abstracts, BorderLayout.CENTER);

		// abstract buttons
		buttons = new JPanel();
		buttons.setLayout(new GridLayout(3, 1));
		addAbstract = new JButton("Add Abstract");
		addAbstract.setFont(DEFAULT_FONT);
		addAbstract.setPreferredSize(new Dimension(300, 50));
		addAbstract.addActionListener(add_abstract_listener());
		buttons.add(addAbstract);
		editAbstract = new JButton("Edit Abstract");
		editAbstract.setFont(DEFAULT_FONT);
		editAbstract.setPreferredSize(new Dimension(300, 50));
		editAbstract.addActionListener(edit_abstract_listener());
		buttons.add(editAbstract);
		deleteAbstract = new JButton("Delete Abstract");
		deleteAbstract.setFont(DEFAULT_FONT);
		deleteAbstract.setPreferredSize(new Dimension(300, 50));
		deleteAbstract.addActionListener(delete_abstract_listener());
		buttons.add(deleteAbstract);
		frame.add(buttons, BorderLayout.PAGE_END);

		// Finish up and show frame
		frame.setVisible(true);
	}
	
	// Handle add abstract button
	private ActionListener add_abstract_listener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				add_abstract_dialog();
			}
		};
	}
	
	// Blocking dialog for adding an abstract
	private void add_abstract_dialog() {
		// Setup dialog
		JDialog dialog = new JDialog(frame, Dialog.ModalityType.APPLICATION_MODAL);
		dialog.setSize(400, 300);
		dialog.setLocationRelativeTo(null);
		dialog.setTitle("Create Abstract");
		dialog.setLayout(new BorderLayout());
		
		abstractName = new JTextField();
		abstractName.setText("Abstract title");
		abstractName.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10), abstractName.getBorder()));
		dialog.add(abstractName, BorderLayout.PAGE_START);
		
		abstractBody = new JTextField();
		abstractBody.setText("Abstract body");
		abstractBody.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10), abstractBody.getBorder()));
		abstractBody.setAlignmentY(1);
		dialog.add(abstractBody, BorderLayout.CENTER);
		
		JButton submit = new JButton("Submit");
		submit.addActionListener(submit_abstract());
		dialog.add(submit, BorderLayout.PAGE_END);
		
		// Finish up and show dialog
		dialog.setVisible(true);
	}
	
	// Perform submit call
	private ActionListener submit_abstract() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dl.insertFacultyAbstract(abstractName.getText(), abstractBody.getText(), /*REMOVE THIS*/ID/*REMOVE THIS*/);
				JOptionPane.showMessageDialog(null, "Success", "Abstract successfully inserted.", JOptionPane.PLAIN_MESSAGE);
			}
		};
		
	}

	// Handle edit abstract button
	private ActionListener edit_abstract_listener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				edit_abstract_dialog();
			}
		};
	}

	// Blocking dialog for editing an abstract
	private void edit_abstract_dialog() {
		// Setup dialog
		JDialog dialog = new JDialog(frame, Dialog.ModalityType.APPLICATION_MODAL);
		dialog.setSize(400, 300);
		dialog.setLocationRelativeTo(null);
		dialog.setTitle("Edit Abstract");
		dialog.setLayout(new BorderLayout());

		// Retrieve abstract
		String abstractID = JOptionPane.showInputDialog(null, "Enter the ID of the abstract to edit:", "Enter ID", 0);
		int id = Integer.parseInt(abstractID);
		String abstractText = dl.getAbstract(ID, id);
		String abstractTitleText = "[title]"; //NEEDS dl.getAbstractTitle(ID, id)

		abstractName = new JTextField();
		abstractName.setText(abstractTitleText);
		abstractName.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10), abstractName.getBorder()));
		dialog.add(abstractName, BorderLayout.PAGE_START);
		
		abstractBody = new JTextField();
		abstractBody.setText(abstractText);
		abstractBody.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10), abstractBody.getBorder()));
		abstractBody.setAlignmentY(1);
		dialog.add(abstractBody, BorderLayout.CENTER);
		
		JButton submit = new JButton("Confirm Edit");
		submit.addActionListener(confirm_abstract(abstractText, abstractTitleText));
		dialog.add(submit, BorderLayout.PAGE_END);
		
		// Finish up and show dialog
		dialog.setVisible(true);
	}

	// Perform update call
	private ActionListener confirm_abstract(String oldName, String oldBody) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dl.updateFacultyAbstract(oldName, abstractName.getText(), oldBody, abstractBody.getText());
				JOptionPane.showMessageDialog(null, "Success", "Abstract successfully updated.", JOptionPane.PLAIN_MESSAGE);
			}
		};
	}

	// Handle delete abstract button
	private ActionListener delete_abstract_listener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				delete_abstract_dialog();
			}
		};
	}

	// Blocking dialog for editing an abstract
	private void delete_abstract_dialog() {
		// Retrieve abstract
		String abstractTitle = JOptionPane.showInputDialog(null, "Enter the title of the abstract to delete:", "Enter Title", 0);
		// Confirm delete
		int choice = JOptionPane.showConfirmDialog(null, "Delete the abstract '" + abstractTitle + "'?", "Delete Abstract", JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION) {
			delete_abstract(abstractTitle, ID);
		}
	}

	// Perform delete call
	private void delete_abstract(String title, int id) {
		dl.deleteFacultyAbstract(title, id);
		JOptionPane.showMessageDialog(null, "Success", "Abstract successfully deleted.", JOptionPane.PLAIN_MESSAGE);
	}

}
