package com.yw.crm.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.yw.crm.domain.SaleVisit;
import com.yw.crm.domain.User;
import com.yw.crm.service.SaleVisitService;
import com.yw.crm.utils.PageBean;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class SaleVisitAction extends ActionSupport implements ModelDriven<SaleVisit> {
	private SaleVisit saleVisit = new SaleVisit();
	private SaleVisitService svs;
	private Integer currentPage;
	private Integer pageSize;
	public String list() throws Exception {
		//封装离线查询对象
		DetachedCriteria dc = DetachedCriteria.forClass(SaleVisit.class);
		//判断并封装参数
		if(saleVisit.getCustomer()!=null&&saleVisit.getCustomer().getCust_id()!=null){
			dc.add(Restrictions.eq("customer.cust_id", saleVisit.getCustomer().getCust_id()));
		}
		//1 调用Service查询分页数据(PageBean)
		PageBean pb = svs.getPageBean(dc,currentPage,pageSize);
		//2 将PageBean放入request域,转发到列表页面显示
		ActionContext.getContext().put("pageBean", pb);
		return "list";
	}


	//保存
	public String add() throws Exception {
		//取出登录用户信息放入到SaleVisit中
		User user = (User) ActionContext.getContext().getSession().get("user");
		saleVisit.setUser(user);
		//保存
		svs.save(saleVisit);
		return "toList";
	}

	//跳转修改
	public String toEdit() throws Exception {
		//根据id获取
		SaleVisit sv = svs.getById(saleVisit.getVisit_id());
		//放置到域中
		ActionContext.getContext().put("saleVisit",sv);
		return "toEdit";
	}

	//修改
	public String update() throws Exception {
		svs.update(saleVisit);
		return "toList";
	}

	@Override
	public SaleVisit getModel() {
		return saleVisit;
	}

	public void setSvs(SaleVisitService svs) {
		this.svs = svs;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}


}
