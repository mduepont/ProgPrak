package beans;

/**
 * 
 * @author Hajiba
 *
 */
public class WunschlisteErsteller {
	//----attribute
	
	 private String name;
	 private int idWuenschListe;
	 private String email;
	 
	 
	public WunschlisteErsteller(String name, int idWuenschListe, String email) {
		this.name = name;
		this.idWuenschListe = idWuenschListe;
		this.email = email;
	}

	public WunschlisteErsteller(){
		super();
	}
	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getIdWuenschListe() {
		return idWuenschListe;
	}


	public void setIdWuenschListe(int idWuenschListe) {
		this.idWuenschListe = idWuenschListe;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	 
	 
	
	 
	 
}
