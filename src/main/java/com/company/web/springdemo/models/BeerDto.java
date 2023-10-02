package com.company.web.springdemo.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class BeerDto {
    @Positive(message = "Id should be positive")
    private int id;
    @NotNull(message = "Name can't be empty")
    @Size(min = 2, max = 20, message = "Name should be between 2 and 20 symbols")
    private String name;

    @Positive(message = "ABV should be positive")
    private double abv;
}
