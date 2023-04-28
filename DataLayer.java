/*
Fennell, Kayla
Chen, Steven
Franco, Alfred
Conte, Jacob
Foley, Ben
Chuhi, Reg
Group 2
ISTE 330 
Group Project Week 2 HW
4/14/23
*/

//start of encryption libraries
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
//end of encryption libraries

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.Types;

import java.util.*;

public class DataLayer {
	//encryption variables
   public static SecretKeySpec secretKey;
   public static byte[] key;
	// variables for connecting and executing statements
	private Connection connection;
	private final String DEFAULT_HOST = "localhost";
	private final String DEFAULT_DRIVER = "com.mysql.cj.jdbc.Driver";
	private final String URL_HEADING = "jdbc:mysql://" + DEFAULT_HOST + "/";
   
   private ResultSet rs;
   private ResultSet rs1;
   
   private static int id;
	
	/* This code was moved to the presentation layer since it uses GUIs DELETE LATER 
	// create connection and prompt user for credentials
	public DataLayer() {
		loadDriver();
		String username = JOptionPane.showInputDialog(null, "Username (default=root): ", "Connect", JOptionPane.PLAIN_MESSAGE);
		String password = JOptionPane.showInputDialog(null, "Password: ", "Connect", JOptionPane.PLAIN_MESSAGE);
		String database = JOptionPane.showInputDialog(null, "Database: ", "Connect", JOptionPane.PLAIN_MESSAGE);
		if (username.isBlank()) username = "root"; // assign default username
		loadConnection(username, password, database);
      System.out.println("Connected to database!");
	}
	*/
	
	//constructor 
        public DataLayer() {
        }
	
	// create connection with specified credentials
	public DataLayer(String username, String password, String database) {
		loadDriver();
		loadConnection(username, password, database);
	}
	
	// load jdbc driver
	public void loadDriver() {
		try {
			Class.forName(DEFAULT_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("Error loading driver | " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	// begin connection with given credentials
	public boolean loadConnection(String username, String password, String database) {
	    boolean connected = false; 
		
	    try {
		connection = DriverManager.getConnection(URL_HEADING + database + "?serverTimezone=UTC", username, password);
		System.out.println("Connected to database!");
         	connected = true;
	    } catch (SQLException e) {
		System.out.println("Error connecting to DB | " + e.getMessage());
		e.printStackTrace();
	    }
		
	    return connected;
	}
	
	// execute query statement (SELECT)
	public ResultSet query(String query) {
		try {
			Statement statement = connection.prepareStatement(query);
			ResultSet results = statement.executeQuery(query);
			return results;
		} catch (SQLException e) {
			System.out.println("Query error | " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
   
   // insert person 
   public void insertPerson(String username, String password, int id, String discriminator){
      String sql = "";
      String secretKey = "turtles";
      String encryptedPass = encrypt(password, secretKey);
      
      try{
         // faculty and student will use RIT id, guest will be given one (field is auto increment)
         if(discriminator.equals("G")){
            sql = "INSERT INTO person (username, password, discriminator) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, encryptedPass);
            statement.setString(3, discriminator);
	    statement.executeUpdate();
         }
         else{
            sql = "INSERT INTO person (username, password, ID, discriminator) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, encryptedPass);
            statement.setInt(3, id);
            statement.setString(4, discriminator);
	    statement.executeUpdate();
         }
         System.out.println("User profile has been inserted successfully");
      }
      catch (SQLException e) {
           // handle any errors
           e.printStackTrace();
      }
   }
   	
   // delete key topic
   public int deleteKeyTopic(String topic, int ID, String discriminator) {
      try {
    	  // call stored procedures
         String procedureCall = "";
         if(discriminator.equals("F")){
            procedureCall = "{call delete_faculty_keytopic(?,?,?)}";
         }
         else if(discriminator.equals("S")){
            procedureCall = "{call delete_student_keytopic(?,?,?)}";
         }
         else if(discriminator.equals("G")){
            procedureCall = "{call delete_guest_keytopic(?,?,?)}";
         }
   
         CallableStatement statement = connection.prepareCall(procedureCall);
         //set parameters
         statement.setString(1, topic);
         statement.setInt(2, ID);
         statement.registerOutParameter(3, Types.INTEGER);
         statement.executeUpdate();
         int rowsAffected = statement.getInt(3);
   
         System.out.println("The delete statement affected " + rowsAffected + " row(s)");
         return rowsAffected;
       } catch (SQLException e) {
           // handle any errors
           e.printStackTrace();
           return -1;
       }
   }
   
   // update faculty key topic
   public int insertKeyTopic(int ID, String topic, String discriminator) {
      try {
    	 // call stored procedures
         String procedureCall = "";
         if(discriminator.equals("F")){
            procedureCall = "{call add_faculty_keytopic(?,?)}";
         }
         else if(discriminator.equals("S")){
            procedureCall = "{call add_student_keytopic(?,?)}";
         }
         else if(discriminator.equals("G")){
            procedureCall = "{call add_guest_keytopic(?,?)}";
         }
   
         CallableStatement statement = connection.prepareCall(procedureCall);
     	 // set parameters
         statement.setInt(1, ID);
         statement.setString(2, topic);
   
         int result = statement.executeUpdate();
      
         System.out.println("Your keytopic has been successfully inserted");
         return result;
       } catch (SQLException e) {
   
           e.printStackTrace();
           return -1;
       }
   } 
   
   //Add a faculty member
   public void insertFacultyMember(int facultyId, String fName, String lName, String email, String phoneNum, String officePhoneNum, int officeNum, String buildingCode, int departmentId) {
       try {
           // create SQL statement
           String sql = "INSERT INTO faculty (facultyID, fName, lName, email, phoneNum, officePhoneNum, officeNum, buildingCode, departmentID) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
           PreparedStatement statement = connection.prepareStatement(sql);
   
           // set parameters
           statement.setInt(1, facultyId);
           statement.setString(2, fName);
           statement.setString(3, lName);
           statement.setString(4, email);
           statement.setString(5, phoneNum);
           statement.setString(6, officePhoneNum);
           statement.setInt(7, officeNum);
           statement.setString(8, buildingCode);
           statement.setInt(9, departmentId);
   
           // execute statement
           statement.executeUpdate();
   
           System.out.println("New faculty member inserted successfully!");
       } catch (SQLException e) {
           // handle any errors
           e.printStackTrace();
       }
   }
   
   //Add a student
   public void insertStudent(int studentID, String fName, String lName, String email, String phoneNum, int departmentId) {
       try {
           // create SQL statement
           String sql = "INSERT INTO student (studentID, fName, lName, email, phoneNum, departmentID) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
           PreparedStatement statement = connection.prepareStatement(sql);
   
           // set parameters
           statement.setInt(1, studentID);
           statement.setString(2, fName);
           statement.setString(3, lName);
           statement.setString(4, email);
           statement.setString(5, phoneNum);
           statement.setInt(6, departmentId);
   
           // execute statement
           statement.executeUpdate();
   
           System.out.println("New student inserted successfully!");
       } catch (SQLException e) {
           // handle any errors
           e.printStackTrace();
       }
   }
   
   //Add a guest
   public void insertGuest(int guestID, String fName, String lName, String company, String email, String phoneNum) {
       try {
           // create SQL statement
           String sql = "INSERT INTO guest (guestID, fName, lName, company, email, phoneNum) " +
                        "VALUES (?, ?, ?, ?, ?, ?)";
           PreparedStatement statement = connection.prepareStatement(sql);
   
           // set parameters
           statement.setInt(1, guestID);
           statement.setString(2, fName);
           statement.setString(3, lName);
           statement.setString(4, company);
           statement.setString(5, email);
           statement.setString(6, phoneNum);
   
           // execute statement
           statement.executeUpdate();
   
           System.out.println("New guest inserted successfully!");
       } catch (SQLException e) {
           // handle any errors
           e.printStackTrace();
       }
   }

   
   //start of insertFacultyAbstract
   public void insertFacultyAbstract(String abstractTitle, String abstractText, int facultyID){
      try{
         String sql = "{CALL insert_abstract(?, ?, ?)}";
         PreparedStatement stmt = connection.prepareStatement(sql);
         
         stmt.setString(1, abstractTitle);
         stmt.setString(2, abstractText);
         stmt.setInt(3, facultyID);
         
         stmt.executeUpdate();
         
         System.out.println("Executed code: " + sql);
      }
      catch(Exception e){
         e.printStackTrace();
         System.out.println("Error: " + e);
      }
   }//end of insertFacultyAbstract
   
   
   public void deleteFacultyAbstract(String abstractTitle, int facultyID){
      try{
         String sql = "{CALL delete_abstract(?, ?)}";
         PreparedStatement stmt = connection.prepareStatement(sql);
         // set parameters
         stmt.setString(1, abstractTitle);
         stmt.setInt(2, facultyID);
         
         stmt.executeUpdate();
         
         System.out.println("Executed code: " + sql);
      }
      catch(Exception e){
         System.out.println("Error: " + e);
      }
   }//end of deleteFacultyAbstract
   
   //start of updateFacultyAbstract
   public void updateFacultyAbstract(String oldTitle, String newTitle, String oldText, String newAbstract){
      try{
    	  System.out.println("Old title: " + oldTitle + "\nNew title: " + newTitle + "\nOld text: " + oldText + "\nNew abstract: " + newAbstract);
         String sql = "{CALL update_abstract(?, ?, ?, ?)}";
         PreparedStatement stmt = connection.prepareStatement(sql);
         // set parameters
         stmt.setString(1, oldTitle);
         stmt.setString(2, newTitle);
         stmt.setString(3, oldText);
         stmt.setString(4, newAbstract);
         
         stmt.executeUpdate();
         
         System.out.println("Executed code: " + sql);
      }
      catch(Exception e){
         System.out.println("Error: " + e);
      }
   }//end of updateFacultyAbstract

   public int getGuestId(String guestUsername){
      int id = -1;
      
      try{
         Statement stmt = connection.createStatement();
         String sql = "SELECT ID FROM person WHERE username = '" + guestUsername + "'";
         ResultSet rs = stmt.executeQuery(sql);
         
         while(rs.next()){
            id = Integer.parseInt(rs.getString("ID"));
         }
      }
      catch(Exception e){
         System.out.println("Error: " + e);
      }
      
      return id;
   }// end getGuestId

   //encryption stuff where we use SHA-256
   public static void setKey(final String myKey){
      MessageDigest sha = null;
      
      try{
         key = myKey.getBytes("UTF-8");
         sha = MessageDigest.getInstance("SHA-256");
         key = sha.digest(key);
         key = Arrays.copyOf(key, 16);
         secretKey = new SecretKeySpec(key, "AES");
      }
      catch(NoSuchAlgorithmException | UnsupportedEncodingException e){
         e.printStackTrace();
      }//end of try/catch blocks
   }//end of function
   
   public static String encrypt(final String strToEncrypt, final String secret) {
       try {
         setKey(secret);
         Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
         cipher.init(Cipher.ENCRYPT_MODE, secretKey);
         return Base64.getEncoder()
           .encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
       } catch (Exception e) {
         System.out.println("Error while encrypting: " + e.toString());
       }
    return null;
  }// end of encrypt function
  //start of decrypting function
  public static String decrypt(final String strToDecrypt, final String secret){
      try {
         setKey(secret);
         Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
         cipher.init(Cipher.DECRYPT_MODE, secretKey);
         return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
      } 
      catch (Exception e) {
         System.out.println("Error while decrypting: " + e.toString());
        }
      return null;
  }//end of decrypt function
  //end of encryption methods
  
  //beginning of SELECT methods that allow java to output matches as strings
  public String match(int id, String discriminator) {
	    String matched = "";
       matched += "----------\n";
	    try {
	        String sql = "{CALL match_keytopic(?, ?)}";
	        CallableStatement cs = connection.prepareCall(sql);
	        //set parameters
	        cs.setInt(1, id);
	        cs.setString(2, discriminator);
	        ResultSet rs = cs.executeQuery();
	        // print matching person's values
	        while (rs.next()) {
	            if (discriminator.equals("F")) {
	                matched += "Student ID: " + rs.getInt("Student_ID") + "\n";
	                matched += "Student Name: " + rs.getString("Student_First_Name") + " " + rs.getString("Student_Last_Name") + "\n";
	                matched += "Student Email: " + rs.getString("Student_Email") + "\n";
	                matched += "Student Phone: " + rs.getString("Student_PhoneNum") + "\n";
	                matched += "Student Department ID: " + rs.getString("Student_DepartmentID") + "\n";
	                matched += "Student_Topic: " + rs.getString("Student_Topic") + "\n";
                   matched += "----------\n";
	            } else if (discriminator.equals("S")) {
	                matched += getMatchingFaculty(id, discriminator);
                   matched += "----------\n";
	            }else if (discriminator.equals("G")) {
	                matched += getMatchingFaculty(id, discriminator);
                   matched += "----------\n";
	            }
	        }
	        if (cs.getMoreResults()) {
	            rs = cs.getResultSet();
	            while (rs.next()) {
	                if (discriminator.equals("S")) {
	                    matched += "Guest ID: " + rs.getInt("Guest_ID") + "\n";
	                    matched += "Guest Name: " + rs.getString("Guest_First_Name") + " " + rs.getString("Guest_Last_Name") + "\n";
	                    matched += "Guest Email: " + rs.getString("Guest_Email") + "\n";
	                    matched += "Guest Phone: " + rs.getString("Guest_PhoneNum") + "\n";
	                    matched += "Guest_Topic: " + rs.getString("Guest_Topic") + "\n";
                       matched += "----------\n";
	                } else if (discriminator.equals("G")) {
	                    matched += "Student ID: " + rs.getInt("Student_ID") + "\n";
	                    matched += "Student Name: " + rs.getString("Student_First_Name") + " " + rs.getString("Student_Last_Name") + "\n";
	                    matched += "Student Email: " + rs.getString("Student_Email") + "\n";
	                    matched += "Student Phone: " + rs.getString("Student_PhoneNum") + "\n";
	                    matched += "Student Department ID: " + rs.getString("Student_DepartmentID") + "\n";
	                    matched += "Student_Topic: " + rs.getString("Student_Topic") + "\n";
                       matched += "----------\n";
	                }
	            }
	        }
	    } catch (Exception e) {
	        System.out.println("Error: " + e);
	    }

	    return matched;
	} // end of match function
      
   public String getMatchingFaculty(int id, String discriminator){
      ArrayList<Integer> facIds = new ArrayList<Integer>();
      ArrayList<String> keytopics = new ArrayList<String>();
      int currId;
      String procedureCall = "";
      String sql = "";
      String matchingFaculty = "";
      
      // get list of faculty IDs
      try{
         if(discriminator.equals("S")){
            sql = "SELECT keytopic FROM student_keytopics WHERE studentID = " + id;
         }
         else if(discriminator.equals("G")){
            sql = "SELECT keytopic FROM guest_keytopics WHERE guestID = " + id;
         }
         Statement stmt = connection.createStatement();
         ResultSet rs = stmt.executeQuery(sql);
         while(rs.next()){
            String topic = rs.getString("keytopic");
            procedureCall = "{CALL get_faculty_ids(?)}";
            CallableStatement statement = connection.prepareCall(procedureCall);
            statement.setString(1, topic);
            ResultSet rs2 = statement.executeQuery();
            while(rs2.next()){
               currId = rs2.getInt("facultyID");
               if(!facIds.contains(currId)){
                  facIds.add(currId);
                  keytopics.add(topic);
               }
            }
         }
      }
      catch(Exception e){
        System.out.println("Error 1: " + e);
      }
      
      // get faculty info 
      try{
         String select = "";
         Statement selectStmt = connection.createStatement();
         for(int i = 0; i < facIds.size(); i++){
            select = "SELECT * FROM faculty WHERE facultyID = '" + facIds.get(i) + "'";
            ResultSet selectRS = selectStmt.executeQuery(select);
            while(selectRS.next()) {
               matchingFaculty += "Faculty ID: " + selectRS.getInt("facultyID") + "\n";
               matchingFaculty += "Faculty Name: " + selectRS.getString("fName") + " " + selectRS.getString("lName") + "\n";
   	         matchingFaculty += "Faculty Email: " + selectRS.getString("email") + "\n";
   	         matchingFaculty += "Faculty Phone: " + selectRS.getString("phoneNum") + "\n";
   	         matchingFaculty += "Faculty Office Phone: " + selectRS.getString("officePhoneNum") + "\n";
   	         matchingFaculty += "Faculty Office Number: " + selectRS.getString("officeNum") + "\n";
   	         matchingFaculty += "Faculty Building Code: " + selectRS.getString("buildingCode") + "\n";
   	         matchingFaculty += "Faculty Department ID: " + selectRS.getString("departmentID") + "\n";
   	         matchingFaculty += "Faculty_Topic: " + keytopics.get(i) + "\n";
            }
         }
      }
      catch(Exception e){
        System.out.println("Error 2: " + e);
      }
      
      System.out.println(matchingFaculty);
      return matchingFaculty;
      
   } // end getMatchingFacultyIds

  //class that gets an abstract from the database 
  public String getAbstract(int facultyID, int abstractID){
     String abstractTopic = "";
     
     try{
        String getFacultyAbstract = "SELECT abstract FROM abstract WHERE abstractID = (SELECT abstractID FROM faculty_abstract WHERE facultyID = " + facultyID + ")";
        
        PreparedStatement stmt = connection.prepareStatement(getFacultyAbstract);
        
        
        rs = stmt.executeQuery(getFacultyAbstract);
        
        while(rs.next()){
           abstractTopic = rs.getString("abstract");
           
        }        
     }
     catch(Exception e){
        System.out.println("Error: " + e);
     }
     return abstractTopic;
  }
  //method that returns a faculty's interests as a string since rs.execureQuery doesn't 
  public String getFacultyInterests(int facultyID){
     String interests = "";
     try{
        String getFacultyKeyWords = "SELECT keytopic FROM faculty_keytopics WHERE facultyID = " + facultyID;
        
        PreparedStatement stmt = connection.prepareStatement(getFacultyKeyWords);
        
        rs = stmt.executeQuery(getFacultyKeyWords);
        
        while(rs.next()){
           interests += rs.getString("keytopic");
           //System.out.println(interests.toString());
        }
        
     }
     catch(Exception e){
        System.out.println("Error: " + e);
     }
     
     return interests;
  }
  //method that sorts through an abstract and returns key words that match a faculty's 
  public String getAbstractKeyTopics(int facultyID, int abstractID){
     String facultyInterests = getFacultyInterests(facultyID);
     String abstractText = getAbstract(facultyID, abstractID);
     String tbreturned = "";
     
     // clears all non-alphabetical characters and replaces with whitespace
     String tempList[] = abstractText.replaceAll("[^a-zA-Z]+", " ").split("\\s+");
     String tempList1[] = facultyInterests.replaceAll("[^a-zA-Z]+", " ").split("\\s+");
          
     List <String> abstractList = Arrays.asList(tempList);
     List <String> facultyInterestsList = Arrays.asList(tempList1);
     
     for(int i = 0; i < facultyInterestsList.size(); i++){
    	 String element = facultyInterestsList.get(i);
    	 if(abstractList.contains(element)){
    		 	tbreturned += element;
    	 }
    	 else{
    		 	System.out.println("No interests noted in the abstract");
    	 }
     }
     return tbreturned;
  }
  
  public static int getId() {
	  return id;
  }
  
  //check Username
  public boolean checkUsername(String username) {
      boolean check = false;
      
      try {
        CallableStatement statement = connection.prepareCall("{call checkUsername(?, ?)}");
        statement.setString(1, username);
        statement.registerOutParameter(2, Types.INTEGER);
        statement.executeQuery();
        int result = statement.getInt(2);
        if (result == 0) {
             check = true;
        }
      } catch (SQLException e) {
        System.out.println("Error checking username | " + e.getMessage());
        e.printStackTrace();
      }
    return check;
  }
   
  //check password
  public boolean checkPasswd(String username, String password) {
      boolean check = false;
      
      try {
        CallableStatement statement = connection.prepareCall("{call checkPasswd(?, ?, ?)}");
        statement.setString(1, username);
        statement.setString(2, password);
        statement.registerOutParameter(3, Types.INTEGER);
        statement.executeQuery();
        int result = statement.getInt(3);
        if (result == 1) {
            check = true;
        }
    } catch (SQLException e) {
        System.out.println("Error checking password | " + e.getMessage());
        e.printStackTrace();
    }
    return check;
   }
} // end of class
