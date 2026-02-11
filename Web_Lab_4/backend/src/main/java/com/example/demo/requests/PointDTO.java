package com.example.demo.requests;


import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class PointDTO {
    @NotNull @Min(value = -10) @Max(value = 10)Double x;
    @NotNull @Min(value = -10) @Max(value = 10)Double y;
    @NotNull @Min(value = -3) @Max(value = 5)Double r;
}

