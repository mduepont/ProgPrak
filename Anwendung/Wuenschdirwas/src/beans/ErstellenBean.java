package beans;

import java.time.LocalTime;
import javax.annotation.PostConstruct;
import javax.faces.bean.*;
import javax.faces.model.SelectItem;

@ManagedBean(name="erstellenBean")
@SessionScoped
public class ErstellenBean {
	
	private String header ="Daten eingeben";
	private String title ="";
	private String anlass="";
	private LocalTime ablaufdatum;
	private String[] anlaesse;
	
	
	public Object[] getAnlaesse() {
		SelectItem[] items = new SelectItem[anlaesse.length];
		for (int i = 0; i < anlaesse.length; i++){
			items[i] = new SelectItem(anlaesse[i],
					anlaesse[i].toLowerCase());
		}
		return items;
	}

	/*public List<SelectItem> getAnlaesse(){

		   List<SelectItem> items = new ArrayList<SelectItem>();
		   List<Category> categoryList = dao.getAllCategory();
		    for(Category category: categotyList){
		       items.add(new SelectItem(category.getCategoryId(), category.getName()));
		   }
		   return items;
		}*/
	
	public void setAnlaesse(String[] anlaesse) {
		this.anlaesse = anlaesse;
	}

	@PostConstruct
	   public void init() {
	       anlaesse = new String[3];
	       anlaesse[0]="Geburt";
	       anlaesse[1]="Geburtstag";
	       anlaesse[2]="Hochzeit";
	}
				
	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAnlass() {
		return anlass;
	}

	public void setAnlass(String anlass) {
		this.anlass = anlass;
	}

	public LocalTime getAblaufdatum() {
		return ablaufdatum;
	}

	public void setAblaufdatum(LocalTime ablaufdatum) {
		this.ablaufdatum = ablaufdatum;
	}

	


}
