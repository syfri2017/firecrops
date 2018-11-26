package com.syfri.userservice.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import com.syfri.userservice.model.*;
import com.syfri.userservice.utils.CurrentUserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.syfri.userservice.service.CodelistService;
import com.syfri.baseapi.controller.BaseController;

import java.util.List;

@Api(value = "代码集",tags = "代码集API",description = "代码集")
@Controller
@RequestMapping("codelist")
public class CodelistController  extends BaseController<CodelistVO>{

	@Autowired
	private CodelistService codelistService;

	@Autowired
	protected Environment environment;

	@Override
	public CodelistService getBaseService() {
		return this.codelistService;
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
	public String getCodelist(Model model, @RequestParam(value="index") String index){
		model.addAttribute("index", index);
		return "system/codelist_list";
	}

	/**
	 * 查询代码集
	 */
	@ApiOperation(value="根据代码集查询代码集",notes="列表信息")
	@ApiImplicitParam(name="vo",value="代码集对象")
	@RequiresPermissions("system/codelist:list")
	@PostMapping("/findByVO")
	public @ResponseBody ResultVO findByVO(@RequestBody CodelistVO codelistVO){
		ResultVO resultVO = ResultVO.build();
		try{
			PageHelper.startPage(codelistVO.getPageNum(),codelistVO.getPageSize());
			List<CodelistVO> list = codelistService.doFindCodelist(codelistVO);
			PageInfo<CodelistVO> pageInfo = new PageInfo<>(list);
			resultVO.setResult(pageInfo);
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 新增代码集
	 */
	@ApiOperation(value="根据代码集新增代码集",notes="新增")
	@ApiImplicitParam(name="vo",value="代码集对象")
	@RequiresPermissions("system/codelist:add")
	@PostMapping("/insertByVO")
	public @ResponseBody ResultVO insertByVO(@RequestBody CodelistVO codelistVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(codelistService.doInsertCodelist(codelistVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 修改代码集
	 */
	@ApiOperation(value="根据代码集修改代码集",notes="修改")
	@ApiImplicitParam(name="vo",value="代码集对象")
	@RequiresPermissions("system/codelist:edit")
	@PostMapping("/updateByVO")
	public @ResponseBody ResultVO updateByVO(@RequestBody CodelistVO codelistVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(codelistService.doUpdateCodelist(codelistVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 删除代码集
	 */
	@ApiOperation(value="根据主键删除代码集代码集",notes="删除")
	@ApiImplicitParam(name="id",value="代码集主键")
	@RequiresPermissions("system/codelist:delete")
	@PostMapping("/deleteByList")
	public @ResponseBody ResultVO deleteByList(@RequestBody List<CodelistVO> list){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(codelistService.doDeleteCodelist(list));;
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 根据代码集类型查询代码集数量
	 */
	@ApiOperation(value="根据代码集类型查询代码集数量",notes="查询")
	@ApiImplicitParam(name="codetype",value="权限名")
	@GetMapping("/getNum/{codetype}")
	public @ResponseBody ResultVO getNum(@PathVariable String codetype){
		ResultVO resultVO = ResultVO.build();
		try{
			CodelistVO codelistVO = new CodelistVO();
			codelistVO.setCodetype(codetype);
			if(codelistService.doSearchListByVO(codelistVO).size() == 0){
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

	/**
	 * 跳转到代码集详情页
	 */
	@ApiOperation(value="跳转到代码集详情页",notes="页面跳转")
	@GetMapping("/detail/{codeid}")
	public String getDetailPage(Model model, @PathVariable String codeid,  @RequestParam(value="index") String index){
		model.addAttribute("index", index);
		model.addAttribute("codeid", codeid);
		return "system/codelist_detail_list";
	}

	/**
	 * 获取所有的代码集类型
	 */
	@ApiOperation(value="获取所有的代码集类型",notes="列表信息")
	@RequiresPermissions("system/codelist:list")
	@PostMapping("/detail/doFindByCodeid")
	public @ResponseBody ResultVO getDetail(@RequestBody CodelistDetailVO codelistDetailVO){
		ResultVO resultVO = ResultVO.build();
		try{
			PageHelper.startPage(codelistDetailVO.getPageNum(),codelistDetailVO.getPageSize());
			List<CodelistDetailVO> list= codelistService.doFindDetail(codelistDetailVO.getCodeid());
			PageInfo<CodelistDetailVO> pageInfo = new PageInfo<>(list);
			resultVO.setResult(pageInfo);
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 根据代码集获取资源信息
	 */
	@ApiOperation(value="根据代码值、代码名称查询代码值",notes="列表信息")
	@ApiImplicitParam(name="vo",value="代码集详细对象")
	@RequiresPermissions("system/codelist:list")
	@PostMapping("/detail/findByVO")
	public @ResponseBody ResultVO findByVO(@RequestBody CodelistDetailVO codelistDetailVO){
		ResultVO resultVO = ResultVO.build();
		try{
			PageHelper.startPage(codelistDetailVO.getPageNum(),codelistDetailVO.getPageSize());
			List<CodelistDetailVO> list= codelistService.doFindCodelistDetail(codelistDetailVO);
			PageInfo<CodelistDetailVO> pageInfo = new PageInfo<>(list);
			resultVO.setResult(pageInfo);
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 新增代码值
	 */
	@ApiOperation(value="根据代码集新增代码值",notes="新增")
	@ApiImplicitParam(name="vo",value="代码值对象")
	@RequiresPermissions("system/codelist:add")
	@PostMapping("/detail/insertByVO")
	public @ResponseBody ResultVO insertByVO(@RequestBody CodelistDetailVO codelistDetailVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(codelistService.doInsertCodelistDetail(codelistDetailVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 修改代码值
	 */
	@ApiOperation(value="根据代码集修改代码值",notes="修改")
	@ApiImplicitParam(name="vo",value="代码值对象")
	@RequiresPermissions("system/codelist:edit")
	@PostMapping("/detail/updateByVO")
	public @ResponseBody ResultVO updateByVO(@RequestBody CodelistDetailVO codelistDetailVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(codelistService.doUpdateCodelistDetail(codelistDetailVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 删除代码值
	 */
	@ApiOperation(value="根据主键删除代码集代码值",notes="删除")
	@ApiImplicitParam(name="id",value="代码值主键")
	@RequiresPermissions("system/codelist:delete")
	@PostMapping("/detail/deleteByList")
	public @ResponseBody ResultVO deleteDetailByList(@RequestBody List<CodelistDetailVO> list){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(codelistService.doDeleteCodelistDetail(list));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 根据代码集类型查询代码集数量
	 */
	@ApiOperation(value="根据代码集类型查询代码集数量",notes="查询")
	@ApiImplicitParam(name="codeValue",value="权限名")
	@GetMapping("/detail/getNum/{codeid}/{codevalue}")
	public @ResponseBody ResultVO getDetailNum(@PathVariable String codeid, @PathVariable String codevalue){
		ResultVO resultVO = ResultVO.build();
		try{
			CodelistDetailVO codelistDetailVO = new CodelistDetailVO();
			codelistDetailVO.setCodeValue(codevalue);
			codelistDetailVO.setCodeid(codeid);
			resultVO.setResult(codelistService.doFindByCodelistDetailNum(codelistDetailVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 根据代码集类型查询代码项
	 */
	@ApiOperation(value="根据代码集类型查询代码项",notes="查询")
	@ApiImplicitParam(name="codetype",value="代码类型")
	@GetMapping("/getCodetype/{codetype}")
	public @ResponseBody ResultVO getCodeType(@PathVariable String codetype){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(codelistService.doFindCodelistByType(codetype));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 根据代码集类型查询代码项，按数字大小排序
	 */
	@ApiOperation(value="根据代码集类型查询代码项，按数字大小排序",notes="查询")
	@ApiImplicitParam(name="codetype",value="代码类型")
	@GetMapping("/getCodeTypeOrderByNum/{codetype}")
	public @ResponseBody ResultVO getCodeTypeOrderByNum(@PathVariable String codetype){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(codelistService.doFindCodelistByTypeOrderByNum(codetype));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 根据代码类型获取树状资源
	 * 要求：子类别是父类别代码值的扩充
	 * 如：01,0101,0102
	 * 目前只级联两级
	 */
	@ApiOperation(value="根据代码类型获取树状资源",notes="查询")
	@ApiImplicitParam(name="vo",value="树状资源参数对象")
	@PostMapping("/getCodelisttree")
	public @ResponseBody ResultVO getCodelisttree(@RequestBody CodelistParams codelistParams){
		ResultVO resultVO = ResultVO.build();
		try{
			List<CodelistTree> result = codelistService.doFindCodelistTreeByType(codelistParams);
			resultVO.setResult(result);
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 根据代码类型获取树状资源2
	 * 要求：所有类别的code_value的字段长度相同
	 * 如：010000,010100,010200,010201
	 * 目前最多可级联五级，六级不划分
	 */
	@ApiOperation(value="根据代码类型获取树状资源2",notes="查询")
	@ApiImplicitParam(name="vo",value="树状资源参数对象")
	@PostMapping("/getCodelisttree2")
	public @ResponseBody ResultVO getCodelisttree2(@RequestBody CodelistParams codelistParams){
		ResultVO resultVO = ResultVO.build();
		try{
			List<CodelistTree> result = codelistService.doFindCodelistTreeByType2(codelistParams);
			resultVO.setResult(result);
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/*
	 * @Description:查询队站类型树状资源，只加载其他消防队伍的子级，其余只保留父级
	 * @Author: yushch
	 * @Modified By:
	 * @Date: 20180423
	 */
	@ApiOperation(value="查询队站类型树状资源",notes="查询")
	@ApiImplicitParam(name="codetype",value="代码类型")
	@GetMapping("/getDzlxTree/{codetype}")
	public @ResponseBody ResultVO getDzlxTree(@PathVariable String codetype){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(codelistService.doFindDzlxCodelisttree(codetype));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/*
	 * @Description:查询行政区划，只保留31个省
	 * @Author: yushch
	 * @Modified By:
	 * @Date: 20180516
	 */
	@ApiOperation(value="查询行政区划",notes="查询")
	@ApiImplicitParam(name="codetype",value="代码类型")
	@GetMapping("/getXzqh/{codetype}")
	public @ResponseBody ResultVO getXzqh(@PathVariable String codetype){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(codelistService.doFindXzqhCodelist(codetype));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	@ApiOperation(value="查询行政区划",notes="查询")
	@ApiImplicitParam(name="codetype",value="代码类型")
	@GetMapping("/getXzqhTreeByUser")
	public @ResponseBody ResultVO getXzqhTreeByUser(){
		ResultVO resultVO = ResultVO.build();
		try{
			OrganizationVO organizationVO = CurrentUserUtil.getCurrentUser().getOrganizationVO();
			resultVO.setResult(codelistService.getXzqhTreeByUser(organizationVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * @Description: 查询燃烧物质树状资源，数据结构为（1，2）
	 * @Param: [codetype]
	 * @Return: com.syfri.baseapi.model.ResultVO
	 * @Author: liurui
	 * @Modified By:
	 * @Date: 2018/6/9 16:40
	 */
	@ApiOperation(value="查询燃烧物质树状资源",notes="查询")
	@ApiImplicitParam(name="codetype",value="代码类型")
	@GetMapping("/getRswzTree/{codetype}")
	public @ResponseBody ResultVO getRswzTree(@PathVariable String codetype){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(codelistService.doFindRswzCodelisttree(codetype));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	@ApiOperation(value="查询药剂类型树状资源",notes="查询")
	@ApiImplicitParam(name="codetype",value="代码类型")
	@RequestMapping("/getYjlxTree/{codetype}")
	public @ResponseBody ResultVO getYjlxTree(@PathVariable String codetype){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(codelistService.doFindYjlxCodelisttree(codetype));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	@ApiOperation(value="查询泡沫液类型树状资源",notes="查询")
	@ApiImplicitParam(name="codetype",value="代码类型")
	@RequestMapping("/getPmylxTree/{codetype}")
	public @ResponseBody ResultVO getPmylxTree(@PathVariable String codetype){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(codelistService.doFindPmylxlisttree(codetype));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}
}
