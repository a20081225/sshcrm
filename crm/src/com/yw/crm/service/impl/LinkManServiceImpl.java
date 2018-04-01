package com.yw.crm.service.impl;

import com.yw.crm.dao.LinkManDao;
import com.yw.crm.domain.LinkMan;
import com.yw.crm.service.LinkManService;
import com.yw.crm.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

public class LinkManServiceImpl implements LinkManService {
	private LinkManDao lmd;
	@Override
	public PageBean getPageBean(DetachedCriteria dc, Integer currentPage, Integer pageSize) {
		//1 调用Dao查询总记录数
		Integer totalCount = lmd.getTotalCount(dc);
		//2 创建PageBean对象
		PageBean pb = new PageBean(currentPage, totalCount, pageSize);
		//3 调用Dao查询分页列表数据
		List<LinkMan> list = lmd.getPageList(dc,pb.getStart(),pb.getPageSize());
		//4 列表数据放入pageBean中.并返回
		pb.setList(list);
		return pb;
	}

    @Override
    public void save(LinkMan linkMan) {
		//
        lmd.save(linkMan);
    }

    @Override
    public LinkMan getById(Long lkm_id) {
        return lmd.getById(lkm_id);
    }

    @Override
    public void update(LinkMan linkMan) {
        lmd.update(linkMan);
    }

    @Override
    public void deleteById(Long lkm_id) {
        lmd.delete(lkm_id);
    }

    public void setLmd(LinkManDao lmd) {
		this.lmd = lmd;
	}

}
