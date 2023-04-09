package com.bsuir.lr.demo.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DryPercentage {
    private Double dryPercentage;
    static Logger logger = LoggerFactory.getLogger(DryPercentage.class);

    public DryPercentage(Double dryPercentage) {
        this.dryPercentage = dryPercentage;
    }

    public static void validate(Double dryPercentage) throws RuntimeException {

        if (dryPercentage <= 0 || dryPercentage > 100) {
            logger.info("dryPercentage has to be > 0 and <= 100");
            throw new IllegalArgumentException("dryPercentage has to be > 0 and <= 100");
        }

    }

    public Double getDryPercentage() {
        return dryPercentage;
    }

    @Override
    public String toString() {
        return  dryPercentage.toString();
    }
}
