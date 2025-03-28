package application.client;

public class Client {
public Client(long id, String firstname, String lastname, String tel) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.tel = tel;

	}
public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}

	
private long id ;
private String firstname,lastname,tel;

}

