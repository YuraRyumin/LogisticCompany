package com.logistic.company.dto;

import lombok.Data;

@Data
public class TruckDTO {
    private Long id;
    private String number;
    private Integer capacity;
    private boolean status;
    private String city;
}
