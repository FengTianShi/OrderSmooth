package com.nobody.OrderSmoothAPI.dto.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HttpResponseDTO {
    private Integer code;
    private String msg;
}