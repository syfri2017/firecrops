package com.syfri.digitalplan.controller.buildingzoning;

import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import com.syfri.digitalplan.model.buildingzoning.ChuguanVO;
import com.syfri.digitalplan.utils.Base64ImageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.syfri.digitalplan.model.buildingzoning.BuildingVO;
import com.syfri.digitalplan.service.buildingzoning.BuildingService;
import com.syfri.baseapi.controller.BaseController;

import java.util.List;

@Api(value = "单位建筑信息",tags = "单位建筑信息API",description = "BuildingController")
@RestController
@RequestMapping("building")
public class BuildingController extends BaseController<BuildingVO> {

    @Autowired
    private BuildingService buildingService;

    @Override
    public BuildingService getBaseService() {
        return this.buildingService;
    }

    /**
     * 通过id获取建筑分区信息及分类信息
     * by yushch 20180501
     */
    @ApiOperation(value = "通过id获取建筑分区信息及分类信息", notes = "详情")
    @ApiImplicitParam(name = "vo", value = "建筑分区VO")
    @PostMapping("/findFqDetailByVo")
    public @ResponseBody
    ResultVO findById(@RequestBody BuildingVO buildingVO) {
        ResultVO resultVO = ResultVO.build();
        try {
            resultVO.setResult(buildingService.doFindFqDetailByVo(buildingVO));
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            resultVO.setCode(EConstants.CODE.FAILURE);
        }
        return resultVO;
    }

    /**
     * @Description: 删除单体建筑信息
     * @Param: [fireegineList, fireegineVo]
     * @Return: com.syfri.baseapi.
     * @Author: zhaijianchen
     * @Modified By:
     * @Date: 2018/7/31 15:52
     */
    @ApiOperation(value = "根据VO删除建筑信息", notes = "删除")
    @ApiImplicitParam(name = "vo", value = "建筑信息VO")
    @RequiresPermissions("buildingzoning/buildingzoning:delete")
    @PostMapping("/doDeleteBuildingzoning")
    public @ResponseBody
    ResultVO doDeleteBuildingzoning(@RequestBody List<BuildingVO> buildingList) {
        ResultVO resultVO = ResultVO.build();
        try {
//            int count = 0;
//            if (buildingList.size() > 0) {
//                for (BuildingVO buildingVO : buildingList) {
//                    buildingVO.setDeleteFlag("Y");
//                    count = count + buildingService.doUpdateByVO(buildingVO);
//                    buildingService.doDeleteBuildingzoning(buildingList);
//                }
//            }
            resultVO.setResult(buildingService.doDeleteBuildingzoning(buildingList));
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            resultVO.setCode(EConstants.CODE.FAILURE);
        }
        return resultVO;
    }

    /**
     * @Description: 单体建筑新增
     * @Param: [VO]
     * @Return: com.syfri.baseapi.model.ResultVO
     * @Author: zhaijianchen
     * @Modified By:
     * @Date: 2018/7/31 15:44
     */
    @ApiOperation(value = "根据VO新增建筑信息", notes = "新增")
    @ApiImplicitParam(name = "vo", value = "建筑信息VO")
    @RequiresPermissions("buildingzoning/buildingzoning:add")
    @PostMapping("/insertByVO")
    public @ResponseBody
    ResultVO insertByVO(@RequestBody BuildingVO buildingVO) {
        ResultVO resultVO = ResultVO.build();
        try {
            int count = buildingService.doInsertByVO(buildingVO);//主表新增
            buildingService.doInsertDetailByVO(buildingVO);//从表新增
            resultVO.setResult(count);
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            resultVO.setCode(EConstants.CODE.FAILURE);
        }
        return resultVO;
    }

    /**
     * @Description: 单体建筑修改
     * @Param: [BuildingVO]
     * @Return: com.syfri.baseapi.model.ResultVO
     * @Author: zhaijianchen
     * @Modified By:
     * @Date: 2018/7/31 16:13
     */
    @ApiOperation(value = "根据VO修改建筑信息", notes = "修改")
    @ApiImplicitParam(name = "vo", value = "建筑信息VO")
    @RequiresPermissions("buildingzoning/buildingzoning:edit")
    @PostMapping("/doUpdateBuildingzoning")
    public @ResponseBody
    ResultVO doUpdateBuildingzoning(@RequestBody BuildingVO buildingVO) {
        ResultVO resultVO = ResultVO.build();
        try {
            resultVO.setResult(buildingService.doUpdateBuildingzoning(buildingVO));
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            resultVO.setCode(EConstants.CODE.FAILURE);
        }
        return resultVO;
    }


}
