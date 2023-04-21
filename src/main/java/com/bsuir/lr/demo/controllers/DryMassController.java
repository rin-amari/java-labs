package com.bsuir.lr.demo.controllers;

import com.bsuir.lr.demo.models.DryMass;
import com.bsuir.lr.demo.models.DryPercentage;
import com.bsuir.lr.demo.models.SolutionMass;
import com.bsuir.lr.demo.cache.Cache;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DryMassController {
    Logger logger = LoggerFactory.getLogger(DryMassController.class);
    Cache cache = new Cache();

    @GetMapping("/mass")
    public String dryMass(@RequestParam("solutionMass") Double trySolutionMass, @RequestParam("dryPercentage") Double tryDryPercentage) throws JSONException, IllegalArgumentException {
        logger.info("started processing");

        SolutionMass solutionMass = new SolutionMass(trySolutionMass);
        logger.info("solutionMass validation");
        SolutionMass.validate(solutionMass.getSolutionMass());
        logger.info("successful validation");

        DryPercentage dryPercentage = new DryPercentage(tryDryPercentage);
        logger.info("dryPercentage validation");
        DryPercentage.validate(dryPercentage.getDryPercentage());
        logger.info("successful validation");

        Double value = cache.get(solutionMass + " " + dryPercentage);
        DryMass dryMass;
        if (value != null) {
            dryMass = new DryMass(value);
            logger.info("got from cache");
        } else {
            dryMass = DryMass.calculate(solutionMass, dryPercentage);
            cache.put(solutionMass + " " + dryPercentage, dryMass.getDryMass());
            logger.info("calculated");
        }

        JSONObject response = new JSONObject();
        return response.put("dry_mass: ", dryMass.getDryMass().toString()).toString();
    }

}