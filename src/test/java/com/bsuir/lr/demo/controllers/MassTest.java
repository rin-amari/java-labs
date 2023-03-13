package com.bsuir.lr.demo.controllers;

import com.bsuir.lr.demo.models.DryPercentage;
import com.bsuir.lr.demo.models.SolutionMass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MassTest {
    @Test
    void validateExceptionNegativeSolutionMass() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            SolutionMass.validate((double) -1);
        });
    }
    @Test
    void validateExceptionNegativePercentage() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            DryPercentage.validate((double) -9);
        });
    }

    @Test
    void validateExceptionGreaterPercentage() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            DryPercentage.validate((double) 200);
        });
    }
}
