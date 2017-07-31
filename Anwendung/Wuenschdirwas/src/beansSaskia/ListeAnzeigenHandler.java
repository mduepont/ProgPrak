package beansSaskia;

import java.time.LocalDate;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIInput;

import dao.WuenschdwDAOImple;
import daten.Wuensche;
import daten.Wuenschliste;


@ManagedBean(name="listeS")
@SessionScoped
public class ListeAnzeigenHandler {
	private Wuenschliste liste;
	private String uuid;
	private String anlasse;
	private LocalDate datum;
	private boolean ueberraschung;
	private Wuensche wuensche;
	private Wuensche wunschGewaehlt;
	private String nameSchenker;
	
	
	

	public String getNameSchenker() {
		return nameSchenker;
	}
	public void setNameSchenker(String schenker) {
		this.nameSchenker = schenker;
	}
	public Wuensche getWunschGewaehlt() {
		return wunschGewaehlt;
	}
	public void setWunschGewaehlt(Wuensche wunschGewaehlt) {
		this.wunschGewaehlt = wunschGewaehlt;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
		listeLaden();
		System.out.println(this.uuid.toString());
	}
	public Wuenschliste getListe() {
		return liste;
	}
	public void setListe(Wuenschliste liste) {
		this.liste = liste;
	}
	public String getAnlasse() {
		return anlasse;
	}
	public void setAnlasse(String anlasse) {
		this.anlasse = anlasse;
	}
	public LocalDate getDatum() {
		return datum;
	}
	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}
	public boolean isUeberraschung() {
		return ueberraschung;
	}
	public void setUeberraschung(boolean ueberraschung) {
		this.ueberraschung = ueberraschung;
	}
	public Wuensche getWuensche() {
		return wuensche;
	}
	public void setWuensche(Wuensche wuensche) {
		this.wuensche = wuensche;
	}
	
	private void listeLaden(){
//		System.out.println(uuid.toString());
//		System.out.println(uuid);
		liste = WuenschdwDAOImple.getInstance().ladeWunschliste(uuid);
		System.out.println(liste.getAnlass());
	}
	public String schenken(){
		System.out.println("schenken");
		System.out.println(wunschGewaehlt.toString());
//		wunschGewaehlt.setSchenker("xy");
//		System.out.println(wunschGewaehlt.toString());
//		System.out.println(wunschGewaehlt.getId());
//		WuenschdwDAOImple.getInstance().aendereWunsch(wunschGewaehlt.getId(), wunschGewaehlt);
		return "NameSchenker";
	}
	
	public String schenkerBestaetigen(){
		wunschGewaehlt.setSchenker(nameSchenker);
		System.out.println("Schenken: "+wunschGewaehlt.toString());
		setWunschGewaehlt(null);
		setNameSchenker(null);
		return "PersoenlicheListe";
	}
}
