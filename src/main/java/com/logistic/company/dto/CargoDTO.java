package com.logistic.company.dto;

import lombok.Data;

@Data
public class CargoDTO {
    private Long id;
    private String name;
    private Integer weight;
    private String status;
}
