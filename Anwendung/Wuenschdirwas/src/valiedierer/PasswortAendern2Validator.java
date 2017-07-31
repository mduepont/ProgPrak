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

@FacesValidator(value = "passwortAendern2")
public class PasswortAendern2Validator implements Validator{

	@Override
	public void validate(FacesContext context, UIComponent comp, Object obj) throws ValidatorException {
		String pw2 = (String) obj;
		UIInput pwd2 = (UIInput) comp.getAttributes().get("password1");
		String pw1 = (String)pwd2.getValue();
		pw1 = pw1.trim();
		if(pw1 == null){
			FacesMessage msg = new FacesMessage("Zum ändern des Passworts müssen beide Passwort-Felder ausgefüllt werden");
			throw new ValidatorException(msg);
		}
		if(!pw1.equals(pw2)){
			FacesMessage msg = new FacesMessage("Abweichende Passwörter 2");
			throw new ValidatorException(msg);
		}
	}

}

