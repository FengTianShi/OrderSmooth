package com.nobody.OrderSmoothAPI.service;

import com.nobody.OrderSmoothAPI.common.JwtUtils;
import com.nobody.OrderSmoothAPI.common.StringUtils;
import com.nobody.OrderSmoothAPI.dto.OwnerLoginParamDTO;
import com.nobody.OrderSmoothAPI.entity.Owner;
import com.nobody.OrderSmoothAPI.entity.OwnerLoginMgt;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OwnerLoginService {

  Logger logger = LoggerFactory.getLogger(OwnerLoginService.class);

  @Value("${owner.login.failure-max-times}")
  private Long ownerLoginFailureMaxTimes;

  @Value("${owner.login.failure-ban-seconds}")
  private Long ownerLoginFailureBanSeconds;

  @Value("${owner.session.expiration}")
  private Long ownerSessionExpiration;

  @Autowired
  private OwnerService ownerService;

  @Autowired
  private OwnerLoginMgtService ownerLoginMgtService;

  public String ownerLogin(OwnerLoginParamDTO ownerLoginParamDTO) {
    Owner owner = ownerService.getOwnerByEmail(
      ownerLoginParamDTO.getOwnerEmail()
    );

    if (owner == null) return null;

    OffsetDateTime nowTime = OffsetDateTime.now(ZoneOffset.UTC);

    OwnerLoginMgt ownerLoginMgt = ownerLoginMgtService.getOwnerLoginMgt(
      owner.getOwnerId(),
      ownerLoginParamDTO.getIpAddress()
    );

    if (ownerLoginMgt == null) {
      ownerLoginMgt =
        OwnerLoginMgt
          .builder()
          .ownerId(owner.getOwnerId())
          .ipAddress(ownerLoginParamDTO.getIpAddress())
          .deviceInfo(ownerLoginParamDTO.getDeviceInfo())
          .failureCount(0)
          .isBan(false)
          .banStartTime(null)
          .banEndTime(null)
          .insertTime(nowTime)
          .insertedBy("SYSTEM")
          .updateTime(nowTime)
          .updatedBy("SYSTEM")
          .build();

      ownerLoginMgtService.createOwnerLoginMgt(ownerLoginMgt);
    }

    if (
      !StringUtils
        .encryptPassword(ownerLoginParamDTO.getOwnerPassword())
        .equals(owner.getOwnerPassword())
    ) {
      ownerLoginMgt.setFailureCount(ownerLoginMgt.getFailureCount() + 1);
      if (ownerLoginMgt.getFailureCount() >= ownerLoginFailureMaxTimes) {
        ownerLoginMgt.setIsBan(true);
        ownerLoginMgt.setBanStartTime(nowTime);
        ownerLoginMgt.setBanEndTime(
          nowTime.plusSeconds(ownerLoginFailureBanSeconds)
        );
      }
      ownerLoginMgt.setUpdateTime(nowTime);
      ownerLoginMgt.setUpdatedBy("SYSTEM");

      ownerLoginMgtService.updateOwnerLoginMgt(ownerLoginMgt);

      return null;
    }

    if (
      ownerLoginMgt.getIsBan() &&
      ownerLoginMgt.getBanStartTime().isBefore(nowTime) &&
      ownerLoginMgt.getBanEndTime().isAfter(nowTime)
    ) {
      return null;
    }

    ownerLoginMgt.setIsBan(false);
    ownerLoginMgt.setFailureCount(0);
    ownerLoginMgt.setUpdateTime(nowTime);
    ownerLoginMgt.setUpdatedBy("SYSTEM");
    ownerLoginMgtService.updateOwnerLoginMgt(ownerLoginMgt);

    owner.setOwnerPassword(null);

    return JwtUtils.generateToken(
      "owner-session-token",
      owner,
      ownerSessionExpiration
    );
  }
}
