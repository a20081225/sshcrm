package com.yw.crm.service;

import com.yw.crm.domain.SaleVisit;
import com.yw.crm.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;

public interface SaleVisitService {
	//分页业务方法
	PageBean getPageBean(DetachedCriteria dc, Integer currentPage, Integer pageSize);
	//保存
    void save(SaleVisit saleVisit);
	//id获取信息
	SaleVisit getById(String visit_id);
	//修改
	void update(SaleVisit saleVisit);
}
