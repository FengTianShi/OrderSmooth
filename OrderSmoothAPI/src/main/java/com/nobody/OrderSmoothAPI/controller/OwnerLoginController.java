package com.nobody.OrderSmoothAPI.controller;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang3.StringUtils;

import com.nobody.OrderSmoothAPI.common.RequestUtils;
import com.nobody.OrderSmoothAPI.dto.OwnerLoginParamDTO;
import com.nobody.OrderSmoothAPI.entity.Owner;
import com.nobody.OrderSmoothAPI.entity.OwnerLoginMgt;

@RestController
@RequestMapping("/api/auth")
public class OwnerLoginController {

    Logger logger = LoggerFactory.getLogger(OwnerLoginController.class);

    @Autowired
    private OwnerSessionService ownerSessionService;

    @PostMapping("/owner/session")
    public OwnerSessionDTO createOwnerSession(
            @Valid @RequestBody OwnerLoginParamDTO createOwnerSessionDTO, HttpServletRequest request) {

        String ipAddress = RequestUtils.getIpAdrress(request);
        if (StringUtils.isBlank(ipAddress)) {
            return null;
        }
        createOwnerSessionDTO.setIpAddress(ipAddress);

        String deviceinfo = RequestUtils.getDeviceInfo(request);
        if (StringUtils.isBlank(deviceinfo)) {
            return null;
        }
        createOwnerSessionDTO.setDeviceInfo(deviceinfo);

        return ownerSessionService.createOwnerSession(createOwnerSessionDTO);
    }

    @DeleteMapping("/owner/session/{ownerId}/{sessionToken}")
    public ResultDTO deleteOwnerSession(@PathVariable Long ownerId, @PathVariable String sessionToken) {
        if (ownerSessionService.deleteOwnerSession(ownerId, sessionToken) > 0) {
            return ResultDTO.builder()
                    .code(204)
                    .message("owner session remove success")
                    .build();
        } else {
            return ResultDTO.builder()
                    .code(404)
                    .message("owner session not found")
                    .build();
        }
    }

    @GetMapping("/owner/session/{ownerId}/{sessionToken}")
    public Boolean checkOwnerSession(@PathVariable Long ownerId, @PathVariable String sessionToken) {
        return ownerSessionService.checkOwnerSession(ownerId, sessionToken);
    }

        public OwnerSessionDTO createOwnerSession(OwnerLoginParamDTO ownerLoginDTO) {

        Owner owner = ownerService.getOwnerByEmail(ownerLoginDTO.getOwnerEmail());

        if (owner == null) {
            return null;
        }

        OffsetDateTime nowTime = OffsetDateTime.now(ZoneOffset.UTC);

        OwnerLoginMgt ownerLoginAttempt = ownerLoginAttemptService
                .getOwnerLoginAttempt(owner.getOwnerId(), ownerLoginDTO.getIpAddress());

        if (ownerLoginAttempt == null) {
            ownerLoginAttempt = OwnerLoginMgt.builder()
                    .ownerId(owner.getOwnerId())
                    .ipAddress(ownerLoginDTO.getIpAddress())
                    .deviceInfo(ownerLoginDTO.getDeviceInfo())
                    .failureCount(0)
                    .isBan(false)
                    .banStartTime(null)
                    .banEndTime(null)
                    .insertTime(nowTime)
                    .insertedBy("SYSTEM")
                    .updateTime(nowTime)
                    .updatedBy("SYSTEM")
                    .build();

            ownerLoginAttemptService.insertOwnerLoginAttempt(ownerLoginAttempt);
        }

        if (!StringUtils.encryptPassword(ownerLoginDTO.getOwnerPassword()).equals(owner.getOwnerPassword())) {

            ownerLoginAttempt.setFailureCount(ownerLoginAttempt.getFailureCount() + 1);

            if (ownerLoginAttempt.getFailureCount() >= ownerLoginMaxFailureAttempt) {
                ownerLoginAttempt.setIsBan(true);
                ownerLoginAttempt.setBanStartTime(nowTime);
                ownerLoginAttempt.setBanEndTime(nowTime.plusSeconds(ownerLoginFailureBanSeconds));
            }

            ownerLoginAttempt.setUpdateTime(nowTime);
            ownerLoginAttempt.setUpdatedBy("SYSTEM");

            ownerLoginAttemptService.updateOwnerLoginAttempt(ownerLoginAttempt);

            return null;
        }

        if (ownerLoginAttempt.getIsBan()
                && ownerLoginAttempt.getBanStartTime().isBefore(nowTime)
                && ownerLoginAttempt.getBanEndTime().isAfter(nowTime)) {
            return null;
        }

        // pass
        ownerLoginAttempt.setIsBan(false);
        ownerLoginAttempt.setFailureCount(0);
        ownerLoginAttempt.setUpdateTime(nowTime);
        ownerLoginAttempt.setUpdatedBy("SYSTEM");
        ownerLoginAttemptService.updateOwnerLoginAttempt(ownerLoginAttempt);

        String sessionToken = UUIDUtil.getUUID();
        OwnerSession ownerSession = OwnerSession.builder()
                .ownerId(owner.getOwnerId())
                .sessionToken(sessionToken)
                .ipAddress(ownerLoginDTO.getIpAddress())
                .deviceInfo(ownerLoginDTO.getDeviceInfo())
                .insertTime(nowTime)
                .insertedBy("SYSTEM")
                .updateTime(nowTime)
                .updatedBy("SYSTEM")
                .build();

        insertOwnerSession(ownerSession);

        if (getOwnerSessionCount(owner.getOwnerId()) > ownerSessionMaxConnection) {
            deleteOwnerSession(getOldestOwnerSession(owner.getOwnerId()));
        }

        return OwnerSessionDTO.builder()
                .ownerId(owner.getOwnerId())
                .sessionToken(sessionToken).build();
    }
}
