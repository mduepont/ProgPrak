package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import datenbank.DatenbankIO;

public class DatabaseTest {

	private DatenbankIO dbio;
	
	@Before
	public void setUp() throws Exception {
		dbio = new DatenbankIO();
	}

	@Ignore
	@Test
	public void testProperties() {
		dbio.getProperties();
		assertTrue(dbio.getDbadresse().equals("jdbc:postgresql://localhost/wuenschdirwas"));
		assertTrue(dbio.getDbtreiber().equals("org.postgresql.Driver"));
		assertTrue(dbio.getDbnutzer().equals("postgres"));
		assertTrue(dbio.getDbpasswort().equals("shit1066"));
		System.out.println(dbio.toString());
	}

//	@Ignore
	@Test
	public void testConnection(){
		dbio.getProperties();
		dbio.verbinde();
//		assertTrue(dbio.testIfConnection());
		System.out.println(dbio.getVerbindung().toString());
	}
}
