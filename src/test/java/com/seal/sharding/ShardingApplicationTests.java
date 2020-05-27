package com.seal.sharding;

import com.seal.sharding.user.entity.User;
import com.seal.sharding.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShardingApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
		User user = new User();
		user.setUserName("liupan");
		userService.save(user);
	}

}
