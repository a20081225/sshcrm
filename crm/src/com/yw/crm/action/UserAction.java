package com.yw.crm.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.yw.crm.domain.User;
import com.yw.crm.service.UserService;
import com.yw.crm.utils.PageBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserAction extends ActionSupport implements ModelDriven<User> {
	private User user = new User();
	
	private UserService userService ;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getRows() {
		return rows;

	}

	private Integer page;
	private Integer rows;
	public String list() throws Exception {
//		HttpServletRequest request = ServletActionContext.getRequest();
//		page = Integer.parseInt(request.getParameter("page"));
//		rows = Integer.parseInt(request.getParameter("rows"));
		//封装离线查询对象
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		//1 调用Service查询分页数据(PageBean)
		PageBean pb = userService.getPageBean(dc,page,rows);
		//2 将列表数据转换为json发送给浏览器
		//total:总记录数
		//rows:每行显示的数据
		//{total:xx,rows:[{user_id:1,user_name:'tom'}]}
		Map map  = new HashMap();
		map.put("total", pb.getTotalCount());
		map.put("rows", pb.getList());
		//将map转换为json
		String json = JSON.toJSONString(map);
		ServletActionContext.getResponse().setContentType("application/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(json);
		return null;
	}
	public String login() throws Exception {
			//1 调用Service执行登陆逻辑
			User u = userService.getUserByCodePassword(user);
			//2 将返回的User对象放入session域
			ActionContext.getContext().getSession().put("user", u);
			//3 重定向到项目首页
		return "toHome";
	}

	public String regist() throws Exception {
		//1 调用Service保存注册用户
		try {
			char state = '1';
			user.setUser_state(state);
			userService.saveUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			ActionContext.getContext().put("error",e.getMessage());
			return "error";
		}
		return null;
	}

	public String update() throws Exception {
		//1 调用Service保存注册用户
		try {
			char state = '1';
			user.setUser_state(state);
			userService.updateUser(user);
		} catch (Exception e) {
			e.printStackTrace();
//			ActionContext.getContext().put("error",e.getMessage());
			String json = JSON.toJSONString(e.getMessage());
			ServletActionContext.getResponse().setContentType("application/json;charset=utf-8");
			ServletActionContext.getResponse().getWriter().write(json);
			return null;
		}
		return null;
	}

	public String delete() throws Exception {
		//1 调用Service删除注册用户
		try {
			userService.deleteById(user.getUser_id());
		} catch (Exception e) {
			e.printStackTrace();
			ActionContext.getContext().put("error",e.getMessage());
			return "error";
		}
		return null;
	}

	//跳转修改
	public String toEdit() throws Exception {
		//根据id获取
		User u = userService.getById(user.getUser_id());
		//放置到域中
//		ActionContext.getContext().put("user",u);
		u.setUser_password("");
		String json = JSON.toJSONString(u);
		ServletActionContext.getResponse().setContentType("application/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(json);
		return null;
	}

	public String toSelect() throws Exception {
		DetachedCriteria dc = DetachedCriteria.forClass(User.class);
		if(StringUtils.isNotBlank(user.getUser_name())){
			dc.add(Restrictions.like("user_name", "%"+user.getUser_name()+"%"));
		}
		List<User> list = userService.getList(dc);
		//将map转换为json
		String json = JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect);
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(json);
		return null;
	}


	@Override
	public User getModel() {
		return user;
	}

	
	
}
