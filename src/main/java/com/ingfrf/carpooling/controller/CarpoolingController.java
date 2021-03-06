package com.ingfrf.carpooling.controller;

import com.ingfrf.carpooling.model.Car;
import com.ingfrf.carpooling.model.Journey;
import com.ingfrf.carpooling.model.LocateResponse;
import com.ingfrf.carpooling.service.CarpoolingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@RestController
@RequestMapping("/")
public class CarpoolingController {

    @Autowired
    private CarpoolingService carpoolingService;

    @GetMapping("status")
    @ResponseStatus(HttpStatus.OK)
    public void getStatus() {

    }

    @PutMapping("cars")
    @ResponseStatus(HttpStatus.OK)
    public void putCars(@RequestBody ArrayList<Car> carList) {
        carList.forEach(car -> log.debug("Car number: " + car.getId() + " with " + car.getSeats()+" seats"));
        carpoolingService.addCars(carList);
    }

    @PostMapping("journey")
    @ResponseStatus(HttpStatus.OK)
    public void postJourney(@RequestBody Journey journey) {
        log.debug("Journey number: " + journey.getId() + " with " + journey.getPeople()+" people");
        carpoolingService.addJourney(journey);
    }

    @PostMapping("dropoff")
    public ResponseEntity<?> dropoff(@RequestBody MultiValueMap<String,String> request) throws HttpMediaTypeNotSupportedException {
        try {
            Integer journeyId = Integer.parseInt(request.get("ID").get(0));
            log.debug("DropOff: "+journeyId);
            HttpStatus httpStatus = carpoolingService.dropJourneyById(journeyId);
            return new ResponseEntity<>(httpStatus);
        } catch (NumberFormatException e) {
            throw new HttpMediaTypeNotSupportedException(e.toString());
        }
    }

    @PostMapping("locate")
    public ResponseEntity<Car> locate(@RequestBody MultiValueMap<String,String> request) throws HttpMediaTypeNotSupportedException {
        try {
            Integer journeyId = Integer.parseInt(request.get("ID").get(0));
            log.debug("Locate: "+ journeyId);
            LocateResponse response = carpoolingService.locateCarByJourneyId(journeyId);
            return new ResponseEntity<>(response.getCar(), response.getHttpStatus());
        } catch (NumberFormatException e) {
            throw  new HttpMediaTypeNotSupportedException(e.toString());
        }
    }
}
