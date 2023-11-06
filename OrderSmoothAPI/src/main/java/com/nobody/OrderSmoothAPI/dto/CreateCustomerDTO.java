package com.nobody.OrderSmoothAPI.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerDTO implements Serializable {

    @NotBlank
    @Size(min = 1, max = 100)
    private String customerName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past
    private Date customerBir;

    @Min(0)
    @Max(1)
    private Integer customerGander;

    @Size(max = 11)
    private String customerPhone;

    @Size(max = 500)
    private String customerAdd;

}
