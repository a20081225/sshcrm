package com.yw.crm.dao.impl;

import com.yw.crm.dao.BaseDictDao;
import com.yw.crm.domain.BaseDict;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository("baseDictDao")
public class BaseDictDaoImpl extends BaseDaoImpl<BaseDict> implements BaseDictDao  {

        @Override
        public List<BaseDict> getListByTypeCode(String dict_type_code) {
            //封装离线查询对象
            DetachedCriteria dc = DetachedCriteria.forClass(BaseDict.class);
            //判断并封装参数
            dc.add(Restrictions.like("dict_type_code", "%"+dict_type_code+"%"));
            //查询
            List<BaseDict> list = (List<BaseDict>) getHibernateTemplate().findByCriteria(dc);
            return list;
        }

}
