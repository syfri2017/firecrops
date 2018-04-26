package com.syfri.digitalplan.controller.auxiliarydecision.firecalculation;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import com.syfri.digitalplan.dao.auxiliarydecision.firecalculation.FirecalculationlistDAO;
import com.syfri.digitalplan.model.auxiliarydecision.firecalculation.FirecalculationparamVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.syfri.digitalplan.model.auxiliarydecision.firecalculation.FirecalculationlistVO;
import com.syfri.digitalplan.service.auxiliarydecision.firecalculation.FirecalculationlistService;
import com.syfri.baseapi.controller.BaseController;
import java.util.List;

@RestController
@RequestMapping("firecalculationlist")
public class FirecalculationlistController  extends BaseController<FirecalculationlistVO>{

	@Autowired
	protected Environment environment;

	@Autowired
	private FirecalculationlistService firecalculationlistService;

	@Override
	public FirecalculationlistService getBaseService() {
		return this.firecalculationlistService;
	}

	@Autowired
	private FirecalculationlistDAO firecalculationlistDAO;

	@ModelAttribute
	public void Model(Model model){
		if (environment.containsProperty("server.context-path")) {
			model.addAttribute("contextPath", environment.getProperty("server.context-path"));
		}else{
			model.addAttribute("contextPath", "/");
		}
	}

	@GetMapping("")
	public String getFirecalculationlist(Model model, @RequestParam(value="index") String index){
		model.addAttribute("index", index);
		return "auxiliarydecision/firecalculation_list";
	}

	/**
	 * @Description: 根据条件获取计算信息
	 * @Param: [firecalculationlistVO]
	 * @Return: com.syfri.baseapi.model.ResultVO
	 * @Author: dongbo
	 * @Modified By:
	 * @Date: 2018/4/24 16:26
	 */
	@ApiOperation(value="根据条件获取计算信息",notes="列表信息")
	@ApiImplicitParam(name="vo",value="计算信息对象")
	@PostMapping("/findByVO")
	public @ResponseBody
	ResultVO findByVO(@RequestBody FirecalculationlistVO firecalculationlistVO){
		ResultVO resultVO = ResultVO.build();
		try{
			List<FirecalculationlistVO> result = firecalculationlistService.doFindlist(firecalculationlistVO);
			resultVO.setResult(result);
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	@ApiOperation(value="根据条件更新计算信息",notes="列表信息")
	@ApiImplicitParam(name="vo",value="计算信息对象")
	@PostMapping("/updateByVO")
	public @ResponseBody
	ResultVO updateByVO(@RequestBody FirecalculationlistVO firecalculationlistVO){
		ResultVO resultVO = ResultVO.build();
		try{
			int result = firecalculationlistService.doUpdateJsgsCs(firecalculationlistVO);
			resultVO.setResult(result);
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 新增公式，同时新增其参数信息
	 */
	@ApiOperation(value="根据公式新增公式及其参数信息",notes="新增")
	@ApiImplicitParam(name="vo",value="公式对象")
	@RequiresPermissions("Firecalculationlist:add")
	@PostMapping("/insertByVO")
	public @ResponseBody ResultVO insertByVO(@RequestBody FirecalculationlistVO firecalculationlistVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(firecalculationlistService.doInsertJsgsCs(firecalculationlistVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}


	@ApiOperation(value="根据公式名查询公式数量",notes="查询")
	@ApiImplicitParam(name="gsmc",value="公式名")
	@GetMapping("/getNum/{gsmc}")
	public @ResponseBody ResultVO getNum(@PathVariable String gsmc){
		ResultVO resultVO = ResultVO.build();
		try{
			FirecalculationlistVO firecalculationlistVO = new FirecalculationlistVO();
			firecalculationlistVO.setGsmc(gsmc);
			if(firecalculationlistService.doSearchListByVO(firecalculationlistVO).size() == 0){
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
	 * @Description: 根据id获取计算信息
	 * @Param: [uuid]
	 * @Return: com.syfri.baseapi.model.ResultVO
	 * @Author: dongbo
	 * @Modified By:
	 * @Date: 2018/4/24 16:28
	 */
	@ApiOperation(value="根据id获取计算信息",notes="列表信息")
	@GetMapping("/doFindById/{uuid}")
	public @ResponseBody ResultVO getDetail(@PathVariable String uuid){
		ResultVO resultVO = ResultVO.build();
		try{
			List<FirecalculationparamVO> result = firecalculationlistService.doFindCsById(uuid);
			resultVO.setResult(result);
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
	@RequiresPermissions("gsmc:delete")
	@PostMapping("/deleteByIds")
	public @ResponseBody ResultVO deleteByIds(@RequestBody String id){
		JSONObject jsonObject = JSON.parseObject(id);
		JSONArray ids = jsonObject.getJSONArray("ids");
		ResultVO resultVO = ResultVO.build();
		try{
			for(int i=0;i<ids.size();i++){
				String uuid = (String)ids.get(i);
				firecalculationlistService.doDeleteJsgsCs(uuid);
			}
			resultVO.setMsg("删除成功");
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}
}
