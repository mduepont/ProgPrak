package beansSaskia;

import java.util.ArrayList;
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

@ManagedBean(name="bearbeitenS")
@SessionScoped
public class ListeBearbeitenHandler {

	private String zugangscode;
	private String passwort;
	private String passwort1;
	private String passwort2;
	private String info;
	private String pwInfo = "";
	private Wuenschliste liste;
	private WunschlisteErsteller ersteller; 
	private ArrayList<String> anlaesse;
	private SelectItem[] anlaesseItems;
	private Wuensche wunschBearb;
	private Wuensche speicherWunsch;
	private HtmlDataTable wunschtabelle;
	private int indexWunsch;
	private int listenGroesse;
	private String nameWunsch;
	private String link;
	private String beschreibung;
	
	
	
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
	public String getBeschreibung() {
		return beschreibung;
	}
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
	public int getListenGroesse() {
		System.out.println("groesse liste: "+liste.getWuensche().size());
		return liste.getWuensche().size();
	}
	public int getIndexWunsch() {
		return indexWunsch;
	}
	public void setIndexWunsch(int indexWunsch) {
		this.indexWunsch = indexWunsch;
	}
	public HtmlDataTable getWunschtabelle() {
		return wunschtabelle;
	}
	public void setWunschtabelle(HtmlDataTable wunschtabelle) {
		this.wunschtabelle = wunschtabelle;
	}
	public Wuensche getSpeicherWunsch() {
		return speicherWunsch;
	}
	public void setSpeicherWunsch(Wuensche speicherWunsch) {
		this.speicherWunsch = speicherWunsch;
	}
	public String getPwInfo() {
		return pwInfo;
	}
	public void setPwInfo(String pwInfo) {
		this.pwInfo = pwInfo;
	}
	public String getPasswort() {
		return passwort;
	}
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}
	public WunschlisteErsteller getErsteller() {
		return ersteller;
	}
	public void setErsteller(WunschlisteErsteller ersteller) {
		this.ersteller = ersteller;
	}
	public Wuenschliste getListe() {
		return liste;
	}
	public void setListe(Wuenschliste liste) {
		this.liste = liste;
	}
	public String getZugangscode() {
		return zugangscode;
	}
	public void setZugangscode(String zugangscode) {
		this.zugangscode = zugangscode;
	}
	public String getPasswort1() {
		return passwort1;
	}
	public void setPasswort1(String passwort) {
		this.passwort1 = passwort;
	}
	
	public Wuensche getWunschBearb() {
		return wunschBearb;
	}
	public void setWunschBearb(Wuensche wunschBearb) {
		this.wunschBearb = wunschBearb;
	}
	public String getPasswort2() {
		return passwort2;
	}
	public void setPasswort2(String passwort2) {
		this.passwort2 = passwort2;
	}
	public void setAnlaesse(ArrayList<String> anlaesse) {
		this.anlaesse = anlaesse;
	}
	public SelectItem[] getAnlaesseItems() {
		return anlaesseItems;
	}
	public void setAnlaesseItems(SelectItem[] anlaesseItems) {
		this.anlaesseItems = anlaesseItems;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
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
	
	public String anzeigen(){
		info="";
		boolean zugangsCode = WuenschdwDAOImple.getInstance().listeSuchen(zugangscode);
		boolean passwortStimmt = WuenschdwDAOImple.getInstance().listeNachPasswort(passwort);
		if(zugangsCode == true && passwortStimmt == true){
			liste = WuenschdwDAOImple.getInstance().ladeWunschliste(zugangscode);
			ersteller = WuenschdwDAOImple.getInstance().ladeErstellerId(liste.getIdErsteller());
			return "ListeBearbeitenSaskiaB?faces-redirect=true";
		}
		if(zugangsCode == true && passwortStimmt == false){
			info = "Leider stimmt das Passwort nicht mit dem der angegebenen Liste überein";
		}
		if(zugangsCode == false && passwortStimmt == false){
			info = "Leider konnten wir keinen passenden Eintrag finden";
		}	
		return "ListeBearbeitenSaskiaA?faces-redirect=true";
	}
	
	public String zurListe(){
		return "ListeBearbeitenSaskiaListe?faces-redirect=true";
	}
	public String zuDenWuenschen(){
		return "ListeBearbeitenSaskiaWuensche?faces-redirect=true";
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
	
	public String fertig(){
		return "/index.xhtml?faces-redirect=true";
	}
	
	public String weiter(){
		return "ListeBearbeitenSaskiaB?faces-redirect=true";
	}
	
	public String aendern(){
		if(pwInfo.equals("Passwort erfolgreich geändert")){
			liste.setListepwd(passwort2);
			setPasswort1(null);
			setPasswort2(null);
			pwInfo = "";
		}
		
		WuenschdwDAOImple.getInstance().aenderWunschliste(liste);
		return "ListeBearbeitenSaskiaErfolg?faces-redirect=true";
	}
	
	public String pwAendern(){
		pwInfo = "Passwort erfolgreich geändert";
		return ".";
	}
	
	public String bearbeitenWunsch(){
		speicherWunsch = new Wuensche();
		speicherWunsch.setName(wunschBearb.getName());
		speicherWunsch.setLink(wunschBearb.getLink());
		speicherWunsch.setBeschreibung(wunschBearb.getBeschreibung());
		speicherWunsch.setSchenker(wunschBearb.getSchenker());
		indexWunsch = wunschtabelle.getRowIndex();
		return "ListeBearbeitenSaskiaWunsch?faces-redirect=true";
	}
	public String wunschAendernBestaetigen(){
		System.out.println("Wunsch bestätigen");
		wunschBearb.setName(speicherWunsch.getName());
		wunschBearb.setLink(speicherWunsch.getLink());
		wunschBearb.setBeschreibung(speicherWunsch.getLink());
		WuenschdwDAOImple.getInstance().aendereWunsch(wunschBearb);
		return "ListeBearbeitenSaskiaWuensche?faces-redirect=true";
	}
	public String wuenscheAendernFertig(){
		return "ListeBearbeitenSaskiaErfolg?faces-redirect=true";
	}
public String listeLoeschen(){
		
		return "ListeBearbeitenSaskiaLoeschen?faces-redirect=true";
	}
	
	public String endgueltigLoeschen(){
		WuenschdwDAOImple.getInstance().loescheWunschliste(liste.getIdListe());
		System.out.println("löschen liste: "+liste.toString());
		zugangscode = null;
		passwort = null;
		passwort1 = null;
		passwort2 = null;
		info = null;
		pwInfo = "";
		liste = null;
		ersteller = null; 
		wunschBearb = null;
		speicherWunsch = null;
		indexWunsch = -1;
		return "/index?faces-redirect=true";
	}
	
	public String wunschLoeschenEndg(){
		WuenschdwDAOImple.getInstance().loescheWunsch(wunschBearb.getId());
		liste.getWuensche().remove(indexWunsch);
		return "ListeBearbeitenSaskiaWuensche?faces-redirect=true";
	}
	public String zurueckWunsch(){
		return "ListeBearbeitenSaskiaWuensche?faces-redirect=true";
	}
	public String zurueckWuensche(){
		return "ListeBearbeitenSaskiaB?faces-redirect=true";
	}
	public String neuerWunsch(){
		return "ListeBearbeitenSaskiaNeu?faces-redirect=true";
	}
	public String speichernWunschNeu(){
		Wuensche w = new Wuensche();
		w.setName(nameWunsch);
		w.setBeschreibung(beschreibung);
		w.setLink(link);
		WuenschdwDAOImple.getInstance().speichereWunsch(liste.getIdListe(), w);
		liste.getWuensche().add(w);
		setNameWunsch(null);
		setBeschreibung(null);
		setLink(null);
		return "ListeBearbeitenSaskiaWuensche?faces-redirect=true";
	}
}
