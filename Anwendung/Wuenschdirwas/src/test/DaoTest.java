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
		System.out.println(er.toString());
		int id = WuenschdwDAOImple.getInstance().speichereErsteller(er);
		assertFalse(id == -1);
		WuenschdwDAOImple.getInstance().schliesseVerbindung(WuenschdwDAOImple.getInstance().getVerbindung());
	}

	@Ignore
	@Test
	public void testErstellerLaden() { 
		WunschlisteErsteller er = WuenschdwDAOImple.getInstance().ladeErsteller("sabby678@web.de");
		WuenschdwDAOImple.getInstance().schliesseVerbindung(WuenschdwDAOImple.getInstance().getVerbindung());
		assertTrue(er.getId() == 1);
	}
	
	@Ignore
	@Test
	public void testErstellerLadenId(){
		WunschlisteErsteller e = WuenschdwDAOImple.getInstance().ladeErstellerId(1);
		assertTrue(e.getEmail().equals("sabby678@web.de"));
		WuenschdwDAOImple.getInstance().schliesseVerbindung(WuenschdwDAOImple.getInstance().getVerbindung());
	}
	
	@Ignore
	@Test
	public void testErstellerLoeschen() {
		boolean erg = WuenschdwDAOImple.getInstance().loescheErsteller(5);
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
		System.out.println(wl.toString());
		String zugriff = WuenschdwDAOImple.getInstance().speichereWunschliste(wl);
		assertFalse(zugriff == null);
		System.out.println(wl.toString());
		WuenschdwDAOImple.getInstance().schliesseVerbindung(WuenschdwDAOImple.getInstance().getVerbindung());
	}
	
	@Ignore
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
		String zugriff = WuenschdwDAOImple.getInstance().speichereWunschliste(wl);
		assertFalse(zugriff == null);
		System.out.println(zugriff);
		WuenschdwDAOImple.getInstance().schliesseVerbindung(WuenschdwDAOImple.getInstance().getVerbindung());
	}
	
	@Ignore
	@Test
	public void testLadenWunschlisteUUID(){
		Wuenschliste wl = new Wuenschliste();
		wl = WuenschdwDAOImple.getInstance().ladeWunschliste("546gvg452-43g326-980h76");
		System.out.println(wl.toString());
		assertTrue((wl.getWuensche().get(0).getName()).equals("Tragetuch"));
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
	@Ignore 
	@Test
	public void testAendernWunschliste(){
		Wuenschliste liste = new Wuenschliste();
		liste.setAnlass("Hochzeit");
		LocalDate datum = LocalDate.of(2018, 1, 11);
		liste.setDatum(datum);
//		liste.setIdErsteller(1);
		liste.setDesignid(1);
		liste.setListepwd("123AbC");
		liste.setIdListe(45);
		liste.setName("Neuer Name");
		liste.setUeberraschung(true);
//		liste.setIdListe(1);
//		liste.setZugriffsId("aaa1-bbb2-ccc3");
		//WUENSCHE
		assertTrue(WuenschdwDAOImple.getInstance().aenderWunschliste(liste));
		WuenschdwDAOImple.getInstance().schliesseVerbindung(WuenschdwDAOImple.getInstance().getVerbindung());
	}
	
	@Ignore
	@Test
	public void testListeNachPasswort(){
		assertTrue(WuenschdwDAOImple.getInstance().listeNachPasswort("bBaA89cC"));
		WuenschdwDAOImple.getInstance().schliesseVerbindung(WuenschdwDAOImple.getInstance().getVerbindung());
	}
	

	@Ignore
	@Test
	public void testListeSuchen(){
		assertTrue(WuenschdwDAOImple.getInstance().listeSuchen("fd876g-87gas5-87s8696"));
		WuenschdwDAOImple.getInstance().schliesseVerbindung(WuenschdwDAOImple.getInstance().getVerbindung());
	}
	
	@Ignore
	@Test
	public void loeschenWunsch(){
		assertTrue(WuenschdwDAOImple.getInstance().loescheWunsch(33));
		WuenschdwDAOImple.getInstance().schliesseVerbindung(WuenschdwDAOImple.getInstance().getVerbindung());
	}
	
	@Ignore
	@Test
	public void loeschenWunschliste(){
		assertTrue(WuenschdwDAOImple.getInstance().loescheWunschliste(1));
		WuenschdwDAOImple.getInstance().schliesseVerbindung(WuenschdwDAOImple.getInstance().getVerbindung());
		
	}
	
	@Ignore
	@Test
	public void speichernWunsch(){
		Wuensche w = new Wuensche();
		w.setBeschreibung("mein aller größter Wunsch");
		w.setLink("www.Kamel_Reiten_Hümner.de");
		w.setName("Kamel Reitwanderung");
		w = WuenschdwDAOImple.getInstance().speichereWunsch(1, w);
		assertFalse(w.getId() == -1);
		WuenschdwDAOImple.getInstance().schliesseVerbindung(WuenschdwDAOImple.getInstance().getVerbindung());
	}
	
//	@Ignore
	@Test
	public void aendernWunsch(){ //alle werte setzen, da wunsch diese normalerweise hat
		Wuensche w = new Wuensche();
		w.setId(62);
		w.setName("Yogakurs");
		w.setBeschreibung("Texti textchen");
		w.setLink("www.link.de");
		w.setSchenker("frei");
		assertTrue(WuenschdwDAOImple.getInstance().aendereWunsch(w));
		WuenschdwDAOImple.getInstance().schliesseVerbindung(WuenschdwDAOImple.getInstance().getVerbindung());
	}
}
