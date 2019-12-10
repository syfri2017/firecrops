package com.syfri.digitalplan.controller.planobject;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import com.syfri.digitalplan.model.planobject.GuardobjectsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import com.syfri.digitalplan.service.planobject.GuardobjectsService;
import com.syfri.baseapi.controller.BaseController;

import java.util.List;

@Api(value = "保卫警卫管理" ,tags = "保卫警卫管理API" ,description = "GuardobjectsController")
@RestController
@RequestMapping("bwjwplan")
public class GuardobjectsController extends BaseController<GuardobjectsVO>{

	@Autowired
	private GuardobjectsService guardobjectsService;



	@Override
	public GuardobjectsService getBaseService() {
		return this.guardobjectsService;
	}

	@Autowired
	protected Environment environment;

	@ModelAttribute
	public void Model(Model model){
		if (environment.containsProperty("server.context-path")) {
			model.addAttribute("contextPath", environment.getProperty("server.context-path"));
		}else{
			model.addAttribute("contextPath", "/");
		}
	}

	@GetMapping("")
	public String getBwjwplanlist(Model model, @RequestParam(value="index") String index){
		model.addAttribute("index", index);
		return "planobject/bwjwplan_list";
	}

	/**
	 * @Description:
	 * @Param: [keyunitVO]
	 * @Return: com.syfri.baseapi.model.ResultVO
	 * @Author: zhaijianchen
	 * @Modified By:
	 * @Date: 2018/4/28 8:39
	 */
	@ApiOperation(value="根据条件查询保卫警卫",notes="列表信息")
	@ApiImplicitParam(name="vo",value="保卫警卫VO")

	@PostMapping("/findBwjwList")
	public @ResponseBody ResultVO findByVO(@RequestBody GuardobjectsVO guardobjectsVO){
		ResultVO resultVO = ResultVO.build();
		try{
			PageHelper.startPage(guardobjectsVO.getPageNum(),guardobjectsVO.getPageSize());
			List<GuardobjectsVO> list = guardobjectsService.doFindBwjwplanlist(guardobjectsVO);
			PageInfo<GuardobjectsVO> pageInfo = new PageInfo<>(list);
			resultVO.setResult(pageInfo);
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/***
	 * @Description:
	 * @Param: [model, pkid]
	 * @Return: java.lang.String
	 * @Author: zhaijianchen
	 * @Modified By:
	 * @Date: 2018/4/28 8:40
	 */
	@ApiOperation(value="跳转到重点单位详情页",notes="页面跳转")
	@ApiImplicitParam(name="pkid",value="保卫警卫对象pkid")
	@GetMapping("/detail/{pkid}")
	public String getDetailPage(Model model, @PathVariable String pkid){

		model.addAttribute("pkid", pkid);
		return "planobject/bwjwplan_detail";
	}

	/***
	 * @Description:
	 * @Param: [pkid]
	 * @Return: com.syfri.baseapi.model.ResultVO
	 * @Author: zhaijianchen
	 * @Modified By:
	 * @Date: 2018/4/28 8:40
	 */
	@ApiOperation(value="获取保卫警卫详情",notes="列表信息")
	@ApiImplicitParam(name="uuid",value="保卫警卫对象uuid")
	@GetMapping("/doFindDetailById/{uuid}")
	public @ResponseBody ResultVO getDetail(@PathVariable String uuid){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(guardobjectsService.doFindDetailById(uuid));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}



}
