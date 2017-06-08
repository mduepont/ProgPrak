package test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import beans.Wuensche;
import beans.Wuenschliste;
import beans.WunschlisteErsteller;
import dao.WuenschdwDAOImple;
import datenbank.DatenbankIO;

public class DaoTest {

	private DatenbankIO db;
	private WuenschdwDAOImple wdw;
	@Before
	public void setUp() throws Exception {
		db = new DatenbankIO();
		wdw = new WuenschdwDAOImple();
	}

	@Ignore
	@Test
	public void testErstellerSpeichern() {
		wdw.getProperties();
		wdw.verbinde();
		wdw.statementsVorbereiten();
		WunschlisteErsteller er = new WunschlisteErsteller();
		er.setEmail("karlmarx@gmx.de");
		er.setName("Karl"); 
		System.out.println(er.toString());
		int id = wdw.speichereErsteller(er);
		er.setId(id);
		assertFalse(er.getId() == -1);
		wdw.schliesseVerbindung(wdw.getVerbindung());
	}

	@Ignore
	@Test
	public void testErstellerLaden() {
		wdw.getProperties();
		wdw.verbinde();
		wdw.statementsVorbereiten();
		WunschlisteErsteller er = wdw.ladeErsteller("karlmarx@gmx.de");
		wdw.schliesseVerbindung(wdw.getVerbindung());
		assertTrue(er.getName().equals("Karl"));
	}
	
	@Ignore
	@Test
	public void testErstellerLoeschen() {
		wdw.getProperties();
		wdw.verbinde();
		wdw.statementsVorbereiten();
		boolean erg = wdw.loescheErsteller(10);
		wdw.schliesseVerbindung(wdw.getVerbindung());
		assertTrue(erg);
	}
	
	@Ignore
	@Test
	public void testSpeichernListe(){
		Wuenschliste wl = new Wuenschliste();
		wl.setAnlass("Konfirmation");
		LocalDate date = LocalDate.of(2017, 12, 3);
		wl.setDatum(date);
		wl.setIdErsteller(7);
		wl.setListepwd("123bFd");
		wl.setName("Meine Konfirmation");
		Wuensche w = new Wuensche();
		w.setName("Armband");
		Wuensche w2 = new Wuensche();
		w2.setName("Hausschuhe");
		ArrayList<Wuensche> wuensche = new ArrayList<Wuensche>();
		wuensche.add(w2);
		wuensche.add(w);
		wl.setWuensche(wuensche);
		wdw.getProperties();
		wdw.verbinde();
		wdw.statementsVorbereiten();
		int id = wdw.speichereWunschliste(wl);
		assertFalse(id == -1);
		wdw.schliesseVerbindung(wdw.getVerbindung());
	}
	
	@Ignore
	@Test
	public void testLadenWunschlisteID(){
		Wuenschliste wl = new Wuenschliste();
		wdw.getProperties();
		wdw.verbinde();
		wdw.statementsVorbereiten();
		wl = wdw.ladeWunschliste(5);
		assertTrue(wl.getWuensche().get(1).getBeschreibung().equals("In schwarz silber, Groesse 44"));
		System.out.println(wl.getWuensche().get(1).toString());
		wdw.schliesseVerbindung(wdw.getVerbindung());
	}
	
//	@Ignore
	@Test
	public void testLadenAnlaesse(){
		ArrayList<String> anlaesse = new ArrayList<String>();
		wdw.getProperties();
		wdw.verbinde();
		wdw.statementsVorbereiten();
		anlaesse = wdw.anlaesseLaden();
		wdw.schliesseVerbindung(wdw.getVerbindung());
		assertTrue(anlaesse.size() >0);
		System.out.println(anlaesse.toString());
	}
}
