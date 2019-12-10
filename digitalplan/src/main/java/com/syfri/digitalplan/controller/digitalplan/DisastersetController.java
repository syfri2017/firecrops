package com.syfri.digitalplan.controller.digitalplan;

import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.syfri.digitalplan.model.digitalplan.DisastersetVO;
import com.syfri.digitalplan.service.digitalplan.DisastersetService;
import com.syfri.baseapi.controller.BaseController;

@Api(value = "重点单位预案灾情设定",tags = "重点单位预案灾情设定API",description = "DisastersetController")
@RestController
@RequestMapping("disasterset")
public class DisastersetController  extends BaseController<DisastersetVO>{

	@Autowired
	private DisastersetService disastersetService;

	@Override
	public DisastersetService getBaseService() {
		return this.disastersetService;
	}

	/**
	 * 根据预案id查询
	 */
	@ApiOperation(value="根据重点单位预案ID获取预案灾情信息",notes="列表信息")
	@ApiImplicitParam(name="id",value="重点单位预案ID")
	@GetMapping("/doFindByPlanId/{yaid}")
	public @ResponseBody
	ResultVO getDetail(@PathVariable String yaid){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(disastersetService.doFindByPlanId(yaid));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

}
