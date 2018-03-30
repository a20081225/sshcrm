package com.yw.crm.action;

import com.opensymphony.xwork2.ActionSupport;
import com.yw.crm.domain.BaseDict;
import com.yw.crm.service.BaseDictService;
import net.sf.json.JSONArray;
import org.apache.struts2.ServletActionContext;

import java.util.List;

public class BaseDictAction extends ActionSupport {

	private String dict_type_code;

	public String getDict_type_code() {
		return dict_type_code;
	}

	public void setDict_type_code(String dict_type_code) {
		this.dict_type_code = dict_type_code;
	}

	private BaseDictService baseDictService;

	public void setBaseDictService(BaseDictService baseDictService) {
		this.baseDictService = baseDictService;
	}

	@Override
	public String execute() throws Exception {
		//调用service获取list
		List<BaseDict> list = baseDictService.getListByTypeCode(dict_type_code);
		//将list转换成json
		String json = JSONArray.fromObject(list).toString();
		//返回前端
		ServletActionContext.getResponse().setContentType("application/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(json);
		return null;
	}

}
