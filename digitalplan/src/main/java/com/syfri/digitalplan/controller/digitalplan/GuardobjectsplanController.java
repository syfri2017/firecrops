package com.syfri.digitalplan.controller.digitalplan;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import com.syfri.digitalplan.utils.Base64ImageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.syfri.digitalplan.model.digitalplan.GuardobjectsplanVO;
import com.syfri.digitalplan.service.digitalplan.GuardobjectsplanService;
import com.syfri.baseapi.controller.BaseController;

import java.util.List;

@Api(value = "消防保卫警卫预案",tags = "消防保卫警卫预案API",description = "GuardobjectsplanController")
@RestController
@RequestMapping("xfbwjw")
public class GuardobjectsplanController extends BaseController<GuardobjectsplanVO>{

	private static final Logger logger  = LoggerFactory.getLogger(GuardobjectsplanController.class);

	@Autowired
	protected Environment environment;

	@Autowired
	private GuardobjectsplanService guardobjectsplanService;

	@Override
	public GuardobjectsplanService getBaseService() {
		return this.guardobjectsplanService;
	}
	/**
	 * @Description:
	 * @Param: [model]
	 * @Return: void
	 * @Author: zhaijianchen
	 * @Modified By:
	 * @Date: 2018/4/20 16:37
	 */

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
		return "digitalplan/xfbwjw_list";
	}


	/**
	 * 根据条件获取防消保卫警卫预案信息
	 */
	@ApiOperation(value="根据VO获取消防保卫警卫预案信息",notes="列表信息")
	@ApiImplicitParam(name="vo",value="消防保卫警卫预案VO")
	@PostMapping("/findBwjwplanList")
	public @ResponseBody ResultVO findBwjwplanList(@RequestBody GuardobjectsplanVO guardobjectsplanVO){
		ResultVO resultVO = ResultVO.build();
		try{
			PageHelper.startPage(guardobjectsplanVO.getPageNum(),guardobjectsplanVO.getPageSize());
			List<GuardobjectsplanVO> list = guardobjectsplanService.doFindXfbwjwlist(guardobjectsplanVO);
			PageInfo<GuardobjectsplanVO> pageInfo = new PageInfo<>(list);
			resultVO.setResult(pageInfo);
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}


	/**
	 * 根据id获取消防保卫警卫预案信息
	 */
	@ApiOperation(value="根据id获取消防保卫警卫预案信息",notes="详情")
	@GetMapping("/doFindById/{pkid}")
	public @ResponseBody ResultVO getDetail(@PathVariable String pkid){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(guardobjectsplanService.doFindDetailById(pkid));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

}
