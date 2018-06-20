package it.uniroma3.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import it.uniroma3.model.Attivita;

@Component
public class AttivitaValidator implements Validator {

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "descrizione", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oraInizio", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oraFine", "required");
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Attivita.class.equals(aClass);
	}
}
