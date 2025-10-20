package com.hiennhatt.vod.validations;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateProfileValidation {
    @NotBlank
    String firstName;
    String lastName;
    String middleName;
    String description;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past
    LocalDate dateOfBirth;
}
