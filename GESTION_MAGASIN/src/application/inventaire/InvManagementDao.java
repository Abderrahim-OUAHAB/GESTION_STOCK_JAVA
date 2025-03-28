package application.inventaire;

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

public class InvManagementDao  extends AbstractDao implements IinvManagement {
	Connection conn = s.getConnection();
    PreparedStatement prst = null;
    ResultSet rs = null;
public List<Inventaire> getAll(){
	List<Inventaire> produits=new ArrayList<Inventaire>();
	  try {
      // Cr�er l'instruction SQL
	  String sql="select * from Inventaire";
      prst = conn.prepareStatement(sql);
      rs=prst.executeQuery();
      while(rs.next()){
    	produits.add(new Inventaire(rs.getLong("id"),rs.getLong("produit_id"),rs.getInt("quantite"),rs.getDate("date_ajout")));
      }
	  }
      catch (SQLException e) {
  		// TODO Auto-generated catch block
  		e.printStackTrace();
  	}
	 // System.out.println("ref s dans getAll: "+conn);
      return produits;
    
}

@Override
public void add(Inventaire p) {
	String query="insert into Inventaire values(?,?,?,?)";
	try {
		prst=conn.prepareStatement(query);
		prst.setLong(1, p.getId());
		prst.setLong(2,p.getProduitId());
		prst.setDouble(3, p.getQuantite());
		Date date = p.getDateAjout();
		Instant instant = date.toInstant();
		LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
		String formattedDate = localDate.format(DateTimeFormatter.ISO_DATE);
		prst.setString(4, formattedDate);
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
	String query="delete from inventaire where id=?";
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
public Inventaire getOne(long id) {
	// TODO Auto-generated method stub
	String query="select * from produit where id=?";
	try {
		prst=conn.prepareStatement(query);
		prst.setLong(1, id);
		rs=prst.executeQuery();
		if(rs.next()) {
			// System.out.println("ref s,conn dans getOne: "+s+conn);
			return new Inventaire(rs.getLong("id"),rs.getLong("produit_id"),rs.getInt("quantite"),rs.getDate("date_ajout"));
		}
	}catch(SQLException e){
		e.printStackTrace();
	}

	return null;
}

@Override
public List<Inventaire> getAll(String designation) {
	// TODO Auto-generated method stub
	List<Inventaire> produits=new ArrayList<Inventaire>();
	String query="select * from produit where designation=?";
	try {
		prst=conn.prepareStatement(query);
		prst.setString(1, designation);
		rs=prst.executeQuery();
		while(rs.next()) {
			produits.add(new Inventaire(rs.getLong("id"),rs.getLong("produit_id"),rs.getInt("quantite"),rs.getDate("date_ajout")));
		}
		 System.out.println("ref s,conn dans getAll(des): "+s+conn);
		return produits;
	}catch(SQLException e){
		e.printStackTrace();
	}
	return null;
}

@Override
public void update(Inventaire p) {
	// TODO Auto-generated method stub
	String query ="update inventaire set  produit_id=?,quantite=?,date_ajout=? where id=?";
	try {
		prst=conn.prepareStatement(query);
	
		prst.setLong(1, p.getProduitId());
		prst.setInt(2, p.getQuantite());
		Date date = p.getDateAjout();
		Instant instant = date.toInstant();
		LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
		String formattedDate = localDate.format(DateTimeFormatter.ISO_DATE);
		prst.setString(3,formattedDate);
		prst.setLong(4, p.getId());
		prst.execute();
	}
	catch(SQLException e) {
		e.printStackTrace();
	}
	// System.out.println("ref s dans update: "+conn);
}
}
