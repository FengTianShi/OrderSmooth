package com.nobody.OrderSmoothAPI.common;

import com.nobody.OrderSmoothAPI.entity.Owner;
import javax.servlet.http.HttpServletRequest;
import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.StringUtils;

public class RequestUtils {

  public static String getIpAddress(HttpServletRequest request) {
    String[] headers = {
      "X-Forwarded-For", // Squid service proxy
      "Proxy-Client-IP", // Apache service proxy
      "WL-Proxy-Client-IP", // Weblogic service proxy
      "HTTP_CLIENT_IP", // Some proxy servers
      "X-Real-IP", // Nginx service proxy
    };

    for (String header : headers) {
      String ipAddresses = request.getHeader(header);
      if (
        ipAddresses != null &&
        ipAddresses.length() != 0 &&
        !"unknown".equalsIgnoreCase(ipAddresses)
      ) {
        return ipAddresses.split(",")[0];
      }
    }

    return request.getRemoteAddr();
  }

  public static String getDevice(HttpServletRequest request) {
    String userAgentStr = request.getHeader("User-Agent");
    if (StringUtils.isBlank(userAgentStr)) {
      return null;
    }

    UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
    OperatingSystem os = userAgent.getOperatingSystem();
    Browser browser = userAgent.getBrowser();

    return String.format("%s,%s", os.getName(), browser.getName());
  }

  public static Owner getOwner(HttpServletRequest request) {
    String ownerToken = request.getHeader("Authorization");
    if (ownerToken == null || !ownerToken.startsWith("Bearer ")) {
      return null;
    }

    ownerToken = ownerToken.substring(7);
    if (!JwtUtils.isValid(ownerToken)) {
      return null;
    }

    return JwtUtils.getContent(ownerToken, Owner.class);
  }
}
