package valiedierer;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


/**
 * Validator zur groben �berpr�fung der Emailadresse 
 * @author SaRue
 *
 */

@FacesValidator(value = "emailValidator")
public class EmailValidator implements Validator {

	private String regEx = ".+@.+\\..+";
	
	
	@Override
	public void validate(FacesContext context, UIComponent comp, Object obj) throws ValidatorException {
		String email = (String)obj;
		if(!email.matches(regEx)){
			FacesMessage message =
					new FacesMessage("Die Emailadresse ist ungültig");
			throw new ValidatorException(message);
		}
	}

}
