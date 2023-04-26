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


public class PresentationLayer {

   private static final Font DEFAULT_FONT = new Font("Apple Casual", Font.PLAIN, 24);
	
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
      if (dbPassword.isBlank()) dbPassword = "Student-2023"; // assign default password
      if (dbName.isBlank()) dbName = "researchinterests"; // assign default database
      
      dl = new DataLayer(dbUsername, dbPassword, dbName);
      
      //guestGUI();
      new FacultyGUI(dbUsername, dbPassword, dbName);
      
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
   
   public static void main(String [] args){
      System.out.println("ISTE330-01  Group Project HW3 Group 2  2023-04-28\n");
      System.out.println("Default Password: student");
      new PresentationLayer();
   }

}