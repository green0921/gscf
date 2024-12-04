package com.gscf.renovation.controller;

import com.gscf.renovation.service.RenovationCalculator;
import com.gscf.renovation.model.Renovation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.gscf.constants.Constants.ROOM_FILE_NAME;

@RestController
public class RenovationController {

    @Autowired
    private RenovationCalculator renovationCalculator;

    @GetMapping("/renovation")
    public Renovation renovation() {
        return renovationCalculator.calculate(ROOM_FILE_NAME);
    }
}
