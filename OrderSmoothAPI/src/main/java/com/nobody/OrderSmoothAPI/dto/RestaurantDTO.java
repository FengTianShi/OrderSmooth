package com.nobody.OrderSmoothAPI.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO implements Serializable {

  private Long restaurantId;

  private Long ownerId;

  private Integer genreId;

  private String restaurantLogoAddress;

  private String restaurantTel;

  private String restaurantPostalCode;

  private Double restaurantLatitude;

  private Double restaurantLongitude;

  private Integer restaurantServiceDistance;

  private Boolean isLimitServiceDistance;

  private Integer currencyId;

  private Integer defaultServiceFee;

  private Integer defaultTax;

  private Boolean isDisplayServiceFee;

  private Boolean isDisplayTax;

  private String wifiSsid;

  private String wifiPassword;

  private Boolean isInvalid;

  private Boolean isDeleted;

  private OffsetDateTime insertTime;

  private String insertedBy;

  private OffsetDateTime updateTime;

  private String updatedBy;

  private List<RestaurantOpeningHoursDTO> openingHours;

  private List<RestaurantPayMethodDTO> payMethods;

  private List<RestaurantImageDTO> images;

  private List<RestaurantI18nDTO> i18ns;
}
