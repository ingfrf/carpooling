package com.ingfrf.carpooling.controller;

import com.ingfrf.carpooling.model.Car;
import com.ingfrf.carpooling.model.Journey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@RestController
@RequestMapping("/")
public class CarpoolingController {

    @GetMapping("status")
    @ResponseStatus(HttpStatus.OK)
    public void getStatus() {

    }

    @PutMapping("cars")
    @ResponseStatus(HttpStatus.OK)
    public void putCars(@RequestBody ArrayList<Car> carList) {
        carList.forEach(car -> log.info("Car number: " + car.getId() + " with " + car.getSeats()+" seats"));
    }

    @PostMapping("journey")
    @ResponseStatus(HttpStatus.OK)
    public void postJourney(@RequestBody Journey journey) {
        log.info("Journey number: " + journey.getId() + " with " + journey.getPeople()+" people");
    }

    @PostMapping("dropoff")
    public ResponseEntity<String> dropoff(@RequestBody MultiValueMap<String,String> request) {
        log.info("DropOff: "+request.get("ID"));
        return new ResponseEntity<>("Parece que foi ben", HttpStatus.ACCEPTED);
    }

    @PostMapping("locate")
    public ResponseEntity<Car> locate(@RequestBody MultiValueMap<String,String> request) {
        log.info("Locate: "+ Integer.parseInt(request.get("ID").get(0)));
        Car car = Car.builder()
                .id(9).seats(8).build();
        return new ResponseEntity<>(car, HttpStatus.OK);
    }
}
