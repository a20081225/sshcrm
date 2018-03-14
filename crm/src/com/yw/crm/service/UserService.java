package com.yw.crm.service;

import com.yw.crm.domain.User;

public interface UserService {
	//登陆方法
	User getUserByCodePassword(User u);
	//注册用户
	void saveUser(User u);
}
