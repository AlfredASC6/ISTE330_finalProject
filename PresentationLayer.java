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
   int action; // 0 if logging in, 1 if registering 
   

   public PresentationLayer(){
      dbUsername = JOptionPane.showInputDialog(null, "Username (default=root): ", "Connect", JOptionPane.PLAIN_MESSAGE);
      dbPassword = JOptionPane.showInputDialog(null, "Password (default=student):", "Connect", JOptionPane.PLAIN_MESSAGE);
      dbName = JOptionPane.showInputDialog(null, "Database (default=researchInterests): ", "Connect", JOptionPane.PLAIN_MESSAGE);
      if (dbUsername.isBlank()) dbUsername = "root"; // assign default username
      if (dbPassword.isBlank()) dbPassword = "Student-2023"; // assign default password
      if (dbName.isBlank()) dbName = "researchinterests"; // assign default database
      
      
      
      ///// testing GUIs remove later /////
      //new GuestGUI(dbUsername, dbPassword, dbName);
      new FacultyGUI(dbUsername, dbPassword, dbName);
      
      
      
      // ASK USER IF THEY ARE A FACULTY, STUDENT, OR GUEST HERE 
      // set discriminator = f, s, or g depending on answer 
      
      // IF STATEMENTS THAT CALL METHODS DEPENDING ON DISCRIMINATOR 
   }
   
   /**************
      - We need a method that displays a log in GUI that can be called 
      for any faculty, student, or guest
      - Just prompts for username & password 
   ***************/
   
   public static void main(String [] args){
      System.out.println("ISTE330-01  Group Project HW3 Group 2  2023-04-28\n");
      System.out.println("Default Password: student");
      new PresentationLayer();
   }

}
