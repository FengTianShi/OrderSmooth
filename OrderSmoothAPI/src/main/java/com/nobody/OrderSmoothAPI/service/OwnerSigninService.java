package com.nobody.OrderSmoothAPI.service;

import com.nobody.OrderSmoothAPI.common.JwtUtils;
import com.nobody.OrderSmoothAPI.common.StringUtils;
import com.nobody.OrderSmoothAPI.dto.OwnerSigninParamDTO;
import com.nobody.OrderSmoothAPI.entity.Owner;
import com.nobody.OrderSmoothAPI.entity.OwnerSigninMgt;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OwnerSigninService {

  Logger logger = LoggerFactory.getLogger(OwnerSigninService.class);

  @Value("${owner.signin.failure-max-times}")
  private Long ownerSigninFailureMaxTimes;

  @Value("${owner.signin.failure-ban-seconds}")
  private Long ownerSigninFailureBanSeconds;

  @Value("${owner.signin.expiration}")
  private Long ownerSigninExpiration;

  @Autowired
  private OwnerService ownerService;

  @Autowired
  private OwnerSigninMgtService ownerSigninMgtService;

  public String ownerSignin(OwnerSigninParamDTO ownerSigninParamDTO) {
    Owner owner = ownerService.getOwnerByEmail(
      ownerSigninParamDTO.getOwnerEmail()
    );

    if (owner == null) return null;

    OffsetDateTime nowTime = OffsetDateTime.now(ZoneOffset.UTC);

    OwnerSigninMgt ownerSigninMgt = ownerSigninMgtService.getOwnerSigninMgt(
      owner.getOwnerId(),
      ownerSigninParamDTO.getIpAddress()
    );

    if (ownerSigninMgt == null) {
      ownerSigninMgt =
        OwnerSigninMgt
          .builder()
          .ownerId(owner.getOwnerId())
          .ipAddress(ownerSigninParamDTO.getIpAddress())
          .deviceInfo(ownerSigninParamDTO.getDeviceInfo())
          .failureCount(0)
          .isBan(false)
          .banStartTime(null)
          .banEndTime(null)
          .insertTime(nowTime)
          .insertedBy("SYSTEM")
          .updateTime(nowTime)
          .updatedBy("SYSTEM")
          .build();

      ownerSigninMgtService.createOwnerSigninMgt(ownerSigninMgt);
    }

    if (
      !StringUtils
        .encryptPassword(ownerSigninParamDTO.getOwnerPassword())
        .equals(owner.getOwnerPassword())
    ) {
      ownerSigninMgt.setFailureCount(ownerSigninMgt.getFailureCount() + 1);
      if (ownerSigninMgt.getFailureCount() >= ownerSigninFailureMaxTimes) {
        ownerSigninMgt.setIsBan(true);
        ownerSigninMgt.setBanStartTime(nowTime);
        ownerSigninMgt.setBanEndTime(
          nowTime.plusSeconds(ownerSigninFailureBanSeconds)
        );
      }
      ownerSigninMgt.setUpdateTime(nowTime);
      ownerSigninMgt.setUpdatedBy("SYSTEM");

      ownerSigninMgtService.updateOwnerSigninMgt(ownerSigninMgt);

      return null;
    }

    if (
      ownerSigninMgt.getIsBan() &&
      ownerSigninMgt.getBanStartTime().isBefore(nowTime) &&
      ownerSigninMgt.getBanEndTime().isAfter(nowTime)
    ) {
      return null;
    }

    ownerSigninMgt.setIsBan(false);
    ownerSigninMgt.setFailureCount(0);
    ownerSigninMgt.setUpdateTime(nowTime);
    ownerSigninMgt.setUpdatedBy("SYSTEM");
    ownerSigninMgtService.updateOwnerSigninMgt(ownerSigninMgt);

    owner.setOwnerPassword(null);

    return JwtUtils.generateToken("owner-token", owner, ownerSigninExpiration);
  }
}
