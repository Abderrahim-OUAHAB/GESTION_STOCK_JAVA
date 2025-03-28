package application.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.BD.AbstractDao;

public class ClientManagementDao extends AbstractDao implements IClientManagement {

	Connection conn = s.getConnection();
    PreparedStatement prst = null;
    ResultSet rs = null;
public List<Client> getAll(){
	List<Client> clients=new ArrayList<Client>();
	  try {
      // Cr�er l'instruction SQL
	  String sql="select * from client";
      prst = conn.prepareStatement(sql);
      rs=prst.executeQuery();
      while(rs.next()){
    	  clients.add(new Client(rs.getLong("id"),
    			rs.getString("firstname"),rs.getString("lastname"),rs.getString("tel")));
      }
	  }
      catch (SQLException e) {
  		// TODO Auto-generated catch block
  		e.printStackTrace();
  	}
	 // System.out.println("ref s dans getAll: "+conn);
      return clients;
    
}

@Override
public void add(Client p) {
	String query="insert into client values(?,?,?,?)";
	try {
		prst=conn.prepareStatement(query);
		prst.setLong(1, p.getId());
		prst.setString(2,p.getFirstname());
		prst.setString(3, p.getLastname());
		
		prst.setString(4, p.getTel());
		prst.execute();
		//System.out.println("ref s dans add: "+conn);
	}catch(SQLException e){
		e.printStackTrace();
	}
	// TODO Auto-generated method stub
}

@Override
public void delete(long id) {
	// TODO Auto-generated method stub
	String query="delete from client where id=?";
	try {
		prst=conn.prepareStatement(query);
		prst.setLong(1, id);
		prst.execute();
		//System.out.println("ref s dans delete: "+conn);
	}catch(SQLException e){
		e.printStackTrace();
	}

}

@Override
public Client getOne(long id) {
	// TODO Auto-generated method stub
	String query="select * from client where id=?";
	try {
		prst=conn.prepareStatement(query);
		prst.setLong(1, id);
		rs=prst.executeQuery();
		if(rs.next()) {
			 System.out.println("ref s,conn dans getOne: "+s+conn);
			return new Client(rs.getLong("id"),
	    			rs.getString("firstname"),rs.getString("lastname"),rs.getString("tel"));
		}
	}catch(SQLException e){
		e.printStackTrace();
	}

	return null;
}

@Override
public List<Client> getAll(String designation) {
	// TODO Auto-generated method stub
	List<Client> clients =new ArrayList<Client>();
	String query="select * from client where desigantion=?";
	try {
		prst=conn.prepareStatement(query);
		prst.setString(1, designation);
		rs=prst.executeQuery();
		while(rs.next()) {
			clients.add(new Client(rs.getLong("id"),
	    			rs.getString("firstname"),rs.getString("lastname"),rs.getString("tel")));
		}
		 System.out.println("ref s,conn dans getAll(des): "+s+conn);
		return clients;
	}catch(SQLException e){
		e.printStackTrace();
	}
	return null;
}

@Override
public void update(Client p) {
	// TODO Auto-generated method stub
	String query ="update client set firstname=?,lastname=? ,tel=? where id=?";
	try {
		prst=conn.prepareStatement(query);
		prst.setString(1,p.getFirstname());
		prst.setString(2, p.getLastname());
	
		prst.setString(3, p.getTel());
		prst.setLong(4, p.getId());
		prst.execute();
	}
	catch(SQLException e) {
		e.printStackTrace();
	}
	// System.out.println("ref s dans update: "+conn);
}

}
