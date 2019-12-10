package com.syfri.digitalplan.controller.yafjxz;

import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.syfri.digitalplan.model.yafjxz.YaxxzlVO;
import com.syfri.digitalplan.service.yafjxz.YaxxzlService;
import com.syfri.baseapi.controller.BaseController;

@Api(value = "历史预案下载" ,tags = "历史预案下载API" ,description = "yaxxzl")
@RestController
@RequestMapping("yaxxzl")
public class YaxxzlController  extends BaseController<YaxxzlVO>{

	@Autowired
	private YaxxzlService yaxxzlService;

	@Override
	public YaxxzlService getBaseService() {
		return this.yaxxzlService;
	}

	@ApiOperation(value="通过yaid获取历史预案附件的url信息",notes="查询url信息")
	@ApiImplicitParam(name="vo",value="历史预案对象")
	@PostMapping("/getUrl")
	public @ResponseBody
	ResultVO getUrl(@RequestBody YaxxzlVO yaxxzlVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(yaxxzlService.doSearchListByVO(yaxxzlVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

}
