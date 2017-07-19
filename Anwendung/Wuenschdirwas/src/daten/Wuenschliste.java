package daten;

import java.time.LocalDate;
import java.util.ArrayList;

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
	private int designid = 1;
	private boolean ueberraschung = false;
	private String zugriffsId;
	private ArrayList<Wuensche> wuensche;
	private String regEx = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{5,8})"; 
	
	
	
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



	public String getZugriffsId() {
		return zugriffsId;
	}



	public void setZugriffsId(String zugriffsId) {
		this.zugriffsId = zugriffsId;
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
		if(ablaufDatumKorrekt(datum)){
			this.datum = datum;
		}
	}

	public String getListepwd() {
		return listepwd;
	}

	public void setListepwd(String listepwd) {
		if(passwortKorrekt(listepwd)){
			this.listepwd = listepwd;
		}
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
	
	public ArrayList<Wuensche> getWuensche() {
		return wuensche;
	}



	public void setWuensche(ArrayList<Wuensche> wuensche) {
		this.wuensche = wuensche;
	}

	public void wuenscheInstanz(){
		wuensche = new ArrayList<Wuensche>();
	}

	private boolean passwortKorrekt(String passwort){
		boolean erg = false;
		if(passwort.matches(regEx)){
			erg = true;
		}
		return erg;
	}
	
	private boolean ablaufDatumKorrekt(LocalDate ablaufdatum){
		//muss in der Zukunft liegen? und weniger als 5 Jahre
		boolean erg = false;
		LocalDate aktuell = LocalDate.now();
		LocalDate max     = aktuell.plusYears(5);
		if(ablaufdatum.compareTo(aktuell) > 0 && ablaufdatum.compareTo(max) < 0){
			erg = true;
		}
		return erg;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((anlass == null) ? 0 : anlass.hashCode());
		result = prime * result + ((datum == null) ? 0 : datum.hashCode());
		result = prime * result + designid;
		result = prime * result + idErsteller;
		result = prime * result + idListe;
		result = prime * result + ((listepwd == null) ? 0 : listepwd.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (ueberraschung ? 1231 : 1237);
		result = prime * result + ((wuensche == null) ? 0 : wuensche.hashCode());
		result = prime * result + ((zugriffsId == null) ? 0 : zugriffsId.hashCode());
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
		Wuenschliste other = (Wuenschliste) obj;
		if (anlass == null) {
			if (other.anlass != null)
				return false;
		} else if (!anlass.equals(other.anlass))
			return false;
		if (datum == null) {
			if (other.datum != null)
				return false;
		} else if (!datum.equals(other.datum))
			return false;
		if (designid != other.designid)
			return false;
		if (idErsteller != other.idErsteller)
			return false;
		if (idListe != other.idListe)
			return false;
		if (listepwd == null) {
			if (other.listepwd != null)
				return false;
		} else if (!listepwd.equals(other.listepwd))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (ueberraschung != other.ueberraschung)
			return false;
		if (wuensche == null) {
			if (other.wuensche != null)
				return false;
		} else if (!wuensche.equals(other.wuensche))
			return false;
		if (zugriffsId == null) {
			if (other.zugriffsId != null)
				return false;
		} else if (!zugriffsId.equals(other.zugriffsId))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "Wuenschliste [idErsteller=" + idErsteller + ", name=" + name + ", idListe=" + idListe + ", anlass="
				+ anlass + ", datum=" + datum + ", listepwd=" + listepwd + ", designid=" + designid + ", ueberraschung="
				+ ueberraschung + ", zugriffsId=" + zugriffsId + ", wuensche=" + wuensche + "]";
	}


}