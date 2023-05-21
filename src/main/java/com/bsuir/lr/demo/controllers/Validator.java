package com.bsuir.lr.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Validator {
    static Logger logger = LoggerFactory.getLogger(Validator.class);

    public static void dryPercentageValidation(Double dryPercentage) throws RuntimeException {
        if (dryPercentage <= 0 || dryPercentage > 100) {
            logger.info("dryPercentage has to be > 0 and <= 100");
            throw new IllegalArgumentException("dryPercentage has to be > 0 and <= 100");
        }
    }

    public static void solutionMassValidation(Double solutionMass) throws RuntimeException {
        if (solutionMass <= 0) {
            logger.info("solutionMass has to be > 0");
            throw new IllegalArgumentException("solutionMass has to be > 0");
        }
    }


}
