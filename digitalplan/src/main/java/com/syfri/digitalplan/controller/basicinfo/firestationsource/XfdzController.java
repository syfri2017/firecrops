package com.syfri.digitalplan.controller.basicinfo.firestationsource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import oracle.jdbc.proxy.annotation.Post;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.syfri.digitalplan.model.basicinfo.firestationsource.XfdzVO;
import com.syfri.digitalplan.service.basicinfo.firestationsource.XfdzService;
import com.syfri.baseapi.controller.BaseController;

import java.util.List;

@Api(value = "消防队站管理",tags = "消防队站管理API",description = "XfdzController")
@RestController
@RequestMapping("xfdz")
public class XfdzController extends BaseController<XfdzVO> {

    @Autowired
    private XfdzService xfdzService;

    @Override
    public XfdzService getBaseService() {
        return this.xfdzService;
    }

    /**
     * 通过队站vo获取队站详细信息
     * by yushch 20180531
     */
    @ApiOperation(value = "通过VO获取队站详细信息", notes = "详情信息")
    @ApiImplicitParam(name = "vo", value = "消防队站VO")
    @PostMapping("/findDzDetailByVo")
    public @ResponseBody ResultVO findById(@RequestBody XfdzVO xfdzVO) {
        ResultVO resultVO = ResultVO.build();
        try {
            resultVO.setResult(xfdzService.doFindDzDetailByVo(xfdzVO));
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            resultVO.setCode(EConstants.CODE.FAILURE);
        }
        return resultVO;
    }

    /***
     * @Description: 查询行政区划所在省的所有队站
     * @Param: [xfdzVO]
     * @Return: com.syfri.baseapi.model.ResultVO
     * @Author: liurui
     * @Modified By:
     * @Date: 2018/6/7 16:23
     */
    @ApiOperation(value = "查询行政区划所在省的所有队站", notes = "列表信息")
    @ApiImplicitParam(name = "vo", value = "消防队站VO")
    @PostMapping("/doSearchProvinceList")
    public @ResponseBody ResultVO doSearchProvinceList(@RequestBody XfdzVO xfdzVO) {
        ResultVO resultVO = ResultVO.build();
        try {
            PageHelper.startPage(xfdzVO.getPageNum(),xfdzVO.getPageSize());
            List<XfdzVO> list = xfdzService.doSearchProvinceList(xfdzVO);
            PageInfo<XfdzVO> pageInfo = new PageInfo<>(list);
            resultVO.setResult(pageInfo);
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            resultVO.setCode(EConstants.CODE.FAILURE);
        }
        return resultVO;
    }

    /**
     * 通过登录用户获取队站树
     * by li.xue 20180725
     */
    @ApiOperation(value = "通过VO获取消防队站树（下级，不带本级）", notes = "消防队站树")
    @ApiImplicitParam(name = "vo", value = "队站对象VO")
    @PostMapping("/findSjdzByUser")
    public @ResponseBody ResultVO findSjdzByUser(@RequestBody XfdzVO xfdzVO) {
        ResultVO resultVO = ResultVO.build();
        try {
            resultVO.setResult(xfdzService.doFindDzTreeByUser(xfdzVO));
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            resultVO.setCode(EConstants.CODE.FAILURE);
        }
        return resultVO;
    }
    /**
     * 通过登录用户获取队站树（带本级）
     * by li.xue 20180725
     */
    @ApiOperation(value = "通过VO获取消防队长树（带本级）", notes = "消防队站树")
    @ApiImplicitParam(name = "vo", value = "队站对象VO")
    @PostMapping("/findSjdzByUserAll")
    public @ResponseBody ResultVO findSjdzByUserAll(@RequestBody XfdzVO xfdzVO) {
        ResultVO resultVO = ResultVO.build();
        try {
            resultVO.setResult(xfdzService.doFindDzTreeByUserAll(xfdzVO));
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            resultVO.setCode(EConstants.CODE.FAILURE);
        }
        return resultVO;
    }

    @ApiOperation(value="判断队站名称是否已存在",notes="列表信息")
    @ApiImplicitParam(name="vo",value="消防队站名称")
    @GetMapping("/doCheckName/{dzmc}")
    public @ResponseBody ResultVO doCheckName(@PathVariable String dzmc) {
        ResultVO resultVO = ResultVO.build();
        try{
            resultVO.setResult(xfdzService.doCheckName(dzmc));
        }catch(Exception e){
            logger.error("{}",e.getMessage());
            resultVO.setCode(EConstants.CODE.FAILURE);
        }
        return resultVO;
    }

    /**
     * 新增队站
     * by li.xue 20180725
     */
    @ApiOperation(value = "通过VO新增消防队站", notes = "新增")
    @ApiImplicitParam(name = "vo", value = "消防队站VO")
    @RequiresPermissions("basicinfo/firestation:add")
    @PostMapping("/insertByXfdzVO")
    public @ResponseBody ResultVO insertByXfdzVO(@RequestBody XfdzVO xfdzVO) {
        ResultVO resultVO = ResultVO.build();
        try {
            resultVO.setResult(xfdzService.doInsertByXfdzVO(xfdzVO));
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            resultVO.setCode(EConstants.CODE.FAILURE);
        }
        return resultVO;
    }

    /**
     * 修改队站
     * by li.xue 20180725
     */
    @ApiOperation(value = "通过VO修改消防队站", notes = "修改")
    @ApiImplicitParam(name = "vo", value = "消防队站VO")
    @RequiresPermissions("basicinfo/firestation:edit")
    @PostMapping("/updateByXfdzVO")
    public @ResponseBody ResultVO updateByXfdzVO(@RequestBody XfdzVO xfdzVO) {
        ResultVO resultVO = ResultVO.build();
        try {
            resultVO.setResult(xfdzService.doUpdateByXfdzVO(xfdzVO));
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            resultVO.setCode(EConstants.CODE.FAILURE);
        }
        return resultVO;
    }

    /**
     * 批量删除队站
     * by li.xue 20180725
     */
    @ApiOperation(value = "通过VO删除消防队站", notes = "删除")
    @ApiImplicitParam(name = "vo", value = "消防队站VO")
    @RequiresPermissions("basicinfo/firestation:delete")
    @PostMapping("/doDeleteBatch")
    public @ResponseBody ResultVO doDeleteBatch(@RequestBody List<XfdzVO> list) {
        ResultVO resultVO = ResultVO.build();
        try {
            resultVO.setResult(xfdzService.doDeleteBatch(list));
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            resultVO.setCode(EConstants.CODE.FAILURE);
        }
        return resultVO;
    }

    /**
     * 根据登陆人组织机构ID获取其队站类型
     * by li.xue 20180725
     */
    @ApiOperation(value = "根据登陆人组织机构ID获取其队站类型", notes = "列表信息")
    @ApiImplicitParam(name = "orgId", value = "组织机构ID")
    @GetMapping("/doFindDzlxByOrgId/{orgId}")
    public @ResponseBody ResultVO doFindDzlxByOrgId(@PathVariable String orgId) {
        ResultVO resultVO = ResultVO.build();
        try {
            resultVO.setResult(xfdzService.doFindById(orgId).getDzlx());
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            resultVO.setCode(EConstants.CODE.FAILURE);
        }
        return resultVO;
    }

    /**
     * 根据登陆人组织机构ID获取其队站类型
     * by li.xue 20180725
     */
    @ApiOperation(value = "根据登陆人组织机构ID获取其子一级队站", notes = "列表信息")
    @ApiImplicitParam(name = "orgId", value = "组织机构ID")
    @GetMapping("/doFindDzYjByOrgId/{orgId}")
    public @ResponseBody ResultVO doFindDzYjByOrgId(@PathVariable String orgId) {
        ResultVO resultVO = ResultVO.build();
        try {
            resultVO.setResult(xfdzService.doFindDzYjByOrgId(orgId));
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            resultVO.setCode(EConstants.CODE.FAILURE);
        }
        return resultVO;
    }


    @ApiOperation(value="查询分发获取其相应机构ID",notes="列表信息")
    @ApiImplicitParam(name="vo",value = "消防队站ID")
    @PostMapping("doFindCorresJgid")
    public @ResponseBody ResultVO doFindCorresJgid(@RequestBody XfdzVO xfdzVO) {
        ResultVO resultVO = ResultVO.build();
        try {
            resultVO.setResult(xfdzService.doFindCorresJgid(xfdzVO));
        } catch (Exception e) {
            logger.error("{}",e.getMessage());
        }
        return resultVO;
    }
}
