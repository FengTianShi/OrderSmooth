package com.nobody.OrderSmoothAPI.service;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.nobody.OrderSmoothAPI.entity.OwnerLoginMgt;
import com.nobody.OrderSmoothAPI.mapper.OwnerLoginMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OwnerLoginMgtService {

  Logger logger = LoggerFactory.getLogger(OwnerLoginMgtService.class);

  @Autowired
  private OwnerLoginMapper ownerLoginMapper;

  public OwnerLoginMgt getOwnerLoginMgt(Long ownerId, String ipAddress) {
    return ownerLoginMapper.selectOne(
      new MPJLambdaWrapper<OwnerLoginMgt>()
        .selectAll(OwnerLoginMgt.class)
        .eq(OwnerLoginMgt::getOwnerId, ownerId)
        .eq(OwnerLoginMgt::getIpAddress, ipAddress)
    );
  }

  @Transactional
  public Integer createOwnerLoginMgt(OwnerLoginMgt ownerLoginMgt) {
    return ownerLoginMapper.insert(ownerLoginMgt);
  }

  @Transactional
  public Integer updateOwnerLoginMgt(OwnerLoginMgt ownerLoginMgt) {
    return ownerLoginMapper.updateById(ownerLoginMgt);
  }
}
