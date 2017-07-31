/**
 * Klasse die das Interface WuenschdwDAO implementiert und somit alle noetigen Methoden
 * zum lesen und schreiben in die Datenbank von Wunschlisten, Wuenschen und Erstellern
 */
package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import daten.Wuensche;
import daten.Wuenschliste;
import daten.WunschlisteErsteller;
import datenbank.DatenbankIO;

public class WuenschdwDAOImple extends DatenbankIO implements WuenschdwDAO {
	
	private static WuenschdwDAOImple instance;
	
	
	/**
	 * Nullkonstruktor, nur innerhalb der Klasse aufrufbar um die 
	 * Implementierung als Singleton zu gewährleisten (Es soll nur eine 
	 * Instanz dieser Klasse existieren können)
	 * Mit Hilfe der Methoden @see getProperties() und @see verbinde()
	 * wird die benötigte Datenbankverbindung bezogen
	 */
	private WuenschdwDAOImple(){
		super();
		getProperties();
		verbinde();
	}
	/**
	 * Methode die die Instanz dieser Klasse zurück liefert
	 * @return instance
	 * Falls keine Instanz besteht, wird diese erzeugt
	 */
	public static synchronized WuenschdwDAOImple getInstance(){
		if(instance == null){
			instance = new WuenschdwDAOImple();
		}
		return instance;
	}
	
	@Override
	/**
	 * Speichert einen uebergebenen Ersteller in die Datenbank
	 * @param ersteller
	 * liefert die ID des gespeicherten Erstellers zurueck
	 * @return id
	 */
	public int speichereErsteller(WunschlisteErsteller ersteller) {
		int id = -1;
		try {
			PreparedStatement stmspeichernersteller =
					getVerbindung().prepareStatement("INSERT INTO ersteller( email) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			stmspeichernersteller.setString(1, ersteller.getEmail());
			int geschrieben = stmspeichernersteller.executeUpdate();
			if(geschrieben == 0){
				throw new SQLException("Kein Ersteller gespeichert");
			}
			try(ResultSet setid = stmspeichernersteller.getGeneratedKeys()){
					if(setid.next()){
						id = setid.getInt(1);
					}
					else {
			                throw new SQLException("Kein Ersteller, keine ID bekommen");
					}
			}
			stmspeichernersteller.close();
		} catch (SQLException e) {
			System.out.println("WuenschdwDAOImple/speichereErsteller(ersteller): ");
			e.printStackTrace();
		}
		return id;
	}


	@Override
	/**
	 * Liesst einen Ersteller aus der Datenbank zu einer uebergebenen Emailadresse
	 * @pram email
	 * liefert den gelesenen Ersteller zurueck
	 * @return ersteller
	 */
	public WunschlisteErsteller ladeErsteller(String email) {
		WunschlisteErsteller ersteller = null;
		try {
			PreparedStatement stmladenersteller =
					getVerbindung().prepareStatement("SELECT * FROM ersteller WHERE email=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmladenersteller.setString(1, email);
			ResultSet setersteller = stmladenersteller.executeQuery();
			if(setersteller.next()){
				ersteller = new WunschlisteErsteller();
				ersteller.setId(setersteller.getInt(1));
				ersteller.setName(setersteller.getString(2));
				ersteller.setEmail(setersteller.getString(3));
			}
			else{
				System.out.println("Kein passender Nutzer vorhanden");
			}
			stmladenersteller.close();
			setersteller.close();
		} catch (SQLException e) {
			System.out.println("WuenschdwDAOImple/ladeErsteller(email): ");
			e.printStackTrace();
		}
		return ersteller;
	}

	
	/*@Override
	 public boolean aendereErsteller(WunschlisteErsteller ersteller) {
		boolean erfolgreich = false;
		try {
			stmaendernersteller.setString(1, ersteller.getName());
			stmaendernersteller.setString(2, ersteller.getEmail());
			stmaendernersteller.setInt(3, ersteller.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return erfolgreich;
	}*/

	@Override
	/**
	 * Loescht einen Ersteller zu einer uebergebenen ID aus der Datenbank
	 * @param id
	 * Liefert zurueck ob dies erfolgreich war oder nicht
	 * @return boolean erfolgreich
	 */
	public boolean loescheErsteller(int id) {
		boolean erfolgreich = false;
		try {
			PreparedStatement stmloeschenersteller =
					getVerbindung().prepareStatement("DELETE FROM ersteller WHERE id_ersteller=?");
			stmloeschenersteller.setInt(1, id);
			int erg = stmloeschenersteller.executeUpdate();
			if(erg == 1){
				erfolgreich = true;
			}
			stmloeschenersteller.close();
		} catch (SQLException e) {
			System.out.println("WuenschedwDAOImple/loescheErsteller(id): ");
			e.printStackTrace();
		}
		
		return erfolgreich;
	}

	private String zugriffsIdGenerieren(){
		String id = null;
		UUID identifizierer = UUID.randomUUID();
		id = identifizierer.toString();
		return id;
	}
	
	@Override
	//Vorbedingung: erstellerid muss gesetzt sein!
	/**
	 * Speichert eine uebergebene Wunschliste in die Datenbank
	 * @param liste
	 * Dabei wird eine UUID für den Zugriff via link generiert @see zugriffsIdGenerieren()
	 * Nutzt dabei die Methode @see speichernUndIdHolenWuensche(wuensche)
	 * Liefert die Zugriffs ID der gespeicherten Liste zurueck
	 * @return String zugriffsId
	 */
	public String speichereWunschliste(Wuenschliste liste) {
//		int id = -1;
		String zugriffsId = zugriffsIdGenerieren();
		liste.setZugriffsId(zugriffsId);
//		id_ersteller,id_zugriff, name_wunschliste, anlass, ablaufdatum, listenpasswort, design_id, uberraschungsmodus
		try {
			PreparedStatement stmspeichernliste =
					getVerbindung().prepareStatement("INSERT INTO wunschliste(id_ersteller, id_zugriff, name_wunschliste, anlass, ablaufdatum, listenpasswort, design_id, uberraschungsmodus) VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			stmspeichernliste.setInt(1, liste.getIdErsteller());
			stmspeichernliste.setString(2, liste.getZugriffsId());
			stmspeichernliste.setString(3, liste.getName());
			stmspeichernliste.setString(4, liste.getAnlass());
			Date date = Date.valueOf(liste.getDatum());
			stmspeichernliste.setDate(5, date);
			stmspeichernliste.setString(6, liste.getListepwd());
			stmspeichernliste.setInt(7, liste.getDesignid());
			stmspeichernliste.setBoolean(8, liste.isUeberraschung());
			int geschrieben = stmspeichernliste.executeUpdate();
			if(geschrieben == 0){
				throw new SQLException("keine Liste gespeichert");
			}try(ResultSet setid = stmspeichernliste.getGeneratedKeys()){
				if(setid.next()){
					int id = setid.getInt(1);
					liste.setIdListe(id); 
					liste.setWuensche(speichernUndIdHolenWuensche(id, liste.getWuensche()));
				}
				else{
					throw new SQLException("keine Id erhalten");
				}
			}
			stmspeichernliste.close();
		} catch (SQLException e) {
			System.out.println("WuenschedwDAOImple/speichereWunschliste(liste): ");
			e.printStackTrace();
		}
		
		return liste.getZugriffsId();
	}
	/**
	 * Liefert eine ArrayList von Wünschen zurück, die gespeichert wurden un um eine ID
	 * ergänzt.
	 * @return wuensche
	 * Eingangsparameter ist die ID der Wunschliste, zu der die Wünsche gespeichert
	 * werden sollen
	 * @param listeID 
	 * und die Wünsche, die gespeichert werden sollen
	 * @param wuensche
	 * Die Methode @see standardWerteWunsch(wunsch) wird verwendet um leere Attribute
	 * eines Wunsches mit Werten zu füllen
	 * Die Methode @see speichereWunsch(liteId, wunsch) wird verwendet um einzelne
	 * Wünsche zu speichern
	 */
	private ArrayList<Wuensche> speichernUndIdHolenWuensche(int listeID, ArrayList<Wuensche> wuensche){
		ArrayList<Wuensche> wuenscheNeu = null;
		ArrayList<Wuensche> speicher = new ArrayList<Wuensche>();
		//wunsch speichern und id setzen!
//		name_wunsch, id_wunsch, beschreibung, link_wunsch
		for(Wuensche w: wuensche){
			w = speichereWunsch(listeID, w);
			speicher.add(w);
		}
		if(speicher.size() > 0){
			wuenscheNeu = speicher;
		}
		return wuenscheNeu;
	}

	/**
	 * Füllt für fehlende Attributwerte eines übergebenen Wunsches standard Werte ein
	 * @param  Wuensche w, der Wunsch
	 * @return Wuensche w, der Wunsch
	 */
	private Wuensche standardWerteWunsch(Wuensche w){
		System.out.println("standardWerte(wunsch) "+w.toString());
		if(istLeer(w.getBeschreibung())){
			w.setBeschreibung("keine");
		}
		if(istLeer(w.getLink())){
			w.setLink("keiner");
		}
		if(istLeer(w.getSchenker())){
			w.setSchenker("frei");
		}
		System.out.println(w.toString()+" nachher");
		return w;
	}
/**
 * Testet ob ein uebergebener String leer ist
 * @param str
 * @return leer oder nicht
 */
	public boolean istLeer(String str){
		 if (str == null || str.length() == 0){
	            return true;
		 }
	     else{
	            return false;
	     }
	}
	
	@Override
	/**
	 * Lädt eine Wunschliste aus der Datenbank und gibt diese zurück
	 * @return Wuenschliste liste
	 * Verwendet die Methode @see ladeWuensche
	 */
	public Wuenschliste ladeWunschliste(String zugriffsId) {
		Wuenschliste liste = new Wuenschliste();
		try {
			PreparedStatement stmladenliste =
					getVerbindung().prepareStatement("SELECT * FROM wunschliste WHERE id_zugriff=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmladenliste.setString(1, zugriffsId);
			ResultSet setliste = stmladenliste.executeQuery();
			if(setliste.next()){
				liste.setIdListe(setliste.getInt(1));
				liste.setZugriffsId(setliste.getString(2));
				liste.setIdErsteller(setliste.getInt(3));
				liste.setName(setliste.getString(4));
				liste.setAnlass(setliste.getString(5));
				Date datum = setliste.getDate(6);
				LocalDate ldatum = datum.toLocalDate();
				liste.setDatum(ldatum);
				liste.setListepwd(setliste.getString(7));
				liste.setDesignid(setliste.getInt(8));
				liste.setUeberraschung(setliste.getBoolean(9));
				liste.setWuensche(ladeWuensche(liste.getIdListe()));
			}
			stmladenliste.close();
			setliste.close();
		} catch (SQLException e) {
			System.out.println("WuenschdwDAOImple/ladeWunschliste(id):" );
			e.printStackTrace();
		}
		return liste;
	}
	
	/**
	 * Lädt eine Liste von Wünschen zu einer übergebenen WunschlistenID
	 * @param id
	 * @return wuensche
	 */
	private ArrayList<Wuensche> ladeWuensche(int id){
		ArrayList<Wuensche> wuensche = new ArrayList<Wuensche>();
		try {
			PreparedStatement stmladenwuensche =
					getVerbindung().prepareStatement("SELECT * FROM wunsch WHERE id_wunschliste=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmladenwuensche.setInt(1, id);
			ResultSet setwuensche = stmladenwuensche.executeQuery();
			while(setwuensche.next()){
				Wuensche w = new Wuensche();
				w.setId(setwuensche.getInt(1));
				w.setName(setwuensche.getString(2));
				w.setBeschreibung(setwuensche.getString(4));
				w.setLink(setwuensche.getString(5));
				w.setSchenker(setwuensche.getString(6));
				wuensche.add(w);
			}
			stmladenwuensche.close();
			setwuensche.close();
		} catch (SQLException e) {
			System.out.println("WuenschdwDAOImple/ladeWuensche(id): ");
			e.printStackTrace();
		}
		return wuensche;
	}

	/**
	 * Aendert die Daten einer uebergebenen Wunschliste
	 * @param liste
	 * Fixe Daten sind: ID der Liste, ZugriffsID, ErstellerID
	 * Liefert zurück ob erfolgreich
	 * @return erg
	 */
	@Override
	public boolean aenderWunschliste(Wuenschliste liste) {
		boolean erg = false;
		
		try {
			PreparedStatement stmaendernliste =
					getVerbindung().prepareStatement("UPDATE wunschliste SET name_wunschliste=?, anlass=?, ablaufdatum=?, listenpasswort=?, design_id=?, uberraschungsmodus=? WHERE id_wunschliste=?"); //7
			stmaendernliste.setString(1, liste.getName());
			stmaendernliste.setString(2, liste.getAnlass());
			Date datum = Date.valueOf(liste.getDatum());
			stmaendernliste.setDate(3, datum);
			stmaendernliste.setString(4, liste.getListepwd());
			stmaendernliste.setInt(5, liste.getDesignid());
			stmaendernliste.setBoolean(6, liste.isUeberraschung());
			stmaendernliste.setInt(7, liste.getIdListe());
			int zeilen = stmaendernliste.executeUpdate();
			if(zeilen > 0){
				erg = true;
			}
			stmaendernliste.close();
		} catch (SQLException e) {
			System.out.println("WuenschdwDAOImple/aendernWunschliste(liste): ");
			e.printStackTrace();
		}
		return erg;
	}

	/**
	 * Loescht alle Wuensche zu einer uebergebenen LIstenID
	 * @param idListe
	 * Liefert zurueck ob dies erfolgreich war
	 * @return erfolgreich
	 */
	private boolean loeschenWuensche(int idListe){
		boolean geloescht = false;
		try {
			PreparedStatement loeschenwuensche=
					getVerbindung().prepareStatement("DELETE FROM wunsch where id_wunschliste=?");
//			System.out.println(loeschenwuensche.toString());
			loeschenwuensche.setInt(1, idListe);
			int zeilen = loeschenwuensche.executeUpdate();
			if(zeilen > 0){
//				System.out.println("wuensche gelöscht");
				geloescht = true;
			}
		}catch (SQLException e) {
			System.out.println("WuenschdwDAOImple/loescheWuenschw(idListe): ");
			e.printStackTrace();
		}
		return geloescht;
	}
	@Override
	/**
	 * Loescht eine Wunschliste zu einer uebergebenen ID
	 * @param idListe
	 * Liefert zurück ob dies erfolgreich war
	 * @return geloescht
	 * Verwendet die Methode @see loeschenWuensche(idListe)
	 */
	public boolean loescheWunschliste(int idListe) {
		boolean geloescht = false;
		if(loeschenWuensche(idListe)){
			try {
				PreparedStatement loeschenliste =
						getVerbindung().prepareStatement("DELETE FROM wunschliste WHERE id_wunschliste=?");
//				System.out.println(loeschenliste.toString());
				loeschenliste.setInt(1, idListe);
				int zeilen = loeschenliste.executeUpdate();
				if(zeilen > 0){
//					System.out.println("WL gelöscht");
					geloescht = true;
				}
			} catch (SQLException e) {
				System.out.println("WuenschdwDAOImple/loescheWunschliste(id): ");
				e.printStackTrace();
			}
		}
		return geloescht;
	}

	@Override
	/**
	 * Liefert ein String Array mit den möglichen Anläßen zurück
	 * @return anlaesse
	 */
	public ArrayList<String> anlaesseLaden() {
		System.out.println("anlaesseLaden: ");
		ArrayList<String> anlaesse = null;
		try {
			PreparedStatement stmladenanlaesse =
					getVerbindung().prepareStatement("SELECT * FROM anlass", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet setanlaesse = stmladenanlaesse.executeQuery();
			anlaesse = new ArrayList<String>();
			while(setanlaesse.next()){
				String anlass = setanlaesse.getString(1);
				anlaesse.add(anlass);
			}
			stmladenanlaesse.close();
			setanlaesse.close();
		} catch (SQLException e) {
			System.out.println("WuenschdwDAOImple/anlaesseLaden(): ");
			e.printStackTrace();
		}
		Collections.sort(anlaesse);
		return anlaesse;
	}

	@Override
	/**
	 * Speichert einen uebergebenen Wunsch 
	 * @param wunsch
	 * Dazu wird die id der Liste benoetigt
	 * @param idListe
	 * Liefert den Wunsch mit gesetzter id zurück
	 * @return wunsch
	 */
	public Wuensche speichereWunsch(int idListe, Wuensche wunsch) {
		int id = -1;
		wunsch = standardWerteWunsch(wunsch);
//		System.out.println("speicherWunsch: "+wunsch.toString());
			try {
				PreparedStatement stmspeichernwunsch =
						getVerbindung().prepareStatement("INSERT INTO wunsch(name_wunsch, id_wunschliste, beschreibung, link_wunsch, schenker) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
				stmspeichernwunsch.setString(1, wunsch.getName());
				stmspeichernwunsch.setInt(2, idListe);
				stmspeichernwunsch.setString(3, wunsch.getBeschreibung());
				stmspeichernwunsch.setString(4, wunsch.getLink());
				stmspeichernwunsch.setString(5, wunsch.getSchenker());
				System.out.println(stmspeichernwunsch.toString());
				int geschrieben = stmspeichernwunsch.executeUpdate();
				if(geschrieben == 0){
					throw new SQLException("Kein Wunsch geschrieben");
				}
				try(ResultSet setid = stmspeichernwunsch.getGeneratedKeys()){
					if(setid.next()){
						id = setid.getInt(1);
						wunsch.setId(id);
					}
					else{
						throw new SQLException("Keine ID erhalten");
					}
				}
				stmspeichernwunsch.close();
			} catch (SQLException e) {
				System.out.println("WuenschdwDAOImple/speichereWunsch(idliste, wunsch): ");
				e.printStackTrace();
			}
		return wunsch;
	}

	@Override
	public boolean aendereWunsch(int idWunsch, Wuensche wunsch) {
		boolean erg = false;
		try {
			PreparedStatement stmaendernwunsch =
					getVerbindung().prepareStatement("UPDATE wunsch SET name_wunsch=?, beschreibung=?, link_wunsch=? WHERE id_wunsch=?");
			stmaendernwunsch.setString(1, wunsch.getName());
			stmaendernwunsch.setString(2, wunsch.getBeschreibung());
			stmaendernwunsch.setString(3, wunsch.getLink());
			stmaendernwunsch.setInt(4, idWunsch);
			int zeilen = stmaendernwunsch.executeUpdate();
			if(zeilen > 0){
				erg = true;
			}
		} catch (SQLException e) {
			System.out.println("WuenschdwDAOImple/aendereWunsch(idWunsch, wunsch): ");
			e.printStackTrace();
		}
		
		return erg;
	}

	@Override
	/**
	 * Loescht einen Wunsch zu einer uebergebenen id
	 * @param idWunsch
	 * Liefert zurueck ob dies erfolgreich war
	 * @return geloescht
	 */
	public boolean loescheWunsch(int idWunsch) {
		boolean geloescht = false;
		try {
			PreparedStatement loeschenwunsch =
					getVerbindung().prepareStatement("DELETE FROM wunsch where id_wunsch=?");
			loeschenwunsch.setInt(1, idWunsch);
			int zeilen = loeschenwunsch.executeUpdate();
			if(zeilen > 0){
				geloescht = true;
			}
		} catch (SQLException e) {
			System.out.println("WuenschDWDAOImple/loescheWunsch(id): ");
			e.printStackTrace();
		}
		return geloescht;
	}

	/**
	 * Sucht in der Datenbank nach einer Liste zu einer uebergebenen uuid
	 * @param uuid
	 * Liefert zurueck ob etwas gefunden wurde
	 * @return erg
	 */
	public boolean listeSuchen(String uuid){ //TEST
		boolean erg = false;
		try {
			PreparedStatement stmsuchenliste = 
					getVerbindung().prepareStatement("SELECT * FROM wunschliste WHERE id_zugriff=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmsuchenliste.setString(1, uuid);
			ResultSet setliste = stmsuchenliste.executeQuery();
			if(setliste.next()){
				erg = true;
			}
			stmsuchenliste.close();
			setliste.close();
		} catch (SQLException e) {
			System.out.println("WuenschdwDAOImple/ listeSuchen(uuid, passwort): ");
			e.printStackTrace();
		}
		return erg;
	}
	
	/**
	 * Sucht in der Datenbank nach einer Liste zu einem uebergebenen pqsswort
	 * @param passwor
	 * Liefert zurueck ob etwas gefunden wurde
	 * @return erg
	 */
	public boolean listeNachPasswort(String passwort){ //TEST
		boolean erg = false;
		try {
			PreparedStatement stmsuchenpasswort = 
					getVerbindung().prepareStatement("SELECT * FROM wunschliste WHERE listenpasswort=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmsuchenpasswort.setString(1, passwort);
			ResultSet setliste = stmsuchenpasswort.executeQuery();
			if(setliste.next()){
				erg = true;
			}
			stmsuchenpasswort.close();
			setliste.close();
		} catch (SQLException e) {
			System.out.println("WuenschdwDAOImple/listeNachPasswort(passwort): ");
			e.printStackTrace();
		}
		return erg;
	}
	
	public WunschlisteErsteller ladeErstellerId(int idErsteller){ //TEST
		WunschlisteErsteller ersteller = null;
		try {
			PreparedStatement stmladenersteller = 
					getVerbindung().prepareStatement("SELECT * FROM ersteller WHERE id_ersteller=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmladenersteller.setInt(1, idErsteller);
			ResultSet setersteller = stmladenersteller.executeQuery();
			if(setersteller.next()){
				ersteller = new WunschlisteErsteller();
				ersteller.setEmail(setersteller.getString(2));
				ersteller.setId(setersteller.getInt(1));
			}
			stmladenersteller.close();
			setersteller.close();
		} catch (SQLException e) {
			System.out.println("WuenschdwDAOImple/ladeErsteller(idERsteller): ");
			e.printStackTrace();
		}
		return ersteller;
	}
}
