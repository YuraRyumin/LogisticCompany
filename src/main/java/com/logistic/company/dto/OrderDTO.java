package com.logistic.company.dto;

import lombok.Data;

@Data
public class OrderDTO {
    private Long id;
    private String uniqueNumber;
    private boolean completed;
}
