package util;

//import java.sql.DriverManager;

//import com.filemaker.jdbc.Driver;
import java.sql.*;
public class ConnectFilemaker {

	private Connection con;
	private Statement stmt;
	
	
	public Connection getCon() {
		return con;
	}


	public void setCon(Connection con) {
		this.con = con;
	}


	public Statement getStmt() {
		return stmt;
	}


	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}


	public ConnectFilemaker() {
		// register the JDBC client driver

		try {
			Driver d = (Driver) Class.forName("com.filemaker.jdbc.Driver").newInstance();
		} catch (Exception e) {
			System.out.println("Erro ao registrar: " + e);
		}

		// establish a connection to FileMaker
		try {
			// Class.forName("com.filemaker.jdbc.Driver").newInstance();
//			con = DriverManager.getConnection("jdbc:filemaker://192.168.1.2/FasbamX", "odbc", "odbc");
			con = DriverManager.getConnection("jdbc:filemaker://192.168.100.3/FasbamX", "odbc", "odbc");
			// get connection warnings
			SQLWarning warning = null;
			try {
				warning = con.getWarnings();
				/*if (warning == null) {
					System.out.println("No warnings");
					return;
				}*/
				while (warning != null) {
					System.out.println("Warning: " + warning);
					warning = warning.getNextWarning();
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println("Erro ao estabelecer conexão: " + e);
		}
	}
}
