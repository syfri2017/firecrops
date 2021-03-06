package com.syfri.digitalplan.dao.digitalplan;

import com.syfri.baseapi.dao.BaseDAO;
import com.syfri.digitalplan.model.digitalplan.DigitalplanlistVO;

import java.util.List;

public interface DigitalplanlistDAO extends BaseDAO<DigitalplanlistVO>{

    public String doFindShztmcByShzt(String shzt);

    public List<DigitalplanlistVO> doFindListByZddwId(String zddwId);

    public List<DigitalplanlistVO> doSearchApproveListByVO(DigitalplanlistVO vo);

    public int doFindCountByJgAndZhlx(String yabm);
}