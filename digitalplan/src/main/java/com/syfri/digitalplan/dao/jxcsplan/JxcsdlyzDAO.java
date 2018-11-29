package com.syfri.digitalplan.dao.jxcsplan;

import com.syfri.baseapi.dao.BaseDAO;
import com.syfri.digitalplan.model.jxcsplan.JxcsdlyzVO;

public interface JxcsdlyzDAO extends BaseDAO<JxcsdlyzVO>{
    //根据单位id逻辑删除建筑中间表中信息
    int doDeleteByDwid(String dwid);

    //根据单位id查统一社会信用代码 by yushch
    String doFindUnscidByDwid(String dwid);
    //根据单位id修改统一社会信用代码
    int doUpdateUnscidByVO(JxcsdlyzVO vo);
    //查询登录验证表中是否存在当前统一社会信用代码
    int doFindCountByUnscid(String unscid);
    //根据单位id逻辑删除登录验证中间表中信息
    int doDeleteDlyzByDwid(String dwid);
    //根据单位id逻辑删除基本信息-建筑信息中间表中数据
    int doDeleteJbxxJzxxByDwid(String dwid);
}