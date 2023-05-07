package com.bsuir.lr.demo.controllers;

import com.bsuir.lr.demo.cache.Cache;
import com.bsuir.lr.demo.models.DryMass;
import com.bsuir.lr.demo.models.DryPercentage;
import com.bsuir.lr.demo.models.SolutionMass;
import com.bsuir.lr.demo.counter.CounterThread;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class DryMassController {
    Logger logger = LoggerFactory.getLogger(DryMassController.class);
    Cache cache = new Cache();

    @RequestMapping(value = "/mass",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String dryMass(@RequestBody List<Map<String, Double>> params)
            throws JSONException, IllegalArgumentException {
        logger.info("started processing");

        CounterThread counter = new CounterThread();
        counter.start();

        List<Double> dryMassList = new ArrayList<>();

        for (Map<String, Double> param : params) {
            Double solutionMass = param.get("solutionMass");
            Double dryPercentage = param.get("dryPercentage");

            logger.info("solutionMass validation");
            SolutionMass.validate(solutionMass);

            logger.info("dryPercentage validation");
            DryPercentage.validate(dryPercentage);

            Double dryMass = cache.get(solutionMass + " " + dryPercentage);
            if (dryMass == null) {
                logger.info("counted");
                DryMass dryMassObj = DryMass.calculate(solutionMass, dryPercentage);
                dryMass = dryMassObj.getDryMass();
                cache.put(solutionMass + " " + dryPercentage, dryMass);
            } else {
                logger.info("got from cache");
            }

            dryMassList.add(dryMass);
        }

        JSONObject response = new JSONObject();
        response.put("answers", dryMassList);
        return response.toString();
    }


    @RequestMapping(value = "/bulk",
            method = RequestMethod.POST,
            produces = "application/json")
    public String bulk(@RequestBody List<Map<String, Double>> params) {
        logger.info("bulk started");

        List<Double> answers = new ArrayList<>();
        for (Map<String, Double> solution : params) {
            Double solutionMass = solution.get("solutionMass");
            Double dryPercentage = solution.get("dryPercentage");
            logger.info("solutionMass validation");
            SolutionMass.validate(solutionMass);

            logger.info("dryPercentage validation");
            DryPercentage.validate(dryPercentage);

            Double dryMass = cache.get(solutionMass + " " + dryPercentage);
            if (dryMass == null) {
                logger.info("counted");
                DryMass dryMassObj = DryMass.calculate(solutionMass, dryPercentage);
                dryMass = dryMassObj.getDryMass();
                cache.put(dryPercentage.toString(), dryMass);
            } else {
                logger.info("got from cache");
            }

            answers.add(dryMass);
        }

        JSONObject response = new JSONObject();
        response.put("answers", answers);
        response.put("minMassInput", params.stream().mapToDouble(map -> map.get("solutionMass")).min().orElse(Double.MIN_VALUE));
        response.put("avgMassInp", params.stream().mapToDouble(map -> map.get("solutionMass")).average().orElse(Double.MIN_VALUE));
        response.put("maxMassInput", params.stream().mapToDouble(map -> map.get("solutionMass")).max().orElse(Double.MIN_VALUE));
        response.put("minPercentageInput", params.stream().mapToDouble(map -> map.get("dryPercentage")).min().orElse(Double.MIN_VALUE));
        response.put("avgPercentageInp", params.stream().mapToDouble(map -> map.get("dryPercentage")).average().orElse(Double.MIN_VALUE));
        response.put("maxPercentageInput", params.stream().mapToDouble(map -> map.get("dryPercentage")).max().orElse(Double.MIN_VALUE));
        response.put("minAns", answers.stream().mapToDouble(dm -> new DryMass(dm).getDryMass()).min().orElse(Double.MIN_VALUE));
        response.put("maxAns", answers.stream().mapToDouble(dm -> new DryMass(dm).getDryMass()).max().orElse(Double.MIN_VALUE));
        response.put("avgAns", answers.stream().mapToDouble(dm -> new DryMass(dm).getDryMass()).average().orElse(Double.MIN_VALUE));
        response.put("amount", answers.size());

        logger.info("GOOD ENDING!");
        return response.toString();
    }


}