package com.bsuir.lr.demo.exceptions;

import com.bsuir.lr.demo.controllers.DryMassController;
import org.apache.logging.log4j.LogManager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.logging.Logger;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "bad argument(s)")
public class IllegalArgumentsException extends Exception {
    private static final Logger logger = (Logger) LogManager.getLogger(DryMassController.class);

    public IllegalArgumentsException(String message) {
        super(message);
        logger.severe(message);
    }

}
