package business.security;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManagerSecurity extends Login {

	private ManagerSecurity(){}	
	
	private static List<String> allManagers = new ArrayList<String>();
	private static Scanner reader = new Scanner(System.in);
	private static String[] toAdd = new String[6];
	private static String employeeID = null;
	
	public static void newEmployee(String employeeID, String name, String password, String position, String phone, String email, String managerID, String connectionString, String sqlID, String sqlPassword) throws SQLException {
		//For Daniel "jdbc:oracle:thin:@198.168.52.73:1522/pdborad12c.dawsoncollege.qc.ca",
	    //"A1633028", "password"
		Connection connection = DriverManager.getConnection(connectionString, sqlID, sqlPassword);
	    CallableStatement cStmt = connection.prepareCall("call Manager.addEmployee(?,?,?,?,?,?,?,?,?,?)");
		String salt = getSalt();
		cStmt.setString(1, employeeID);
		cStmt.setString(2, name);
		cStmt.setString(3, position);
		cStmt.setString(4, phone);
		cStmt.setString(5, email);
		cStmt.setString(6, managerID);
		cStmt.setBytes(7, hash(password, salt));
		cStmt.setString(8, salt);
		cStmt.setString(9, "0");
		cStmt.setString(10, "1");

		cStmt.execute();
		cStmt.close();
		connection.close();
	}


	public static void newEmployee(String connectionString, String sqlID, String sqlPassword) throws SQLException, ClassNotFoundException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection(connectionString, sqlID, sqlPassword);


		String[] alertValues = {" name", " password", " position", " phone", "n email", "manager id if applicable"  };
		String nameRegex = "^[A-Z]?[a-z]{2,}\\s[A-Z]?[a-z]{2,}$";
		String passwordRegex = "^[a-zA-Z0-9._@#$%^&+=]{3,}$";
		String phoneRegex = "^[0-9]{3}[- ]?[0-9]{3}[- ]?[0-9]{4}$";
		String emailRegex = "^[A-z0-9._%+-]+@[A-z0-9.-]+\\.[A-z]{2,6}$";
		String idRegex = "^[1-9]{1,}$";
		String positionRegex = "^[Ss]ales$|^[Bb]illing$|^[Tt]ech [Ss]upport$|^[Mm]anager$|^[Ii]nstaller$";
		String[] regexArray = {nameRegex, passwordRegex, positionRegex, phoneRegex, emailRegex, idRegex, idRegex};
		String alert = "";

		Statement st = connection.createStatement();
		ResultSet managerList = st.executeQuery("SELECT DISTINCT managerID FROM Representatives WHERE managerID IS NOT NULL");
		while(managerList.next()) {
			//list.next();
			allManagers.add(managerList.getString("managerID"));
			System.out.println(managerList.getString("managerID"));
		}

		String sqlIdentifier = "select employeeIndent.nextval from dual";
		PreparedStatement pst = connection.prepareStatement(sqlIdentifier);
		ResultSet rs = pst.executeQuery();

		if(rs.next())
			employeeID = String.valueOf(rs.getInt(1));

		for(int i = 0; i < allManagers.size(); i++) {
			System.out.println("Manager IDs not available: " + allManagers.get(i));
		}
		for(int i = 0; i < toAdd.length; i ++) {
			alert = "Please enter a" + alertValues[i];
			verifyRegex(toAdd, regexArray, alert, i);
		}
		   
		try {//call sequence for all ids
			newEmployee(employeeID, toAdd[0],toAdd[1], toAdd[2], toAdd[3], toAdd[4], toAdd[5],  connectionString, sqlID, sqlPassword);
			System.out.println("Success!");
		}
		catch (SQLException e) {
			System.out.println("An error has happened: " + e.getMessage());
		}
		st.close();
		connection.close();
	}

	public static void removeEmployee(String connectionString, String sqlID, String sqlPassword, String employeeID, String managerID) throws SQLException, ClassNotFoundException {
		//For Daniel "jdbc:oracle:thin:@198.168.52.73:1522/pdborad12c.dawsoncollege.qc.ca",
		//"A1633028", "password"
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection(connectionString, sqlID, sqlPassword);
		CallableStatement cStmt = connection.prepareCall("call Manager.removeEmployee(?,?)");
		cStmt.setString(1, employeeID);
		cStmt.setString(2, managerID);

		cStmt.execute();
		cStmt.close();
		connection.close();
	}


	private static void verifyRegex(String[] value, String[] regexElement, String alert, int indexValue) {

		if(indexValue < 5) {
		do {
			System.out.println(alert + "\n");
			value[indexValue] = reader.nextLine();
		}while(!value[indexValue].matches(regexElement[indexValue]));
	}else {
		do {
			System.out.println(alert + "\n");
			value[indexValue] = reader.nextLine();
			if(value[indexValue] == "") {
				value[indexValue] = null;
			}
			System.out.println("ID entered: " + value[indexValue]);

		}while(allManagers.contains(value[indexValue]));
		allManagers.add(value[indexValue]);
}
	
}}
