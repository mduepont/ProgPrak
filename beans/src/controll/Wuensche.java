package controll;

public class Wuensche {
	
	private String name;
	private String beschreibung;
	private String link;

	
	public Wuensche(String name,String beschreibung, String link){
		this.name= name;
		this.beschreibung = beschreibung;
		this.link = link;

	}
	
	public Wuensche(){
		
		super();
	}


	public String getname() {
		return name;
	}


	public void setname(String name) {
		this.name = name;
	}


	public String getBeschreibung() {
		return beschreibung;
	}


	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}


	public String getlink() {
		return link;
	}


	public void setlink(String link) {
		this.link = link;
	}
	

}


