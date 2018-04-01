package com.yw.crm.service.impl;

import com.yw.crm.dao.UserDao;
import com.yw.crm.domain.User;
import com.yw.crm.service.UserService;
import com.yw.crm.utils.MD5Utils;
import com.yw.crm.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
public class UserServiceImpl implements UserService {
	
	private UserDao ud;
	
	@Override
	public User getUserByCodePassword(User u) {
			//1 根据登陆名称查询登陆用户
			User existU = ud.getByUserCode(u.getUser_code());
			//2 判断用户是否存在.不存在=>抛出异常,提示用户名不存在
			if(existU==null){
				throw new RuntimeException("用户名不存在!");
			}
			//3 判断用户密码是否正确=>不正确=>抛出异常,提示密码错误
			if(!existU.getUser_password().equals(MD5Utils.md5(u.getUser_password()))){
				throw new RuntimeException("密码错误!");
			}
			//4 返回查询到的用户对象
		
		return existU;
	}

	@Override
	public void saveUser(User u) {
		//调用dao根据用户名获取对象
		User exitU = ud.getByUserCode(u.getUser_code());
		//判断用户名存在
		if(exitU!=null){
			throw new RuntimeException("用户名存在!");
		}
		//md5加密
		u.setUser_password(MD5Utils.md5(u.getUser_password()));
		//保存
		ud.save(u);
	}

    public void setUd(UserDao ud) {
		this.ud = ud;
	}

	public PageBean getPageBean(DetachedCriteria dc, Integer currentPage, Integer pageSize) {
		//1 调用Dao查询总记录数
		Integer totalCount = ud.getTotalCount(dc);
		//2 创建PageBean对象
		PageBean pb = new PageBean(currentPage, totalCount, pageSize);
		//3 调用Dao查询分页列表数据

		List<User> list = ud.getPageList(dc,pb.getStart(),pb.getPageSize());
		//4 列表数据放入pageBean中.并返回
		pb.setList(list);
		return pb;
	}

    @Override
    public User getById(Long user_id) {
        return ud.getById(user_id);
    }

    @Override
	public void updateUser(User user) {
//		//调用dao根据用户名获取对象
//		User exitU = ud.getByCodeExcId(user.getUser_code(),user.getUser_id());
//		//判断用户名存在
//		if(exitU!=null){
//			throw new RuntimeException("用户名存在!");
//		}
		//md5加密
//		String password= ud.getById(user.getUser_id()).getUser_password();
//		if (!user.getUser_password().equals(password)) {
			user.setUser_password(MD5Utils.md5(user.getUser_password()));
//		}
//		Session session = null;
//		session.evict(exitU);
		//保存
		ud.update(user);
    }

	@Override
	public void deleteById(Long user_id) {
		ud.delete(user_id);
	}

    @Override
    public List<User> getList(DetachedCriteria dc) {
        return ud.getList(dc);
    }


}
