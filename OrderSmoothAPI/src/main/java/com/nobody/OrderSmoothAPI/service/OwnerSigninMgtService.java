package com.nobody.OrderSmoothAPI.service;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.nobody.OrderSmoothAPI.entity.OwnerSigninMgt;
import com.nobody.OrderSmoothAPI.mapper.OwnerSigninMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OwnerSigninMgtService {

  Logger logger = LoggerFactory.getLogger(OwnerSigninMgtService.class);

  @Autowired
  private OwnerSigninMapper ownerSigninMapper;

  public OwnerSigninMgt getOwnerSigninMgt(Long ownerId, String ipAddress) {
    return ownerSigninMapper.selectOne(
      new MPJLambdaWrapper<OwnerSigninMgt>()
        .selectAll(OwnerSigninMgt.class)
        .eq(OwnerSigninMgt::getOwnerId, ownerId)
        .eq(OwnerSigninMgt::getIpAddress, ipAddress)
    );
  }

  @Transactional
  public Integer createOwnerSigninMgt(OwnerSigninMgt ownerSigninMgt) {
    return ownerSigninMapper.insert(ownerSigninMgt);
  }

  @Transactional
  public Integer updateOwnerSigninMgt(OwnerSigninMgt ownerSigninMgt) {
    return ownerSigninMapper.updateById(ownerSigninMgt);
  }
}
