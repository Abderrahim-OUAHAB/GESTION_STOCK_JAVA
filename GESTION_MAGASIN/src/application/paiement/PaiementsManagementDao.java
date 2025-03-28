package application.paiement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import application.BD.AbstractDao;

public class PaiementsManagementDao  extends AbstractDao implements IPaiementsManagement{
	Connection conn = s.getConnection();
    PreparedStatement prst = null;
    ResultSet rs = null;
public List<Paiement> getAll(){
	List<Paiement> clients=new ArrayList<Paiement>();
	  try {
      // Cr�er l'instruction SQL
	  String sql="select * from Paiements";
      prst = conn.prepareStatement(sql);
      rs=prst.executeQuery();
      while(rs.next()){
    	  clients.add(new Paiement(rs.getLong("id"),
    			rs.getLong("cli_id"),rs.getDate("date_paiement"),rs.getString("methode_paiement"),rs.getString("statut_paiement")));
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
public void add(Paiement p) {
	String query="insert into Paiements values(?,?,?,?,?,?)";
	try {
		prst=conn.prepareStatement(query);
		prst.setLong(1, p.getId());
		prst.setLong(2,p.getCliId());
		prst.setDouble(3, p.getMontant());	
		Date date = p.getDatePaiement();
		Instant instant = date.toInstant();
		LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
		String formattedDate = localDate.format(DateTimeFormatter.ISO_DATE);
		prst.setString(4, formattedDate);
		prst.setString(5, p.getMethodePaiement());
		prst.setString(6, p.getStatutPaiement());
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
	String query="delete from paiements where id=?";
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
public Paiement getOne(long id) {
	// TODO Auto-generated method stub
	String query="select * from paiements where id=?";
	try {
		prst=conn.prepareStatement(query);
		prst.setLong(1, id);
		rs=prst.executeQuery();
		if(rs.next()) {
			// System.out.println("ref s,conn dans getOne: "+s+conn);
			return new Paiement(rs.getLong("id"),
	    			rs.getLong("cli_id"),rs.getDate("date_paiement"),rs.getString("methode_paiement"),rs.getString("statut_paiement"));
		}
	}catch(SQLException e){
		e.printStackTrace();
	}

	return null;
}

@Override
public List<Paiement> getAll(String designation) {
	// TODO Auto-generated method stub
	List<Paiement> clients =new ArrayList<Paiement>();
	String query="select * from client where desigantion=?";
	try {
		prst=conn.prepareStatement(query);
		prst.setString(1, designation);
		rs=prst.executeQuery();
		while(rs.next()) {
			clients.add(new Paiement(rs.getLong("id"),
	    			rs.getLong("cli_id"),rs.getDate("date_paiement"),rs.getString("methode_paiement"),rs.getString("statut_paiement")));
		}
		 System.out.println("ref s,conn dans getAll(des): "+s+conn);
		return clients;
	}catch(SQLException e){
		e.printStackTrace();
	}
	return null;
}


@Override
public void update(Paiement p) {
	// TODO Auto-generated method stub
	String query ="update paiements set cli_id=?,montant=?,date_paiement=? ,methode_paiement=?,statut_paiement=? where id=?";
	try {
		prst=conn.prepareStatement(query);
		prst.setLong(1,p.getCliId());
		prst.setDouble(2, p.getMontant());
	
		Date date = p.getDatePaiement();
		Instant instant = date.toInstant();
		LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
		String formattedDate = localDate.format(DateTimeFormatter.ISO_DATE);
		prst.setString(3, formattedDate);
		prst.setString(4, p.getMethodePaiement());
		prst.setString(5, p.getStatutPaiement());
		prst.setLong(6, p.getId());
		prst.execute();
	}
	catch(SQLException e) {
		e.printStackTrace();
	}
	// System.out.println("ref s dans update: "+conn);
}
}
