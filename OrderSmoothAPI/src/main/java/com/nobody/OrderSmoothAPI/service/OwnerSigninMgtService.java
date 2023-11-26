package com.nobody.OrderSmoothAPI.service;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.nobody.OrderSmoothAPI.entity.OwnerSigninMgt;
import com.nobody.OrderSmoothAPI.mapper.OwnerSigninMgtMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OwnerSigninMgtService {

  private final OwnerSigninMgtMapper ownerSigninMgtMapper;

  public OwnerSigninMgtService(OwnerSigninMgtMapper ownerSigninMgtMapper) {
    this.ownerSigninMgtMapper = ownerSigninMgtMapper;
  }

  public OwnerSigninMgt getOwnerSigninMgt(Long ownerId, String ipAddress) {
    return ownerSigninMgtMapper.selectOne(
      new MPJLambdaWrapper<OwnerSigninMgt>()
        .selectAll(OwnerSigninMgt.class)
        .eq(OwnerSigninMgt::getOwnerId, ownerId)
        .eq(OwnerSigninMgt::getIpAddress, ipAddress)
    );
  }

  @Transactional
  public Integer createOwnerSigninMgt(OwnerSigninMgt ownerSigninMgt) {
    return ownerSigninMgtMapper.insert(ownerSigninMgt);
  }

  @Transactional
  public Integer updateOwnerSigninMgt(OwnerSigninMgt ownerSigninMgt) {
    return ownerSigninMgtMapper.updateById(ownerSigninMgt);
  }
}
