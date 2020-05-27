package com.seal.sharding.common.utils;

import java.util.Map;

/**
 * @author: liupan
 * @date: 2018-12-09 23:53
 * @description
 */
public class MapUtil {

    public static Integer getIntDefault(Map<?, ?> map, Object key, Integer defaultVal) {
        Integer r = cn.hutool.core.map.MapUtil.getInt(map, key);
        if (r == null) {
            return defaultVal;
        }
        return r;
    }
}
