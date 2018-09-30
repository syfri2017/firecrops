package com.syfri.digitalplan.controller.jxcsplan;

import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.syfri.digitalplan.model.jxcsplan.JxcsjbxxVO;
import com.syfri.digitalplan.service.jxcsplan.JxcsjbxxService;
import com.syfri.baseapi.controller.BaseController;

import java.util.List;

@RestController
@RequestMapping("jxcsjbxx")
public class JxcsjbxxController  extends BaseController<JxcsjbxxVO>{

	@Autowired
	private JxcsjbxxService jxcsjbxxService;

	@Override
	public JxcsjbxxService getBaseService() {
		return this.jxcsjbxxService;
	}

	@ApiOperation(value="根据vo删除",notes="删除")
	@ApiImplicitParam(name="vo",value="九小场所")
	@PostMapping("/doDeleteByVOList")
	public @ResponseBody
	ResultVO doDeleteByVOList(@RequestBody List<JxcsjbxxVO> jxcsjbxxVOList){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(jxcsjbxxService.doDeleteByVOList(jxcsjbxxVOList));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	@ApiOperation(value="根据VO保存",notes="保存")
	@ApiImplicitParam(name="vo",value = "九小场所")
	@PostMapping("/doInsertByVo")
	public @ResponseBody ResultVO save(@RequestBody JxcsjbxxVO vo) throws Exception{
		ResultVO resultVO = ResultVO.build();
		try {
			resultVO.setResult(jxcsjbxxService.doInsertJxcsByVO(vo));
		} catch (Exception e) {
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}

		return 	resultVO;
	}

	@ApiOperation(value="根据VO更新",notes="更新")
	@ApiImplicitParam(name="vo",value = "九小场所")
	@PostMapping("/doUpdateJxcsByVO")
	public @ResponseBody ResultVO doUpdateJxcsByVO(@RequestBody JxcsjbxxVO vo) throws Exception{
		ResultVO resultVO = ResultVO.build();
		try {
			resultVO.setResult(jxcsjbxxService.doUpdateJxcsByVO(vo));
		} catch (Exception e) {
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return 	resultVO;
	}

	@ApiOperation(value="根据VO审批",notes="修改")
	@ApiImplicitParam(name="vo",value="九小场所")
	@PostMapping("/approveByVO")
	public @ResponseBody ResultVO approveByVO(@RequestBody JxcsjbxxVO vo){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(jxcsjbxxService.doApproveUpdate(vo));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}
}
