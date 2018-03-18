package com.yw.crm.service;

import com.yw.crm.domain.BaseDict;

import java.util.List;

public interface BaseDictService {

    List<BaseDict> getListByTypeCode(String dict_type_code);
}
