/**
 * DatabaseIO verfuegt ueber noetige Methoden um Zugang zur Datenbank zu ermoeglichen
 * ev. 0. setPropFile(...)
 * 1. muss getProperties() aufgerufen werden (setzt die Zugangsdaten)
 * 2. muss getConnection() aufgerufen werden (erstellt die Verbindung)
 * 3. zum Schluss muss closeConnectionQuietly(Connection connection) aufgerufen werden
 */
package datenbank;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatenbankIO {

	/**
	 * Treiberobjekt 
	 */
	private Driver treiber = null;
	/**
	 * ReprÃ¤sentiert die Properties File in der Datenbankzugangsdaten stehen
	 */
	 private String dateiProperties;

	/**
	 * Connection 
	 */
	private Connection verbindung = null;
	/**
	 * Name des Treibers
	 */
	private String dbtreiber;
	/**
	 * String zur Verbindung mit der Datenbank, bestehend aus Protokoll, Subprotokoll
	 * und Adresse des Servers
	 */
	private String dbadresse;
	/**
	 * Nutzer Name
	 */
	private String dbnutzer;
	/**
	 * Passwort
	 */
	private String dbpasswort;
	
	/**
	 * Nullkonstruktor
	 */
	public DatenbankIO(){
		super();
	}
	
	public Driver getTreiber() {
		return treiber;
	}

	public void setTreiber(Driver treiber) {
		this.treiber = treiber;
	}

	public String getDateiProperties() {
		return dateiProperties;
	}

	public void setDateiProperties(String prop) {
		this.dateiProperties = prop;
	}
	
	public Connection getVerbindung(){
		return verbindung;
	}
	public void setVerbindung(Connection verbindung) {
		this.verbindung = verbindung;
	}

	public String getDbtreiber() {
		return dbtreiber;
	}

	public void setDbtreiber(String dbtreiber) {
		this.dbtreiber = dbtreiber;
	}

	public String getDbadresse() {
		return dbadresse;
	}

	public void setDbadresse (String dbadresse) {
		this.dbadresse = dbadresse;
	}

	public String getDbnutzer() {
		return dbnutzer;
	}

	public void setDbuser(String dbnutzer) {
		this.dbnutzer = dbnutzer;
	}

	public String getDbpasswort() {
		return dbpasswort;
	}

	public void setDbpasswort(String dbpasswort) {
		this.dbpasswort = dbpasswort;
	}
	/**
	 * Methode die die Daten zum Zugriff auf die Datenbank aus einer Properties File ausliesst
	 */
	public void getProperties() {
        Properties properties = new Properties();
        try {
			properties.load(getClass().getClassLoader().getResourceAsStream("dbzugang.properties"));
		} catch (Exception e1) {
			System.out.println("DatenbankIO/getProperties(): ");
			e1.printStackTrace();
		}
//         File propertiesFile = new File("resources/dbaccess.properties"); 
        //File propertiesFile = new File(propFile);
       /* if(propertiesFile.exists()){
        	try{
	          BufferedInputStream bis = new BufferedInputStream(new FileInputStream(propertiesFile));
	          properties.load(bis);
	          bis.close();  
        	}catch(Exception e){
        		System.out.println("DatabaseIO/ getProperties(): ");
        		e.printStackTrace();
        	}
        }*/
        dbadresse   = properties.getProperty("database");
    	dbnutzer     = properties.getProperty("dbuser");
    	dbpasswort = properties.getProperty("dbpassword");
    	dbtreiber   = properties.getProperty("dbdriver");
        
    }

	//Treiber erzeugen
	/**
	 * Versucht den Treiber zu erzeugen und liefert zurueck ob erfolgreich
	 * @return boolean erfolgreich
	 */
	private boolean ladeTreiber(){
		boolean erfolgreich = false;
		try {
			treiber = (Driver)Class.forName(dbtreiber).newInstance();
			erfolgreich = true;
		} catch (Exception e) {
			System.out.print("Es konnte keine Instanz des Treibers "+dbtreiber+" erzeugt werden");
			e.printStackTrace();
		}
		return erfolgreich;
	}
//Verbindung herstellen
	/**
	 * Liefert eine Verbindung zurueck
	 * ist abhaengig von erfolgreichem Laden des Treibers @see loadDriver()
	 * @return
	 */
	public void verbinde(){
		
		if(ladeTreiber()){
//			System.out.println(">"+database+"<>"+dbuser+"<>"+dbpassword+"<");
			try {
				verbindung = DriverManager.getConnection(dbadresse, dbnutzer, dbpasswort);
//				setVerbindung(verbindung);
			}
			 catch (SQLException e) {
					System.out.println("DatabaseIO/ getVerbindung(): "
							+e.getMessage());
					e.printStackTrace();
				}
		}
		
	}
	
	/**
	 * Schliesst eine Connection und ignoriert Fehlermeldungen.
	 * @param connection, zu schliessende Connection.
	 */
	public void schliesseVerbindung(Connection verb) {
	    if (verb != null) {
	        try {
	            verb.close();
	        } catch (SQLException e) {
	            /* ignorieren */ }
	    }
	}
	
	/**
	 * Liefert eine Verbindung zurueck
	 * @return Connection verbindung
	 */
	public boolean testIfConnection(){
		return verbindung != null;
	}

	@Override
	public String toString() {
		return "DatenbankIO [treiber=" + treiber + ", dateiProperties=" + dateiProperties + ", verbindung=" + verbindung
				+ ", dbtreiber=" + dbtreiber + ", dbadresse=" + dbadresse + ", dbnutzer=" + dbnutzer + ", dbpasswort="
				+ dbpasswort + "]";
	}
	

}
