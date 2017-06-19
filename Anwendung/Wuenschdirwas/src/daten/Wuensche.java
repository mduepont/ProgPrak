package daten;

public class Wuensche {
	
	private String name = null;
	private String beschreibung = null;
	private String link = null;
	private int id = -1;
	private String schenker;

	
	public Wuensche(String name,String beschreibung, String link){
		this.name= name;
		this.beschreibung = beschreibung;
		this.link = link;

	}
	
	public Wuensche(){
		super();
	}


	
	public String getSchenker() {
		return schenker;
	}

	public void setSchenker(String schenker) {
		this.schenker = schenker;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getBeschreibung() {
		return beschreibung;
	}


	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}


	public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((beschreibung == null) ? 0 : beschreibung.hashCode());
		result = prime * result + id;
		result = prime * result + ((link == null) ? 0 : link.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((schenker == null) ? 0 : schenker.hashCode());
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
		Wuensche other = (Wuensche) obj;
		if (beschreibung == null) {
			if (other.beschreibung != null)
				return false;
		} else if (!beschreibung.equals(other.beschreibung))
			return false;
		if (id != other.id)
			return false;
		if (link == null) {
			if (other.link != null)
				return false;
		} else if (!link.equals(other.link))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (schenker == null) {
			if (other.schenker != null)
				return false;
		} else if (!schenker.equals(other.schenker))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Wuensche [name=" + name + ", beschreibung=" + beschreibung + ", link=" + link + ", id=" + id
				+ ", schenker=" + schenker + "]";
	}
	

	
}


