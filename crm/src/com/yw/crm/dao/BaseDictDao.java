package com.yw.crm.dao;

import com.yw.crm.domain.BaseDict;

import java.util.List;

public interface BaseDictDao extends BaseDao<BaseDict>{

    List<BaseDict> getListByTypeCode(String dict_type_code);
}
