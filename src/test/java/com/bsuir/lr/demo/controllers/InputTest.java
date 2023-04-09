package com.bsuir.lr.demo.controllers;

import com.bsuir.lr.demo.models.DryMass;
import com.bsuir.lr.demo.models.DryPercentage;
import com.bsuir.lr.demo.models.SolutionMass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InputTest {
    @Test
    void validateExceptionNegativeSolutionMass() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            SolutionMass.validate((double) -1);
        });
    }
    @Test
    void validateExceptionNegativePercentage() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            DryPercentage.validate((double) -1);
        });
    }

    @Test
    public void testCalculate() {
        SolutionMass sm = new SolutionMass(100.0);
        DryPercentage dp = new DryPercentage(50.0);
        DryMass expectedDryMass = new DryMass(50.0);

        DryMass actualDryMass = DryMass.calculate(sm, dp);

        Assertions.assertEquals(expectedDryMass.getDryMass(), actualDryMass.getDryMass());
    }
}




