package com.syfri.digitalplan.service.buildingzoning;

import com.syfri.baseapi.service.BaseService;
import com.syfri.digitalplan.model.buildingzoning.BuildingVO;
import com.syfri.digitalplan.model.buildingzoning.ChuguanVO;

import java.util.List;

public interface BuildingService  extends BaseService<BuildingVO>{
    //通过id获取建筑分区信息及分类信息
    BuildingVO doFindFqDetailByVo(BuildingVO buildingVO);
    int doDeleteBuildingzoning(List<BuildingVO> buildingList) ;
    BuildingVO doUpdateBuildingzoning(BuildingVO buildingVO);
    BuildingVO doInsertDetailByVO(BuildingVO buildingVO);//从表新增
    int  doDeleteBuildingzoningDetail(BuildingVO buildingVO);//从表删除



}