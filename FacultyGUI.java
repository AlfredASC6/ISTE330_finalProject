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
	private static final Insets DEFAULT_INSETS = new Insets(20, 20, 20, 20);
	private static final Border DEFAULT_PADDING = BorderFactory.createEmptyBorder(40, 40, 40, 40);

	private JFrame frame;
	private JPanel home;
	private JLabel heading;
	private JButton addAbstract;
	private JButton findMatches;

	private JTextField abstractName;
	private JTextField abstractBody;
	
	private DataLayer dl;

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
		frame.setLayout(new GridLayout(2, 1));
		home = new JPanel();

		// Heading element
		heading = new JLabel("Faculty Home", SwingConstants.CENTER);
		heading.setFont(DEFAULT_FONT);
		heading.setBorder(DEFAULT_PADDING);
		frame.add(heading);

		// Add abstract button
		addAbstract = new JButton("Add Abstract");
		addAbstract.setFont(DEFAULT_FONT);
		addAbstract.setPreferredSize(new Dimension(300, 50));
		addAbstract.addActionListener(add_abstract_listener());
		home.add(addAbstract);

		// Find matches button
		findMatches = new JButton("Find Matches");
		findMatches.setFont(DEFAULT_FONT);
		findMatches.setPreferredSize(new Dimension(300, 50));
		findMatches.addActionListener(find_matches_listener());
		home.add(findMatches);

		// Finish up and show frame
		frame.add(home);
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
	
	// Handle find matches button
	private ActionListener find_matches_listener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				find_matches_dialog();
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
	
	private ActionListener submit_abstract() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dl.insertFacultyAbstract(abstractName.getText(), abstractBody.getText(), /*REMOVE THIS*/1/*REMOVE THIS*/);
				JOptionPane.showMessageDialog(null, "Success", "Abstract successfully inserted.", JOptionPane.PLAIN_MESSAGE);
			}
		};
		
	}
	
	// Blocking dialog for finding matches
	private void find_matches_dialog() {
		// Setup dialog
		JDialog dialog = new JDialog(frame, Dialog.ModalityType.APPLICATION_MODAL);
		dialog.setSize(400, 300);
		dialog.setLocationRelativeTo(null);
		dialog.setTitle("Find Matches");
		
		// Add components
		dialog.add(new JLabel("blocking-modal"));
		
		// Finish up and show dialog
		dialog.setVisible(true);
	}
	

}
