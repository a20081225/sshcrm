package com.yw.crm.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.yw.crm.domain.SaleVisit;
import com.yw.crm.service.SaleVisitService;
import com.yw.crm.utils.PageBean;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StandardBasicTypes;

import java.util.HashMap;
import java.util.Map;

public class SaleVisitAction extends ActionSupport implements ModelDriven<SaleVisit> {
	private SaleVisit saleVisit = new SaleVisit();
	private SaleVisitService svs;
	private Integer page;
	private Integer rows;
	private String visit_starttime;
	private String visit_endtime;
	public String list() throws Exception {
		//封装离线查询对象
		DetachedCriteria dc = DetachedCriteria.forClass(SaleVisit.class);
		//1 调用Service查询分页数据(PageBean)
		PageBean pb = svs.getPageBean(dc,page,rows);
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
		DetachedCriteria dc = DetachedCriteria.forClass(SaleVisit.class);
		//判断并封装参数
		if(saleVisit.getCustomer()!=null&&saleVisit.getCustomer().getCust_id()!=null){
			dc.add(Restrictions.eq("customer.cust_id", saleVisit.getCustomer().getCust_id()));
		}
		if(saleVisit.getUser()!=null&&saleVisit.getUser().getUser_id()!=null){
			dc.add(Restrictions.eq("user.user_id", saleVisit.getUser().getUser_id()));
		}
		if (!visit_starttime.equals("")&&visit_starttime!=null){
			dc.add(Restrictions.sqlRestriction("visit_time>=?", visit_starttime, StandardBasicTypes.STRING));

		}
		if (!visit_endtime.equals("")&&visit_endtime!=null) {
			dc.add(Restrictions.sqlRestriction("visit_nexttime<=?", visit_endtime, StandardBasicTypes.STRING));
		}
		//1 调用Service查询分页数据(PageBean)
		PageBean pb = svs.getPageBean(dc,page,rows);
		//2 将PageBean放入request域,转发到列表页面显示
		ActionContext.getContext().put("pageBean", pb);
		Map map  = new HashMap();
		map.put("total", pb.getTotalCount());
		map.put("rows", pb.getList());
		//将map转换为json
		String json = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect);
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(json);
		return null;
	}


	//保存
	public String add() throws Exception {
		//取出登录用户信息放入到SaleVisit中
//		User user = (User) ActionContext.getContext().getSession().get("user");
//		saleVisit.setUser(user);
		//保存
		svs.save(saleVisit);
		return null;
	}

	//跳转修改
	public String toEdit() throws Exception {
		//根据id获取
		SaleVisit sv = svs.getById(saleVisit.getVisit_id());
		//放置到域中
//		ActionContext.getContext().put("saleVisit",sv);
//		String format = "yyyy-MM-dd";
//		SimpleDateFormat sdf = new SimpleDateFormat(format);
//		String visit_time = sdf.format(sv.getVisit_time());
//		JsonConfig config = new JsonConfig();
//		//设置生成json时不包含哪些字段
//		config.setExcludes(new String[]{"visit_time","visit_nexttime"});
//		JSONArray jsonArray = JSONArray.fromObject(sv, config);
		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("visit_time",sv.getVisit_time_s());
//		jsonObject.put("visit_nexttime",sv.getVisit_nexttime_s());
//		jsonArray.add(jsonObject);
		String json = JSON.toJSONString(sv, SerializerFeature.DisableCircularReferenceDetect);
		jsonObject = JSON.parseObject(json);
		jsonObject.replace("visit_time",sv.getVisit_time_s());
		jsonObject.replace("visit_nexttime",sv.getVisit_nexttime_s());
		json = jsonObject.toJSONString();
		ServletActionContext.getResponse().setContentType("application/json;charset=utf-8");

		ServletActionContext.getResponse().getWriter().write(json);
		return null;
	}

	//修改
	public String update() throws Exception {
		svs.update(saleVisit);
		return null;
	}

	public String delete() throws Exception {
		//1 调用Service删除注册用户
		try {
			svs.deleteById(saleVisit.getVisit_id());
		} catch (Exception e) {
			e.printStackTrace();
			ActionContext.getContext().put("error",e.getMessage());
			return "error";
		}
		return null;
	}

	@Override
	public SaleVisit getModel() {
		return saleVisit;
	}

	public void setSvs(SaleVisitService svs) {
		this.svs = svs;
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

	public SaleVisit getSaleVisit() {
		return saleVisit;
	}

	public void setSaleVisit(SaleVisit saleVisit) {
		this.saleVisit = saleVisit;
	}

	public String getVisit_starttime() {
		return visit_starttime;
	}

	public void setVisit_starttime(String visit_starttime) {
		this.visit_starttime = visit_starttime;
	}

	public String getVisit_endtime() {
		return visit_endtime;
	}

	public void setVisit_endtime(String visit_endtime) {
		this.visit_endtime = visit_endtime;
	}
}
