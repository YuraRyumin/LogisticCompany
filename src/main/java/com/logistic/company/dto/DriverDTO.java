package com.logistic.company.dto;

import lombok.Data;

@Data
public class DriverDTO {
    private Long id;
    private String name;
    private String surname;
    private String personalNumber;
    private Integer workingHours;
    private String status;
    private String currentCity;
    private TruckDTO currentTruck;
    //private String currentTruck;
}
