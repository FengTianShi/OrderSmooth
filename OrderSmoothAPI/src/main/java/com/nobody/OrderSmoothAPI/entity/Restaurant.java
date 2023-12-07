package com.nobody.OrderSmoothAPI.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_restaurant")
public class Restaurant implements Serializable {

  @TableId(type = IdType.AUTO)
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
}
