package com.yw.crm.dao.impl;

import com.yw.crm.dao.CustomerDao;
import com.yw.crm.domain.Customer;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;

import java.util.List;

public class CustomerDaoImpl extends BaseDaoImpl<Customer> implements CustomerDao {

    @SuppressWarnings("all")
    @Override
    public List<Object[]> getIndustryCount() {
        //sql查询
        List<Object[]> list = getHibernateTemplate().execute(new HibernateCallback<List>() {
            String sql = "SELECT b.dict_item_name, COUNT(*) FROM cst_customer c,base_dict b WHERE c.cust_industry = b.dict_id GROUP BY c.cust_industry";
            @Override
            public List doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sql);
                return query.list();
            }
        });
        return list;
    }
}
