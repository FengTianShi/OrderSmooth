package com.nobody.OrderSmoothAPI.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang3.StringUtils;

import com.nobody.OrderSmoothAPI.common.RequestUtils;
import com.nobody.OrderSmoothAPI.dto.OwnerLoginParamDTO;
import com.nobody.OrderSmoothAPI.service.OwnerLoginService;

@RestController
@RequestMapping("/owner")
public class OwnerLoginController {

    Logger logger = LoggerFactory.getLogger(OwnerLoginController.class);

    @Autowired
    private OwnerLoginService ownerLoginService;

    @PostMapping("/login")
    public ResponseEntity<String> ownerLogin(
            @Valid @RequestBody OwnerLoginParamDTO ownerLoginParamDTO, HttpServletRequest request) {

        String ipAddress = RequestUtils.getIpAdrress(request);
        if (StringUtils.isBlank(ipAddress))
            return ResponseEntity.badRequest().body("IP ADDRESS NOT FOUND");
        ownerLoginParamDTO.setIpAddress(ipAddress);

        String deviceinfo = RequestUtils.getDeviceInfo(request);
        if (StringUtils.isBlank(deviceinfo))
            return ResponseEntity.badRequest().body("DEVICE INFO NOT FOUND");
        ownerLoginParamDTO.setDeviceInfo(deviceinfo);

        String ownerSessionToken = ownerLoginService.ownerLogin(ownerLoginParamDTO);
        if (ownerSessionToken == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("EMAIL OR PASSWORD NOT CORRECT");

        return ResponseEntity.ok(ownerSessionToken);
    }

}
