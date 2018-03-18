package com.yw.crm.service.impl;

import com.yw.crm.dao.BaseDictDao;
import com.yw.crm.domain.BaseDict;
import com.yw.crm.service.BaseDictService;

import java.util.List;

public class BaseDictServiceImpl implements BaseDictService {
    private BaseDictDao baseDictDao;

    public void setBaseDictDao(BaseDictDao baseDictDao) {
        this.baseDictDao = baseDictDao;
    }

    @Override
    public List<BaseDict> getListByTypeCode(String dict_type_code) {
        return baseDictDao.getListByTypeCode(dict_type_code);
    }
}
