//package org.example.likelion.constraint;
//
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//import org.example.likelion.dto.request.OrderDetailRequest;
//
//import java.util.List;
//
//public class MaxSizeConstraintValidator implements ConstraintValidator<MaxSizeConstraint, List<OrderDetailRequest>> {
//    @Override
//    public boolean isValid(List<OrderDetailRequest> values, ConstraintValidatorContext context) {
//        return values.size() <= 4;
//    }
//}