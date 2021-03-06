package com.yw.crm.dao;

import com.yw.crm.domain.User;

public interface UserDao extends BaseDao<User> {
	
	//根据登陆名称查询user对象
	User getByUserCode(String usercode);

	User getByCodeExcId(String usercode,Long userid);
}
