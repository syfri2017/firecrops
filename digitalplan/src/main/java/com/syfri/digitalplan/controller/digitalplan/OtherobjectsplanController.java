package com.syfri.digitalplan.controller.digitalplan;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import com.syfri.digitalplan.model.digitalplan.OtherobjectsplanVO;
import com.syfri.digitalplan.service.digitalplan.OtherobjectsplanService;
import com.syfri.baseapi.controller.BaseController;

import java.util.List;

@Api(value = "其他对象预案管理",tags = "其他对象预案管理API",description = "OtherobjectsplanController")
@RestController
@RequestMapping("otherobjectsplan")
public class OtherobjectsplanController extends BaseController<OtherobjectsplanVO>{

	private static final Logger logger  = LoggerFactory.getLogger(OtherobjectsplanController.class);

	@Autowired
	protected Environment environment;

	@Autowired
	private OtherobjectsplanService otherobjectsplanService;

	@Override
	public OtherobjectsplanService getBaseService() {
		return this.otherobjectsplanService;
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
		return "digitalplan/otherobjectsplan";
	}

	/**
	 * 根据条件获取预案信息
	 */
	@ApiOperation(value="根据VO获取其他对象预案列表",notes="列表信息")
	@ApiImplicitParam(name="vo",value="其他对象预案VO")
	@PostMapping("/findByVO")
	public @ResponseBody ResultVO findByVO(@RequestBody OtherobjectsplanVO otherobjectsplanVO){
		ResultVO resultVO = ResultVO.build();
		try{
			PageHelper.startPage(otherobjectsplanVO.getPageNum(),otherobjectsplanVO.getPageSize());
			List<OtherobjectsplanVO> list = otherobjectsplanService.doSearchListByVO(otherobjectsplanVO);
			PageInfo<OtherobjectsplanVO> pageInfo = new PageInfo<>(list);
			resultVO.setResult(pageInfo);
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 根据id获取预案信息
	 */
	@ApiOperation(value="根据ID获取其他对象预案详细信息",notes="详情")
	@ApiImplicitParam(name="ID",value="其他对象预案ID")
	@GetMapping("/doFindById/{uuid}")
	public @ResponseBody ResultVO getDetail(@PathVariable String uuid){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(otherobjectsplanService.doFindById(uuid));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}
}
