package com.syfri.digitalplan.controller.basicinfo.Equipengine;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.syfri.digitalplan.model.basicinfo.equipmentsource.EquipengineVO;
import com.syfri.digitalplan.service.basicinfo.equipmentsource.EquipengineService;
import com.syfri.baseapi.controller.BaseController;

@Api(value = "车辆装载装备管理",tags = "车辆装载装备管理API",description = "EquipengineController")
@RestController
@RequestMapping("equipengine")
public class EquipengineController  extends BaseController<EquipengineVO>{

	@Autowired
	private EquipengineService equipengineService;

	@Override
	public EquipengineService getBaseService() {
		return this.equipengineService;
	}

}
