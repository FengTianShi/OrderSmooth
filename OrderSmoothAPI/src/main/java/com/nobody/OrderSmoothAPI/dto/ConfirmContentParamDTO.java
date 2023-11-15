package com.nobody.OrderSmoothAPI.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmContentParamDTO implements Serializable {

    @NotBlank
    private String token;

    @NotBlank
    private String otp;

}
