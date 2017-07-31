package valiedierer;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Validator zur �berpr�fung einer korrekten Aenderung
 * des Passworts, beim aendern der Listendaten für Passwort1
 *
 */

@FacesValidator(value = "passwortAendern1")
public class PasswortAendern1Validator implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent comp, Object obj) throws ValidatorException {
		String pw1 = (String)obj;
		System.out.println("pw1: "+pw1);
		UIInput pwd1 = (UIInput) comp.getAttributes().get("passwort2");
		String pw2 = (String)pwd1.getValue();
		pw2 = pw2.trim();
		System.out.println("pw2: "+pw2);
		if(pw2 == null){
			FacesMessage msg = new FacesMessage("Zum ändern des Passworts müssen beide Passwort-Felder ausgefüllt werden");
			throw new ValidatorException(msg);
		}
		if(!pw2.equals(pw1)){
			FacesMessage msg = new FacesMessage("Abweichende Passwörter 1");
			throw new ValidatorException(msg);
		}
	}

}
