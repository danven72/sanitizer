package it.bitrock.sanitizer.validator.annotation;

import it.bitrock.sanitizer.validator.StringEnumerationValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = StringEnumerationValidator.class)
@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
public @interface StringEnumeration {
    String message() default "";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum<?>> enumClass();
}
