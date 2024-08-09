package it.bitrock.sanitizer.controller;

import it.bitrock.sanitizer.dto.AppointmentDTO;
import it.bitrock.sanitizer.dto.FilterType;
import it.bitrock.sanitizer.validator.annotation.NoHtml;
import it.bitrock.sanitizer.validator.annotation.StringEnumeration;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @GetMapping("/patient/{surname}")
    public AppointmentDTO findByPatientSurname(@PathVariable @NoHtml @Length(min = 3, message = "The field must be at least 3 characters") String surname) {

        System.out.println("Surname input: " + surname);

        return new AppointmentDTO("3456tyuioe", "Rossi", "Curatolo");
    }

    @GetMapping("/patient/{patientSurname}/doctor/{doctorSurname}")
    public AppointmentDTO findByPatientSurnameAndDoctor(
            @PathVariable @NoHtml @Length(min = 3, message = "The field must be at least 3 characters") String patientSurname,
            @PathVariable @NoHtml @Length(min = 4, message = "The field must be at least 4 characters") String doctorSurname
    ) {

        System.out.println("PatientSurname input: " + patientSurname + " DoctorSurname: " + doctorSurname);

        return new AppointmentDTO("3456tyuioe", "Bianchi", "Avorio");
    }

    @GetMapping("search")
    public List<AppointmentDTO> findByFilter(@RequestParam @NotNull @StringEnumeration(enumClass = FilterType.class) String filter) {
        System.out.println("Filter: " + filter);
        return List.of(new AppointmentDTO("3456tyuioe", "Bianchi", "Avorio"),
                new AppointmentDTO("2222tyuioe", "Verdi", "Avorio"),
        new AppointmentDTO("3333tyuioe", "Rossi", "Avorio"),
        new AppointmentDTO("3444tyuioe", "Giallini", "Avorio"));
    }

    @GetMapping("parameter")
    public AppointmentDTO findByParamRequest(@RequestParam @NotNull @NoHtml String param) {
        System.out.println("Param: " + param);
        return new AppointmentDTO("3444tyuioe", "Giallini", "Avorio");
    }


    @PostMapping
    public String create(@RequestBody  @Valid AppointmentDTO appointmentDTO) {
        System.out.println("Appointment input: " + appointmentDTO);
        return "Appointment  " + appointmentDTO + " created";

    }

}
