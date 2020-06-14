package business.security;


import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
	

public final class CustomerSecurity extends Login {

	private CustomerSecurity(){}	
	private static String customerID;
		
	public static String getCustomerID() {
		return customerID;
	}
	
	private static Connection connection = null;
		public static boolean newUser(String password, String packageID, String username, String name, String phone, String email, double uploadUser, double downloadUser, String installationID, String streetNumber, String city, String province, String postalCode) throws SQLException {
			//For Daniel "jdbc:oracle:thin:@198.168.52.73:1522/pdborad12c.dawsoncollege.qc.ca",
		    //"A1633028", "password"
			String salt = getSalt();
		    CallableStatement cStmt = connection.prepareCall("call SalesRep.addUser(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			cStmt.setString(1, customerID);
			cStmt.setString(2, packageID);
			cStmt.setString(3, username); 
			cStmt.setString(4, name);
			cStmt.setString(5, phone);
			cStmt.setString(6, email); 
			cStmt.setDouble(7, uploadUser);
			cStmt.setDouble(8, downloadUser);
			cStmt.setString(9, installationID);
			cStmt.setString(10, "0"); 
			cStmt.setString(11, salt);
			cStmt.setBytes(12, hash(password, salt));
			cStmt.setString(13, streetNumber);
			cStmt.setString(14, city);
			cStmt.setString(15, province); 
			cStmt.setString(16, postalCode);
			
			cStmt.execute();
			cStmt.close();
			return true;
		}
		
		public static boolean newUser(String connectionString, String sqlID, String sqlPassword) throws SQLException {
			Scanner reader = new Scanner(System.in);
			connection = DriverManager.getConnection(connectionString, sqlID, sqlPassword);
			Statement stat = connection.createStatement();
			double up;
			double down;
			String installationID = "";
			String[] toAdd = new String[10];
			String[] alertValues = {" username", " password", " full name", " phone number", "n email adderss", " street number", " city", " province", " postal code", " package id",};
			String usernameRegex = "^[a-zA-Z0-9._]{3,}$";
			String nameRegex = "^[A-Z]?[a-z]{2,}\\s[A-Z]?[a-z]{2,}$";
			String passwordRegex = "^[a-zA-Z0-9._@#$%^&+=]{3,}$";
			String phoneRegex = "^[0-9]{3}[ -]?[0-9]{3}[ -]?[0-9]{4}$";
			String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
			String postalRegex = "^([A-Za-z]\\d[A-Za-z]\\s?\\d[A-Za-z]\\d)$";
			String streetRegex = "^\\d{1,4}\\s[A-Za-z]{1,10}\\s[A-Za-z-]{1,50}$";
			String provinceRegex = "^[A-Z]?[a-z]{1,30}$";
			String idRegex = "^[0-9]{1,}$";
			String[] regexArray = {usernameRegex, passwordRegex, nameRegex, phoneRegex, emailRegex, streetRegex, usernameRegex, provinceRegex, postalRegex, idRegex};
			String alert = "";
			for(int i = 0; i < toAdd.length; i ++) {
				alert = "Please enter a" + alertValues[i];
				verifyRegex(toAdd, regexArray, alert, i);
			}
		
			
			String sqlIdentifier = "select customerIndent.nextval from dual";
			PreparedStatement pst = connection.prepareStatement(sqlIdentifier);
			   ResultSet rs = pst.executeQuery();
			   if(rs.next())
			     customerID = String.valueOf(rs.getInt(1));
			
				
				ResultSet uname = stat.executeQuery("Select username from Customer where customerID = " + "'" + customerID + "'");
				if(uname.next()) {
					return false;
				}
				
				ResultSet pId = stat.executeQuery("Select packageID from Package where packageID = " + "'" + toAdd[9] + "'");
				if(!pId.next()) {
					return false;
				}
								
				installationID = createInstallation(customerID, Login.getID());
				
				ResultSet speeds = stat.executeQuery("Select uploadspeed, downloadspeed from package where packageID = " + "'" + toAdd[9] + "'");
				speeds.next();
				up = speeds.getDouble(1);
				down = speeds.getDouble(2);
				
				
			   
			try {//call sequence for all ids
				newUser(toAdd[1], toAdd[9], toAdd[0], toAdd[2], toAdd[3], toAdd[4], up, down, installationID, toAdd[5], toAdd[6], toAdd[7], toAdd[8]);			}
			catch (SQLException e) {
				System.out.println("An error has happened: " + e.getMessage());
			}
			pst.close();
			return true;
		}
		
		private static void verifyRegex(String[] value, String[] regexElement, String alert, int indexValue) {
			Scanner reader = new Scanner(System.in);
			do {
				System.out.println(alert + "\n");
				value[indexValue] = reader.nextLine();
			}while(!value[indexValue].matches(regexElement[indexValue]));
		}
		
		private static String createInstallation(String customerID, String employeeID) throws SQLException {
			String installationId = "";
			Scanner reader = new Scanner(System.in);
			String notes = "";
			String date = "";
			java.sql.Date scheduledDate = null;
			java.util.Date today = new java.util.Date();
			java.sql.Date sqlDateToday = new java.sql.Date(today.getTime());

			String sqlIdentifier = "select InstallationIndent.nextval from dual";
			PreparedStatement pst = connection.prepareStatement(sqlIdentifier);
			   ResultSet rs = pst.executeQuery();
			   if(rs.next())
			     installationId = String.valueOf(rs.getInt(1));
			   else
				   throw new IllegalArgumentException("Unable to reach server to increment Installation ID");
			   
			   do {
					System.out.println("Enter the date you would like for the installation to take place. It must be in the yyyy-MM-dd format.\n");
					try {
						date = reader.nextLine();
						scheduledDate = java.sql.Date.valueOf(
								LocalDate.from(
										DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(date)));
					} catch (Exception e) {
						System.out.println(e.getMessage());
						date = "2222-22-22";
						scheduledDate = java.sql.Date.valueOf(
								LocalDate.from(
										DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(date)));
					}
					
				}while(scheduledDate.before(sqlDateToday));
			   
					System.out.println("Notes to Add:\n");
					notes = reader.nextLine();
					
				    CallableStatement cStmt = connection.prepareCall("call SalesRep.addInstallation(?,?,?,?,?)");
					cStmt.setString(1, installationId); 
					cStmt.setString(2, customerID);
					cStmt.setString(3, employeeID);
					cStmt.setDate(4, scheduledDate); 
					cStmt.setString(5, notes);
					
					cStmt.execute();
					cStmt.close();
				    return installationId;
		}
	}
		
		
