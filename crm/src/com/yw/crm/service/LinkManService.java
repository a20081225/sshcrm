package com.yw.crm.service;

import com.yw.crm.domain.LinkMan;
import com.yw.crm.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;

public interface LinkManService {
	//分页业务方法
	PageBean getPageBean(DetachedCriteria dc, Integer currentPage, Integer pageSize);
	//保存
    void save(LinkMan linkMan);
	//id获取信息
    LinkMan getById(Long lkm_id);
	//修改
	void update(LinkMan linkMan);

    void deleteById(Long lkm_id);
}
