package com.syfri.digitalplan.controller.jxcsplan;

import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.syfri.digitalplan.model.jxcsplan.JxcsdlyzVO;
import com.syfri.digitalplan.service.jxcsplan.JxcsdlyzService;
import com.syfri.baseapi.controller.BaseController;

@RestController
@RequestMapping("jxcsdlyz")
public class JxcsdlyzController  extends BaseController<JxcsdlyzVO>{

	@Autowired
	private JxcsdlyzService jxcsdlyzService;

	@Override
	public JxcsdlyzService getBaseService() {
		return this.jxcsdlyzService;
	}

	//add by yushch 查询登录验证表中是否存在当前统一社会信用代码
	@ApiOperation(value="查询统一社会信用代码数量",notes="列表信息")
	@ApiImplicitParam(name="unscid",value="单位id")
	@GetMapping("/doFindCountByUnscid/{unscid}")
	public @ResponseBody ResultVO doFindCountByUnscid(@PathVariable String unscid){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(jxcsdlyzService.doFindCountByUnscid(unscid));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}
}
