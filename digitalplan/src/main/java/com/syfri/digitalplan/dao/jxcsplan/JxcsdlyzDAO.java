package com.syfri.digitalplan.dao.jxcsplan;

import com.syfri.baseapi.dao.BaseDAO;
import com.syfri.digitalplan.model.jxcsplan.JxcsdlyzVO;

public interface JxcsdlyzDAO extends BaseDAO<JxcsdlyzVO>{
    //根据单位id逻辑删除中间表中信息
    int doDeleteByDwid(String dwid);

    //根据单位id查统一社会信用代码 by yushch
    String doFindUnscidByDwid(String dwid);
    //根据单位id修改统一社会信用代码
    int doUpdateUnscidByVO(JxcsdlyzVO vo);
    //查询登录验证表中是否存在当前统一社会信用代码
    int doFindCountByUnscid(String unscid);
}