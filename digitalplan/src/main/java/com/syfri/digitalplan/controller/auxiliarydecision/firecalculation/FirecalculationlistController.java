package com.syfri.digitalplan.controller.auxiliarydecision.firecalculation;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import com.syfri.digitalplan.dao.auxiliarydecision.firecalculation.FirecalculationlistDAO;
import com.syfri.digitalplan.model.auxiliarydecision.firecalculation.FirecalculationparamVO;
import com.syfri.digitalplan.utils.Calculator;
import io.swagger.annotations.Api;
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

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.List;

@Api(value = "火场计算",tags = "火场计算API",description = "FirecalculationlistController")
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
	@ApiOperation(value="根据获取火场计算VO获取列表",notes="列表信息")
	@ApiImplicitParam(name="vo",value="火场计算VO")
	@PostMapping("/findByVO")
	public @ResponseBody
	ResultVO findByVO(@RequestBody FirecalculationlistVO firecalculationlistVO){
		ResultVO resultVO = ResultVO.build();
		try{
			PageHelper.startPage(firecalculationlistVO.getPageNum(),firecalculationlistVO.getPageSize());
			List<FirecalculationlistVO> list = firecalculationlistService.doFindlist(firecalculationlistVO);
			PageInfo<FirecalculationlistVO> pageInfo = new PageInfo<>(list);
 			resultVO.setResult(pageInfo);
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
	@ApiOperation(value="根据火场计算VO进行计算",notes="结果")
	@ApiImplicitParam(name="vo",value="火场计算VO")
	@PostMapping("/doCalculate")
	public @ResponseBody
	Object fireCalculation(@RequestBody FirecalculationlistVO firecalculationlistVO){

		try{
			String jsgs = firecalculationlistVO.getJsgs();
			List<FirecalculationparamVO> paramslist = firecalculationlistVO.getFirecalculationparams();
			String[] params=new String[paramslist.size()];

			for(int i=0;i<paramslist.size();i++){
				params[i]=paramslist.get(i).getMrz();
			}
			String result = Calculator.string2Calculation(jsgs, params);
			String resultString=jse.eval(result.toString()).toString();
			Double resultStr=Double.parseDouble(resultString);
			return resultStr;
		}catch(Exception e){
			logger.error("{}",e.getMessage());
		}
		return null;
	}

	/**
	 * @Description: 根据条件更新计算信息
	 * @Param: [firecalculationlistVO]
	 * @Return: com.syfri.baseapi.model.ResultVO
	 * @Author: dongbo
	 * @Modified By:
	 * @Date: 2018/4/27 9:52
	 */
	@ApiOperation(value="根据VO修改火场计算公式",notes="修改")
	@ApiImplicitParam(name="vo",value="火场计算VO")
	@RequiresPermissions("auxiliarydecision/firecalculation:edit")
	@PostMapping("/updateByVO")
	public @ResponseBody
	ResultVO updateByVO(@RequestBody FirecalculationlistVO firecalculationlistVO){
		ResultVO resultVO = ResultVO.build();
		try{
			String jsgs = firecalculationlistVO.getJsgs();
			List<FirecalculationparamVO> paramslist = firecalculationlistVO.getFirecalculationparams();
			String[] params=new String[paramslist.size()];

			for(int i=0;i<paramslist.size();i++){
				params[i]=paramslist.get(i).getMrz();
			}
			if(Calculator.isCalculationParams(jsgs, params)) {
				int result = firecalculationlistService.doUpdateJsgsCs(firecalculationlistVO);
				resultVO.setResult(result);
			}
			else{
				resultVO.setMsg("计算公式中参数与参数信息中参数个数不符!请重新输入。");
			}
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	* @Description: 根据公式新增公式及其参数信息
	* @Param: [firecalculationlistVO]
	* @Return: com.syfri.baseapi.model.ResultVO
	* @Author: dongbo
	* @Modified By:
	* @Date: 2018/4/27 9:52
	*/
	@ApiOperation(value="根据VO新增火场计算公式",notes="新增")
	@ApiImplicitParam(name="vo",value="火场计算VO")
	@RequiresPermissions("auxiliarydecision/firecalculation:add")
	@PostMapping("/insertByVO")
	public @ResponseBody ResultVO insertByVO(@RequestBody FirecalculationlistVO firecalculationlistVO){
		ResultVO resultVO = ResultVO.build();
		try{
			String jsgs = firecalculationlistVO.getJsgs();
			List<FirecalculationparamVO> paramslist = firecalculationlistVO.getFirecalculationparams();
			String[] params=new String[paramslist.size()];

			for(int i=0;i<paramslist.size();i++){
				params[i]=paramslist.get(i).getMrz();
			}
			if(Calculator.isCalculationParams(jsgs, params)) {
				resultVO.setResult(firecalculationlistService.doInsertJsgsCs(firecalculationlistVO));
			}
			else{
				resultVO.setMsg("计算公式中参数与参数信息中参数个数不符!请重新输入。");
			}
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * @Description: 根据公式名查询公式数量
	 * @Param: [gsmc]
	 * @Return: com.syfri.baseapi.model.ResultVO
	 * @Author: dongbo
	 * @Modified By:
	 * @Date: 2018/4/27 9:53
	 */
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
	@ApiOperation(value="根据id获取火场计算信息",notes="详情")
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
	 * @Description: 根据主键删除公式及其参数信息
	 * @Param: [id]
	 * @Return: com.syfri.baseapi.model.ResultVO
	 * @Author: dongbo
	 * @Modified By:
	 * @Date: 2018/4/27 9:53
	 */
	@ApiOperation(value="根据主键删除公式及其参数信息",notes="删除")
	@ApiImplicitParam(name="id",value="火场计算公式主键")
	@RequiresPermissions("auxiliarydecision/firecalculation:delete")
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

	/**
	 * @Description: 根据条件更新是否启用状态
	 * @Param: [firecalculationlistVO]
	 * @Return: com.syfri.baseapi.model.ResultVO
	 * @Author: dongbo
	 * @Modified By:
	 * @Date: 2018/4/27 9:52
	 */
	@ApiOperation(value="根据条件更新是否启用状态",notes="列表信息")
	@ApiImplicitParam(name="vo",value="火场计算VO")
	@PostMapping("/updateBySfqy")
	public @ResponseBody
	ResultVO updateBySfqy(@RequestBody FirecalculationlistVO firecalculationlistVO){
		ResultVO resultVO = ResultVO.build();
		try{
			int result = firecalculationlistService.doUpdateSfqy(firecalculationlistVO);
			resultVO.setResult(result);
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}
}
