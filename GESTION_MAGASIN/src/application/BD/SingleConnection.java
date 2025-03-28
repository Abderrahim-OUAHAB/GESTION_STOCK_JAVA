package application.BD;

import java.sql.*;
public class SingleConnection {
private Connection connection=null;
private static SingleConnection s=null;

private SingleConnection() {
	 	String URL = "jdbc:mysql://localhost:3306/STOCK";
	    String UTILISATEUR = "root";
	    String MOT_DE_PASSE = "Mysql2004.";
		try {
			connection = DriverManager.getConnection(URL, UTILISATEUR, MOT_DE_PASSE);
		}catch(SQLException e){
			e.printStackTrace();
		}
}

public Connection getConnection() {
	return connection;
}

public static SingleConnection getInstance() {
	if(s==null) {
		synchronized(SingleConnection.class) {
			if(s==null) {
				s=new SingleConnection();
			}
		}
		
	}
	return s;

}

public void setConnection(Connection connection) {
	this.connection = connection;
}

}
