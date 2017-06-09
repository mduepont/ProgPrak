/**
 * Interface das die Methoden zum Speichern und Laden von Listenerstellern und Listen sammelt
 */
package dao;

import java.util.ArrayList;

import daten.Wuenschliste;
import daten.WunschlisteErsteller;

public interface WuenschdwDAO {

	public ArrayList<String> anlaesseLaden(); 
	
	public int speichereWunsch(int idListe); //TODO
	public boolean aendereWunsch(int idWunsch); //TODO
	public boolean loescheWunsch(int idWunsch); //TODO
	
//	public ArrayList<Design> designsLaden();  
	
	public int speichereErsteller(WunschlisteErsteller ersteller);
	public WunschlisteErsteller ladeErsteller(String email);
//	public boolean aendereErsteller(WunschlisteErsteller ersteller);
	public boolean loescheErsteller(int id);
	
	public int speichereWunschliste(Wuenschliste liste);
	public Wuenschliste ladeWunschliste(int id);
	public boolean aenderWunschliste(Wuenschliste liste); //TODO
	public boolean loescheWunschliste(Wuenschliste liste);//TODO
	
//	public Wuenschliste sucheWunschlistenKategorie(String Kategorie);
//	public Wuenschliste sucheWunschlistenXY(xy)
	
}
