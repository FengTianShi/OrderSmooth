package com.nobody.OrderSmoothAPI.service;

import com.nobody.OrderSmoothAPI.common.JwtUtils;
import com.nobody.OrderSmoothAPI.common.StringUtils;
import com.nobody.OrderSmoothAPI.dto.OwnerSigninParam;
import com.nobody.OrderSmoothAPI.entity.Owner;
import com.nobody.OrderSmoothAPI.entity.OwnerSigninMgt;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OwnerSigninService {

  private final OwnerService ownerService;

  private final OwnerSigninMgtService ownerSigninMgtService;

  private final Long OWNER_SIGNIN_EXPIRATION;

  private final Long OWNER_SIGNIN_FAILURE_MAX_TIMES;

  private final Long OWNER_SIGNIN_FAILURE_BAN_SECONDS;

  public OwnerSigninService(
    OwnerService ownerService,
    OwnerSigninMgtService ownerSigninMgtService,
    @Value("${owner.signin.expiration}") Long OWNER_SIGNIN_EXPIRATION,
    @Value(
      "${owner.signin.failure-max-times}"
    ) Long OWNER_SIGNIN_FAILURE_MAX_TIMES,
    @Value(
      "${owner.signin.failure-ban-seconds}"
    ) Long OWNER_SIGNIN_FAILURE_BAN_SECONDS
  ) {
    this.ownerService = ownerService;
    this.ownerSigninMgtService = ownerSigninMgtService;
    this.OWNER_SIGNIN_EXPIRATION = OWNER_SIGNIN_EXPIRATION;
    this.OWNER_SIGNIN_FAILURE_MAX_TIMES = OWNER_SIGNIN_FAILURE_MAX_TIMES;
    this.OWNER_SIGNIN_FAILURE_BAN_SECONDS = OWNER_SIGNIN_FAILURE_BAN_SECONDS;
  }

  public String ownerSignin(OwnerSigninParam ownerSigninParam) {
    Owner owner = ownerService.getOwnerByEmail(
      ownerSigninParam.getOwnerEmail()
    );
    if (owner == null) {
      return null;
    }

    OffsetDateTime nowTime = OffsetDateTime.now(ZoneOffset.UTC);

    OwnerSigninMgt ownerSigninMgt = ownerSigninMgtService.getOwnerSigninMgt(
      owner.getOwnerId(),
      ownerSigninParam.getIpAddress()
    );

    if (ownerSigninMgt == null) {
      ownerSigninMgt =
        OwnerSigninMgt
          .builder()
          .ownerId(owner.getOwnerId())
          .ipAddress(ownerSigninParam.getIpAddress())
          .deviceInfo(ownerSigninParam.getDeviceInfo())
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
        .hashPassword(ownerSigninParam.getOwnerPassword())
        .equals(owner.getOwnerPassword())
    ) {
      ownerSigninMgt.setFailureCount(ownerSigninMgt.getFailureCount() + 1);
      if (ownerSigninMgt.getFailureCount() >= OWNER_SIGNIN_FAILURE_MAX_TIMES) {
        ownerSigninMgt.setIsBan(true);
        ownerSigninMgt.setBanStartTime(nowTime);
        ownerSigninMgt.setBanEndTime(
          nowTime.plusSeconds(OWNER_SIGNIN_FAILURE_BAN_SECONDS)
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
    return JwtUtils.generate("owner-token", owner, OWNER_SIGNIN_EXPIRATION);
  }
}
