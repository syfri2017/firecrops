package com.syfri.digitalplan.dao.jxcsfjxz;

import com.syfri.baseapi.dao.BaseDAO;
import com.syfri.digitalplan.model.jxcsfjxz.JxcsfjxzVO;

import java.util.List;

public interface JxcsfjxzDAO extends BaseDAO<JxcsfjxzVO>{
    List<JxcsfjxzVO> doFindPicsByDwid(JxcsfjxzVO vo);
    List<JxcsfjxzVO> doFindByDwid(JxcsfjxzVO vo);
    List<JxcsfjxzVO> doFindVideosByDwid(JxcsfjxzVO vo);
}