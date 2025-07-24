package com.hiennhatt.vod.validator;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.web.multipart.MultipartFile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;

class IsImageValidator implements jakarta.validation.ConstraintValidator<IsImage, MultipartFile> {
    @Override
    public void initialize(IsImage constraintAnnotation) {
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return Objects.requireNonNull(value.getContentType()).startsWith("image/");
    }
}

@Constraint(validatedBy = IsImageValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsImage {
    String message() default "Invalid video file";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
