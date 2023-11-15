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
@TableName("t_owner_login_mgt")
public class OwnerLoginMgt implements Serializable {

  @TableId(type = IdType.AUTO)
  private Long loginId;

  private Long ownerId;

  private String ipAddress;

  private String deviceInfo;

  private Integer failureCount;

  private Boolean isBan;

  private OffsetDateTime banStartTime;

  private OffsetDateTime banEndTime;

  private OffsetDateTime insertTime;

  private String insertedBy;

  private OffsetDateTime updateTime;

  private String updatedBy;
}
