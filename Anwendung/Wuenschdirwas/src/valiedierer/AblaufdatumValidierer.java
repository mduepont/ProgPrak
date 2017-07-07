package valiedierer;

import java.time.LocalDate;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


/**
 * Validator zur Pr�fung des Ablaufdatums eines Angebots
 *
 */

@FacesValidator(value = "ablauf")
public class AblaufdatumValidierer implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent comp, Object obj) throws ValidatorException {
		LocalDate termination = (LocalDate) obj;
		LocalDate now = LocalDate.now();
		LocalDate min = now.plusDays(1);
		LocalDate max = now.plusYears(1);
		if(termination.compareTo(min) < 0){
			FacesMessage message =
					new FacesMessage("Das Ablaufdatum muss mindestens einen Tag in der Zukunft liegen");
			throw new ValidatorException(message);
		}
		if(termination.compareTo(max) > 0){
			FacesMessage message =
					new FacesMessage("Das Ablaufdatum kann h�chstens zwei Jahre in der Zukunft liegen");
			throw new ValidatorException(message);
		}
	}

}
