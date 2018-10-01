package com.syfri.digitalplan.dao.jxcsplan;

import com.syfri.baseapi.dao.BaseDAO;
import com.syfri.digitalplan.model.jxcsplan.JxcsjbxxVO;

import java.util.List;

public interface JxcsjbxxDAO extends BaseDAO<JxcsjbxxVO>{

    public List<JxcsjbxxVO> doSearchApproveListByVO(JxcsjbxxVO vo);
}