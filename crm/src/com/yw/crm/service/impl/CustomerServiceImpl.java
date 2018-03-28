package com.yw.crm.service.impl;

import com.yw.crm.dao.CustomerDao;
import com.yw.crm.domain.Customer;
import com.yw.crm.service.CustomerService;
import com.yw.crm.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
	private CustomerDao cd;
	@Override
	public PageBean getPageBean(DetachedCriteria dc, Integer currentPage, Integer pageSize) {
		//1 调用Dao查询总记录数
		Integer totalCount = cd.getTotalCount(dc);
		//2 创建PageBean对象
		PageBean pb = new PageBean(currentPage, totalCount, pageSize);
		//3 调用Dao查询分页列表数据
		
		List<Customer> list = cd.getPageList(dc,pb.getStart(),pb.getPageSize());
		//4 列表数据放入pageBean中.并返回
		pb.setList(list);
		return pb;
	}

    @Override
    public void save(Customer customer) {
		//
        cd.save(customer);
    }

    @Override
    public Customer getById(Long cust_id) {
        return cd.getById(cust_id);
    }

    @Override
    public void update(Customer customer) {
        cd.update(customer);
    }

    @Override
    public List<Object[]> getIndustryCount() {
        return cd.getIndustryCount();
    }

    public void setCd(CustomerDao cd) {
		this.cd = cd;
	}

}
