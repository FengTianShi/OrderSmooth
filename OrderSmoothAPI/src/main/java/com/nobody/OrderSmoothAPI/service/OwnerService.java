package com.nobody.OrderSmoothAPI.service;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.nobody.OrderSmoothAPI.common.StringUtils;
import com.nobody.OrderSmoothAPI.dto.OwnerResetPasswordParam;
import com.nobody.OrderSmoothAPI.dto.OwnerSignupParam;
import com.nobody.OrderSmoothAPI.entity.Owner;
import com.nobody.OrderSmoothAPI.mapper.OwnerMapper;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OwnerService {

  private final OwnerMapper ownerMapper;

  public OwnerService(OwnerMapper ownerMapper) {
    this.ownerMapper = ownerMapper;
  }

  public Owner getOwnerById(Long ownerId) {
    return ownerMapper.selectOne(
      new MPJLambdaWrapper<Owner>()
        .selectAll(Owner.class)
        .eq(Owner::getOwnerId, ownerId)
        .eq(Owner::getIsInvalid, false)
        .eq(Owner::getIsDeleted, false)
    );
  }

  public Owner getOwnerByEmail(String ownerEmail) {
    return ownerMapper.selectOne(
      new MPJLambdaWrapper<Owner>()
        .selectAll(Owner.class)
        .eq(Owner::getOwnerEmail, ownerEmail)
        .eq(Owner::getIsInvalid, false)
        .eq(Owner::getIsDeleted, false)
    );
  }

  @Transactional
  public Integer createOwner(OwnerSignupParam ownerSignupParam) {
    OffsetDateTime nowTime = OffsetDateTime.now(ZoneOffset.UTC);
    return ownerMapper.insert(
      Owner
        .builder()
        .ownerName(ownerSignupParam.getOwnerName())
        .ownerEmail(ownerSignupParam.getOwnerEmail())
        .ownerPassword(
          StringUtils.hashPassword(ownerSignupParam.getOwnerPassword())
        )
        .isInvalid(false)
        .isDeleted(false)
        .insertTime(nowTime)
        .insertedBy("SYSTEM")
        .updateTime(nowTime)
        .updatedBy("SYSTEM")
        .build()
    );
  }

  @Transactional
  public Integer updateOwnerPasswordByEmail(
    OwnerResetPasswordParam ownerResetPasswordParam
  ) {
    OffsetDateTime nowTime = OffsetDateTime.now(ZoneOffset.UTC);
    return ownerMapper.update(
      Owner
        .builder()
        .ownerPassword(
          StringUtils.hashPassword(ownerResetPasswordParam.getNewPassword())
        )
        .updateTime(nowTime)
        .updatedBy("SYSTEM")
        .build(),
      new MPJLambdaWrapper<Owner>()
        .eq(Owner::getOwnerEmail, ownerResetPasswordParam.getOwnerEmail())
        .eq(Owner::getIsInvalid, false)
        .eq(Owner::getIsDeleted, false)
    );
  }
}
