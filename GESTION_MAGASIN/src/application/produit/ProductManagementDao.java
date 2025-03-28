package application.produit;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import application.BD.AbstractDao;



public class ProductManagementDao extends AbstractDao implements IProduitManagement{
	Connection conn = s.getConnection();
    PreparedStatement prst = null;
    ResultSet rs = null;
public List<Produit> getAll(){
	List<Produit> produits=new ArrayList<Produit>();
	  try {
      // Cr�er l'instruction SQL
	  String sql="select * from produit";
      prst = conn.prepareStatement(sql);
      rs=prst.executeQuery();
      while(rs.next()){
    	produits.add(new Produit(rs.getLong("id"),rs.getString("designation"),rs.getDouble("prix"),rs.getInt("qte"),rs.getDate("datec")));
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
public void add(Produit p) {
	String query="insert into produit values(?,?,?,?,?,?)";
	try {
		prst=conn.prepareStatement(query);
		prst.setLong(1, p.getId());
		prst.setString(2,p.getDesignation());
		prst.setDouble(3, p.getPrix());
		prst.setInt(4, p.getQte());
		prst.setDouble(5, p.getPrix()*p.getQte());
		Date date = p.getDate();
		Instant instant = date.toInstant();
		LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
		String formattedDate = localDate.format(DateTimeFormatter.ISO_DATE);
		prst.setString(6, formattedDate);
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
	String query="delete from produit where id=?";
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
public Produit getOne(long id) {
	// TODO Auto-generated method stub
	String query="select * from produit where id=?";
	try {
		prst=conn.prepareStatement(query);
		prst.setLong(1, id);
		rs=prst.executeQuery();
		if(rs.next()) {
			// System.out.println("ref s,conn dans getOne: "+s+conn);
			return new Produit(rs.getLong("id"),rs.getString("designation"),rs.getDouble("prix"),rs.getInt("qte"),rs.getDate("datec"));
		}
	}catch(SQLException e){
		e.printStackTrace();
	}

	return null;
}

@Override
public List<Produit> getAll(String designation) {
	// TODO Auto-generated method stub
	List<Produit> produits=new ArrayList<Produit>();
	String query="select * from produit where designation=?";
	try {
		prst=conn.prepareStatement(query);
		prst.setString(1, designation);
		rs=prst.executeQuery();
		while(rs.next()) {
			produits.add(new Produit(rs.getLong("id"),rs.getString("desigantion"),rs.getDouble("prix"),rs.getInt("qte"),rs.getDate("date")));
		}
		 System.out.println("ref s,conn dans getAll(des): "+s+conn);
		return produits;
	}catch(SQLException e){
		e.printStackTrace();
	}
	return null;
}

@Override
public void update(Produit p) {
	// TODO Auto-generated method stub
	String query ="update produit set  designation=?,prix=?,qte=?,total=? ,datec=? where id=?";
	try {
		prst=conn.prepareStatement(query);
		prst.setString(1,p.getDesignation());
		prst.setDouble(2, p.getPrix());
		prst.setInt(3, p.getQte());
		prst.setDouble(4, p.getTotal());
		Date date = p.getDate();
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
