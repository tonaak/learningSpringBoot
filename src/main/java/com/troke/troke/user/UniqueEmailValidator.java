package com.troke.troke.user;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

	@Autowired
	UserRepository userRepository;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		List<User> inDB = userRepository.findByEmail(value);
		if (inDB.size() == 0) {
			return true;
		}
		return false;
	}

}
