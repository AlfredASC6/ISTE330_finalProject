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

public class GuestGUI {

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
         id = dl.getGuestId(username);
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
      boolean validUsername = true;
      
      if(validUsername){
         dl.insertPerson(username, password, 0, discriminator); // 0 is a placeholder for id, guests are given an id
         id = dl.getGuestId(username);
         dl.insertGuest(id, fname, lname, company, email, phone);
         this.displayGuestHome();
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
   } // end displayGuestHome
   
} // end class 