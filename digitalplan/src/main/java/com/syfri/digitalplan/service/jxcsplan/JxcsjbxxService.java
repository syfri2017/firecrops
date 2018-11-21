package com.syfri.digitalplan.service.jxcsplan;

import com.syfri.baseapi.service.BaseService;
import com.syfri.digitalplan.model.jxcsplan.JxcsjbxxVO;

import java.util.List;

public interface JxcsjbxxService extends BaseService<JxcsjbxxVO> {

    /*--批量删除九小场所 by liurui 2018/9/7--*/
    int doDeleteByVOList(List<JxcsjbxxVO> jxcsjbxxVOList);
    //新增九小场所 by yushch 20180920
    JxcsjbxxVO doInsertJxcsByVO(JxcsjbxxVO vo);
    //编辑九小场所 by yushch 20180929
    JxcsjbxxVO doUpdateJxcsByVO(JxcsjbxxVO vo);
    //审核加载List by huangrui 20180930
    List<JxcsjbxxVO> doSearchApproveListByVO(JxcsjbxxVO vo);
    //审核九小场所 by huangrui 20180930
    JxcsjbxxVO doApproveUpdate(JxcsjbxxVO vo);

    //查询九小场所基本信息 同时查询中间表统一社会信用代码 by yushch 20181121
    JxcsjbxxVO doFindJbxxById(String id);
}