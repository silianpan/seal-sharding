package com.seal.sharding.common.utils;

import java.math.BigDecimal;

/**
 * @author: hsc
 * @date: 2018-12-06 17:13
 * @description
 */
public final class ArithHelper {

    private ArithHelper() {
    }


    /**
     * @param v1
     * @param v2
     * @return double
     * @description 2个浮点数相加
     * @author: hsc
     * @date: 2018-12-06 17:14
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * @param v1
     * @param v2
     * @return double
     * @description 2个浮点数相减
     * @author: hsc
     * @date: 2018-12-06 17:15
     */
    public static double sub(double v1, double v2) {

        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * @param v1
     * @param v2
     * @return double
     * @description 2个浮点数 相乘
     * @author: hsc
     * @date: 2018-12-06 17:15
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return double
     * @description
     * @author: hsc
     * @date: 2018-12-06 17:16
     */
    public static double div(double v1, double v2, int scale) {

        if (scale < 0) {
            throw new IllegalArgumentException("此参数错误");
        }
        BigDecimal one = new BigDecimal(Double.toString(v1));
        BigDecimal two = new BigDecimal(Double.toString(v2));
        return one.divide(two, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v
     * @param scale
     * @return double
     * @description
     * @author: hsc
     * @date: 2018-12-06 17:19
     */
    public static double round(double v, int scale) {
        return div(v, 1d, scale);
    }
}
