package com.bsuir.lr.demo.service;

import com.bsuir.lr.demo.cache.Cache;
import com.bsuir.lr.demo.models.DryMass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceM {
    private static final Cache cache = new Cache();
    private static final Logger logger = LoggerFactory.getLogger(ServiceM.class);

    public static void validate(Double sm, Double dp) {
        logger.info("solutionMass validation");
        Validator.solutionMassValidation(sm);

        logger.info("dryPercentage validation");
        Validator.dryPercentageValidation(dp);
    }

    public static Double calculateDryMass(Double solutionMass, Double dryPercentage) {
        Double dryMass = cache.get(solutionMass + " " + dryPercentage);
        if (dryMass == null) {
            logger.info("counted");
            DryMass dryMassObj = DryMass.calculate(solutionMass, dryPercentage);
            dryMass = dryMassObj.getDryMass();
            cache.put(solutionMass + " " + dryPercentage, dryMass);
        } else {
            logger.info("got from cache");
        }
        return dryMass;
    }

    public static List<Double> processDryMassParams(List<Map<String, Double>> params) {
        List<Double> dryMassList = new ArrayList<>();

        params.forEach(param -> {
            Double solutionMass = param.get("solutionMass");
            Double dryPercentage = param.get("dryPercentage");

            ServiceM.validate(solutionMass, dryPercentage);

            Double dryMass = ServiceM.calculateDryMass(solutionMass, dryPercentage);
            dryMassList.add(dryMass);
        });

        return dryMassList;
    }

}
