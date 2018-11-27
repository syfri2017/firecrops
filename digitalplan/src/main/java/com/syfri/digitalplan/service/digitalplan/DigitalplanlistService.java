package com.syfri.digitalplan.service.digitalplan;

import com.syfri.baseapi.service.BaseService;
import com.syfri.digitalplan.model.buildingzoning.BuildingVO;
import com.syfri.digitalplan.model.digitalplan.DigitalplanlistVO;

import java.util.List;

public interface DigitalplanlistService extends BaseService<DigitalplanlistVO> {

    /*预案审批（更新部分字段）
     *by yushch 20180426
     */
    DigitalplanlistVO doApproveUpdate(DigitalplanlistVO digitalplanlistVO);

    /**
     * @Description: 新增：预案
     * @Param: [digitalplanlistVO]
     * @Return: com.syfri.digitalplan.model.digitalplan.DigitalplanlistVO
     * @Author: liurui
     * @Modified By:
     * @Date: 2018/5/1 12:15
     */
    DigitalplanlistVO doInsertDigitalplan(DigitalplanlistVO digitalplanlistVO);
    String createPlanCode(String jgid, String zhlx);

    /**
     * @Description: 修改：预案
     * @Param: [digitalplanlistVO]
     * @Return: com.syfri.digitalplan.model.digitalplan.DigitalplanlistVO
     * @Author: liurui
     * @Modified By:
     * @Date: 2018/5/2 15:56
     */
    DigitalplanlistVO doUpdateDigitalplan(DigitalplanlistVO digitalplanlistVO);

    /***
     * @Description: 删除：预案
     * @Param: [digitalplanList]
     * @Return: java.util.List<com.syfri.digitalplan.model.digitalplan.DigitalplanlistVO>
     * @Author: liurui
     * @Modified By:
     * @Date: 2018/5/26 13:54
     */
    int doDeleteDigitalplan(List<DigitalplanlistVO> digitalplanList,DigitalplanlistVO digitalplanVo);

    /***
     * @Description: 查询：通过重点单位id查询建筑分区list
     * @Param: [zddwid]
     * @Return: java.util.List<com.syfri.digitalplan.model.buildingzoning.BuildingVO>
     * @Author: liurui
     * @Modified By:
     * @Date: 2018/5/23 10:22
     */
    List<BuildingVO> doSearchJzListByZddwId(BuildingVO buildingVO);
    /***
     * @Description: 查询：通过重点单位id查询预案list
     * @Param: [zddwid]
     * @Return: java.util.List<com.syfri.digitalplan.model.buildingzoning.BuildingVO>
     * @Author: liurui
     * @Modified By:
     * @Date: 2018/5/23 10:22
     */
    List<DigitalplanlistVO> doFindListByZddwId(String zddwId);

    //审核初始化时加载List
    //by yushch 20180604
    List<DigitalplanlistVO> doSearchApproveListByVO(DigitalplanlistVO vo);
}