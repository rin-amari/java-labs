package com.bsuir.lr.demo.controllers;

import com.bsuir.lr.demo.counter.CounterThread;
import com.bsuir.lr.demo.models.DryMass;
import com.bsuir.lr.demo.repos.DryMassRepository;
import com.bsuir.lr.demo.service.ServiceM;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RestController
public class DryMassController {
    private final Logger logger = LoggerFactory.getLogger(DryMassController.class);

    @Autowired
    private DryMassRepository dryMassRepo;


    @RequestMapping(value = "/mass",
            method = RequestMethod.GET,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> dryMass(@RequestBody List<Map<String, Double>> params)
            throws JSONException, IllegalArgumentException {
        logger.info("started processing");

        CounterThread counter = new CounterThread();
        counter.start();

        List<Double> dryMassList = ServiceM.processDryMassParams(params);

        JSONObject response = new JSONObject();
        response.put("answers", dryMassList);
        return ResponseEntity.ok(response.toString());
    }

    @RequestMapping(value = "/db_mass" )
    public ResponseEntity<?> dbMass(@RequestParam("solutionMass") Double solutionMass, @RequestParam("dryPercentage") Double dryPercentage) throws JSONException {
        logger.info("started processing");

        ServiceM.validate(solutionMass, dryPercentage);

        DryMass dryMass;
        dryMass = DryMass.calculate(solutionMass, dryPercentage);

        CompletableFuture<CompletableFuture<Double>> future = CompletableFuture.supplyAsync(() ->
                saveDryMass(solutionMass, dryPercentage)
        );

        try {
            future.get();
            JSONObject response = new JSONObject();
            response.put("ok: ", 1);
            //response.put("ok: ", dryMass.getDryMassId());
            return ResponseEntity.ok(response.toString());
        } catch (Exception e) {
            logger.error("Error saving dry mass", e);
            JSONObject response = new JSONObject();
            response.put("Error", -1);
            return new ResponseEntity<>("Error:" + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Async
    public CompletableFuture<Double> saveDryMass(Double solutionMass, Double dryPercentage) {
        return CompletableFuture.supplyAsync(() -> {
            DryMass dryMass = DryMass.calculate(solutionMass, dryPercentage);
            logger.info(solutionMass + " " + dryPercentage + " = " + dryMass.getDryMass());
            dryMassRepo.save(dryMass);

            logger.info("id: " + dryMass.getDryMassId().toString());

            return dryMass.getDryMass();
        }, CompletableFuture.delayedExecutor(20000, TimeUnit.MILLISECONDS));
    }

    @RequestMapping(value = "/result",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<?> result(@RequestParam("id") Long id) {
        DryMass dryMass = dryMassRepo.findById(id).orElse(null);
        if (dryMass == null) {
            JSONObject response = new JSONObject();
            response.put("null: ", 0);
            return ResponseEntity.ok(response.toString());
        }
        JSONObject response = new JSONObject();
        response.put("result from db: ", dryMass.getDryMass());
        return ResponseEntity.ok(response.toString());
    }

    @RequestMapping(value = "/bulk",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<?> bulk(@RequestBody List<Map<String, Double>> params) {
        logger.info("bulk started");

        List<Double> answers = ServiceM.processDryMassParams(params);

        JSONObject response = new JSONObject();
        response.put("answers", answers);
        response.put("minMassInput", params.stream().mapToDouble(map -> map.get("solutionMass")).min().orElse(Double.MIN_VALUE));
        response.put("avgMassInp", params.stream().mapToDouble(map -> map.get("solutionMass")).average().orElse(Double.MIN_VALUE));
        response.put("maxMassInput", params.stream().mapToDouble(map -> map.get("solutionMass")).max().orElse(Double.MIN_VALUE));
        response.put("minPercentageInput", params.stream().mapToDouble(map -> map.get("dryPercentage")).min().orElse(Double.MIN_VALUE));
        response.put("avgPercentageInp", params.stream().mapToDouble(map -> map.get("dryPercentage")).average().orElse(Double.MIN_VALUE));
        response.put("maxPercentageInput", params.stream().mapToDouble(map -> map.get("dryPercentage")).max().orElse(Double.MIN_VALUE));
        response.put("minAns", answers.stream().mapToDouble(dm -> new DryMass(dm, 0.0, 0.0).getDryMass()).min().orElse(Double.MIN_VALUE));
        response.put("maxAns", answers.stream().mapToDouble(dm -> new DryMass(dm, 0.0, 0.0).getDryMass()).max().orElse(Double.MIN_VALUE));
        response.put("avgAns", answers.stream().mapToDouble(dm -> new DryMass(dm, 0.0, 0.0).getDryMass()).average().orElse(Double.MIN_VALUE));
        response.put("amount", answers.size());

        logger.info("GOOD ENDING!");
        return ResponseEntity.ok(response.toString());
    }
}
