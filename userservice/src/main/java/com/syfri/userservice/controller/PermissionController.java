package com.syfri.userservice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

import com.syfri.userservice.model.PermissionVO;
import com.syfri.userservice.service.PermissionService;
import com.syfri.baseapi.controller.BaseController;

import java.util.List;

@Api(value = "权限管理",tags = "权限管理API",description = "权限管理")
@Controller
@RequestMapping("permission")
public class PermissionController  extends BaseController<PermissionVO>{

	@Autowired
	protected Environment environment;

	@Autowired
	private PermissionService permissionService;

	@Override
	public PermissionService getBaseService() {
		return this.permissionService;
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
	public String getPermission(Model model, @RequestParam(value="index") String index){
		model.addAttribute("index", index);
		return "system/permission_list";
	}

	/**
	 * 根据权限获取资源信息
	 */
	@ApiOperation(value="根据权限查询权限及其资源信息",notes="列表信息")
	@ApiImplicitParam(name="permissionVO",value="权限对象")
	@RequiresPermissions("system/permission:list")
	@PostMapping("/findByVO")
	public @ResponseBody ResultVO findByVO(@RequestBody PermissionVO permissionVO){
		ResultVO resultVO = ResultVO.build();
		try{
			PageHelper.startPage(permissionVO.getPageNum(),permissionVO.getPageSize());
			List<PermissionVO> list = permissionService.doSearchListByVO(permissionVO);
			PageInfo<PermissionVO> pageInfo = new PageInfo<>(list);
			resultVO.setResult(pageInfo);
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 新增权限，同时新增其资源信息
	 */
	@ApiOperation(value="根据权限新增权限及其资源信息",notes="新增")
	@ApiImplicitParam(name="permissionVO",value="权限对象")
	@RequiresPermissions("system/permission:add")
	@PostMapping("/insertByVO")
	public @ResponseBody ResultVO insertByVO(@RequestBody PermissionVO permissionVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(permissionService.doInsertPermission(permissionVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 修改权限，同时修改其资源信息
	 */
	@ApiOperation(value="根据权限修改权限及其资源信息",notes="修改")
	@ApiImplicitParam(name="permissionVO",value="权限对象")
	@RequiresPermissions("system/permission:edit")
	@PostMapping("/updateByVO")
	public @ResponseBody ResultVO updateByVO(@RequestBody PermissionVO permissionVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(permissionService.doUpdatePermission(permissionVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 删除权限，同时删除其资源信息
	 */
	@ApiOperation(value="根据主键删除权限权限及其资源信息",notes="删除")
	@ApiImplicitParam(name="id",value="权限主键")
	@RequiresPermissions("system/permission:delete")
	@PostMapping("/deleteByList")
	public @ResponseBody ResultVO deleteByList(@RequestBody List<PermissionVO> list){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(permissionService.doDeletePermissions(list));
			resultVO.setMsg("删除成功");
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	@ApiOperation(value="获取所有的权限",notes="查询")
	@GetMapping("/getAll")
	public @ResponseBody ResultVO findAll(){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(permissionService.doFindAll());
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	@ApiOperation(value="根据资源ID查询其权限",notes="查询")
	@ApiImplicitParam(name="resourceid",value="资源ID")
	@GetMapping("/getPermission/{resourceid}")
	public @ResponseBody ResultVO getPermission(@PathVariable String resourceid){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(permissionService.doFindPermissionByResourceId(resourceid));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	@ApiOperation(value="根据权限名称查询权限数量",notes="查询")
	@ApiImplicitParam(name="permissionname",value="权限名")
	@GetMapping("/getNum/{permissionname}")
	public @ResponseBody ResultVO getNum(@PathVariable String permissionname){
		ResultVO resultVO = ResultVO.build();
		try{
			PermissionVO permissionVO = new PermissionVO();
			permissionVO.setPermissionname(permissionname);
			if(permissionService.doSearchListByVO(permissionVO).size() == 0){
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
