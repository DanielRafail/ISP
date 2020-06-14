/**
 *
 */
package business;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import business.security.*;

/**
 * @author 1633028
 *
 */
public class MainApp {

	/**
	 * @param args
	 * @throws SQLException
	 */

	private static Scanner reader = new Scanner(System.in);
	static boolean logged = false;

	public static void main(String[] args) throws SQLException {
		int response = welcomeMessage();
		if (response == 1) {
			customer();
		} else if (response == 2) {
			callEmployee();
		} else if (response == 0) {
			System.exit(0);
		}
	}

	private static int welcomeMessage() {
		int response = -1;
		do {
			System.out.println(
					"Welcome to our official ISP management application.\nIf you are a customer, please press 1\nIf you are an employee, please press 2\nTo quit, please press 0\n");
			try {
				response = reader.nextInt();
			} catch (InputMismatchException e) {
				System.out.println(
						"You seem to have entered an invalid value, please try again\n==========================================================\n");
				reader.nextLine();
			}
		} while (response > 2 || response < 0);
		return response;
	}

	private static void callCustomer() {
		System.out.println("Hello Customer, here are a list of things you can do\n Kill yourself\n");
	}

	private static void callEmployee() throws SQLException, ClassNotFoundException {
		int response = getEmployeeType();
		if (response == 1) {
			manager();
		} else if (response == 2) {
			sales();
		} else if (response == 0) {
			System.exit(0);
		}
	}

	private static void manager() throws SQLException, ClassNotFoundException{

		String id = "";
		boolean managerLogged = false;
		do {
			managerLogged = Login.login("jdbc:oracle:thin:@localhost:1521:XE", "aegisce",
					"A3NAegisce", "Representatives", "employeeID", "Manager");
		} while (!managerLogged);
		int response = -1;
		do {
			System.out.println("Hello Manager, here is a list of things you can do, please chose from it:\n");
			System.out.println(
					"1:Add new Employees\n2:Remove an existing Employee\n0:Exit\n");
			try {
				response = reader.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("You seem to have entered an invalid value, please try again\n==========================================================\n");
				reader.nextLine();
			}
		} while (response > 2 || response < 0);
		managerAction(response, Login.getID());
	}


	private static void sales() throws SQLException {
		if (!logged) {
			do {
				logged = Login.login("jdbc:oracle:thin:@198.168.52.73:1522/pdborad12c.dawsoncollege.qc.ca", "A1633028",
						"password", "Representatives", "employeeID", "Sales");
			} while (!logged);
		}
		int response = -1;

		do {
			System.out.println(
					"Hello sales representative, here is a list of things you can do, please chose from it:\n");
			System.out.println(
					"1:Register new customers\n2:Modify a customer's package\n3:Cold Sales\n4:Change your password\n0:Exit\n");
			try {
				response = reader.nextInt();
			} catch (InputMismatchException e) {
				System.out.println(
						"You seem to have entered an invalid value, please try again\n==========================================================\n");
				reader.nextLine();
			}
		} while (response > 4 || response < 0);
		int callValue = response;
		salesAction(callValue);
	}

	private static void salesAction(int responseValue) throws SQLException {
		switch (responseValue) {
		case 0:
			logged = false;
			System.exit(0);
			break;
		case 1:
			SalesRepresentativeApp.addUser("jdbc:oracle:thin:@198.168.52.73:1522/pdborad12c.dawsoncollege.qc.ca",
					"A1633028", "password");
			sales();
			Login.getConnection().close();
			break;
		case 2:
			SalesRepresentativeApp.changePackage();
			sales();
			Login.getConnection().close();
			break;
		case 3:
			SalesRepresentativeApp.coldCall("jdbc:oracle:thin:@198.168.52.73:1522/pdborad12c.dawsoncollege.qc.ca",
					"A1633028", "password");
			sales();
			Login.getConnection().close();
			break;
		case 4:
			SalesRepresentativeApp.changePassword("jdbc:oracle:thin:@198.168.52.73:1522/pdborad12c.dawsoncollege.qc.ca",
					"A1633028", "password");
			sales();
			Login.getConnection().close();
			break;
		}
	}

	private static int getEmployeeType() {
		int response = -1;
		do {
			System.out.println(
					"Hello Employee\nIf you are a manager, please enter 1\nIf you are a sales representative, please enter 2\nIf you are a technician or part of the tech support team, please enter 3\nIf you are part of the billing team, please enter 4\nTo exit, please press 0\n");
			try {
				response = reader.nextInt();
			} catch (InputMismatchException e) {
				System.out.println(
						"You seem to have entered an invalid value, please try again\n==========================================================\n");
				reader.nextLine();
			}
		} while (response > 5 || response < 0);
		return response;

	}

	private static void managerAction(int responseValue, String ID) throws SQLException, ClassNotFoundException {
		switch (responseValue) {
			case 0:
				reader.close();
				System.exit(0);
				break;
			case 1:
				ManagerApp.addUser("jdbc:oracle:thin:@localhost:1521:XE",
						"aegisce", "A3NAegisce");
				break;
			case 2:
				ManagerApp.removeUser("jdbc:oracle:thin:@localhost:1521:XE",
						"aegisce", "A3NAegisce", ID);
				break;
		}
	}

	private static void customer() throws SQLException {
		Scanner reader = new Scanner(System.in);
		int response = -1;

		String custID = "";
		String password = "";
		System.out.println("Please enter your customerID: ");
		custID = reader.nextLine();
		System.out.println("Please enter your password: ");
		password = reader.nextLine();
		
		boolean isLogged = false;
		//do {
			isLogged = CustomerApp.loginCustomer(custID, password, "jdbc:oracle:thin:@198.168.52.73:1522/pdborad12c.dawsoncollege.qc.ca", "A0834484", "2amaretti");
		//} while(!isLogged);
			
		if (!isLogged) {
			System.out.println("first login failed");
			System.exit(0);
		}
		
		do {
			System.out.println("Welcome Cusotomer! Please select one of the following options to perform a task: ");
			System.out.println("Press 1 to view your informtion \n Press 2 to view your invoice \n Press 3 to upgrade your packace 1 \n Press 4 to schedule a service request \n Press 0 to exit ");
			
			response = reader.nextInt();
		}while (response < 0 || response > 4);
		
		switch (response) {
		case 0:
			System.exit(0);
			break;
		case 1:
			CustomerApp.displayPackageInfo(custID, "jdbc:oracle:thin:@198.168.52.73:1522/pdborad12c.dawsoncollege.qc.ca",
					"A0834484", "2amaretti");
			break;
		case 2: 
			CustomerApp.displayInvoiceInfo(custID, "jdbc:oracle:thin:@198.168.52.73:1522/pdborad12c.dawsoncollege.qc.ca",
					"A0834484", "2amaretti");
			break;
		case 3:
			CustomerApp.upgradePackage(custID, "jdbc:oracle:thin:@198.168.52.73:1522/pdborad12c.dawsoncollege.qc.ca",
					"A0834484", "2amaretti");
			break;
		case 4:
			CustomerApp.scheduleServiceRequest(custID, "jdbc:oracle:thin:@198.168.52.73:1522/pdborad12c.dawsoncollege.qc.ca",
					"A0834484", "2amaretti");
			break;
		}
	}// End customer method

}
