package ca.qc.johnabbott.cs616.notes.server.model;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Constraint to ensure temporal consistency of create and modified dates.
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public class NoteDatesRangeValidator implements ConstraintValidator<NoteDatesRange, Note> {

    @Override
    public void initialize(NoteDatesRange constraintAnnotation) {

    }

    @Override
    public boolean isValid(Note value, ConstraintValidatorContext context) {
        if(value.getReminder() == null)
            return true;
        else
            return value.getCreated().before(value.getReminder());
    }
}
