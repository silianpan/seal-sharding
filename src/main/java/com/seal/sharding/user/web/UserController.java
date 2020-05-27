package com.seal.sharding.user.web;

import com.seal.sharding.common.web.BaseController;
import com.seal.sharding.user.entity.User;
import com.seal.sharding.user.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 控制器
 * </p>
 *
 * @author panliu
 * @since 2019-04-21
 */

@Slf4j
@Api(tags = {"用户表操作接口"})
@RestController
@RequestMapping("/user/user")
public class UserController extends BaseController<UserService, User> {

}
