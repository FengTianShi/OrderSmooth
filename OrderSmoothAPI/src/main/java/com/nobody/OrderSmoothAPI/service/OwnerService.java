package com.nobody.OrderSmoothAPI.service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.nobody.OrderSmoothAPI.common.StringUtils;
import com.nobody.OrderSmoothAPI.dto.OwnerResetPasswordParamDTO;
import com.nobody.OrderSmoothAPI.dto.OwnerSignupParamDTO;
import com.nobody.OrderSmoothAPI.entity.Owner;
import com.nobody.OrderSmoothAPI.mapper.OwnerMapper;

@Service
public class OwnerService {

    Logger logger = LoggerFactory.getLogger(OwnerService.class);

    @Autowired
    private OwnerMapper ownerMapper;

    public Owner getOwnerByEmail(String ownerEmail) {
        return ownerMapper.selectOne(new MPJLambdaWrapper<Owner>()
                .selectAll(Owner.class)
                .eq(Owner::getOwnerEmail, ownerEmail)
                .eq(Owner::getIsInvalid, false)
                .eq(Owner::getIsDeleted, false));
    }

    @Transactional
    public Integer insertOwner(OwnerSignupParamDTO ownerSignupParamDTO) {
        OffsetDateTime nowTime = OffsetDateTime.now(ZoneOffset.UTC);

        return ownerMapper.insert(Owner.builder()
                .ownerName(ownerSignupParamDTO.getOwnerName())
                .ownerEmail(ownerSignupParamDTO.getOwnerEmail())
                .ownerPassword(StringUtils.encryptPassword(ownerSignupParamDTO.getOwnerPassword()))
                .isInvalid(false)
                .isDeleted(false)
                .insertTime(nowTime)
                .insertedBy("SYSTEM")
                .updateTime(nowTime)
                .updatedBy("SYSTEM")
                .build());
    }

    @Transactional
    public Integer updateOwnerPasswordByEmail(OwnerResetPasswordParamDTO ownerResetPasswordParamDTO) {
        OffsetDateTime nowTime = OffsetDateTime.now(ZoneOffset.UTC);

        return ownerMapper.update(
                Owner.builder()
                        .ownerPassword(StringUtils.encryptPassword(ownerResetPasswordParamDTO.getNewPassword()))
                        .updateTime(nowTime)
                        .updatedBy("SYSTEM")
                        .build(),
                new MPJLambdaWrapper<Owner>()
                        .eq(Owner::getOwnerEmail, ownerResetPasswordParamDTO.getOwnerEmail())
                        .eq(Owner::getIsInvalid, false)
                        .eq(Owner::getIsDeleted, false));
    }
}
