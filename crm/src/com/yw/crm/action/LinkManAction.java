package com.yw.crm.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.yw.crm.domain.LinkMan;
import com.yw.crm.service.LinkManService;
import com.yw.crm.utils.PageBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.HashMap;
import java.util.Map;

public class LinkManAction extends ActionSupport implements ModelDriven<LinkMan> {
	private LinkMan linkMan = new LinkMan();
	private LinkManService lms;
	private Integer page;
	private Integer rows;
	public String list() throws Exception {
		//封装离线查询对象
		DetachedCriteria dc = DetachedCriteria.forClass(LinkMan.class);
		//1 调用Service查询分页数据(PageBean)
		PageBean pb = lms.getPageBean(dc,page,rows);
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
		DetachedCriteria dc = DetachedCriteria.forClass(LinkMan.class);
		//判断并封装参数
		if(StringUtils.isNotBlank(linkMan.getLkm_name())){
			dc.add(Restrictions.like("lkm_name", "%"+linkMan.getLkm_name()+"%"));

		}
		if(linkMan.getCustomer()!=null&&linkMan.getCustomer().getCust_id()!=null){
			dc.add(Restrictions.eq("customer.cust_id", linkMan.getCustomer().getCust_id()));
		}
		//1 调用Service查询分页数据(PageBean)
		PageBean pb = lms.getPageBean(dc,page,rows);
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


	//保存
	public String add() throws Exception {
		lms.save(linkMan);
		return null;
	}

	//跳转修改
	public String toEdit() throws Exception {
		//根据id获取
		LinkMan lm = lms.getById(linkMan.getLkm_id());
		//放置到域中
//		ActionContext.getContext().put("linkman",lm);
		String json = JSON.toJSONString(lm,SerializerFeature.DisableCircularReferenceDetect);
		ServletActionContext.getResponse().setContentType("application/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(json);
		return null;
	}

	//修改
	public String update() throws Exception {
		lms.update(linkMan);
		return null;
	}

	public String delete() throws Exception {
		//1 调用Service删除注册用户
		try {
			lms.deleteById(linkMan.getLkm_id());
		} catch (Exception e) {
			e.printStackTrace();
			ActionContext.getContext().put("error",e.getMessage());
			return "error";
		}
		return null;
	}
	@Override
	public LinkMan getModel() {
		return linkMan;
	}

	public void setLms(LinkManService lms) {
		this.lms = lms;
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
