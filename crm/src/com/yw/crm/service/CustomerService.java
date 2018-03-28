package com.yw.crm.service;

import com.yw.crm.domain.Customer;
import com.yw.crm.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

public interface CustomerService {
	//分页业务方法
	PageBean getPageBean(DetachedCriteria dc, Integer currentPage, Integer pageSize);
	//保存
    void save(Customer customer);
	//id获取信息
    Customer getById(Long cust_id);
	//修改
	void update(Customer customer);
	//按行业统计客户数量
	List<Object[]> getIndustryCount();
}
