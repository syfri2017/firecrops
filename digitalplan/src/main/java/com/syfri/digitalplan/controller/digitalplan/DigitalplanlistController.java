package com.syfri.digitalplan.controller.digitalplan;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import com.syfri.digitalplan.model.buildingzoning.BuildingVO;
import com.syfri.digitalplan.model.digitalplan.DigitalplanlistVO;
import com.syfri.digitalplan.model.planobject.ImportantunitsVO;
import com.syfri.digitalplan.service.digitalplan.DigitalplanlistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.syfri.baseapi.controller.BaseController;

import java.util.List;
/*
 * 获取路径Controller
 * by dongbo 2018/03/26
 */
@Api(value = "重点单位预案管理",tags = "重点单位预案管理API",description = "DigitalplanlistController")
@RestController
@RequestMapping("digitalplanlist")
public class DigitalplanlistController  extends BaseController<DigitalplanlistVO>{

	private static final Logger logger  = LoggerFactory.getLogger(DigitalplanlistController.class);

	@Autowired
	protected Environment environment;

	@Autowired
	private DigitalplanlistService digitalplanlistService;

	@Override
	public DigitalplanlistService getBaseService() {
		return this.digitalplanlistService;
	}

	/*
	* 预案审批
	* by yuahch 20180426
	*/
	@ApiOperation(value="根据VO进行重点单位预案审批",notes="审批")
	@ApiImplicitParam(name="vo",value="预案VO")
	@RequiresPermissions("digitalplan/digitalplan_approve:approve")
	@PostMapping("/approveByVO")
	public @ResponseBody ResultVO updateByVO(@RequestBody DigitalplanlistVO digitalplanlistVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(digitalplanlistService.doApproveUpdate(digitalplanlistVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/***
	 * @Description: 新增预案
	 * @Param: [DigitalplanlistVO]
	 * @Return: com.syfri.baseapi.model.ResultVO
	 * @Author: liurui
	 * @Modified By:
	 * @Date: 2018/5/2 15:51
	 */
	@ApiOperation(value="根据VO新增重点单位预案",notes="新增")
	@ApiImplicitParam(name="vo",value="重点单位预案VO")
	@RequiresPermissions("digitalplan/digitalplan:add")
	@PostMapping("/insertByVO")
	public @ResponseBody ResultVO insertByVO(@RequestBody DigitalplanlistVO digitalplanlistVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(digitalplanlistService.doInsertDigitalplan(digitalplanlistVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * @Description: 修改预案
	 * @Param: [DigitalplanlistVO]
	 * @Return: com.syfri.baseapi.model.ResultVO
	 * @Author: liurui
	 * @Modified By:
	 * @Date: 2018/5/2 15:52
	 */
	@ApiOperation(value="根据VO修改重点单位预案",notes="修改")
	@ApiImplicitParam(name="vo",value="重点单位预案VO")
	@RequiresPermissions("digitalplan/digitalplan:edit")
	@PostMapping("/doUpdateByVO")
	public @ResponseBody ResultVO doUpdateByVO(@RequestBody DigitalplanlistVO digitalplanlistVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(digitalplanlistService.doUpdateDigitalplan(digitalplanlistVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * @Description: 删除预案
	 * @Param: [digitalplanList, digitalplanVo]
	 * @Return: com.syfri.baseapi.model.ResultVO
	 * @Author: liurui
	 * @Modified By:
	 * @Date: 2018/5/2 15:52
	 */
	@ApiOperation(value="根据List删除重点单位预案",notes="删除")
	@ApiImplicitParam(name="vo",value="重点单位预案List")
	@RequiresPermissions("digitalplan/digitalplan:delete")
	@PostMapping("/doDeleteDigitalplan")
	public @ResponseBody ResultVO doDeleteDigitalplan(@RequestBody List<DigitalplanlistVO> digitalplanList,DigitalplanlistVO digitalplanVo) {
		ResultVO resultVO = ResultVO.build();
		try{
			int result= digitalplanlistService.doDeleteDigitalplan(digitalplanList,digitalplanVo);
			resultVO.setResult(result);
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/***
	 * @Description: 通过重点单位id查询建筑分区list
	 * @Param: [vo]
	 * @Return: com.syfri.baseapi.model.ResultVO
	 * @Author: liurui
	 * @Modified By:
	 * @Date: 2018/5/23 10:26
	 */
	@ApiOperation(value="通过重点单位ID查询建筑分区list",notes="列表信息")
	@ApiImplicitParam(name="vo",value="建筑分区VO")
	@PostMapping("/doSearchJzListByZddwId")
	public @ResponseBody ResultVO doSearchJzListByZddwId(@RequestBody BuildingVO vo) {
		ResultVO resultVO = ResultVO.build();
		try{
			PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
			List<BuildingVO> list = digitalplanlistService.doSearchJzListByZddwId(vo);
			PageInfo<BuildingVO> pageInfo = new PageInfo<>(list);
			resultVO.setResult(pageInfo);
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/***
	 * @Description: 通过重点单位id查询预案list
	 * @Param: [vo]
	 * @Return: com.syfri.baseapi.model.ResultVO
	 * @Author: liurui
	 * @Modified By:
	 * @Date: 2018/5/23 10:26
	 */
	@ApiOperation(value="通过重点单位ID查询预案List",notes="列表信息")
	@ApiImplicitParam(name="vo",value="重点单位ID")
	@RequestMapping("/doFindListByZddwId/{zddwid}")
	public @ResponseBody ResultVO doFindListByZddwId(@PathVariable String zddwid) {
		ResultVO resultVO = ResultVO.build();
		try{
			List<DigitalplanlistVO> result= digitalplanlistService.doFindListByZddwId(zddwid);
			resultVO.setResult(result);
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	@ApiOperation(value="查询重点单位预案审核列表",notes="列表信息")
	@ApiImplicitParam(name="vo",value = "重点单位预案VO")
	@PostMapping("listForApprove")
	public @ResponseBody ResultVO listForApprove(@RequestBody DigitalplanlistVO vo ) {
		ResultVO resultVO = ResultVO.build();
		try {
			PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
			List<DigitalplanlistVO> list = digitalplanlistService.doSearchApproveListByVO(vo);
			PageInfo<DigitalplanlistVO> pageInfo = new PageInfo<>(list);
			resultVO.setResult(pageInfo);
		} catch (Exception e) {
			logger.error("{}",e.getMessage());
		}
		return resultVO;
	}

	/**
	 * 通过yaid查询历史附件信息列表
	 * by huangrui 2019/1/17
	 */
	@ApiOperation(value="根据Yaid查询历史附件信息列表",notes="列表信息")
	@ApiImplicitParam(name="vo",value="重点单位预案VO")
	@PostMapping("/doFindHisPlanListByVo")
	public @ResponseBody ResultVO doFindHisPlanListByVo(@RequestBody DigitalplanlistVO vo) {
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(digitalplanlistService.doFindHisPlanListByVo(vo));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}


	/**
	 * 通过vo查询预案信息和附件信息
	 * by huangrui 2019/1/20
	 */
	@ApiOperation(value="根据VO查询预案信息和附件信息",notes="详情信息")
	@ApiImplicitParam(name="vo",value="重点单位预案VO")
	@PostMapping("/doFindListWithFJListByVo")
	public @ResponseBody ResultVO doFindListWithFJListByVo(@RequestBody DigitalplanlistVO vo) {
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(digitalplanlistService.doFindListWithFJListByVo(vo));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}
}
