package com.syfri.digitalplan.service.jxcsfjxz;

import com.syfri.baseapi.service.BaseService;
import com.syfri.digitalplan.model.jxcsfjxz.JxcsfjxzVO;

import java.util.List;

public interface JxcsfjxzService  extends BaseService<JxcsfjxzVO>{
    List<JxcsfjxzVO> doFindByDwid(JxcsfjxzVO vo);
    int  doUpdateByVOList(List<JxcsfjxzVO> list);
}