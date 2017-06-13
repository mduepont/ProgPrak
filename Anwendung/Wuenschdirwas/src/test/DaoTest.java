package test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import daten.Wuensche;
import daten.Wuenschliste;
import daten.WunschlisteErsteller;
import dao.WuenschdwDAOImple;
import datenbank.DatenbankIO;

public class DaoTest {
	@Before
	public void setUp() throws Exception {
	}

	@Ignore
	@Test
	public void testErstellerSpeichern() {
		WunschlisteErsteller er = new WunschlisteErsteller();
		er.setEmail("karlmarx@gmx.de");
		er.setName("Karl"); 
		System.out.println(er.toString());
		int id = WuenschdwDAOImple.getInstance().speichereErsteller(er);
		er.setId(id);
		assertFalse(er.getId() == -1);
		WuenschdwDAOImple.getInstance().schliesseVerbindung(WuenschdwDAOImple.getInstance().getVerbindung());
	}

	@Ignore
	@Test
	public void testErstellerLaden() {
		WunschlisteErsteller er = WuenschdwDAOImple.getInstance().ladeErsteller("karlmarx@gmx.de");
		WuenschdwDAOImple.getInstance().schliesseVerbindung(WuenschdwDAOImple.getInstance().getVerbindung());
		assertTrue(er.getName().equals("Karl"));
	}
	
	@Ignore
	@Test
	public void testErstellerLoeschen() {
		boolean erg = WuenschdwDAOImple.getInstance().loescheErsteller(10);
		WuenschdwDAOImple.getInstance().schliesseVerbindung(WuenschdwDAOImple.getInstance().getVerbindung());
		assertTrue(erg);
	}
	
	@Ignore
	@Test
	public void testSpeichernListe(){
		Wuenschliste wl = new Wuenschliste();
		wl.setAnlass("Konfirmation");
		LocalDate date = LocalDate.of(2017, 12, 3);
		wl.setDatum(date);
		wl.setIdErsteller(3);
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
		int id = WuenschdwDAOImple.getInstance().speichereWunschliste(wl);
		assertFalse(id == -1);
		WuenschdwDAOImple.getInstance().schliesseVerbindung(WuenschdwDAOImple.getInstance().getVerbindung());
	}
	
//	@Ignore
	@Test
	public void testSpeichernListeII(){
		Wuenschliste wl = new Wuenschliste();
		wl.setAnlass("Jubiläum");
		LocalDate date = LocalDate.of(2018, 05, 01);
		wl.setDatum(date);
		wl.setIdErsteller(1);
		wl.setListepwd("jifk5B");
		wl.setName("25. Jubiläum meiner Firma LatzhosenKG");
		Wuensche w = new Wuensche();
		w.setName("Gemälde");
		Wuensche w2 = new Wuensche();
		w2.setName("Teppich");
		ArrayList<Wuensche> wuensche = new ArrayList<Wuensche>();
		wuensche.add(w2);
		wuensche.add(w);
		wl.setWuensche(wuensche);
		int id = WuenschdwDAOImple.getInstance().speichereWunschliste(wl);
		assertFalse(id == -1);
		System.out.println(id);
		WuenschdwDAOImple.getInstance().schliesseVerbindung(WuenschdwDAOImple.getInstance().getVerbindung());
	}
	
	@Ignore
	@Test
	public void testLadenWunschlisteID(){
		Wuenschliste wl = new Wuenschliste();
		wl = WuenschdwDAOImple.getInstance().ladeWunschliste(5);
		assertTrue(wl.getWuensche().get(1).getBeschreibung().equals("In schwarz silber, Groesse 44"));
		System.out.println(wl.getWuensche().get(1).toString());
		WuenschdwDAOImple.getInstance().schliesseVerbindung(WuenschdwDAOImple.getInstance().getVerbindung());
	}
	
	@Ignore
	@Test
	public void testLadenAnlaesse(){
		ArrayList<String> anlaesse = new ArrayList<String>();
		anlaesse = WuenschdwDAOImple.getInstance().anlaesseLaden();
		WuenschdwDAOImple.getInstance().schliesseVerbindung(WuenschdwDAOImple.getInstance().getVerbindung());
		assertTrue(anlaesse.size() >0);
		System.out.println(anlaesse.toString());
	}
}
