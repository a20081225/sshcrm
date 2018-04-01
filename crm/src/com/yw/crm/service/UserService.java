package com.yw.crm.service;

import com.yw.crm.domain.User;
import com.yw.crm.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

public interface UserService {
	//登陆方法
	User getUserByCodePassword(User u);
	//注册用户
	void saveUser(User u);

	PageBean getPageBean(DetachedCriteria dc, Integer currentPage, Integer pageSize);

    User getById(Long user_id);

    void updateUser(User user);

	void deleteById(Long user_id);

    List<User> getList(DetachedCriteria dc);
}
