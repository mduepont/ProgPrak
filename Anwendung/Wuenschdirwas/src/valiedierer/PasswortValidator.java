package valiedierer;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


/**
 * Validator f�r die Zusammensetzung des Passworts
 * @author SaRue
 *
 */

@FacesValidator(value = "passwortValidator")
public class PasswortValidator implements Validator {

	private String regEx = "((?=.*\\d)(?=.*[a-zäöü])(?=.*[A-Zäöü]).{3,20})"; //((?=.*\\d)(?=.*[a-zäöü])(?=.*[A-Zäöü]).{5,13})
	
	@Override
	public void validate(FacesContext context, UIComponent comp, Object obj) throws ValidatorException {
		String password = (String)obj;
		System.out.println("PW Valid: "+password);
		if(!password.matches(regEx)){
			FacesMessage message =
					new FacesMessage("Das Passwort muss mindestens einen Klein- und einen Gro�buchstaben,"
							+ " und eine Ziffer enthalten");
			throw new ValidatorException(message);
		}
	}

}
