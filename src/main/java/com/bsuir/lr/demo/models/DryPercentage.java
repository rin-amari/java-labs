package com.bsuir.lr.demo.models;

import com.bsuir.lr.demo.controllers.DryMassController;
import com.bsuir.lr.demo.exceptions.IllegalArgumentsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DryPercentage {
    private Double dryPercentage;

    public DryPercentage(Double dryPercentage) {
        this.dryPercentage = dryPercentage;
    }

    public static void validate(Double dryPercentage) throws IllegalArgumentsException, RuntimeException {

        if (dryPercentage <= 0 || dryPercentage > 100) {
            Logger logger = LoggerFactory.getLogger(DryMassController.class);
            logger.info("dryPercentage has to be > 0 and <= 100");
            throw new IllegalArgumentsException("dryPercentage has to be > 0 and <= 100");
        }

    }

    public Double getDryPercentage() {
        return dryPercentage;
    }
}
