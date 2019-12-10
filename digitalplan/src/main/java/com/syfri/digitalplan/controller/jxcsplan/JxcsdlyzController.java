package com.syfri.digitalplan.controller.jxcsplan;

import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.syfri.digitalplan.model.jxcsplan.JxcsdlyzVO;
import com.syfri.digitalplan.service.jxcsplan.JxcsdlyzService;
import com.syfri.baseapi.controller.BaseController;

@Api(value = "九小场所登录验证" ,tags = "九小场所登录验证API" ,description = "JxcsdlyzController")
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

	//删除登录验证表中存在但基本信息表里不存在的数据 add  by yushch 20181127
	@ApiOperation(value="根据统一社会信用代码删除",notes="删除")
	@ApiImplicitParam(name="vo",value="九小场所VO")
	@GetMapping("/doDeleteByUnscid/{dwid}")
	public @ResponseBody ResultVO doDeleteByDwid(@PathVariable String dwid){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(jxcsdlyzService.doDeleteByDwid(dwid));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}
}
