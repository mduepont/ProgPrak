package beans;

import java.time.LocalDate;

/**
 * 
 * @author Hajiba
 *
 */

public class Wuenschliste {
	
	//----------Attributte-------------	
	
	private int idErsteller;
	private String  name;
	private int idListe;
	private String anlass;
	private LocalDate datum;
	private String listepwd;
	private int designid;
	private boolean ueberraschung;
	
	
	
	public Wuenschliste(int idErsteller, String name, int idListe, String anlass, LocalDate datum, String listepwd,
			int designid, boolean ueberraschung) {
	
		this.idErsteller = idErsteller;
		this.name = name;
		this.idListe = idListe;
		this.anlass = anlass;
		this.datum = datum;
		this.listepwd = listepwd;
		this.designid = designid;
		this.ueberraschung = ueberraschung;
	}



	public Wuenschliste() {
		super();
	}



	public int getIdErsteller() {
		return idErsteller;
	}

	public void setIdErsteller(int idErsteller) {
		this.idErsteller = idErsteller;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIdListe() {
		return idListe;
	}

	public void setIdListe(int idListe) {
		this.idListe = idListe;
	}

	public String getAnlass() {
		return anlass;
	}

	public void setAnlass(String anlass) {
		this.anlass = anlass;
	}

	public LocalDate getDatum() {
		return datum;
	}

	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}

	public String getListepwd() {
		return listepwd;
	}

	public void setListepwd(String listepwd) {
		this.listepwd = listepwd;
	}

	public int getDesignid() {
		return designid;
	}

	public void setDesignid(int designid) {
		this.designid = designid;
	}

	public boolean isUeberraschung() {
		return ueberraschung;
	}

	public void setUeberraschung(boolean ueberraschung) {
		this.ueberraschung = ueberraschung;
	}
	
	

	
	
	
	
}