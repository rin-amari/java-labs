package com.bsuir.lr.demo.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolutionMass {
    private final Double solutionMass;
    static Logger logger = LoggerFactory.getLogger(SolutionMass.class);
    public SolutionMass(Double solutionMass) {
        this.solutionMass = solutionMass;
    }

    public static void validate(Double solutionMass) throws RuntimeException {
        if (solutionMass <= 0) {
            logger.info("solutionMass has to be > 0");
            throw new IllegalArgumentException("solutionMass has to be > 0");
        }
    }

    public Double getSolutionMass() {
        return solutionMass;
    }

    @Override
    public String toString() {
        return  solutionMass.toString();
    }
}
