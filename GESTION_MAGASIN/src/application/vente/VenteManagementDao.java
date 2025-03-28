package application.vente;

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

public class VenteManagementDao extends AbstractDao implements IVenteManagement{

	Connection conn = s.getConnection();
    PreparedStatement prst = null;
    ResultSet rs = null;
public List<Vente> getAll(){
	List<Vente> produits=new ArrayList<Vente>();
	  try {
      // Cr�er l'instruction SQL
	  String sql="select * from Vente";
      prst = conn.prepareStatement(sql);
      rs=prst.executeQuery();
      while(rs.next()){
    	produits.add(new Vente(rs.getLong("id"),rs.getLong("client_id"),rs.getLong("produit_id"),rs.getInt("quantite"),rs.getDate("date_vente")));
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
public void add(Vente p) {
	// TODO Auto-generated method stub
	String query="insert into Vente values(?,?,?,?,?,?)";
	try {
		prst=conn.prepareStatement(query);
		prst.setLong(1, p.getId());
		prst.setLong(2,p.getClientId());
		prst.setLong(3, p.getProduitId());
		prst.setInt(4, p.getQuantite());
		prst.setDouble(5, p.getMontant());
		Date date = p.getDateVente();
		Instant instant = date.toInstant();
		LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
		String formattedDate = localDate.format(DateTimeFormatter.ISO_DATE);
		prst.setString(6, formattedDate);
		prst.execute();
		//System.out.println("ref s dans add: "+conn);
	}catch(SQLException e){
		e.printStackTrace();
	}

}

@Override
public void delete(long id) {
	// TODO Auto-generated method stub
	String query="delete from vente where id=?";
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
public Vente getOne(long id) {
	// TODO Auto-generated method stub
	String query="select * from vente where id=?";
	try {
		prst=conn.prepareStatement(query);
		prst.setLong(1, id);
		rs=prst.executeQuery();
		if(rs.next()) {
			// System.out.println("ref s,conn dans getOne: "+s+conn);
			return new Vente(rs.getLong("id"),rs.getLong("client_id"),rs.getLong("produit_id"),rs.getInt("quantite"),rs.getDate("date_vente"));
		}
	}catch(SQLException e){
		e.printStackTrace();
	}

	return null;
}

@Override
public List<Vente> getAll(String designation) {
	// TODO Auto-generated method stub
	List<Vente> produits=new ArrayList<Vente>();
	String query="select * from vente where desigantion=?";
	try {
		prst=conn.prepareStatement(query);
		prst.setString(1, designation);
		rs=prst.executeQuery();
		while(rs.next()) {
			produits.add(new Vente(rs.getLong("id"),rs.getLong("client_id"),rs.getLong("produit_id"),rs.getInt("qte"),rs.getDate("datec")));
		}
		 System.out.println("ref s,conn dans getAll(des): "+s+conn);
		return produits;
	}catch(SQLException e){
		e.printStackTrace();
	}
	return null;
}

@Override
public void update(Vente p) {
	// TODO Auto-generated method stub
	String query ="update vente set client_id=?,produit_id=? ,quantite=?,montant=?,date_vente=? where id=?";
	try {
		prst=conn.prepareStatement(query);
		prst.setLong(1,p.getClientId());
		prst.setLong(2, p.getProduitId());
		prst.setLong(3, p.getQuantite());
		prst.setDouble(4, p.getMontant());
		Date date = p.getDateVente();
		Instant instant = date.toInstant();
		LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
		String formattedDate = localDate.format(DateTimeFormatter.ISO_DATE);
		prst.setString(5,formattedDate);
		prst.setLong(6, p.getId());
		prst.execute();
	}
	catch(SQLException e) {
		e.printStackTrace();
	}
	// System.out.println("ref s dans update: "+conn);
}








}
