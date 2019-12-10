package com.syfri.digitalplan.controller.basicinfo.fireenginesource;

import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.syfri.digitalplan.model.basicinfo.fireenginesource.FireengineVO;
import com.syfri.digitalplan.service.basicinfo.fireenginesource.FireengineService;
import com.syfri.baseapi.controller.BaseController;

import java.util.List;

@Api(value = "消防车辆管理",tags = "消防车辆管理API",description = "FireengineController")
@RestController
@RequestMapping("fireengine")
public class FireengineController  extends BaseController<FireengineVO>{

	@Autowired
	private FireengineService fireengineService;
	@Override
	public FireengineService getBaseService() {
		return this.fireengineService;
	}

	/**
	 * @Description: 删除车辆信息
	 * @Param: [fireegineList, fireegineVo]
	 * @Return: com.syfri.baseapi.model.ResultVO
	 * @Author: zhaijianchen
	 * @Modified By:
	 * @Date: 2018/7/23 15:52
	 */
	@ApiOperation(value="根据VO删除消防车辆",notes="删除")
	@ApiImplicitParam(name="vo",value="消防车辆VO")
	@RequiresPermissions("basicinfo/fireengine:delete")
	@PostMapping("/doDeleteFireengine")
	public @ResponseBody ResultVO doDeleteFireengine(@RequestBody List<FireengineVO> fireengineList) {
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(fireengineService.doDeleteFireengine(fireengineList));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * @Description: 车辆新增
	 * @Param: [dangerVO]
	 * @Return: com.syfri.baseapi.model.ResultVO
	 * @Author: zhaijianchen
	 * @Modified By:
	 * @Date: 2018/7/25 10:13
	 */
	@ApiOperation(value="根据VO新增消防车辆",notes="新增")
	@ApiImplicitParam(name="vo",value="消防车辆VO")
	@RequiresPermissions("basicinfo/fireengine:add")
	@PostMapping("/insertByVO")
	public @ResponseBody ResultVO insertByVO(@RequestBody FireengineVO fireengineVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(fireengineService.doInsertByVO(fireengineVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * @Description: 车辆修改
	 * @Param: [fireengineVO]
	 * @Return: com.syfri.baseapi.model.ResultVO
	 * @Author: zhaijianchen
	 * @Modified By:
	 * @Date: 2018/7/25 10:13
	 */
	@ApiOperation(value="根据VO修改消防车辆",notes="修改")
	@ApiImplicitParam(name="vo",value="消防车辆VO")
	@RequiresPermissions("basicinfo/fireengine:edit")
	@PostMapping("/doUpdateFireengine")
	public @ResponseBody ResultVO doUpdateFireengine(@RequestBody FireengineVO fireengineVO) {
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(fireengineService.doUpdateFireengine(fireengineVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

}
