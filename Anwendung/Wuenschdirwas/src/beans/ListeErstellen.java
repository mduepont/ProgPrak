package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="erstellen")
@SessionScoped
public class ListeErstellen {

	private String titel;

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}
	
	public void anlegen(){
		System.out.println("anlegen");
	}
	
}
