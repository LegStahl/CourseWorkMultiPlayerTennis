package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.Driver;

public class DataBase {
	private final String URL = "jdbc:mysql://127.0.0.1:3306/game";
	
	private final String NAME = "root";
	
	private final String PASSWORD = "root";
	
	private Connection connection;
	
	private PreparedStatement preparedStatement;
	
	private final String INSERT = "INSERT INTO users VALUES(?, ?, ?, ?, ?)";
	
	private final String GETALL = "SELECT * FROM users";
	
	private final String GETID = "SELECT idname, password FROM users where name = ?";
	
	private final String INSERTWHERE = "UPDATE users SET countgames = ? , countvictory = ? WHERE idname = ?";;
	
	private final String GETFIGHTANDVICTORIES = "SELECT countgames, countvictory FROM users where idname = ?";
	
	private final static int WRONGPASSWORD = -1;
	
	private final static int WRONGNAMEUSER = -2;
	
	private final static int EXCEPTION = 0;
	
	private final static int ALLRIGHT = 1;
	
	private int lastID;
	
	

	
	public DataBase() {
		try {
			Driver driver = new Driver();
			DriverManager.registerDriver(driver);
			connection = DriverManager.getConnection(URL, NAME, PASSWORD);
			if(!connection.isClosed()) {
				System.out.println("Succeed");
			}
			preparedStatement = connection.prepareStatement(GETALL);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				 lastID = resultSet.getInt(1);
			}
			preparedStatement.close();
			
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public synchronized int resultFight(int idUser, boolean win) {
		try {
		
			preparedStatement = connection.prepareStatement(GETFIGHTANDVICTORIES);
			
			preparedStatement.setInt(1, idUser);
			
			ResultSet res = preparedStatement.executeQuery();
			
			if(res.next()) {
				int countFights = res.getInt(1);
				int countWin = res.getInt(2);
				if(win) {
					countFights++;
					countWin++;
					preparedStatement = connection.prepareStatement(INSERTWHERE);
					preparedStatement.setInt(1, countFights);
					preparedStatement.setInt(2, countWin);
					preparedStatement.setInt(3, idUser);
					preparedStatement.executeUpdate();
					preparedStatement.close();
					return ALLRIGHT;
				}
				else {
					countFights++;
					preparedStatement = connection.prepareStatement(INSERTWHERE, Statement.RETURN_GENERATED_KEYS);
					preparedStatement.setInt(1, countFights);
					preparedStatement.setInt(2, countWin);
					preparedStatement.setInt(3, idUser);
					preparedStatement.executeUpdate();
					preparedStatement.close();
					return ALLRIGHT;
				}
			}
			else {
				return EXCEPTION;
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
			return EXCEPTION;
		}
	}
	
	public synchronized int addNewUser(String login, String password) {
		try {
			preparedStatement = connection.prepareStatement(INSERT);
			lastID++;
			preparedStatement.setInt(1, lastID);
			preparedStatement.setString(2, login);
			preparedStatement.setString(3, password);
			preparedStatement.setInt(4, 0);
			preparedStatement.setInt(5, 0);
			preparedStatement.execute();
			
			preparedStatement.close();
			return lastID;
		}catch(SQLException e) {
			System.out.println(e.getMessage());
			return EXCEPTION;
		}
	}
	
	public synchronized int verifyUser(String name, String password) {
		try {
			int idFind = 1;
			
			int numberPass = 2;
			
			preparedStatement = connection.prepareStatement(GETID);
			
			preparedStatement.setString(idFind, name);
			
			ResultSet res = preparedStatement.executeQuery();
			
			if(res.next()) {
				int id = res.getInt(1);
				String realPassword = res.getString(numberPass);
				if(realPassword.equals(password)) {
					preparedStatement.close();
					return id;
				}
				preparedStatement.close();
				return WRONGPASSWORD;
			}
			else {
				return WRONGNAMEUSER;
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
			return EXCEPTION;
		}
	}
}
