package com.yw.crm.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.yw.crm.domain.Customer;
import com.yw.crm.service.CustomerService;
import com.yw.crm.utils.PageBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerAction extends ActionSupport implements ModelDriven<Customer> {
	private Customer customer = new Customer();
	private CustomerService cs;

	private File photo;

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	private Integer page;
	private Integer rows;
	public String list() throws Exception {
		//封装离线查询对象
		DetachedCriteria dc = DetachedCriteria.forClass(Customer.class);
		//1 调用Service查询分页数据(PageBean)
		PageBean pb = cs.getPageBean(dc,page,rows);
		//2 将PageBean放入request域,转发到列表页面显示
		ActionContext.getContext().put("pageBean", pb);
		Map map  = new HashMap();
		map.put("total", pb.getTotalCount());
		map.put("rows", pb.getList());
		//将map转换为json
		String json = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
		ServletActionContext.getResponse().setContentType("application/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(json);
		return null;
	}

	public String search() throws Exception {
		//封装离线查询对象
		DetachedCriteria dc = DetachedCriteria.forClass(Customer.class);
		//判断并封装参数
		if(StringUtils.isNotBlank(customer.getCust_name())){
			dc.add(Restrictions.like("cust_name", "%"+customer.getCust_name()+"%"));
		}
		if(customer.getCust_industry()!=null&&!customer.getCust_industry().getDict_id().equals("")){
			dc.add(Restrictions.eq("cust_industry.dict_id", customer.getCust_industry().getDict_id()));
		}
		if(customer.getCust_source()!=null&&!customer.getCust_source().getDict_id().equals("")){
			dc.add(Restrictions.eq("cust_source.dict_id", customer.getCust_source().getDict_id()));
		}
		if(customer.getCust_level()!=null&&!customer.getCust_level().getDict_id().equals("")){
			dc.add(Restrictions.eq("cust_level.dict_id", customer.getCust_level().getDict_id()));
		}
		//1 调用Service查询分页数据(PageBean)
		PageBean pb = cs.getPageBean(dc,page,rows);
		//2 将PageBean放入request域,转发到列表页面显示
		ActionContext.getContext().put("pageBean", pb);
		Map map  = new HashMap();
		map.put("total", pb.getTotalCount());
		map.put("rows", pb.getList());
		//将map转换为json
		String json = JSON.toJSONString(map,SerializerFeature.DisableCircularReferenceDetect);
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(json);
		return null;
	}

	//保存
	public String add() throws Exception {
//		if(photo!=null){
//			//将上传文件保存到指定位置
//			photo.renameTo(new File("E:/upload/haha.jpg"));
//		}
		cs.save(customer);
		return null;
	}

	//跳转修改
	public String toEdit() throws Exception {
		//根据id获取
		Customer c = cs.getById(customer.getCust_id());
		//放置到域中
//		ActionContext.getContext().put("customer",c);

		String json = JSON.toJSONString(c,SerializerFeature.DisableCircularReferenceDetect);
		ServletActionContext.getResponse().setContentType("application/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(json);
		return null;
	}

	//修改
	public String update() throws Exception {
		cs.update(customer);
		return null;
	}

	//选择客户界面
	public String toSelect() throws Exception {
		DetachedCriteria dc = DetachedCriteria.forClass(Customer.class);
		if(StringUtils.isNotBlank(customer.getCust_name())){
			dc.add(Restrictions.like("cust_name", "%"+customer.getCust_name()+"%"));
		}
		List<Customer> list = cs.getList(dc);
//		ActionContext.getContext().put("pageBean", pb);
//		Map map  = new HashMap();
//		map.put("total", pb.getTotalCount());
//		map.put("rows", pb.getList());
		//将map转换为json
		String json = JSON.toJSONString(list, SerializerFeature.DisableCircularReferenceDetect);
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(json);
		return null;
	}

	//统计
	public String industryCount()throws Exception{
		List<Object[]> list = cs.getIndustryCount();
		ActionContext.getContext().put("list", list);
		return "industryCount";
	}

	public String delete() throws Exception {
		//1 调用Service删除注册用户
		try {
			cs.deleteById(customer.getCust_id());
		} catch (Exception e) {
			e.printStackTrace();
			ActionContext.getContext().put("error",e.getMessage());
			return "error";
		}
		return null;
	}

	@Override
	public Customer getModel() {
		return customer;
	}


	public void setCs(CustomerService cs) {
		this.cs = cs;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	
	
}
