package com.novo.microservices.dtos;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class WarmUpRequestDto {
    @NotBlank
    @Pattern(regexp = "warm me up")
    private String warmUpString;

    @Min(10)
    @Max(20)
    private int warmUpNumber;

    @NotNull
    private BigDecimal warmUpBigDecimal;

    public String getWarmUpString() {
        return warmUpString;
    }

    public void setWarmUpString(String warmUpString) {
        this.warmUpString = warmUpString;
    }

    public int getWarmUpNumber() {
        return warmUpNumber;
    }

    public void setWarmUpNumber(int warmUpNumber) {
        this.warmUpNumber = warmUpNumber;
    }

    public BigDecimal getWarmUpBigDecimal() {
        return warmUpBigDecimal;
    }

    public void setWarmUpBigDecimal(BigDecimal warmUpBigDecimal) {
        this.warmUpBigDecimal = warmUpBigDecimal;
    }
}
