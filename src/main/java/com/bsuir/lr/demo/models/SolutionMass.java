package com.bsuir.lr.demo.models;

import com.bsuir.lr.demo.controllers.DryMassController;
import com.bsuir.lr.demo.exceptions.IllegalArgumentsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolutionMass {
    private final Double solutionMass;

    public SolutionMass(Double solutionMass) {
        this.solutionMass = solutionMass;
    }

    public static void validate(Double solutionMass) throws IllegalArgumentsException, RuntimeException {
        if (solutionMass <= 0) {
            Logger logger = LoggerFactory.getLogger(DryMassController.class);
            logger.info("solutionMass has to be > 0");
            throw new IllegalArgumentException("solutionMass has to be > 0");
        }
    }

    public Double getSolutionMass() {
        return solutionMass;
    }
}
