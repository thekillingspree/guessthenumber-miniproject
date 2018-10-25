package GTN;


import java.sql.*;

public class ConnectDatabse {
	private static final String JDBC_PACKAGE = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/theguessinggame";
	
	private static final String USERNAME = "root";
	private static final String PASSWORD = "javaproject";
	
	public static Connection getConnection() {
		Connection connection = null;
		
		try {
			Class.forName(JDBC_PACKAGE);
			connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
		
		return connection;
	}
}
