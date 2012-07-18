package pl.squirrel.money.web;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class SpendingCommandValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return clazz.equals(SpendingCommand.class);
	}

	public void validate(Object target, Errors errors) {
		errors.rejectValue("category", "Testing category error");
	}
}
