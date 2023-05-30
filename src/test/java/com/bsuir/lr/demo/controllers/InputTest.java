package com.bsuir.lr.demo.controllers;

import com.bsuir.lr.demo.models.DryMass;
import com.bsuir.lr.demo.service.Validator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InputTest {
    @Test
    void validateExceptionNegativeSolutionMass() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Validator.solutionMassValidation((double) -1));
    }
    @Test
    void validateExceptionNegativePercentage() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Validator.dryPercentageValidation((double) -1));
    }

    @Test
    public void testCalculate() {
        Double sm = 100.0;
        Double dp = 50.0;
        DryMass expectedDryMass = new DryMass(50.0, sm, dp);

        DryMass actualDryMass = DryMass.calculate(sm, dp);

        Assertions.assertEquals(expectedDryMass.getDryMass(), actualDryMass.getDryMass());
    }
}




