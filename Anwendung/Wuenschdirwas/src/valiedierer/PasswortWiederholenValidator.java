package valiedierer;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Validator zur �berpr�fung einer korrekten Wiederholung
 * der Eingabe des Passworts, beim Anlegen einer neuen Wunschliste
 *
 */

@FacesValidator(value = "duplicate")
public class PasswortWiederholenValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent comp, Object obj) throws ValidatorException {
	
		String password = (String)obj;
		UIInput pw2Id = (UIInput) comp.getAttributes().get("password1");
//		UIInput pw2content = (UIInput)context.getViewRoot().findComponent(pw2Id);
		String pw2 = (String)pw2Id.getValue();
//		System.out.println(password+", "+pw2);
		if(!password.equals(pw2)){
			FacesMessage message =
					new FacesMessage("Die Passwort Eingaben sind abweichend");
			throw new ValidatorException(message);
		}
	}

}
