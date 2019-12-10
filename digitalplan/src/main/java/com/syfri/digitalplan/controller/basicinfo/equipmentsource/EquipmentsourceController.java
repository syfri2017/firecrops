package com.syfri.digitalplan.controller.basicinfo.equipmentsource;

import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import com.syfri.digitalplan.model.basicinfo.equipmentsource.EquipmentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.syfri.digitalplan.service.basicinfo.equipmentsource.EquipmentsourceService;
import com.syfri.baseapi.controller.BaseController;

import java.util.List;

@Api(value = "装备器材管理",tags = "装备器材管理API",description = "EquipmentsourceController")
@RestController
@RequestMapping("equipmentsource")
public class EquipmentsourceController  extends BaseController<EquipmentVO>{

	@Autowired
	private EquipmentsourceService equipmentsourceService;

	@Override
	public EquipmentsourceService getBaseService() {
		return this.equipmentsourceService;
	}

	@Autowired
	protected Environment environment;

	@ApiOperation(value="根据VO新增装备器材",notes="新增")
	@ApiImplicitParam(name="vo",value="装备器材VO")
	@RequiresPermissions("basicinfo/equipment:add")
	@PostMapping("/insertByVO")
	public @ResponseBody ResultVO insertByVO(@RequestBody EquipmentVO equipmentVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(equipmentsourceService.doInsertEquipment(equipmentVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	@ApiOperation(value="根据VO删除装备器材",notes="删除")
	@ApiImplicitParam(name="vo",value="装备器材VO")
	@RequiresPermissions("basicinfo/equipment:delete")
	@PostMapping("/doDeleteEquipment")
	public @ResponseBody ResultVO doDeleteEquipment(@RequestBody List<EquipmentVO> equipmentList) {
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(equipmentsourceService.doDeleteEquipment(equipmentList));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	@ApiOperation(value="根据VO修改装备器材",notes="修改")
	@ApiImplicitParam(name="vo",value="装备器材VO")
	@RequiresPermissions("basicinfo/equipment:edit")
	@PostMapping("/doUpdateEquipment")
	public @ResponseBody ResultVO doUpdateEquipment(@RequestBody EquipmentVO equipmentVO) {
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(equipmentsourceService.doUpdateEquipment(equipmentVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

}
