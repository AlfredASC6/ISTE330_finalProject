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
      		if (dbPassword.isBlank()) dbPassword = "student"; // assign default password
      		if (dbName.isBlank()) dbName = "researchinterests"; // assign default database
      

      	//instantiate database user variable input 
      		JFrame selectionFrame = new JFrame("Select Your Role");
      			selectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      			selectionFrame.setSize(300, 200);

      		JPanel selectionPanel = new JPanel(new GridLayout(3, 1));

      		String discriminator = "";

      		JButton facultyButton = new JButton("Faculty");
      		facultyButton.addActionListener(e -> {
          		this.discriminator = "F";
          		selectionFrame.dispose();
          		showLoginAndSignupFrame();
      		});
      		selectionPanel.add(facultyButton);

      		JButton studentButton = new JButton("Student");
     			studentButton.addActionListener(e -> {
          		this.discriminator = "S";
          		selectionFrame.dispose();
          		showLoginAndSignupFrame();
      		});
      		selectionPanel.add(studentButton);
      		
      		JButton guestButton = new JButton("Guest");
      			guestButton.addActionListener(e -> {
    	  		this.discriminator = "G";
          		selectionFrame.dispose();
          		showLoginAndSignupFrame();
      		});
      		selectionPanel.add(guestButton);

      		selectionFrame.add(selectionPanel);
      		selectionFrame.setLocationRelativeTo(null);
      		selectionFrame.setVisible(true);
		      ///// testing GUIs remove later /////
		      //new GuestGUI(dbUsername, dbPassword, dbName);
		      //new FacultyGUI(dbUsername, dbPassword, dbName);
		      //new StudentGUI(dbUsername, dbPassword, dbName);
		      
		      
		      // ASK USER IF THEY ARE A FACULTY, STUDENT, OR GUEST HERE 
		      // set discriminator = f, s, or g depending on answer 
		      
		      // IF STATEMENTS THAT CALL METHODS DEPENDING ON DISCRIMINATOR 
	}

	private void showLoginAndSignupFrame() {
		JFrame frame = new JFrame("Login and Signup");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 200);

		JPanel panel = new JPanel(new GridLayout(2, 1));

		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(e -> showLoginForm(discriminator));
		panel.add(loginButton);

		JButton signUpButton = new JButton("Sign Up");
		signUpButton.addActionListener(e -> {
			if ("S".equals(discriminator)) {
				new StudentGUI(dbUsername, dbPassword, dbName);
			} else if ("G".equals(discriminator)) {
				new GuestGUI(dbUsername, dbPassword, dbName);
			} else if ("F".equals(discriminator)) {
				new FacultyGUI(dbUsername, dbPassword, dbName);
			}
		});
		panel.add(signUpButton);
		   
		frame.add(panel);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
   private void showLoginForm(String discrim) {
	    JPanel loginPanel = new JPanel(new GridLayout(3, 2));
	    JLabel lblUsername = new JLabel("Username: ");
	    JLabel lblPassword = new JLabel("Password: ");
	    JTextField tfUsername = new JTextField();
	    JPasswordField pfPassword = new JPasswordField();
	    loginPanel.add(lblUsername);
	    loginPanel.add(tfUsername);
	    loginPanel.add(lblPassword);
	    loginPanel.add(pfPassword);

	    int result = JOptionPane.showConfirmDialog(null, loginPanel,
	            "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

	    if (result == JOptionPane.OK_OPTION) {
	        String username = tfUsername.getText();
	        if (dl.checkUsername(username)) {
	            JOptionPane.showMessageDialog(null, "Invalid username. Please try again.",
	                    "Error", JOptionPane.ERROR_MESSAGE);
	        } else {
	            String password = new String(pfPassword.getPassword());
	            if (dl.checkPasswd(username, password)) {
	                JOptionPane.showMessageDialog(null, "Invalid password. Please try again.",
	                        "Error", JOptionPane.ERROR_MESSAGE);
	            } else {
	                // Call the appropriate GUI constructor based on the discriminator value
	                if ("S".equals(discrim)) {
	                    new StudentGUI(dbUsername, dbPassword, dbName);
	                } else if ("G".equals(discrim)) {
	                    new GuestGUI(dbUsername, dbPassword, dbName);
	                } else if ("F".equals(discrim)) {
	                    //new FacultyGUI(dbUsername, dbPassword, dbName);
	                }
	            }
	        }
	    }
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
