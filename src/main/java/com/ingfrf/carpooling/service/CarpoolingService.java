package com.ingfrf.carpooling.service;

import com.ingfrf.carpooling.model.Car;
import com.ingfrf.carpooling.model.Journey;
import com.ingfrf.carpooling.model.LocateResponse;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface CarpoolingService {
    void addCars(List<Car> carList);
    void addJourney(Journey journey);
    HttpStatus dropJourneyById(Integer journeyId);
    LocateResponse locateCarByJourneyId(Integer journeyId);
}
