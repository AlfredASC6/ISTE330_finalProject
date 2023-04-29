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
import java.util.Random;

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
	private String username;

	// faculty is not signed up (sign up)
	public FacultyGUI(String dbUsername, String dbPassword, String database) {
		dl = new DataLayer(dbUsername, dbPassword, database);
		register();
	}
	
	// faculty is signed up (login)
	public FacultyGUI(String dbUsername, String dbPassword, String database, String username) {
		// Initialize data layer
		dl = new DataLayer(dbUsername, dbPassword, database);
		showHome();
	}
	
	// Show register dialog for faculty
	private void register() {
		JPanel registerBox = new JPanel(new GridLayout(9, 2));

	    // labels
	    JLabel usernameLbl = new JLabel("Username: ");
	    JLabel passwordLbl = new JLabel("Password: ");
	    JLabel fnameLbl = new JLabel("First Name: ");
	    JLabel lnameLbl = new JLabel("Last Name: ");
	    JLabel emailLbl = new JLabel("Email: ");
	    JLabel phoneNumLbl = new JLabel("Phone Number: ");
	    JLabel officePhoneNumLbl = new JLabel("Office Phone Number: ");
	    JLabel buildingCodeLbl = new JLabel("Building Code: ");
	    JLabel departmentIdLbl = new JLabel("Department ID: ");

	    // fields
	    JTextField usernameTf = new JTextField("");
	    JTextField passwordTf = new JPasswordField("");
	    JTextField fnameTf = new JTextField("");
	    JTextField lnameTf = new JTextField("");
	    JTextField emailTf = new JTextField("");
	    JTextField phoneNumTf = new JTextField("");
	    JTextField officePhoneNumTf = new JTextField("");
	    JTextField buildingCodeTf = new JTextField("");
	    JTextField departmentIdTf = new JTextField("");

	    // build JPanel
	    registerBox.add(usernameLbl);
	    registerBox.add(usernameTf);
	    registerBox.add(passwordLbl);
	    registerBox.add(passwordTf);
	    registerBox.add(fnameLbl);
	    registerBox.add(fnameTf);
	    registerBox.add(lnameLbl);
	    registerBox.add(lnameTf);
	    registerBox.add(emailLbl);
	    registerBox.add(emailTf);
	    registerBox.add(phoneNumLbl);
	    registerBox.add(phoneNumTf);
	    registerBox.add(officePhoneNumLbl);
	    registerBox.add(officePhoneNumTf);
	    registerBox.add(buildingCodeLbl);
	    registerBox.add(buildingCodeTf);
	    registerBox.add(departmentIdLbl);
	    registerBox.add(departmentIdTf);

	    // display JPanel
	    JOptionPane.showMessageDialog(null, registerBox, "Register New Faculty Member", JOptionPane.INFORMATION_MESSAGE);

	    // get input
	    String username = usernameTf.getText();
	    String password = passwordTf.getText();
	    String fname = fnameTf.getText();
	    String lname = lnameTf.getText();
	    String email = emailTf.getText();
	    String phoneNum = phoneNumTf.getText();
	    String officePhoneNum = officePhoneNumTf.getText();
	    String buildingCode = buildingCodeTf.getText();
	    String departmentId = departmentIdTf.getText();
		
	    boolean personExists = dl.checkUsername(username);
	    if (personExists) {
	        JOptionPane.showMessageDialog(null, "That username is taken!\nPlease choose a different one!");
	        return;
	    }
	    else {
	    	int id = new Random().nextInt(10000); // I dont know why ID isn't set to auto increment??
	    	int res = dl.insertFacultyMember(id, fname, lname, email, phoneNum, officePhoneNum, ID, buildingCode, ID);
	    	if (res != -1) {
	    		showHome();
	    	} else {
	    		JOptionPane.showMessageDialog(null, "An error occurred while inserting the user. Please try again.");
	    		System.exit(1);
	    	}
	    }
	}
	
	private void showHome() {
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
		int aID;
		try {
			aID = Integer.parseInt(abstractID);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Error", "Please enter a valid ID number.", JOptionPane.ERROR_MESSAGE);
			return;
		}

		abstractName = new JTextField();
		abstractName.setText("[new abstract title]");
		abstractName.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10), abstractName.getBorder()));
		dialog.add(abstractName, BorderLayout.PAGE_START);
		
		abstractBody = new JTextField();
		abstractBody.setText("[new abstract body]");
		abstractBody.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10), abstractBody.getBorder()));
		abstractBody.setAlignmentY(1);
		dialog.add(abstractBody, BorderLayout.CENTER);
		
		JButton submit = new JButton("Confirm Edit");
		submit.addActionListener(confirm_abstract(aID));
		dialog.add(submit, BorderLayout.PAGE_END);
		
		// Finish up and show dialog
		dialog.setVisible(true);
	}

	// Perform update call
	private ActionListener confirm_abstract(int id) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dl.updateFacultyAbstract(id, abstractName.getText(), abstractBody.getText());
				JOptionPane.showMessageDialog(null, "Abstract successfully updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
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
		if (abstractTitle != null && !abstractTitle.isBlank()) {
			int choice = JOptionPane.showConfirmDialog(null, "Delete the abstract '" + abstractTitle + "'?", "Delete Abstract", JOptionPane.YES_NO_OPTION);
			if (choice == JOptionPane.YES_OPTION) {
				delete_abstract(abstractTitle, ID);
			}
		}
	}

	// Perform delete call
	private void delete_abstract(String title, int id) {
		dl.deleteFacultyAbstract(title, id);
		JOptionPane.showMessageDialog(null, "Abstract successfully deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
	}

}
