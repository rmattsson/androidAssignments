package ca.qc.johnabbott.cs616.notes.server.model;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Constraint for dates to be consistent.
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
@Constraint(validatedBy = NoteDatesRangeValidator.class)
@Target(TYPE)
@Retention(RUNTIME)
@Documented
public @interface NoteDatesRange {
    String message() default "{Note reminder date/time must be after note creation date/time.}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
