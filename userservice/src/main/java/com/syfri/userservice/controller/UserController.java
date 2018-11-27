package com.syfri.userservice.controller;

import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.syfri.userservice.model.UserVO;
import com.syfri.userservice.service.UserService;
import com.syfri.baseapi.controller.BaseController;

import java.util.List;

@Api(value = "用户管理",tags = "用户管理API",description = "用户管理")
@Controller
@RequestMapping("user")
public class UserController  extends BaseController<UserVO>{

	private static final Logger logger  = LoggerFactory.getLogger(UserController.class);

	@Autowired
	protected Environment environment;

	@Autowired
	private UserService userService;

	@Override
	public UserService getBaseService() {
		return this.userService;
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
	public String getUser(Model model, @RequestParam(value="index") String index){
		model.addAttribute("index", index);
		return "system/user_list";
	}

	@GetMapping("/add")
	public String userAdd(Model model){
		model.addAttribute("pageTitle", "创建用户");
		return "system/user_edit";
	}

	@GetMapping("/update")
	public String userUpdate(Model model){
		model.addAttribute("pageTitle", "修改用户");
		return "system/user_edit";
	}

	/**
	 * 根据用户获取用户信息及角色信息
	 */
	@ApiOperation(value="根据用户查询用户信息及角色信息",notes="列表信息")
	@ApiImplicitParam(name="vo",value="用户对象")
	@RequiresPermissions("system/user:list")
	@PostMapping("/findByVO")
	public @ResponseBody ResultVO findByVO(@RequestBody UserVO userVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(userService.doFindUserRoles(userVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 新增用户，同时新增账号及用户角色
	 */
	@ApiOperation(value="根据用户新增用户（包括账户和角色）",notes="新增")
	@ApiImplicitParam(name="vo",value="用户对象")
	@RequiresPermissions("system/user:add")
	@PostMapping("/insertByVO")
		public @ResponseBody ResultVO insertByVO(@RequestBody UserVO userVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(userService.doInsertUserRoles(userVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 修改用户，同时修改账号及用户角色
	 */
	@ApiOperation(value="根据用户修改用户（包括账户和角色）",notes="修改")
	@ApiImplicitParam(name="vo",value="用户对象")
	@RequiresPermissions("system/user:edit")
	@PostMapping("/updateByVO")
	public @ResponseBody ResultVO updateByVO(@RequestBody UserVO userVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(userService.doUpdateUserRoles(userVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 删除用户，同时删除账号及用户角色
	 */
	@ApiOperation(value="根据主键删除用户（包括账户和角色）",notes="删除")
	@ApiImplicitParam(name="id",value="用户主键")
	@RequiresPermissions("system/user:delete")
	@PostMapping("/deleteByList")
	public @ResponseBody ResultVO deleteByList(@RequestBody List<UserVO> list){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(userService.doDeleteUserRoles(list));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 根据机构ID查询用户
	 */
	@ApiOperation(value="根据机构ID查询用户信息",notes="列表信息")
	@ApiImplicitParam(name="jgid",value="机构iD")
	@GetMapping("/findByJGID/{jgid}")
	public @ResponseBody ResultVO findByJGID(@PathVariable String jgid){
		ResultVO resultVO = ResultVO.build();
		try{
			UserVO userVO = new UserVO();
			userVO.setOrganizationId(jgid);
			resultVO.setResult(userService.doSearchListByVO(userVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}
}
