package com.syfri.digitalplan.service.jxcsplan;

import com.syfri.baseapi.service.BaseService;
import com.syfri.digitalplan.model.jxcsplan.JxcsdlyzVO;

public interface JxcsdlyzService extends BaseService<JxcsdlyzVO>{
    //查询表中是否存在当前统一社会信用代码 by yushch 20181122
    int doFindCountByUnscid(String unscid);
    //根据单位id删除登录验证表信息 add by yushch 20181127
    int doDeleteByDwid(String dwid);
}