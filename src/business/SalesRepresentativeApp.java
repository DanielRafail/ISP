package business;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Scanner;

import business.security.CustomerSecurity;
import business.security.Login;
import business.security.ManagerSecurity;

public class SalesRepresentativeApp {

	private SalesRepresentativeApp() {
	}

	public static void addUser(String connectionString, String sqlID, String sqlPassword) throws SQLException {
		boolean logged = false;
		while (!logged) {
			logged = CustomerSecurity.newUser(connectionString, sqlID, sqlPassword);
		}
		Login.getConnection().commit();
	}

	public static void coldCall(String connectionString, String sqlID, String sqlPassword) throws SQLException {
		String soldToString;
		Scanner reader = new Scanner(System.in);
		String idRegex = "^[1-9]{1,}$";
		String soldRegex = "^[0-1]$";
		Random rand = new Random();
		System.out.println("Calling number: " + (rand.nextInt(899) + 100) + "-" + (rand.nextInt(899) + 100) + "-"
				+ (rand.nextInt(8999) + 1000) + "\n");
		do {
			System.out.println("Have you made a sale? Enter 0 for no and 1 for yes\n");
			soldToString = reader.nextLine();
		} while (!soldToString.matches(soldRegex));
			Connection connection = DriverManager.getConnection(connectionString, sqlID, sqlPassword);
			CallableStatement cStmt = connection.prepareCall("{? = call SalesRep.coldCall(?,?,?)}");
			cStmt.registerOutParameter(1, java.sql.Types.VARCHAR);
			cStmt.setString(2, Login.getID());
			cStmt.setString(3, soldToString);
			if (soldToString .equals("1"))
				cStmt.setString(4, CustomerSecurity.getCustomerID());
			else
				cStmt.setString(4, null);
			cStmt.executeUpdate();
			if (cStmt.getString(1).equals("1")) {
				addUser(connectionString, sqlID, sqlPassword);
		}
	}

	public static void changePassword(String connectionString, String sqlID, String sqlPassword) throws SQLException {
		Scanner reader = new Scanner(System.in);
		Login.changePassword(connectionString, sqlID, sqlPassword, "Representatives", "EmployeeID");
		Login.getConnection().commit();
	}

	public static void changePackage() throws SQLException {
		Scanner reader = new Scanner(System.in);
		Statement stat = Login.getConnection().createStatement();
		boolean exists = false;
		CallableStatement cStmt = Login.getConnection().prepareCall("{call SalesRep.changePackage(?,?)}");
		String idRegex = "^[0-9]{1,}$";
		String customerID;
		String pID;
		do {
			System.out.println("What is the ID of the customer whose pacakge you wish to change?\n");
			customerID = reader.nextLine();
			ResultSet customerExist = stat.executeQuery("Select customerID from Customer where customerID = " + "'" + customerID + "'");
			if(customerExist.next()) {
				exists = true;
			}
		} while (!customerID.matches(idRegex) && !exists);
		exists = false;
		do {
			System.out.println("What is the ID of the new package the customer will have?\n");
			pID = reader.nextLine();
			ResultSet packageExist = stat.executeQuery("Select packageId from Package where packageID = " + "'" + pID + "'");
			if(packageExist.next()) {
				exists = true;
			}
		} while (!pID.matches(idRegex) && !exists);
		cStmt.setString(1, customerID);
		cStmt.setString(2, pID);
		cStmt.executeUpdate();
		Login.getConnection().commit();
		cStmt.close();
	}

}
