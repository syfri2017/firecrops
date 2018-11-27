package com.syfri.digitalplan.dao.basicinfo.equipmentsource;

import com.syfri.baseapi.dao.BaseDAO;
import com.syfri.digitalplan.model.basicinfo.equipmentsource.EquipengineVO;

public interface EquipengineDAO extends BaseDAO<EquipengineVO>{
    int doUpdateByZbid(EquipengineVO vo);
}