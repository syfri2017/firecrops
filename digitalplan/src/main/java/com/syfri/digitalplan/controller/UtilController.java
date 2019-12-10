package com.syfri.digitalplan.controller;

import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.syfri.digitalplan.model.UtilXfdzVO;
import com.syfri.digitalplan.service.UtilService;
import com.syfri.baseapi.controller.BaseController;

@Api(value = "公共类" ,tags = "公共类API" ,description = "UtilController")
@RestController
@RequestMapping("util")
public class UtilController  extends BaseController<UtilXfdzVO>{

	@Autowired
	private UtilService utilService;

	@Override
	public UtilService getBaseService() {
		return this.utilService;
	}

	/**
	 * 查询到消防总队
	 */
	@ApiOperation(value="查询到消防总队",notes="下拉列表信息")
	@ApiImplicitParam(name="vo",value="消防总队对象")
	@GetMapping("/doSearchContingents")
	public @ResponseBody ResultVO doSearchContingents(){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(utilService.doSearchContingents());
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 根据总队查询其下属支队
	 * by li.xue 20180829
	 */
	@ApiOperation(value = "通过队站vo获取队站详细信息", notes = "查询一条信息")
	@ApiImplicitParam(name = "dzid", value = "队站ID")
	@GetMapping("/doFindXfdzByZongdId/{dzid}")
	public @ResponseBody ResultVO doFindXfdzByZongdId(@PathVariable String dzid) {
		ResultVO resultVO = ResultVO.build();
		try {
			resultVO.setResult(utilService.doFindXfdzByZongdId(dzid));
		} catch (Exception e) {
			logger.error("{}", e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}
}
