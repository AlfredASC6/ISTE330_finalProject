/*
Fennell, Kayla
Chen, Steven
Franco, Alfred
Conte, Jacob
Foley, Ben
Chuhi, Reg

Group 2
ISTE 330 
Group Project HW3
4/28/23
*/

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import com.mysql.cj.protocol.a.TextResultsetReader;

public class PresentationLayer {

   private static final Font DEFAULT_FONT = new Font("Apple Casual", Font.PLAIN, 24);
	private static final Font HEADING_FONT = new Font("Apple Casual", Font.BOLD, 36);
	private static final Insets DEFAULT_INSETS = new Insets(20, 20, 20, 20);
	private static final Border DEFAULT_PADDING = BorderFactory.createEmptyBorder(40, 40, 40, 40);

   // faculty GUI  
	private JFrame frame;
	private JPanel home;
	private JLabel heading;
	private JButton addAbstract;
	private JButton findMatches;
	
   // database variables 
   String dbName = new String();
   String dbUsername = new String();
   String dbPassword = new String();
   
   // user info variables 
   String username = new String();
   String password = new String();
   int id;
   String discriminator = new String();
   
	private DataLayer dl;

   public PresentationLayer(){
      dbUsername = JOptionPane.showInputDialog(null, "Username (default=root): ", "Connect", JOptionPane.PLAIN_MESSAGE);
      dbPassword = JOptionPane.showInputDialog(null, "Password (default=student):", "Connect", JOptionPane.PLAIN_MESSAGE);
      dbName = JOptionPane.showInputDialog(null, "Database (default=researchInterests): ", "Connect", JOptionPane.PLAIN_MESSAGE);
      if (dbUsername.isBlank()) dbUsername = "root"; // assign default username
      if (dbPassword.isBlank()) dbPassword = "student"; // assign default username
      if (dbName.isBlank()) dbName = "researchInterests"; // assign default username
      
      dl = new DataLayer(dbUsername, dbPassword, dbName);
      
      guestGUI();
      //facultyGUI();
      
      // ASK USER IF THEY ARE A FACULTY, STUDENT, OR GUEST HERE 
      // set discriminator = f, s, or g depending on answer 
      
      // IF STATEMENTS THAT CALL METHODS DEPENDING ON DISCRIMINATOR 
   }
   
   /**************
      - We need a method that displays a log in GUI that can be called 
      for any faculty, student, or guest
      - Just prompts for username & password 
      - Idk if we have a method for this in the data layer, but we will
      need to make sure the username and password are correct and tell the 
      user if it is not corrent 
   ***************/
   
   /**************
      We need 3 methods that register each type of user
      All will call insertPerson()
      They will also call insertFacultyMember, insertStudent, OR insertGuest  
   ***************/
   
   /*
   * Sets up the Faculty Home GUI
   * !!!!! This appears to need more buttons !!!!!!
   */
   public void facultyGUI(){
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
   
   /*
   * Sets up the Student Home GUI
   */
   public void studentGUI(){
   }
   
   /*
   * Sets up the Guest Home GUI
   */
   public void guestGUI(){
      // set up variables 
      JFrame frame;
   	JPanel home;
   	JLabel heading;
   	JButton insertKeyTopic;
      JButton deleteKeyTopic;
   	JButton findMatches;
      JButton exit;
   
      // Set up frame
		frame = new JFrame("Guest Home: Choose an Action");
		frame.setSize(600, 400);
		frame.setLocation(125, 75);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      home = new JPanel();
		home.setLayout(new GridLayout(4, 1));

		// Heading element
		heading = new JLabel("Guest Home", SwingConstants.CENTER);
		heading.setFont(DEFAULT_FONT);
		frame.add(heading);
		
		// insert key topic button
		insertKeyTopic = new JButton("Insert Key Topic");
		insertKeyTopic.setFont(DEFAULT_FONT);
		//insertKeyTopic.addActionListener();
		home.add(insertKeyTopic);
		
		// delete key topic button
		deleteKeyTopic = new JButton("Delete Key Topic");
		deleteKeyTopic.setFont(DEFAULT_FONT);
		//deleteKeyTopic.addActionListener();
		home.add(deleteKeyTopic);
      
      // find matched button 
      findMatches = new JButton("Find Matches");
		findMatches.setFont(DEFAULT_FONT);
		//findMatches.addActionListener();
		home.add(findMatches);
      
      // exit button 
      exit = new JButton("Exit");
		exit.setFont(DEFAULT_FONT);
      java.util.Date now = new java.util.Date();
		exit.addActionListener(new ActionListener(){
		   public void actionPerformed(ActionEvent ae) {
	         JOptionPane.showMessageDialog(null, "Good-Bye!"+
				"\nEnd of program\nTerminated at -> "+now,
					"EOJ",
				           JOptionPane.PLAIN_MESSAGE);
				System.out.println("\nEnd of program\nTerminated at -> "+now);
				System.exit(0);
			}
		});
		home.add(exit);

		// Finish up and show frame
		frame.add(home);
		frame.setVisible(true);

   }
   
   /** METHODS FROM FACULTY GUI BELOW **/
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
		
		JTextField name = new JTextField();
		name.setText("Abstract title");
		name.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10), name.getBorder()));
		dialog.add(name, BorderLayout.PAGE_START);
		
		JTextField body = new JTextField();
		body.setText("Abstract body");
		body.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10), body.getBorder()));
		body.setAlignmentY(1);
		dialog.add(body, BorderLayout.CENTER);
		
		JButton submit = new JButton("Submit");
		submit.addActionListener(submit_abstract(name.getText(), body.getText()));
		dialog.add(submit, BorderLayout.PAGE_END);
		
		// Finish up and show dialog
		dialog.setVisible(true);
	}
	
	private ActionListener submit_abstract(String name, String body) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dl.insertFacultyAbstract(name, body, /*REMOVE THIS*/1/*REMOVE THIS*/);
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

   /** END FACULTY GUI METHODS **/
   
   public static void main(String [] args){
      System.out.println("ISTE330-01  Group Project HW3 Group 2  2023-04-28\n");
      System.out.println("Default Password: student");
      new PresentationLayer();
   }

}