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

import java.util.*;

import javax.swing.JOptionPane;

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
	
	// create connection with specified credentials
	public DataLayer(String username, String password, String database) {
		loadDriver();
		loadConnection(username, password, database);
	}
	
	// load jdbc driver
	private void loadDriver() {
		try {
			Class.forName(DEFAULT_DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// begin connection with given credentials
	private void loadConnection(String username, String password, String database) {
		try {
			connection = DriverManager.getConnection(URL_HEADING + database, username, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// execute query statement (SELECT)
	public ResultSet query(String query) {
		try {
			Statement statement = connection.prepareStatement(query);
			ResultSet results = statement.executeQuery(query);
			return results;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
// delete faculty key topic
public int deleteFacultyKeyTopic(int facultyId, String topic) {
    try {
        
        String procedureCall = "{call delete_faculty_keytopic(?,?)}";
        CallableStatement statement = connection.prepareCall(procedureCall);

        statement.setInt(1, facultyId);
        statement.setString(2, topic);
        int result = statement.executeUpdate();

        return result;
    } catch (SQLException e) {
        // handle any errors
        e.printStackTrace();
        return -1;
    }
}

// update faculty key topic
public int updateFacultyKeyTopic(int facultyId, String oldTopic, String newTopic) {
    try {
    
        String procedureCall = "{call faculty_key_topic(?,?,?)}";
        CallableStatement statement = connection.prepareCall(procedureCall);

        statement.setInt(1, facultyId);
        statement.setString(2, oldTopic);
        statement.setString(3, newTopic);

        int result = statement.executeUpdate();

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
  
  //begining of SELECT methods that allow java to output matches as strings
  public String match(int studentID){
     String matched = "";
     try{
        String sql = "{CALL match_keyTopic(?)}";
        CallableStatement cs = connection.prepareCall(sql);
        cs.setInt(1, studentID);
        rs = cs.executeQuery(sql);
        
        while(rs.next()){
           matched = rs.getString(1);
        }
     }
     catch(Exception e){
        System.out.println("Error: " + e);
     }
     
     return matched;
  }//end of matching function
  
  //class that gets an abstract from the database 
  public String getAbstract(int facultyID, int abstractID){
     String abstractTopic = "";
     
     try{
        String getFacultyAbstract = "SELECT abstract.abstract FROM faculty_abstract JOIN abstract ON faculty_abstract.facultyID = " + facultyID + "  && abstract.abstractID = " + abstractID;
        
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
     
     
     String tempList[] = abstractText.split("\\s+");
     String tempList1[] = facultyInterests.split("\\s+");
     
     List <String> abstractList = Arrays.asList(tempList);
     List <String> facultyInterestsList = Arrays.asList(tempList1);
     
     // System.out.println(abstractList);
//      System.out.println(facultyInterestsList);
     
     for(int i = 0; i < facultyInterestsList.size(); i++){
        String element = facultyInterestsList.get(i);
        //System.out.println(element);
        if(abstractList.contains(element)){
           //System.out.println(element);
           tbreturned += element;
           //System.out.println(tbreturned);
        }
        else{
           System.out.println("No interestes noted in the abstract");
        }
     }

     return tbreturned;
  }
}