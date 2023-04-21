package com.bsuir.lr.demo.controllers;

import com.bsuir.lr.demo.counter.Counter;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CountController {
    Logger logger = LoggerFactory.getLogger(CountController.class);
    @GetMapping(value = "/counter")
    public String getCounter(){
        JSONObject response = new JSONObject();
        logger.info("count: " + Counter.getCount());
        return response.put("count: ", Counter.getCount()).toString();
    }
}
