/**
 * Interface das die Methoden zum Speichern und Laden von Listenerstellern und Listen sammelt
 */
package dao;

import java.util.ArrayList;

import daten.Wuensche;
import daten.Wuenschliste;
import daten.WunschlisteErsteller;

public interface WuenschdwDAO {

	public ArrayList<String> anlaesseLaden(); 
	
	public Wuensche speichereWunsch(int idListe, Wuensche wunsch); 
	public boolean aendereWunsch(int idWunsch, Wuensche wunsch);
	public boolean loescheWunsch(int idWunsch); 
	
//	public ArrayList<Design> designsLaden();  
	
	public int speichereErsteller(WunschlisteErsteller ersteller);
	public WunschlisteErsteller ladeErsteller(String email);
	public WunschlisteErsteller ladeErstellerId(int idErsteller);
//	public boolean aendereErsteller(WunschlisteErsteller ersteller);
	public boolean loescheErsteller(int id);
	
	//lieferts URL pattern zurück
	public String speichereWunschliste(Wuenschliste liste);
	public Wuenschliste ladeWunschliste(String zugriffsId);
	public boolean aenderWunschliste(Wuenschliste liste);
	public boolean loescheWunschliste(int idListe);
	
	public boolean listeSuchen(String uuid);
	public boolean listeNachPasswort(String passwort);
	
//	public Wuenschliste sucheWunschlistenKategorie(String Kategorie);
//	public Wuenschliste sucheWunschlistenXY(xy)
	
}
