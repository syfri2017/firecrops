package com.syfri.digitalplan.controller.ewbh;

import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.syfri.digitalplan.model.ewbh.EwbhVO;
import com.syfri.digitalplan.service.ewbh.EwbhService;
import com.syfri.baseapi.controller.BaseController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(value = "二维标绘" ,tags = "二维标绘API" ,description = "EwbhController")
@RestController
@RequestMapping("ewbh")
public class EwbhController  extends BaseController<EwbhVO>{

	@Autowired
	private EwbhService ewbhService;

	@Override
	public EwbhService getBaseService() {
		return this.ewbhService;
	}

	/**
	 * @Description: 新增标绘
	 * @Author: liurui
	 * @Date: 2018/9/29 15:49
	 */
	@ApiOperation(value="通过vo新增标绘",notes="新增")
	@ApiImplicitParam(name="vo",value="二维标绘VO")
	@PostMapping(value = "/save")
	@ResponseBody
	public ResultVO save(HttpServletResponse response, EwbhVO ewbhVO) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(this.getBaseService().doInsertByVO(ewbhVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * @Description: 查询标绘VO
	 * @Author: liurui
	 * @Date: 2018/9/30 12:49
	 */
	@ApiOperation(value="通过uuid查询标绘",notes="查询列表")
	@ApiImplicitParam(name="vo",value="二维标绘VO")
	@PostMapping(value = "/findByUuid")
	@ResponseBody
	public ResultVO findByUuid(HttpServletResponse response, EwbhVO ewbhVO) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(this.getBaseService().doFindById(ewbhVO.getUuid()));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
		}
		return resultVO;
	}

	/**
	 * @Description: 更新标绘
	 * @Author: liurui
	 * @Date: 2018/9/30 12:49
	 */
	@ApiOperation(value="通过二维标会VO编辑标绘",notes="编辑")
	@ApiImplicitParam(name="vo",value="二维标绘VO")
	@PostMapping(value = "/edit")
	@ResponseBody
	public ResultVO edit(HttpServletResponse response, EwbhVO ewbhVO) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(this.getBaseService().doUpdateByVO(ewbhVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
		}
		return resultVO;
	}

    @ApiOperation(value="删除标绘",notes="删除")
    @ApiImplicitParam(name="list",value="二维标绘list")
    @PostMapping("/doDeleteEquipment")
    public @ResponseBody ResultVO doDeleteEquipment(@RequestBody List<EwbhVO> ewbhVOList) {
        ResultVO resultVO = ResultVO.build();
        try{
            resultVO.setResult(ewbhService.doDeleteEwbhVOList(ewbhVOList));
        }catch(Exception e){
            logger.error("{}",e.getMessage());
            resultVO.setCode(EConstants.CODE.FAILURE);
        }
        return resultVO;
    }

}
