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
@TableName("t_owner")
public class Owner implements Serializable {

  @TableId(type = IdType.AUTO)
  private Long ownerId;

  private String ownerName;

  private String ownerEmail;

  private String ownerPassword;

  private Boolean isInvalid;

  private Boolean isDeleted;

  private OffsetDateTime insertTime;

  private String insertedBy;

  private OffsetDateTime updateTime;

  private String updatedBy;
}
