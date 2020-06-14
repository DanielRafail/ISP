package business;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import business.security.Login;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerApp {
	
	public static boolean loginCustomer(String custID, String password, String connectionString, String sqlID, String sqlPassword) throws SQLException {
		Connection connection = null;
		Statement statement = null;
		
		try {
			connection = DriverManager.getConnection(connectionString, sqlID, sqlPassword);
			PreparedStatement pStatement = connection.prepareStatement("select passwordExpired from Customer where customerID = ? ");
			pStatement.setString(1, custID);
			ResultSet rs = pStatement.executeQuery();
			String passwordExpired = "";
			
			boolean isLogged;
			while(rs.next()) {
				passwordExpired = rs.getString("passwordExpired");
			}
			
			if (passwordExpired.equals("1")) {
				
				System.out.println("Your password has expired");
				Login.id = custID;
				Login.connection = connection;
				Login.changePassword("jdbc:oracle:thin:@198.168.52.73:1522/pdborad12c.dawsoncollege.qc.ca", "A0834484", "2amaretti", "Customer", "customerID");
				
				statement.executeUpdate("UPDATE Customer SET passwordExpired = '0' WHERE customerID = " + custID);
				isLogged = Login.login("jdbc:oracle:thin:@198.168.52.73:1522/pdborad12c.dawsoncollege.qc.ca", "A0834484", "2amaretti", "Customer", custID, null);
				return isLogged;
			} else {
				isLogged = Login.login("jdbc:oracle:thin:@198.168.52.73:1522/pdborad12c.dawsoncollege.qc.ca", "A0834484", "2amaretti", "Customer", custID, null);
				return isLogged;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		return false;
	}
	
	public static void displayPackageInfo(String custID, String connectionString, String sqlID, String sqlPassword) throws SQLException {
		Connection connection = null;
		Statement statement = null;
		try {
			connection = DriverManager.getConnection(connectionString, sqlID, sqlPassword);
			
			statement = connection.createStatement();
			ResultSet rSetPackID = statement.executeQuery("select packageID from Customer where customerID = " + custID);
			String packID = "";
			while(rSetPackID.next()) {
				packID = rSetPackID.getString("packageID");
				System.out.println("Your package ID: " + packID);
			}			 
			
			ResultSet resultSet = statement.executeQuery("select * from Package where packageID = \'" + packID + "\'");
			while(resultSet.next()) {
				System.out.println("Your uploadSpeed: " + resultSet.getDouble("uploadSpeed"));
				System.out.println("Your downloadSpeed: " + resultSet.getDouble("downloadSpeed"));
				System.out.println("Your badwidthLimit: " + resultSet.getDouble("limitBandwidth"));
				System.out.println("Your cost in case of an overcharge: " + resultSet.getDouble("overchargeCost"));
				System.out.println("Your package description: " + resultSet.getString("description"));
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}
	
	public static void displayInvoiceInfo (String custID, String connectionString, String sqlID, String sqlPassword) throws SQLException {
		Connection connection = null;
		Statement statement = null;
		Scanner reader = null;
		try {
			ArrayList<String> invoiceIDs = displayInvoiceIssueDate(custID, connectionString, sqlID, sqlPassword);
			reader = new Scanner(System.in);
			
			connection = DriverManager.getConnection(connectionString, sqlID, sqlPassword);
		
			String idSelected = "";
			
			do {
				System.out.println("Please enter the invoice ID for the invoice you want to view");
				idSelected = reader.nextLine();
			} while (!invoiceIDs.contains(idSelected));
			
			statement = connection.createStatement();
			
			ResultSet resultSetII = statement.executeQuery("select * from InvoiceItems where invoiceID = " + idSelected);
			double totalBeforeTaxes = 0;
			while (resultSetII.next()) {
				totalBeforeTaxes = resultSetII.getDouble("price");
			}
			
			java.util.Date today = new java.util.Date();
			java.sql.Date sqlDate = new java.sql.Date(today.getTime());
			
			// java.sql.Date sqlDate = new java.sql.Date(today.getTime());
			ResultSet resultSetFedTax = statement.executeQuery("select rates from Taxes where startDate < to_date('" + sqlDate + "', 'yyyy-MM-dd') and endDate > to_date('" + sqlDate + "', 'yyyy-MM-dd') and type = 'GST'");
			double fedTax = 0;
			while(resultSetFedTax.next()) {
				fedTax = resultSetFedTax.getDouble("rates");
			}
			
			ResultSet resultSetProvTax = statement.executeQuery("select rates from Taxes where startDate < to_date('" + sqlDate + "', 'yyyy-MM-dd') and endDate > to_date('" + sqlDate + "', 'yyyy-MM-dd') and type = 'QST'");
			double provTax = 0;
			while (resultSetProvTax.next()) {
				provTax = resultSetProvTax.getDouble("rates");
			}
			
			double totalAfterTaxes = totalBeforeTaxes + totalBeforeTaxes * (fedTax * 0.01) + totalBeforeTaxes * (provTax * 0.01);
			
			ResultSet resultSet = statement.executeQuery("select * from Invoice where invoiceID = " + idSelected);
			while (resultSet.next()) {
				System.out.println("Invoice ID: " + idSelected);
				System.out.println("Issue date: " + resultSet.getDate("creationDate"));
				System.out.println("Due date: " + resultSet.getDate("dueDate"));
				System.out.println("Amount due before taxes: " + totalBeforeTaxes);
				System.out.println("Amount due after taxes: " + totalAfterTaxes);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (connection != null) {
				connection.close();
			}
			
			if (reader != null) {
				reader.close();
			}
		}
	}
	
	public static void upgradePackage(String custID, String connectionString, String sqlID, String sqlPassword) throws SQLException {
		Connection connection = null;
		Scanner reader = null;
		
		try {
			connection = DriverManager.getConnection(connectionString, sqlID, sqlPassword);
			reader = new Scanner(System.in);
			
			ArrayList<String> moreExpensivePackageIDs = displayMoreExpensivePackages(custID, connectionString, sqlID, sqlPassword);
			
			String packageSelected = "";
			do {
				System.out.println("Please select the ID of that which you want");
				packageSelected = reader.nextLine();				
			} while (!moreExpensivePackageIDs.contains(packageSelected));
			
			CallableStatement callableStmt = connection.prepareCall("{call upgradePackage(?,?)}");
			
			callableStmt.setString(1, custID);
			callableStmt.setString(2, packageSelected);
			callableStmt.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (connection != null) {
				connection.close();
			}
			
			if (reader != null) {
				reader.close();
			}
		}
	}
	
	public static void scheduleServiceRequest (String custID, String connectionString, String sqlID, String sqlPassword) throws SQLException {
		Connection connection = null;
		Scanner reader = null;
		
		try {
			connection = DriverManager.getConnection(connectionString, sqlID, sqlPassword);
			reader = new Scanner(System.in);
			
			
			String dateString = "";
			java.sql.Date scheduledDate = null;
			java.util.Date date = new java.util.Date();
			java.sql.Date today = new java.sql.Date(date.getTime());
			do {
				System.out.println("When do you want to receive a call? Enter a date in the YYY-MM-DD format: ");
				dateString = reader.nextLine();
			
			
				scheduledDate = java.sql.Date.valueOf(
					LocalDate.from(
							DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(dateString)));
			} while(scheduledDate.before(today));
			
			System.out.println("What type of request is it?");
			String reqType = reader.nextLine();
			
			System.out.println("Give a brief description of your issue: ");
			String notes = reader.nextLine();
			
			CallableStatement callableStmt = connection.prepareCall("{call scheduleServiceRequest(?, ?, ?, ?)}");
			callableStmt.setString(1, custID);
			callableStmt.setDate(2, scheduledDate);
			callableStmt.setString(3, notes);
			callableStmt.setString(4, reqType);
			callableStmt.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (connection != null) {
				connection.close();
			}
			
			if (reader != null) {
				reader.close();
			}
		}
		
	}
	
	public static ArrayList<String> displayInvoiceIssueDate(String custID, String connectionString, String sqlID, String sqlPassword) throws SQLException {
		Connection connection = null;
		Statement statement = null;
		
		try {
			connection = DriverManager.getConnection(connectionString, sqlID, sqlPassword);
			statement = connection.createStatement();
			
			ResultSet resultSet = statement.executeQuery("select invoiceID, creationDate from Invoice where customerID = " + custID);
			
			ArrayList<String> invoiceIDs = new ArrayList<String>();
			
			System.out.println("Here are your invoices. Please select the invoiceID for the date you want to view");
			while (resultSet.next()) {
				System.out.println("Date: " + resultSet.getDate("creationDate") + " invoiceID: " + resultSet.getString("invoiceID"));
				invoiceIDs.add(resultSet.getString("invoiceID"));
			}
			
			return invoiceIDs;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		return null;
	}
	
	public static ArrayList<String> displayMoreExpensivePackages (String custID, String connectionString, String sqlID, String sqlPassword) throws SQLException {
		Connection connection = null;
		Statement statement = null;
		
		try {
			connection = DriverManager.getConnection(connectionString, sqlID, sqlPassword);
			statement = connection.createStatement();
			
			ResultSet rSetPackID = statement.executeQuery("select packageID from Customer where customerID = " + custID);
			String packID = "";
			while (rSetPackID.next()) {
				packID = rSetPackID.getString(1);
			}
			
			ResultSet rSetPackPrice = statement.executeQuery("select price from Package where packageID = " + packID);
			double packPrice = 0;
			while (rSetPackPrice.next()) {
				packPrice = rSetPackPrice.getDouble(1);
			}
			
			ResultSet resultSet = statement.executeQuery("select packageID, description from Package where price > " + packPrice);
			
			ArrayList<String> packageIDs = new ArrayList<String>();
			System.out.println("Here are the packages that you can upgrade to:");
			while (resultSet.next()) {
				System.out.println("Package ID: " + resultSet.getString("packageID"));
				System.out.println("Package Description: " + resultSet.getString("description"));
				packageIDs.add(resultSet.getString("packageID"));
			}
			
			return packageIDs;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		return null;
	}
}
