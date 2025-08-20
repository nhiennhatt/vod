package com.hiennhatt.vod.validations;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddCategoriesToVideoValidation {
    @NotNull
    @NotEmpty
    List<String> categories;
}
