package com.syfri.digitalplan.service.ewbh;

import com.syfri.baseapi.service.BaseService;
import com.syfri.digitalplan.model.ewbh.EwbhVO;

import java.util.List;

public interface EwbhService  extends BaseService<EwbhVO>{

    public int doDeleteEwbhVOList(List<EwbhVO> ewbhVOList);
}