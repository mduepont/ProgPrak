package beansSaskia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.model.SelectItem;

import dao.WuenschdwDAOImple;
import daten.Wuensche;
import daten.Wuenschliste;
import daten.WunschlisteErsteller;

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
	private String nameWunsch;
	private String link;
	private boolean ueberraschung;
	private String linkListe;
	private ArrayList<String> anlaesse;
	private SelectItem[] anlaesseItems;
	private HtmlDataTable wunschtabelle;
	
	
	public HtmlDataTable getWunschtabelle() {
		return wunschtabelle;
	}
	public void setWunschtabelle(HtmlDataTable wunschtabelle) {
		this.wunschtabelle = wunschtabelle;
	}
	public SelectItem[] getAnlaesseItems() {
		return anlaesseItems;
	}
	public void setAnlaesseItems(SelectItem[] anlaesseItems) {
		this.anlaesseItems = anlaesseItems;
	}
	public void setAnlaesse(ArrayList<String> anlaesse) {
		this.anlaesse = anlaesse;
	}
	public String getLinkListe() {
		return linkListe;
	}
	public void setLinkListe(String linkListe) {
		this.linkListe = linkListe;
	}
	public boolean isUeberraschung() {
		return ueberraschung;
	}
	public void setUeberraschung(boolean ueberraschung) {
		this.ueberraschung = ueberraschung;
	}
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
	
	
	public String getNameWunsch() {
		return nameWunsch;
	}
	public void setNameWunsch(String nameWunsch) {
		this.nameWunsch = nameWunsch;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
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
	
	@PostConstruct
	public void init(){
		anlaesse =
				WuenschdwDAOImple.getInstance().anlaesseLaden();
		int zeilen = anlaesse.size();
		int keineAngabe = -1;
		if(anlaesse != null){
			for(int i = 0; i < anlaesse.size(); i++){
				if(anlaesse.get(i).equals("keine Angabe")){
					keineAngabe = i;
				}
			}
			String speicher = anlaesse.get(keineAngabe);
			anlaesse.remove(keineAngabe);
			Collections.sort(anlaesse);
			anlaesseItems = new SelectItem[zeilen];
			for(int i = 1; i < zeilen; i++){
				anlaesseItems[i] = new SelectItem(anlaesse.get(i-1), anlaesse.get(i-1));
			}
//			Arrays.sort(anlaesseItems);
			SelectItem keineAg = new SelectItem(speicher, speicher);
			anlaesseItems[0] = keineAg;
		}
	}
	
	public String weiterA(){
		System.out.println("weiterA: "+titel+" "+anlass+" "+ablaufdatum+" "+beschreibung+".");
		return "ListeErstellenSaskiaB";
	}
	
	public String weiterB(){
		System.out.println(wunschliste.getWuensche().toArray().toString());
		return "ListeErstellenSaskiaC";
	}
	public String datenEntfernen(){
		this.setTitel(null);
		this.setAblaufdatum(null);
		this.setAnlass(null);
		wunschliste.setWuensche(null);
		this.setEmail(null);
		this.setPasswort(null);
		return "ListeErstellenSaskiaA";
	}
	public String speichernWunsch(){
		Wuensche w = new Wuensche();
		w.setName(nameWunsch);
		w.setBeschreibung(beschreibung);
		w.setLink(link);
		if(wunschliste == null ){
			System.out.println("wunschliste gleich null");
			wunschliste = new Wuenschliste();
		}
		if(wunschliste.getWuensche()  == null){
			wunschliste.wuenscheInstanz();
		}
		wunschliste.getWuensche().add(w);
		setNameWunsch(null);
		setBeschreibung(null);
		setLink(null);
		System.out.println("speichernWunsch, länge liste: "+wunschliste.getWuensche().size());
		return "ListeErstellenSaskiaB.xhtml";
	}
	
	public String listeSpeichern(){
		wunschliste.setName(titel);
		wunschliste.setAnlass(anlass);
		wunschliste.setDatum(ablaufdatum);
		wunschliste.setListepwd(passwort);
		wunschliste.setUeberraschung(ueberraschung);
		WunschlisteErsteller ersteller = new WunschlisteErsteller();
		ersteller.setEmail(email);
		int idErsteller = WuenschdwDAOImple.getInstance().speichereErsteller(ersteller);
		System.out.println("idErsteller= "+idErsteller);
		wunschliste.setIdErsteller(idErsteller);
		System.out.println(wunschliste.toString());
		System.out.println(wunschliste.toString());
		linkListe = WuenschdwDAOImple.getInstance().speichereWunschliste(wunschliste);
		return "SpeichernErfolgreich";
	}
	
	public void loeschenWunsch(){
		System.out.println("loeschen ");
		Wuensche w = (Wuensche)wunschtabelle.getRowData();
		
		System.out.println("Wunsch: "+w.toString());
//			setNameWunsch(w.getName());
//			setBeschreibung(w.getBeschreibung());
//			setLink(w.getLink());
		int index = wunschtabelle.getRowIndex();
		wunschliste.getWuensche().remove(index);
		System.out.println("jsf Index: "+index);
	}
}
