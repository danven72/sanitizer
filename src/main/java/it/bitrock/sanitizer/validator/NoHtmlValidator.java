package it.bitrock.sanitizer.validator;

import it.bitrock.sanitizer.validator.annotation.NoHtml;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

public class NoHtmlValidator implements ConstraintValidator<NoHtml, String> {

    @Override
    public void initialize(NoHtml constraintAnnotation) {
        // TODO specify the policy as an annotation attribute
        // to use them, values from annotation are stored in private properties here
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context)
    {
        PolicyFactory DISALLOW_ALL = new HtmlPolicyBuilder().toFactory();
        String sanitized = DISALLOW_ALL.sanitize(value);

        return sanitized.equals(value);
    }
}
