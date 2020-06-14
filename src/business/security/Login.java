package business.security;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Login {

	public static String id = "";
	public static Connection connection = null;
	private static SecureRandom random = new SecureRandom();
	private static Scanner reader = new Scanner(System.in);

	public static Connection getConnection() {
		return connection;
	}
	
	public static String getID() {
		return id;
	}
	
	
	// Creates a randomly generated String
	public static String getSalt() {
		return new BigInteger(140, random).toString(32);
	}

	// Takes a password and a salt a performs a one way hashing on them, returning
	// an array of bytes.
	public static byte[] hash(String password, String salt) {
		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");

			/*
			 * When defining the keyspec, in addition to passing in the password and salt,
			 * we also pass in a number of iterations (1024) and a key size (256). The
			 * number of iterations, 1024, is the number of times we perform our hashing
			 * function on the input. Normally, you could increase security further by using
			 * a different number of iterations for each user (in the same way you use a
			 * different salt for each user) and storing that number of iterations. Here, we
			 * just use a constant number of iterations. The key size is the number of bits
			 * we want in the output hash
			 */
			PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 1024, 256);

			SecretKey key = skf.generateSecret(spec);
			byte[] hash = key.getEncoded();
			return hash;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new RuntimeException();
		}
	}

	public static boolean login(String id, String password, String connectionString, String sqlID, String sqlPassword,
			String tableName, String idName) throws SQLException {
		Statement stat;
		ResultSet rs = null;
		connection = DriverManager.getConnection(connectionString, sqlID, sqlPassword);
		connection.setAutoCommit(false);
		stat = connection.createStatement();
		if (password != "") {
			rs = stat.executeQuery("Select salt, passwordHash from " + tableName + " where " + idName + "=" + "'" + id + "'");
			rs.next();
			String salt = rs.getString("salt");
			byte[] passHash = rs.getBytes("passwordHash");
			byte[] compareValue = hash(password, salt);
			if (passHash[1] == compareValue[1] && passHash[0] == compareValue[0]) {
				stat.close();
				return true;
			}
			stat.close();
			return false;
		} else {
			stat.close();
			connection.commit();
			return true;
		}
	}

	public static boolean login(String connectionString, String sqlID, String sqlPassword, String tableName,
			String idName, String position) throws SQLException {
		id = "";
		boolean firstTime = false;
		String password = "";
		String idRegex = "^[1-9]{1,}$";
		String passwordRegex = "^[a-zA-Z0-9._@#$%^&+=]{3,}$";
		connection = DriverManager.getConnection(connectionString, sqlID, sqlPassword);
		Statement stat = connection.createStatement();
		do {
			System.out.println("Enter your id");
			id = reader.nextLine();
		} while (!id.matches(idRegex));		
		ResultSet validate = stat.executeQuery("Select " + idName + " from " + tableName + " where " + idName + " =" + "'" + id + "'");
		if(!validate.next()) {
			return false;
		}
		
		if(position != null) {
			ResultSet pos = stat.executeQuery("Select " + idName + " from " + tableName + " where " + idName + " =" + "'" + id + "' and position = " + "'" + position + "'");
			if(!pos.next()) {
				return false;
			}
		}
		
		ResultSet rs = stat.executeQuery("Select passwordExpired from " + tableName + " where " + idName + " =" + "'" + id + "'");
		rs.next();
		firstTime = rs.getBoolean(1);
		while (!password.matches(passwordRegex)) {
			if (firstTime) {
				break;
			}
			System.out.println("Enter your password\n");
			password = reader.nextLine();
		}

		try {
			return login(id, password, connectionString, sqlID, sqlPassword, tableName, idName);
		} catch (SQLException e) {
			System.out.println("An error has happened: " + e.getMessage());
		}
		return false;
	}

	public static void changePassword(String connectionString, String sqlID, String sqlPassword, String tableName,
			String idName) throws SQLException {
		connection.setAutoCommit(false);
		PreparedStatement pst = connection
				.prepareStatement("update " + tableName + " set salt = ?, passwordHash = ?, passwordExpired = '0' where " + idName + "  = ?");


		String newSalt = getSalt();
		String password;
		String passwordRegex = "^[a-zA-Z0-9._@#$%^&+=]{3,}$";
		do {
			System.out.println("Enter a new password\n");
			password = reader.nextLine();
		} while (!password.matches(passwordRegex));
		pst.setString(1, newSalt);
		pst.setBytes(2, hash(password, newSalt));
		pst.setString(3, id);
		pst.executeUpdate();
		connection.commit();
		pst.close();
	}
}
