package com.ingfrf.carpooling.service;

import com.ingfrf.carpooling.dao.CarpoolingDAO;
import com.ingfrf.carpooling.model.Car;
import com.ingfrf.carpooling.model.Journey;
import com.ingfrf.carpooling.model.LocateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarpoolingServiceImpl implements CarpoolingService {

    @Autowired
    private CarpoolingDAO carpoolingDAO;

    @Override
    public void addCars(List<Car> carList) {
        carpoolingDAO.addCars(carList);
    }

    @Override
    public void addJourney(Journey journey) {
        carpoolingDAO.addJourney(journey);
    }

    @Override
    public LocateResponse locateCarByJourneyId(Integer journeyId) {
        Car car = carpoolingDAO.retrieveCarByJourneyId(journeyId);
        if (car != null) {
            return LocateResponse.builder()
                    .car(car)
                    .httpStatus(HttpStatus.OK)
                    .build();
        }
        if (carpoolingDAO.isJourneyInWaitingQueue(journeyId)) {
            return LocateResponse.builder()
                    .httpStatus(HttpStatus.NO_CONTENT)
                    .build();
        }
        return LocateResponse.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();
    }
}
