package com.seal.sharding.user.service.impl;

import com.seal.sharding.common.service.impl.BaseServiceImpl;
import com.seal.sharding.user.entity.User;
import com.seal.sharding.user.mapper.UserMapper;
import com.seal.sharding.user.service.UserService;
import org.springframework.stereotype.Service;


/**
 * <p>
    * 用户表 服务实现类
    * </p>
 *
 * @author panliu
 * @since 2019-04-21
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {
}
