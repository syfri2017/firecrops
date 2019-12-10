package com.syfri.digitalplan.controller.digitalplan;

import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.syfri.digitalplan.model.digitalplan.KeypointsVO;
import com.syfri.digitalplan.service.digitalplan.KeypointsService;
import com.syfri.baseapi.controller.BaseController;

@Api(value = "重点单位预案要点提示",tags = "重点单位预案要点提示API",description = "KeypointsController")
@RestController
@RequestMapping("keypoints")
public class KeypointsController  extends BaseController<KeypointsVO>{

	@Autowired
	private KeypointsService keypointsService;

	@Override
	public KeypointsService getBaseService() {
		return this.keypointsService;
	}

	/**
	 * 根据预案id查询
	 */
	@ApiOperation(value="根据预案ID获取重点单位预案要点提示",notes="列表信息")
	@ApiImplicitParam(name="id",value="预案ID")
	@GetMapping("/doFindByPlanId/{yaid}")
	public @ResponseBody
	ResultVO getDetail(@PathVariable String yaid){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(keypointsService.doFindByPlanId(yaid));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

}
