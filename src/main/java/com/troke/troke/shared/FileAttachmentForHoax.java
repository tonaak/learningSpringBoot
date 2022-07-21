package com.troke.troke.shared;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = FileAttachmentForHoaxValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FileAttachmentForHoax {
	
	String message() default "{troke.constraints.file.FileAttachment.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
