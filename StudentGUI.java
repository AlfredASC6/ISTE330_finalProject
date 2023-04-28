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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StudentGUI {

   private DataLayer dl = new DataLayer();
   String discriminator = "S";
   
   // user info variables 
   String username = new String();
   String password = new String();
   String fname = new String();
   String lname = new String();
   String major = new String();
   int id;
   
   // user input variables 
   String keytopic = new String();
   
   private static final Font DEFAULT_FONT = new Font("Apple Casual", Font.PLAIN, 24);
   
   /*
   * Constructor when returning user logs in
   */
   public StudentGUI(String dbUsername, String dbPassword, String dbName, String username){
      // create data layer & connect to db 
      dl.loadDriver();
      boolean connected = dl.loadConnection(dbUsername, dbPassword, dbName);
      
      if(!connected){
         JOptionPane.showMessageDialog(null,"Unable to connect to data source\nPlease re-run the program");
      }
      else{
         //id = dl.getStudentId(username);
         this.displayStudentHome();
      } // end else
   } // end constructor 
   
   /*
   * Constructor when user is registering 
   */
   public StudentGUI(String dbUsername, String dbPassword, String dbName){
      // create data layer & connect to db 
      dl.loadDriver();
      boolean connected = dl.loadConnection(dbUsername, dbPassword, dbName);
      
      if(!connected){
         JOptionPane.showMessageDialog(null,"Unable to connect to data source\nPlease re-run the program");
      }
      else{
         this.registerStudent();
      } // end else
   } // end constructor 
   
   public void registerStudent() {
	    JPanel registerBox = new JPanel(new GridLayout(6, 2));

	    // labels
	    JLabel usernameLbl = new JLabel("Username: ");
	    JLabel passwordLbl = new JLabel("Password: ");
	    JLabel fnameLbl = new JLabel("First Name: ");
	    JLabel lnameLbl = new JLabel("Last Name: ");
	    JLabel emailLbl = new JLabel("Email: ");
	    JLabel phoneNumLbl = new JLabel("Phone Number: ");

	    // fields
	    JTextField usernameTf = new JTextField("");
	    JTextField passwordTf = new JPasswordField("");
	    JTextField fnameTf = new JTextField("");
	    JTextField lnameTf = new JTextField("");
	    JTextField emailTf = new JTextField("");
	    JTextField phoneNumTf = new JTextField("");

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

	    // display JPanel
	    JOptionPane.showMessageDialog(null, registerBox, "Register New Student", JOptionPane.INFORMATION_MESSAGE);

	    // get input
	    username = usernameTf.getText();
	    password = passwordTf.getText();
	    fname = fnameTf.getText();
	    lname = lnameTf.getText();
	    String email = emailTf.getText();
	    String phoneNum = phoneNumTf.getText();
      
      // insert into db
      // check if username is valid 
	    boolean validUsername = dl.checkUsername(username);
	    
	    if (validUsername) {
	        dl.insertPerson(username, password, 0, discriminator);
	        int newId = 0;
			if (newId != -1) {
	            id = newId;
	            dl.insertStudent(id, fname, lname, email, phoneNum, newId);
	            this.displayStudentHome();
	        } else {
	            JOptionPane.showMessageDialog(null, "An error occurred while inserting the user. Please try again.");
	            this.registerStudent();
	        }
	    } else {
	        JOptionPane.showMessageDialog(null, "That username is taken!\nPlease choose a different one!");
	        this.registerStudent();
	    }
	}

   public void displayStudentHome(){
        // set up variables
	   JFrame frame;
	   JPanel home;
	   JLabel heading;
	   JButton insertKeyTopic;
//	   JButton deleteKeyTopic;
	   JButton findMatches;
	   JButton exit;
	   // Set up frame
	   frame = new JFrame("Student Home: Choose an Action");
	   frame.setSize(600, 400);
	   frame.setLocation(125, 75);
	   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   home = new JPanel();
	   home.setLayout(new GridLayout(3, 1));

	   // Heading element
	   heading = new JLabel("Student Home", SwingConstants.CENTER);
	   heading.setFont(DEFAULT_FONT);
	   frame.add(heading);
	 	
	   // insert key topic button
	   insertKeyTopic = new JButton("Insert Key Topic");
	   insertKeyTopic.setFont(DEFAULT_FONT);
	   insertKeyTopic.addActionListener(insert_keytopic_listener());
	   home.add(insertKeyTopic);
	 	
	   // delete key topic button
//	   deleteKeyTopic = new JButton("Delete Key Topic");
//	   deleteKeyTopic.setFont(DEFAULT_FONT);
//	   deleteKeyTopic.addActionListener(delete_keytopic_listener());
//	   home.add(deleteKeyTopic);
	      
	   // find matched button 
	   findMatches = new JButton("Find Matches");
	   findMatches.setFont(DEFAULT_FONT);
	   findMatches.addActionListener(find_matches_listener());
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
	   frame.add(home);
	   frame.setVisible(true);
	   } // end displayStudentHome

	   /*
	    * Handle insert key topic button
	    */
	   private ActionListener insert_keytopic_listener() {
	       return new ActionListener() {
	           @Override
	           public void actionPerformed(ActionEvent e) {
	               enterKeytopicPanel();
	               int result = dl.insertKeyTopic(id, keytopic, discriminator);
	               if (result == -1) {
	                   JOptionPane.showMessageDialog(null, "An error occured! The key topic could not be inserted.");
	               } else {
	                   JOptionPane.showMessageDialog(null, "The key topic was inserted!");
	               }
	           }
	       };
	   }

	   /*
	    * Handle delete key topic button
	    
	   private ActionListener delete_keytopic_listener() {
	       return new ActionListener() {
	           @Override
	           public void actionPerformed(ActionEvent e) {
	               enterKeytopicPanel();
	               int rowsAffected = dl.deleteKeyTopic(keytopic, id, discriminator);
	               if (rowsAffected == 0) {
	                   JOptionPane.showMessageDialog(null, "The key topic you tried to delete did not exist.\nNo rows were affected.");
	               } else if (rowsAffected == -1) {
	                   JOptionPane.showMessageDialog(null, "An error occured! No key topic was deleted.");
	               } else {
	                   JOptionPane.showMessageDialog(null, "The key topic was deleted!");
	               }
	           }
	       };
	   }
		*/
	   
	   /*
	    * Handle find matches button
	    */
	   private ActionListener find_matches_listener() {
	       return new ActionListener() {
	           @Override
	           public void actionPerformed(ActionEvent e) {
	               String matches = dl.match(id, discriminator);
	               if (matches.equals("")) {
	                   JOptionPane.showMessageDialog(null, "No matches found");
	               } else {
	                   JOptionPane.showMessageDialog(null, matches);
	               }
	           }
	       };
	   }
	   /*
	    * Display input frame for keytopic
	    */
	   public void enterKeytopicPanel() {
	       JPanel insertBox = new JPanel(new GridLayout(1, 2));

	       // label
	       JLabel keytopicLbl = new JLabel("Keytopic (1-3 words): ");

	       // field
	       JTextField keytopicTf = new JTextField("");

	       // build JPanel
	       insertBox.add(keytopicLbl);
	       insertBox.add(keytopicTf);

	       // display JPanel
	       JOptionPane.showMessageDialog(null, insertBox, "Enter the Key Topic", JOptionPane.INFORMATION_MESSAGE);

	       // get input
	       keytopic = keytopicTf.getText();
	   }

} // end class
