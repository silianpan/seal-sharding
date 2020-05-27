package com.seal.sharding.common.utils;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.InetAddress;

/**
 * @author: panliu
 * @date: 2018/8/17 17:25
 * @description
 */
@Slf4j
public class IpUtil {
    /**
     * 获取ip地址
     * 使用Nginx等反向代理软件， 则不能通过request.getRemoteAddr()获取IP地址
     * 如果使用了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP地址，X-Forwarded-For中第一个非unknown的有效IP字符串，则为真实IP地址
     *
     * @param request
     * @return java.lang.String
     * @description
     * @author: panliu
     * @date: 2018/8/17 17:27
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = null;
        String unKnown = "unknown";
        try {
            ip = request.getHeader("x-forwarded-for");
            if (StrUtil.isEmpty(ip) || unKnown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StrUtil.isEmpty(ip) || ip.length() == 0 || unKnown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StrUtil.isEmpty(ip) || unKnown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (StrUtil.isEmpty(ip) || unKnown.equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (StrUtil.isEmpty(ip) || unKnown.equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } catch (Exception e) {
            log.error("IPUtil ERROR ", e.getMessage());
        }
        return ip;
    }


    /**
     * @param ipAddress
     * @param timeOut
     * @return boolean
     * @description 当返回值是true时，说明host是可用的，false则不可。
     * @author: panliu
     * @date: 2018-12-09 21:49
     */
    public static boolean ping(String ipAddress, int timeOut) {
        if (timeOut < 0) {
            return Boolean.FALSE;
        }
        boolean status = false;
        try {
            status = InetAddress.getByName(ipAddress).isReachable(timeOut);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return status;
    }


    /**
     * @param ipAddress
     * @return boolean
     * @description 超时应该在3钞以上
     * @author: panliu
     * @date: 2018-12-09 21:49
     */
    public static boolean ping(String ipAddress) {
        int defaultTimeOut = 3000;
        return ping(ipAddress, defaultTimeOut);
    }


}
