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

public class GuestGUI{

   private DataLayer dl = new DataLayer();
   String discriminator = "G";
   
   // user info variables 
   String username = new String();
   String password = new String();
   String fname = new String();
   String lname = new String();
   String company = new String();
   String email = new String();
   String phone = new String();
   int id;
   
   // user input variables 
   String keytopic = new String();
   
   private static final Font DEFAULT_FONT = new Font("Apple Casual", Font.PLAIN, 24);
   
   /*
   * Constructor when returning user logs in
   */
   public GuestGUI(String dbUsername, String dbPassword, String dbName, String username){
      // create data layer & connect to db 
      dl.loadDriver();
      boolean connected = dl.loadConnection(dbUsername, dbPassword, dbName);
      
      if(!connected){
         JOptionPane.showMessageDialog(null,"Unable to connect to data source\nPlease re-run the program");
      }
      else{
         id = dl.getUserId(username);
         this.displayGuestHome();
      } // end else
   } // end constructor 
   
   /*
   * Constructor when user is registering 
   */
   public GuestGUI(String dbUsername, String dbPassword, String dbName){
      // create data layer & connect to db 
      dl.loadDriver();
      boolean connected = dl.loadConnection(dbUsername, dbPassword, dbName);
      
      if(!connected){
         JOptionPane.showMessageDialog(null,"Unable to connect to data source\nPlease re-run the program");
      }
      else{
         this.registerGuest();
      } // end else
   } // end constructor 
   
   public void registerGuest(){
      JPanel registerBox = new JPanel(new GridLayout(7,2));
      
      // labels 
      JLabel usernameLbl = new JLabel("Username: ");
      JLabel passwordLbl = new JLabel("Password: ");
      JLabel fnameLbl = new JLabel("First Name: ");
      JLabel lnameLbl = new JLabel("Last Name: ");
      JLabel companyLbl = new JLabel("Company: ");
      JLabel emailLbl = new JLabel("Email: ");
      JLabel phoneLbl = new JLabel("Phone Number: ");
      
      // fields 
      JTextField usernameTf = new JTextField("");
      JTextField passwordTf = new JPasswordField("");
      JTextField fnameTf = new JTextField("");
      JTextField lnameTf = new JTextField("");
      JTextField companyTf = new JTextField("");
      JTextField emailTf = new JTextField("");
      JTextField phoneTf = new JTextField("");
      
      // build JPanel
      registerBox.add(usernameLbl);
      registerBox.add(usernameTf);
      registerBox.add(passwordLbl);
      registerBox.add(passwordTf);
      registerBox.add(fnameLbl);
      registerBox.add(fnameTf);
      registerBox.add(lnameLbl);
      registerBox.add(lnameTf);
      registerBox.add(companyLbl);
      registerBox.add(companyTf);
      registerBox.add(emailLbl);
      registerBox.add(emailTf);
      registerBox.add(phoneLbl);
      registerBox.add(phoneTf);
      
      // display JPanel
      JOptionPane.showMessageDialog(null, registerBox, "Register New Guest", JOptionPane.INFORMATION_MESSAGE);
      
      // get input 
      username = usernameTf.getText();
      password = passwordTf.getText();
      fname = fnameTf.getText();
      lname = lnameTf.getText();
      company = companyTf.getText();
      email = emailTf.getText();
      phone = phoneTf.getText();
      
      // insert into db
      // check if username is valid 
      boolean validUsername = dl.checkUsername(username);
      
      if(validUsername){
         int personRes = dl.insertPerson(username, password, 0, discriminator); // 0 is a placeholder for id, guests are given an id
         id = dl.getUserId(username);
         int guestResult = dl.insertGuest(id, fname, lname, company, email, phone);
         if(guestResult == -1 || personRes == -1){
            JOptionPane.showMessageDialog(null, "An error occurred while inserting the user. Please try again.");
         }
         else{
            this.displayGuestHome();
         }
      }
      else{
         JOptionPane.showMessageDialog(null, "That username is taken!\nPlease choose a different one!");
         this.registerGuest();
      }
   }
   
   public void displayGuestHome(){
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
      insertKeyTopic.addActionListener(insert_keytopic_listener());
      home.add(insertKeyTopic);
   		
      // delete key topic button
      deleteKeyTopic = new JButton("Delete Key Topic");
      deleteKeyTopic.setFont(DEFAULT_FONT);
      deleteKeyTopic.addActionListener(delete_keytopic_listener());
      home.add(deleteKeyTopic);
         
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
   } // end displayGuestHome
   
   /*
   * Handle insert key topic button
   */
	private ActionListener insert_keytopic_listener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enterKeytopicPanel();
            int result = dl.insertKeyTopic(id, keytopic, discriminator);
            if(result == -1){
               JOptionPane.showMessageDialog(null, "An error occured! The key topic could not be inserted.");
            }
            else{
               JOptionPane.showMessageDialog(null, "The key topic was inserted!");
            }
			}
		};
	}
   
   /*
   * Handle delete key topic button
   */
	private ActionListener delete_keytopic_listener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enterKeytopicPanel();
            int rowsAffected = dl.deleteKeyTopic(keytopic, id, discriminator);
            if(rowsAffected == 0){
               JOptionPane.showMessageDialog(null, "The key topic you tried to delete did not exist.\nNo rows were affected.");
            }
            else if(rowsAffected == -1){
               JOptionPane.showMessageDialog(null, "An error occured! No key topic was deleted.");
            }
            else{
               JOptionPane.showMessageDialog(null, "The key topic was deleted!");
            }
			}
		};
	}
   
   /*
   * Handle find matches button
   */
	private ActionListener find_matches_listener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String matches = dl.match(id, discriminator);
            if(matches.equals("")){
               JOptionPane.showMessageDialog(null, "No matches found");
            }
            else{
               JOptionPane.showMessageDialog(null, matches);
            }
			}
		};
	}
   
   /*
   * Display input frame for keytopic
   */
   public void enterKeytopicPanel(){
      JPanel insertBox = new JPanel(new GridLayout(1,2));
      
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
