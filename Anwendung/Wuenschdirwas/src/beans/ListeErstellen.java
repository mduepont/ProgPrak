package beans;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import dao.WuenschdwDAOImple;

@ManagedBean(name="erstellen")
@SessionScoped
public class ListeErstellen {

	private String titel;
	private String anlass;
	private ArrayList<String> anlaesse;
//	private Date datum;
	private LocalDate ldatum;

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}
	
	
	public String getAnlass() {
		return anlass;
	}

	public void setAnlass(String anlass) {
		this.anlass = anlass;
	}

	public SelectItem[] getAnlaesse(){
		SelectItem[] items = null;
		
		ArrayList<String> anlaesse =
				WuenschdwDAOImple.getInstance().anlaesseLaden();
		if(anlaesse != null){
			int zeilen = anlaesse.size();
			items = new SelectItem[zeilen];
			for(int i = 0; i < zeilen; i++){
				items[i] = new SelectItem(anlaesse.get(i), anlaesse.get(i));
			}
		}
//		System.out.println(items.toString());
		return items;
	}

	public void setAnlaesse(ArrayList<String> anlaesse) {
		this.anlaesse = anlaesse;
	}

//	public Date getDatum() {
//		return datum;
//	}
//
//	public void setDatum(Date datum) {
//		this.datum = datum;
//		System.out.println("setDatum "+datum.toString());
//		zuLDatum();
//	}
	/*Konvertieren von java.util.Date zu java.time.LocaleDate*/
//	private void zuLDatum(){
//		ldatum = datum.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//		setLdatum(ldatum);
//	}
	public LocalDate getLdatum() {
		return ldatum;
	}
	public void setLdatum(LocalDate ldatum) {
		this.ldatum = ldatum;
		System.out.println("LDatum "+ldatum.toString());
	}

	public void anlegen(){
		System.out.println("Liste angelegt: "+titel+", "+anlass.toString()+", "+ldatum);
	}
	
	public void validateAblaufdatum(FacesContext context, UIComponent component, Object value) throws ValidatorException{
		System.out.println("validator");
		LocalDate vergleichsdatum = LocalDate.now();
		vergleichsdatum = vergleichsdatum.plusDays(1);
		System.out.println(vergleichsdatum+" vgl date und eingabe: "+value.toString());
		Date mindatum = Date.from(vergleichsdatum.atStartOfDay(ZoneId.systemDefault()).toInstant());
		System.out.println("mindatum: "+mindatum.toString()+" ergibt "+(mindatum.compareTo((Date)value) < 0));
		if(!(mindatum.compareTo((Date)value) < 0)){
			throw new ValidatorException(new FacesMessage("Das Ablaufdatum liegt nicht mindestens einen Tag in der Zukunft"));
		}
	}
	
}
