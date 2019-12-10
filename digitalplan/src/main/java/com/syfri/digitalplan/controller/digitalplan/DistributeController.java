package com.syfri.digitalplan.controller.digitalplan;

import com.syfri.baseapi.controller.BaseController;
import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import com.syfri.digitalplan.model.digitalplan.DistributeVO;
import com.syfri.digitalplan.service.digitalplan.DistributeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "预案分发",tags = "预案分发API",description = "DistributeController")
@RestController
@RequestMapping("distribute")
public class DistributeController  extends BaseController<DistributeVO> {

	@Autowired
	private DistributeService distributeService;

	@Override
	public DistributeService getBaseService() {
		return this.distributeService;
	}

	/**
	 * 获取所有总队信息
	 */
	@ApiOperation(value="根据List获取所有总队",notes="列表信息")
	@ApiImplicitParam(name="list",value="预案分发对象List")
	@PostMapping("/distribute")
	public @ResponseBody ResultVO distribute(@RequestBody List<DistributeVO> distributeList){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(distributeService.doInsertDistributeList(distributeList));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 获取已经分发的总队信息
	 */
	@ApiOperation(value="根据预案ID获取已经分发的总队信息",notes="列表信息")
	@ApiImplicitParam(name="id",value="预案ID")
	@GetMapping("/doFindFfdz/{yaid}")
	public @ResponseBody ResultVO doFindFfdz(@PathVariable String yaid){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(distributeService.doFindFfdzByYaid(yaid));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

}
