package business;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

import business.security.CustomerSecurity;
import business.security.Login;
import business.security.ManagerSecurity;

public class ManagerApp {
    private Scanner reader = new Scanner(System.in);
    public static void addUser(String connectionString, String sqlID, String sqlPassword) throws SQLException,ClassNotFoundException {
        ManagerSecurity.newEmployee(connectionString, sqlID, sqlPassword);
    }

    public static void removeUser(String connectionString, String sqlID, String sqlPassword, String ID) throws SQLException, ClassNotFoundException {
        String managerId;
        String employeeId;
        Scanner reader = new Scanner(System.in);
        String idRegex = "^[1-9]{1,}$";

        do {
            System.out.println("Your ID is: " + ID);
            managerId = ID;

            System.out.println("Enter the Employee ID of who you want to remove\n");
            employeeId = reader.nextLine();
        } while (!managerId.matches(idRegex) || !employeeId.matches(idRegex));

        ManagerSecurity.removeEmployee(connectionString, sqlID, sqlPassword, employeeId, managerId);
    }
}
