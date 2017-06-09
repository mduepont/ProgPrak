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
					getVerbindung().prepareStatement("INSERT INTO ersteller(name_ersteller, email) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
			stmspeichernersteller.setString(1, ersteller.getName());
			stmspeichernersteller.setString(2, ersteller.getEmail());
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

	@Override
	//Vorbedingung: erstellerid muss gesetzt sein!
	/**
	 * Speichert eine uebergebene Wunschliste in die Datenbank
	 * @param liste
	 * Nutzt dabei die Methode @see speichernUndIdHolenWuensche(wuensche)
	 * Liefert die ID der gespeicherten Liste zurueck
	 */
	public int speichereWunschliste(Wuenschliste liste) {
		int id = -1;
//		id_ersteller, name_wunschliste, anlass, ablaufdatum, listenpasswort, design_id, uberraschungsmodus
		try {
			PreparedStatement stmspeichernliste =
					getVerbindung().prepareStatement("INSERT INTO wunschliste(id_ersteller, name_wunschliste, anlass, ablaufdatum, listenpasswort, design_id, uberraschungsmodus) VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			stmspeichernliste.setInt(1, liste.getIdErsteller());
			stmspeichernliste.setString(2, liste.getName());
			stmspeichernliste.setString(3, liste.getAnlass());
			Date date = Date.valueOf(liste.getDatum());
			stmspeichernliste.setDate(4, date);
			stmspeichernliste.setString(5, liste.getListepwd());
			stmspeichernliste.setInt(6, liste.getDesignid());
			stmspeichernliste.setBoolean(7, liste.isUeberraschung());
			int geschrieben = stmspeichernliste.executeUpdate();
			if(geschrieben == 0){
				throw new SQLException("keine Liste gespeichert");
			}try(ResultSet setid = stmspeichernliste.getGeneratedKeys()){
				if(setid.next()){
					id = setid.getInt(1);
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
		
		return id;
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
	 */
	private ArrayList<Wuensche> speichernUndIdHolenWuensche(int listeID, ArrayList<Wuensche> wuensche){
		ArrayList<Wuensche> wuenscheNeu = null;
		ArrayList<Wuensche> speicher = new ArrayList<Wuensche>();
		//wunsch speichern und id setzen!
//		name_wunsch, id_wunsch, beschreibung, link_wunsch
		for(Wuensche w: wuensche){
			int id = -1;
			w = standardWerteWunsch(w);
				try {
					PreparedStatement stmspeichernwunsch =
							getVerbindung().prepareStatement("INSERT INTO wunsch(name_wunsch, id_wunschliste, beschreibung, link_wunsch) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
					stmspeichernwunsch.setString(1, w.getName());
					stmspeichernwunsch.setInt(2, listeID);
					stmspeichernwunsch.setString(3, w.getBeschreibung());
					stmspeichernwunsch.setString(4, w.getLink());
					int geschrieben = stmspeichernwunsch.executeUpdate();
					if(geschrieben == 0){
						throw new SQLException("Kein Wunsch geschrieben");
					}
					try(ResultSet setid = stmspeichernwunsch.getGeneratedKeys()){
						if(setid.next()){
							id = setid.getInt(1);
							w.setId(id);
							speicher.add(w);
						}
						else{
							throw new SQLException("Keine ID erhalten");
						}
					}
					stmspeichernwunsch.close();
				} catch (SQLException e) {
					System.out.println("WuenschdwDAOImple/speichernWuensche(id, wuensche): ");
					e.printStackTrace();
				}
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
		if(w.getBeschreibung() == null){
			w.setBeschreibung("<keine>");
		}
		if(w.getLink() == null){
			w.setLink("<keiner>");
		}
		return w;
	}
	
	@Override
	/**
	 * Lädt eine Wunschliste aus der Datenbank und gibt diese zurück
	 * @return Wuenschliste liste
	 * Verwendet die Methode @see ladeWuensche
	 */
	public Wuenschliste ladeWunschliste(int id) {
		Wuenschliste liste = new Wuenschliste();
		try {
			PreparedStatement stmladenliste =
					getVerbindung().prepareStatement("SELECT * FROM wunschliste WHERE id_wunschliste=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmladenliste.setInt(1, id);
			ResultSet setliste = stmladenliste.executeQuery();
			if(setliste.next()){
				liste.setIdListe(setliste.getInt(1));
				liste.setIdErsteller(setliste.getInt(2));
				liste.setName(setliste.getString(3));
				liste.setAnlass(setliste.getString(4));
				Date datum = setliste.getDate(5);
				LocalDate ldatum = datum.toLocalDate();
				liste.setDatum(ldatum);
				liste.setListepwd(setliste.getString(6));
				liste.setDesignid(setliste.getInt(7));
				liste.setUeberraschung(setliste.getBoolean(8));
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

	@Override
	public boolean aenderWunschliste(Wuenschliste liste) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean loescheWunschliste(Wuenschliste liste) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	/**
	 * Liefert ein String Array mit den möglichen Anläßen zurück
	 * @return anlaesse
	 */
	public ArrayList<String> anlaesseLaden() {
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
		return anlaesse;
	}

	@Override
	public int speichereWunsch(int idListe) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean aendereWunsch(int idWunsch) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean loescheWunsch(int idWunsch) {
		// TODO Auto-generated method stub
		return false;
	}

}
