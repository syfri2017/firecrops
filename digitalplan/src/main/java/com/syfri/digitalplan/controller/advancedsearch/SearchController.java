package com.syfri.digitalplan.controller.advancedsearch;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syfri.baseapi.model.ResultVO;
import com.syfri.digitalplan.model.digitalplan.DigitalplanlistVO;
import com.syfri.digitalplan.model.planobject.ImportantunitsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.syfri.digitalplan.model.buildingzoning.BuildingVO;
import com.syfri.digitalplan.service.buildingzoning.BuildingService;
import com.syfri.baseapi.controller.BaseController;

import com.syfri.digitalplan.service.advancedsearch.SearchService;

import java.util.List;

@Api(value = "预案高级搜索",tags = "预案高级搜索API",description = "SearchController")
@RestController
@RequestMapping("advancedsearch")
public class SearchController  extends BaseController<BuildingVO>{

    @Autowired
    private SearchService searchService;
    private BuildingService buildingService;

    @Override
    public BuildingService getBaseService() {
        return this.buildingService;
    }

    /*
    * 高级搜索查询预案信息
    * by yushch 20180521
    */
    @ApiOperation(value="高级搜索查询预案列表",notes="列表信息")
    @ApiImplicitParam(name="vo",value = "预案VO")
    @PostMapping("gjssYaxxList")
    public @ResponseBody ResultVO gjssYaxxList(@RequestBody DigitalplanlistVO vo ) {
        ResultVO resultVO = ResultVO.build();
        try {
            PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
            List<DigitalplanlistVO> list = searchService.doSearchGjssYaxxListByVO(vo);
            PageInfo<DigitalplanlistVO> pageInfo = new PageInfo<>(list);
            resultVO.setResult(pageInfo);
        } catch (Exception e) {
            logger.error("{}",e.getMessage());
        }
        return resultVO;
    }

    /*
    * 高级搜索查询预案对象信息
    * by yushch 20180522
    */
    @ApiOperation(value="高级搜索查询预案对象信息列表",notes="列表信息")
    @ApiImplicitParam(name="vo",value = "预案对象VO")
    @PostMapping("gjssYadxList")
    public @ResponseBody ResultVO gjssYadxList(@RequestBody ImportantunitsVO vo) {
        ResultVO resultVO = ResultVO.build();
        try {
            PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
            List<ImportantunitsVO> list = searchService.doSearchGjssYadxListByVO(vo);
            PageInfo<ImportantunitsVO> pageInfo = new PageInfo<>(list);
            resultVO.setResult(pageInfo);
        } catch (Exception e) {
            logger.error("{}",e.getMessage());
        }
        return resultVO;
    }

    /*
    * 高级搜索查询建筑列表
    * 通过输入框值匹配 建筑名称或建筑位置
    * by yushch 20180517
    */
    @ApiOperation(value="高级搜索查询建筑列表",notes="列表信息")
    @ApiImplicitParam(name="vo",value = "建筑VO")
    @PostMapping("gjssDwjzList")
    public @ResponseBody ResultVO gjssDwjzList(@RequestBody BuildingVO vo ) {
        ResultVO resultVO = ResultVO.build();
        try {
            PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
            List<BuildingVO> list = searchService.doSearchGjssDwjzListByVO(vo);
            PageInfo<BuildingVO> pageInfo = new PageInfo<>(list);
            resultVO.setResult(pageInfo);
        } catch (Exception e) {
            logger.error("{}",e.getMessage());
        }
        return resultVO;
    }

}
