package com.syfri.digitalplan.controller.jxcsplan;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.syfri.digitalplan.model.jxcsplan.JxcsjbxxVO;
import com.syfri.digitalplan.service.jxcsplan.JxcsjbxxService;
import com.syfri.baseapi.controller.BaseController;

import java.util.List;

@Api(value = "九小场所基本信息管理" ,tags = "九小场所基本信息管理API" ,description = "jxcsjbxx")
@RestController
@RequestMapping("jxcsjbxx")
public class JxcsjbxxController  extends BaseController<JxcsjbxxVO>{

	@Autowired
	private JxcsjbxxService jxcsjbxxService;

	@Override
	public JxcsjbxxService getBaseService() {
		return this.jxcsjbxxService;
	}

	@ApiOperation(value="根据九小场所list删除",notes="删除")
	@ApiImplicitParam(name="vo",value="九小场所VO")
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

	@ApiOperation(value="根据九小场所VO保存",notes="保存")
	@ApiImplicitParam(name="vo",value = "九小场所VO")
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

	@ApiOperation(value="根据九小场所VO更新",notes="更新")
	@ApiImplicitParam(name="vo",value = "九小场所VO")
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

	@ApiOperation(value="查询审核列表",notes="列表信息")
	@ApiImplicitParam(name="vo",value = "九小场所VO")
	@PostMapping("listForApprove")
	public @ResponseBody ResultVO listForApprove(@RequestBody JxcsjbxxVO vo ) {
		ResultVO resultVO = ResultVO.build();
		try {
			PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
			List<JxcsjbxxVO> list = jxcsjbxxService.doSearchApproveListByVO(vo);
			PageInfo<JxcsjbxxVO> pageInfo = new PageInfo<>(list);
			resultVO.setResult(pageInfo);
		} catch (Exception e) {
			logger.error("{}",e.getMessage());
		}
		return resultVO;
	}

	@ApiOperation(value="根据九小场所VO审批",notes="审批")
	@ApiImplicitParam(name="vo",value="九小场所VO")
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
	//add by yushch 查询基本数据同时查询统一社会信用代码
	@ApiOperation(value = "根据ID查询基本数据及统一社会信用代码", notes = "查询一条记录")
	@ApiImplicitParam(name = "id", value = "业务ID", dataType = "String", paramType = "path")
	@GetMapping("{id}")
	public @ResponseBody ResultVO find(@PathVariable String id){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(jxcsjbxxService.doFindJbxxById(id));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
		}
		return resultVO;
	}
}
