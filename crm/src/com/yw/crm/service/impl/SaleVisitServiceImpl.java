package com.yw.crm.service.impl;

import com.yw.crm.dao.SaleVisitDao;
import com.yw.crm.domain.SaleVisit;
import com.yw.crm.service.SaleVisitService;
import com.yw.crm.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

public class SaleVisitServiceImpl implements SaleVisitService {
	private SaleVisitDao svd;
	@Override
	public PageBean getPageBean(DetachedCriteria dc, Integer currentPage, Integer pageSize) {
		//1 调用Dao查询总记录数
		Integer totalCount = svd.getTotalCount(dc);
		//2 创建PageBean对象
		PageBean pb = new PageBean(currentPage, totalCount, pageSize);
		//3 调用Dao查询分页列表数据
		List<SaleVisit> list = svd.getPageList(dc,pb.getStart(),pb.getPageSize());
		//4 列表数据放入pageBean中.并返回
		pb.setList(list);
		return pb;
	}

    @Override
    public void save(SaleVisit saleVisit) {
		//
        svd.save(saleVisit);
    }

    @Override
    public SaleVisit getById(String visit_id) {
        return svd.getById(visit_id);
    }

    @Override
    public void update(SaleVisit saleVisit) {
        svd.update(saleVisit);
    }

    public void setSvd(SaleVisitDao svd) {
		this.svd = svd;
	}

}
