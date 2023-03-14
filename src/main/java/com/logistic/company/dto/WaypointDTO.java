package com.logistic.company.dto;

import lombok.Data;

@Data
public class WaypointDTO {
    private Long id;
    private String order;
    private String city;
    private String cargo;
    private String type;
    private String truck;
}
