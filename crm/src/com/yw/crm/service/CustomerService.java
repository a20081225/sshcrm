package com.yw.crm.service;

import com.yw.crm.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;

public interface CustomerService {
	//分页业务方法
	PageBean getPageBean(DetachedCriteria dc, Integer currentPage, Integer pageSize);

}