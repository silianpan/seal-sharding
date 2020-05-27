package com.seal.sharding.common.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 流水号生成 工具类
 *
 * @author: liupan
 * @date: 2018-12-06 10:02
 * @description
 */
public final class SeqHelper {

    private static final String defaultDateFormat = "YYYYMMddHHmmss";
    private static final int randomNumLength = 8;

    private SeqHelper() {

    }

    public static String create() {
        // 生成规则 YYYYMMDDHHMMSS-(8位随机数)
        return create(null, defaultDateFormat, randomNumLength);
    }

    public static String create(String prefix) {
        return create(prefix, defaultDateFormat, randomNumLength);
    }

    public static String create(String prefix, String dateFormat) {
        return create(prefix, dateFormat, randomNumLength);
    }

    /**
     * @param prefix          前缀
     * @param dateFormat      时间格式字符串
     * @param randomNumLength 随机数字长度
     * @return java.lang.String
     * @description
     * @author: liupan
     * @date: 2018-12-06 10:08
     */
    public static String create(String prefix, String dateFormat, int randomNumLength) {

        StringBuffer sb = new StringBuffer();
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateFormat));
        if (!StringUtils.isEmpty(prefix)) {
            sb.append(prefix);
        }
        sb.append(dateStr)
                .append("-");
        if (randomNumLength > 0) {
            sb.append(RandomStringUtils.randomNumeric(randomNumLength));
        }

        return sb.toString();
    }

    public static String create(String prefix, int randomNumLength) {
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isNotEmpty(prefix)) {
            sb.append(prefix);
        }
        if (randomNumLength > 0) {
            sb.append(RandomStringUtils.randomNumeric(randomNumLength));
        }
        return sb.toString();
    }


}
