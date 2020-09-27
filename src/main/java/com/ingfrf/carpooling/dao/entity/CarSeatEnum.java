package com.ingfrf.carpooling.dao.entity;

import java.util.stream.Stream;

public enum CarSeatEnum {
    Under_min(-1),
    Four_seats(4),
    Five_seats(5),
    Six_seats(6),
    ;
    private final int seat;

    CarSeatEnum(int seat) {
        this.seat = seat;
    }

    public int getSeat() {
        return seat;
    }

    public static CarSeatEnum getInstanceBySeat(int seat) {
        return stream()
                .filter(value -> value.getSeat() == seat)
                .findAny()
                .orElse(Under_min);
    }

    public static Stream<CarSeatEnum> stream() {
        return Stream.of(CarSeatEnum.values());
    }
}
