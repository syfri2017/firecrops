package com.syfri.digitalplan.controller.firefacilities;

import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import com.syfri.digitalplan.model.firefacilities.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.syfri.digitalplan.service.firefacilities.FirefacilitiesService;
import com.syfri.baseapi.controller.BaseController;

import java.util.List;

@Api(value = "安全设施管理" ,tags = "安全设施管理API" ,description = "firefacilities")
@RestController
@RequestMapping("firefacilities")
public class FirefacilitiesController extends BaseController<FirefacilitiesVO> {

    @Autowired
    private FirefacilitiesService firefacilitiesService;

    @Override
    public FirefacilitiesService getBaseService() {
        return this.firefacilitiesService;
    }

    @ApiOperation(value = "查询安全设施列表", notes = "列表信息")
    @ApiImplicitParam(name = "vo", value = "安全设施VO")
    @PostMapping("doFindlist")
    public @ResponseBody
    ResultVO list(@RequestBody FirefacilitiesVO vo) {
        ResultVO resultVO = ResultVO.build();
        try {
            resultVO.setResult(firefacilitiesService.doFindlist(vo));
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
        }
        return resultVO;
    }


    @ApiOperation(value = "查询安全设施详情", notes = "详情信息")
    @ApiImplicitParam(name = "vo", value = "安全设施VO")
    @PostMapping("doFindXfssDetail")
    public @ResponseBody
    ResultVO doFindXfssDetail(@RequestBody FirefacilitiesVO vo) {
        ResultVO resultVO = ResultVO.build();
        try {
            resultVO.setResult(firefacilitiesService.doFindXfssDetail(vo));
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
        }
        return resultVO;
    }

    @ApiOperation(value = "删除安全设施", notes = "删除")
    @ApiImplicitParam(name = "vo", value = "安全设施VO")
    @RequiresPermissions("buildingzoning/firefacilities:delete")
    @PostMapping("/doDeleteFacilities")
    public @ResponseBody
    ResultVO doDeleteFacilities(@RequestBody List<FirefacilitiesVO> facilitiesList) {
        ResultVO resultVO = ResultVO.build();
        try {
            resultVO.setResult(firefacilitiesService.doDeleteFirefacilities(facilitiesList));
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            resultVO.setCode(EConstants.CODE.FAILURE);
        }
        return resultVO;
    }

    @ApiOperation(value = "消防设施新增", notes = "新增")
    @ApiImplicitParam(name = "vo", value = "消防设施VO")
    @RequiresPermissions("buildingzoning/firefacilities:add")
    @PostMapping("/insertByVO")
    public @ResponseBody
    ResultVO insertByVO(@RequestBody FirefacilitiesVO firefacilitiesVO) {
        ResultVO resultVO = ResultVO.build();
        try {
//            firefacilitiesService.doInsertByVO(firefacilitiesVO);
            resultVO.setResult(firefacilitiesService.doInsertFirefacilities(firefacilitiesVO));
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            resultVO.setCode(EConstants.CODE.FAILURE);
        }
        return resultVO;
    }

    @ApiOperation(value = "修改消防设施", notes = "编辑")
    @ApiImplicitParam(name = "vo", value = "消防设施VO")
    @RequiresPermissions("buildingzoning/firefacilities:edit")
    @PostMapping("/doUpdateFirefacilities")
    public @ResponseBody
    ResultVO doUpdateFirefacilities(@RequestBody FirefacilitiesVO firefacilitiesVO) {
        ResultVO resultVO = ResultVO.build();
        try {
            resultVO.setResult(firefacilitiesService.doUpdateFirefacilities(firefacilitiesVO));
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            resultVO.setCode(EConstants.CODE.FAILURE);
        }
        return resultVO;
    }

}
