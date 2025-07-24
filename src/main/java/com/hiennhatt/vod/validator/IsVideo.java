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

class IsVideoValidator implements jakarta.validation.ConstraintValidator<IsVideo, MultipartFile> {
    @Override
    public void initialize(IsVideo constraintAnnotation) {
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return Objects.requireNonNull(value.getContentType()).startsWith("video/");
    }
}

@Constraint(validatedBy = IsVideoValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsVideo {
    String message() default "Invalid video file";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
