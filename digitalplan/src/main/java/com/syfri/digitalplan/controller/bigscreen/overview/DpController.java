package com.syfri.digitalplan.controller.bigscreen.overview;

import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.syfri.digitalplan.model.bigscreen.overview.DpVO;
import com.syfri.digitalplan.service.bigscreen.overview.DpService;
import com.syfri.baseapi.controller.BaseController;

@Api(value = "大屏管理",tags = "大屏管理API",description = "DpController")
@RestController
@RequestMapping("dp")
public class DpController  extends BaseController<DpVO>{

	@Autowired
	private DpService dpService;

	@Override
	public DpService getBaseService() {
		return this.dpService;
	}

	@ApiOperation(value="根据VO查询大屏key，value信息",notes="查询列表")
	@ApiImplicitParam(name="vo",value="大屏对象VO")
	@PostMapping("/getListByType")
	public @ResponseBody ResultVO getListByType(@RequestBody DpVO vo){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(dpService.getListByType(vo));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}
}
