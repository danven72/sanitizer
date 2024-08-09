package it.bitrock.sanitizer.validator;

import it.bitrock.sanitizer.validator.annotation.StringEnumeration;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StringEnumerationValidator implements ConstraintValidator<StringEnumeration, String> {

    private Set<String> AVAILABLE_ENUM_NAMES;
    private String[] subset;


    public static Set<String> getNamesSet(Class<? extends Enum<?>> e) {
        Enum<?>[] enums = e.getEnumConstants();
        String[] names = new String[enums.length];
        for (int i = 0; i < enums.length; i++) {
            names[i] = enums[i].name();
        }
        Set<String> mySet = new HashSet<String>(Arrays.asList(names));
        return mySet;
    }


    @Override
    public void initialize(StringEnumeration stringEnumeration) {
        Class<? extends Enum<?>> enumSelected = stringEnumeration.enumClass();
        AVAILABLE_ENUM_NAMES = getNamesSet(enumSelected);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if ( value == null ) {
            return true;
        } else {
            boolean valid = AVAILABLE_ENUM_NAMES.contains(value);
            if (!valid) {
                context.disableDefaultConstraintViolation();
                String message = "Value not acceptable: allowed values are: " + AVAILABLE_ENUM_NAMES;
                context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            }
            return valid;
        }
    }

}
