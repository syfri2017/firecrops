package com.syfri.digitalplan.controller.jxcsplan;

import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.syfri.digitalplan.model.jxcsplan.JxcsxfssVO;
import com.syfri.digitalplan.service.jxcsplan.JxcsxfssService;
import com.syfri.baseapi.controller.BaseController;

@Api(value = "九小场所消防设施管理" ,tags = "九小场所消防设施管理API" ,description = "jxcsxfss")
@RestController
@RequestMapping("jxcsxfss")
public class JxcsxfssController  extends BaseController<JxcsxfssVO>{

	@Autowired
	private JxcsxfssService jxcsxfssService;

	@Override
	public JxcsxfssService getBaseService() {
		return this.jxcsxfssService;
	}

	@ApiOperation(value="根据单位id获取消防设施集合",notes="列表信息")
	@ApiImplicitParam(name="dwid",value="单位id")
	@GetMapping("/doFindXfssByDwid/{dwid}")
	public @ResponseBody
	ResultVO doFindXfssByDwid(@PathVariable String dwid){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(jxcsxfssService.doFindXfssByDwid(dwid));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

}
