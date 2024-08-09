package it.bitrock.sanitizer.dto;

import it.bitrock.sanitizer.validator.annotation.NoHtml;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public record AppointmentDTO(
        @Size(min = 5, max = 5, message = "The field must be 5 characters")
        @NoHtml
        String id,
        @Length(min = 2, message = "The field must be at least 2 characters")
        @NoHtml
        String patientSurname,
        @Length(min = 2, message = "The field must be at least 2 characters")
        @NoHtml
        String doctorSurname) {
}
