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

import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
public class StudentGUI {
	public static DataLayer datal;
	public StudentGUI(DataLayer dl) {
		datal = dl;
		JFrame frame = new JFrame("Login and Signup");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 200);

		JPanel panel = new JPanel(new GridLayout(2, 1));

		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(e -> showLoginForm());
		panel.add(loginButton);

		JButton signUpButton = new JButton("Sign Up");
		signUpButton.addActionListener(e -> showSignUpForm());
		panel.add(signUpButton);
	   
		frame.add(panel);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void showSignUpForm() {
		JPanel signUpPanel = new JPanel(new GridLayout(7, 2));
		JLabel lblStudentID = new JLabel("Student ID: ");
		JLabel lblFirstName = new JLabel("First Name: ");
		JLabel lblLastName = new JLabel("Last Name: ");
    	JLabel lblEmail = new JLabel("Email: ");
    	JLabel lblPhoneNumber = new JLabel("Phone Number: ");
    	JLabel lblDepartmentID = new JLabel("Department ID: ");

    	JTextField tfStudentID = new JTextField();
    	JTextField tfFirstName = new JTextField();
    	JTextField tfLastName = new JTextField();
    	JTextField tfEmail = new JTextField();
    	JTextField tfPhoneNumber = new JTextField();
    	JTextField tfDepartmentID = new JTextField();

    	signUpPanel.add(lblStudentID);
    	signUpPanel.add(tfStudentID);
    	signUpPanel.add(lblFirstName);
    	signUpPanel.add(tfFirstName);
    	signUpPanel.add(lblLastName);
    	signUpPanel.add(tfLastName);
    	signUpPanel.add(lblEmail);
    	signUpPanel.add(tfEmail);
    	signUpPanel.add(lblPhoneNumber);
    	signUpPanel.add(tfPhoneNumber);
    	signUpPanel.add(lblDepartmentID);
    	signUpPanel.add(tfDepartmentID);
    
    	int studentID = Integer.parseInt(tfStudentID.getText());
    	String fName = tfFirstName.getText();
    	String lName = tfLastName.getText();
    	String email = tfEmail.getText();
    	String phoneNum = tfPhoneNumber.getText();
    	int departmentId = Integer.parseInt(tfDepartmentID.getText());
    
    	datal.insertStudent(studentID, fName, lName, email, phoneNum, departmentId);

    	JOptionPane.showMessageDialog(null, signUpPanel,
            "Sign Up", JOptionPane.PLAIN_MESSAGE);
    	// Save sign-up details and create a new account here
    	showInterestSearch();
	}

	private void showLoginForm() {
    	JPanel loginPanel = new JPanel(new GridLayout(3, 2));
    	JLabel lblUsername = new JLabel("Username: ");
    	JLabel lblPassword = new JLabel("Password: ");
    	JTextField tfUsername = new JTextField();
    	JPasswordField pfPassword = new JPasswordField();
    	loginPanel.add(lblUsername);
    	loginPanel.add(tfUsername);
    	loginPanel.add(lblPassword);
    	loginPanel.add(pfPassword);

    	JOptionPane.showMessageDialog(null, loginPanel,
    			"Login", JOptionPane.PLAIN_MESSAGE);
    	// Validate login credentials here
    	showInterestSearch();
	}

	private void showInterestSearch() {
		JPanel searchPanel = new JPanel(new GridLayout(2, 1));
		JTextField tfSearch = new JTextField();
		searchPanel.add(tfSearch);

		//String discriminator = tfSearch.getText();
		int userId = 1; // Replace this with the logged-in user's ID
		
		String matchedResults = datal.match(userId, "S");
		JOptionPane.showMessageDialog(null, matchedResults,
				"Matching Results", JOptionPane.INFORMATION_MESSAGE);

	}
}