package beansSaskia;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import dao.WuenschdwDAOImple;
import daten.Wuenschliste;

@ManagedBean(name="erstellenS")
@SessionScoped
public class ListeErstellenHandler {

	private String anlass;
	private String titel;
	private String beschreibung;
	private LocalDate ablaufdatum;
	private Wuenschliste wunschliste;
	private String passwort;
	private String email;
	public String getAnlass() {
		return anlass;
	}
	public void setAnlass(String anlass) {
		this.anlass = anlass;
	}
	public String getTitel() {
		return titel;
	}
	public void setTitel(String titel) {
		this.titel = titel;
	}
	public String getBeschreibung() {
		return beschreibung;
	}
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
	public LocalDate getAblaufdatum() {
		return ablaufdatum;
	}
	public void setAblaufdatum(LocalDate ablaufdatum) {
		this.ablaufdatum = ablaufdatum;
	}
	public Wuenschliste getWunschliste() {
		return wunschliste;
	}
	public void setWunschliste(Wuenschliste wunschliste) {
		this.wunschliste = wunschliste;
	}
	public String getPasswort() {
		return passwort;
	}
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public SelectItem[] getAnlaesse(){
		SelectItem[] items = null;
		
		ArrayList<String> anlaesse =
				WuenschdwDAOImple.getInstance().anlaesseLaden();
		if(anlaesse != null){
			int zeilen = anlaesse.size();
			items = new SelectItem[zeilen];
			for(int i = 0; i < zeilen; i++){
				items[i] = new SelectItem(anlaesse.get(i).toLowerCase(), anlaesse.get(i));
			}
		}
		return items;
	}
	
	public String weiterA(){
		System.out.println("weiterA: "+titel+" "+anlass+" "+ablaufdatum+" "+beschreibung+".");
		return "ErstellenA";
	}
}
