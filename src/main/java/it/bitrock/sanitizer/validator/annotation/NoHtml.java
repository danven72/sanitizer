package it.bitrock.sanitizer.validator.annotation;

import it.bitrock.sanitizer.validator.NoHtmlValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = NoHtmlValidator.class)
@Documented
public @interface NoHtml {
    // TODO use a better message, look up ValidationMEssages.properties
    String message() default "Html code detected in the field";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

