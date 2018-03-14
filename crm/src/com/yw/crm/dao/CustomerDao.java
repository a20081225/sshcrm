package com.yw.crm.dao;

import java.util.List;

import com.yw.crm.domain.Customer;
import org.hibernate.criterion.DetachedCriteria;

public interface CustomerDao extends BaseDao<Customer> {

    Integer getTotalCount(DetachedCriteria dc);

    List<Customer> getPageList(DetachedCriteria dc, Integer start, Integer pageSize);
}
