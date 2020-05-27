package com.seal.sharding.common;

import com.seal.sharding.common.configuration.MybatisPlusConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author: hsc
 * @date: 2018/9/13 14:57
 * @description
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(MybatisPlusConfig.class)
@Documented
@Inherited
public @interface EnableMybatisPlusConfig {
}
