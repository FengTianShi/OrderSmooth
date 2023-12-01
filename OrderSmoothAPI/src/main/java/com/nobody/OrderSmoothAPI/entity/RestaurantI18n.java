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
@TableName("t_restaurant_i18n")
public class RestaurantI18n implements Serializable {

  @TableId(type = IdType.AUTO)
  private Long seq;

  private Long restaurantId;

  private String langCode;

  private String restaurantName;

  private String restaurantAddress;

  private String restaurantDescription;

  private Boolean isInvalid;

  private Boolean isDeleted;

  private OffsetDateTime insertTime;

  private String insertedBy;

  private OffsetDateTime updateTime;

  private String updatedBy;
}
