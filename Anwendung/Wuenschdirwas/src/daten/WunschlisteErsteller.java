package daten;

/**
 * 
 * @author Hajiba
 *
 */
public class WunschlisteErsteller {
	//----attribute
	
	 private String name;
	 private int id = -1;
	 private String email;
	 

	public WunschlisteErsteller(String name, int id, String email) {
		this.name = name;
		this.id = id;
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


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	 
	 
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WunschlisteErsteller other = (WunschlisteErsteller) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WunschlisteErsteller [name=" + name + ", id=" + id + ", email=" + email + "]";
	}
	
	 
	 
}
