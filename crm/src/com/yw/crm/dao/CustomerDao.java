package com.yw.crm.dao;

import java.util.List;

import com.yw.crm.domain.Customer;
import org.hibernate.criterion.DetachedCriteria;

public interface CustomerDao extends BaseDao<Customer> {

    //按照行业统计客户
    List<Object[]> getIndustryCount();
}
