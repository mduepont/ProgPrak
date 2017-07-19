package konverter;


import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="localDateTimeKonverter")
public class LocalDateTimeKonverter implements javax.faces.convert.Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
    	 if (value == null || (value.trim().length() == 0))
         {
             FacesMessage message = new FacesMessage("Bitte gib ein Datum ein");
    		 throw new ConverterException(message);
//    		 return value;
         }
    	 else{
//          return LocalDate.parse(value);
    		 try{
    		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
             LocalDate localDate = LocalDate.parse(value, formatter);
             return localDate;
    		 }catch(DateTimeParseException e){
    			 FacesMessage message = new FacesMessage("Bitte gib ein Datum im folgenden Format ein: TT.MM.JJJJ");
        		 throw new ConverterException(message);
    		 }
    	 }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

    	if(value instanceof Date){
            LocalDate dateValue = ((Date)value).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            
            return dateValue.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    	}
    	if(value instanceof LocalDate){
        LocalDate dateValue = (LocalDate) value;
        
        return dateValue.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    	}
    	return "";
    }
    
}