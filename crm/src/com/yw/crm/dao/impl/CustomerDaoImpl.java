package com.yw.crm.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;

import com.yw.crm.dao.CustomerDao;
import com.yw.crm.domain.Customer;

public class CustomerDaoImpl extends BaseDaoImpl<Customer> implements CustomerDao {
    @Override
    public Integer getTotalCount(DetachedCriteria dc){
        //实现聚合函数查询
        dc.setProjection(Projections.rowCount());
        List<Long> list = (List<Long>)getHibernateTemplate().findByCriteria(dc);
        dc.setProjection(null);
        if (list!= null && list.size()>0){
            Long count = list.get(0);
            return count.intValue();
        }else{
            return null;
        }
    }

    @Override
    public List<Customer> getPageList(DetachedCriteria dc, Integer start, Integer pageSize) {
        List<Customer> list = (List<Customer>) getHibernateTemplate().findByCriteria(dc, start, pageSize);
        return list;
    }
}
