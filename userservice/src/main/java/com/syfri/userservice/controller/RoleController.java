package com.syfri.userservice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.syfri.userservice.model.RoleVO;
import com.syfri.userservice.service.RoleService;
import com.syfri.baseapi.controller.BaseController;

import java.util.List;

@Api(value = "角色管理",tags = "角色管理API",description = "角色管理")
@Controller
@RequestMapping("role")
public class RoleController  extends BaseController<RoleVO>{

	@Autowired
	protected Environment environment;

	@Autowired
	private RoleService roleService;

	@Override
	public RoleService getBaseService() {
		return this.roleService;
	}

	@ModelAttribute
	public void Model(Model model){
		if (environment.containsProperty("server.context-path")) {
			model.addAttribute("contextPath", environment.getProperty("server.context-path"));
		}else{
			model.addAttribute("contextPath", "/");
		}
	}

	@GetMapping("")
	public String getRole(Model model, @RequestParam(value="index") String index){
		model.addAttribute("index", index);
		return "system/role_list";
	}

	/**
	 * 根据角色获取资源信息
	 */
	@ApiOperation(value="根据角色查询角色及其资源信息",notes="列表信息")
	@ApiImplicitParam(name="vo",value="角色对象")
	@RequiresPermissions("system/role:list")
	@PostMapping("/findByVO")
	public @ResponseBody ResultVO findByVO(@RequestBody RoleVO roleVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(roleService.doFindRoleResources(roleVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 新增角色，同时新增其资源信息
	 */
	@ApiOperation(value="根据角色新增角色及其资源信息",notes="新增")
	@ApiImplicitParam(name="vo",value="角色对象")
	@RequiresPermissions("system/role:add")
	@PostMapping("/insertByVO")
	public @ResponseBody ResultVO insertByVO(@RequestBody RoleVO roleVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(roleService.doInsertRoleResources(roleVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 修改角色，同时修改其资源信息
	 */
	@ApiOperation(value="根据角色修改角色及其资源信息",notes="修改")
	@ApiImplicitParam(name="vo",value="角色对象")
	@RequiresPermissions("system/role:edit")
	@PostMapping("/updateByVO")
	public @ResponseBody ResultVO updateByVO(@RequestBody RoleVO roleVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(roleService.doUpdateRoleResources(roleVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 删除角色，同时删除其资源信息
	 */
	@ApiOperation(value="根据主键删除角色角色及其资源信息",notes="删除")
	@ApiImplicitParam(name="id",value="角色主键")
	@RequiresPermissions("system/role:delete")
	@PostMapping("/deleteByIds")
	public @ResponseBody ResultVO deleteByIds(@RequestBody List<RoleVO> list){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(roleService.doDeleteRoleResources(list));
			resultVO.setMsg("删除成功");
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	@ApiOperation(value="获取所有的角色",notes="查询")
	@GetMapping("/getAll")
	public @ResponseBody ResultVO findAll(){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(roleService.doFindAll());
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	@ApiOperation(value="根据用户ID查询其角色",notes="查询")
	@ApiImplicitParam(name="id",value="用户ID")
	@GetMapping("/getRole/{userid}")
	public @ResponseBody ResultVO getRole(@PathVariable String userid){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(roleService.doFindRoleByUserid(userid));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	@ApiOperation(value="根据角色名查询角色数量",notes="查询")
	@ApiImplicitParam(name="rolename",value="角色名")
	@GetMapping("/getNum/{rolename}")
	public @ResponseBody ResultVO getNum(@PathVariable String rolename){
		ResultVO resultVO = ResultVO.build();
		try{
			RoleVO roleVO = new RoleVO();
			roleVO.setRolename(rolename);
			if(roleService.doSearchListByVO(roleVO).size() == 0){
				resultVO.setResult(0);
			}else{
				resultVO.setResult(1);
			}
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

}
