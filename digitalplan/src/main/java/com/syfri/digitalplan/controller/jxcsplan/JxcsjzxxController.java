package com.syfri.digitalplan.controller.jxcsplan;

import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import com.syfri.digitalplan.model.jxcsplan.JxcsjzxxVO;
import com.syfri.digitalplan.service.jxcsplan.JxcsjzxxService;
import com.syfri.baseapi.controller.BaseController;

import java.util.List;

@Api(value = "九小场所建筑信息管理" ,tags = "九小场所建筑信息管理API" ,description = "JxcsjzxxController")
@RestController
@RequestMapping("jxcsjzxx")
public class JxcsjzxxController  extends BaseController<JxcsjzxxVO>{

	@Autowired
	private JxcsjzxxService jxcsjzxxService;

	@Override
	public JxcsjzxxService getBaseService() {
		return this.jxcsjzxxService;
	}

	@ApiOperation(value="根据单位id获取建筑详情集合",notes="列表信息")
	@ApiImplicitParam(name="dwId",value="单位id")
	@GetMapping("/doFindJzxxByDwid/{dwid}")
	public @ResponseBody ResultVO doFindJzxxByDwid(@PathVariable String dwid){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(jxcsjzxxService.doFindJzxxByDwid(dwid));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	@ApiOperation(value="根据建筑id逻辑删除建筑",notes="列表信息")
	@ApiImplicitParam(name="vo",value="建筑VO")
	@PostMapping("/doDeleteByList")
	public @ResponseBody ResultVO doDeleteByList(@RequestBody List<JxcsjzxxVO> jxcsjzxxList){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(jxcsjzxxService.doDeleteByList(jxcsjzxxList));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	@ApiOperation(value="根据建筑VO新增",notes="新增")
	@ApiImplicitParam(name="vo",value="建筑VO")
	@PostMapping("/doInsertByVO")
	public @ResponseBody ResultVO doInsertByVO(@RequestBody JxcsjzxxVO jxcsjzxxVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(jxcsjzxxService.doInsertByVO(jxcsjzxxVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	@ApiOperation(value="根据建筑VO修改",notes="修改")
	@ApiImplicitParam(name="vo",value="建筑VO")
	@PostMapping("/doUpdateByVO")
	public @ResponseBody ResultVO doUpdateByVO(@RequestBody JxcsjzxxVO jxcsjzxxVO) {
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(jxcsjzxxService.doUpdateByVO(jxcsjzxxVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	//add by yushch 建筑新增 返回建筑vo
	@ApiOperation(value="根据建筑VO新增并返回建筑信息",notes="新增")
	@ApiImplicitParam(name="vo",value="建筑VO")
	@PostMapping("/doInsertBuildingByVO")
	public @ResponseBody ResultVO doInsertBuildingByVO(@RequestBody JxcsjzxxVO jxcsjzxxVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(jxcsjzxxService.doInsertBuildingByVO(jxcsjzxxVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}
}
