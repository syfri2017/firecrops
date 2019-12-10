package com.syfri.digitalplan.controller.auxiliarydecision.danger;

import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import com.syfri.digitalplan.dao.auxiliarydecision.danger.DangerDAO;
import com.syfri.digitalplan.utils.Base64ImageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.syfri.digitalplan.model.auxiliarydecision.danger.DangerVO;
import com.syfri.digitalplan.service.auxiliarydecision.danger.DangerService;
import com.syfri.baseapi.controller.BaseController;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 获取路径Controller
 * by dongbo 2018/03/26
 */

@Api(value = "化学危险品",tags = "化学危险品API",description = "DangerController")
@RestController
@RequestMapping("danger")
public class DangerController  extends BaseController<DangerVO>{

	@Autowired
	protected Environment environment;

	@Autowired
	private DangerService dangerService;

	@Autowired
	private DangerDAO dangerDAO;

	@Override
	public DangerService getBaseService() {
		return this.dangerService;
	}

	/**
	 * @Description: 附件上传
	 * @Param: [request]
	 * @Return: java.util.Map<java.lang.String,java.lang.Object>
	 * @throws UnsupportedEncodingException
	 * @Author: dongbo
	 * @Modified By:
	 * @Date: 2018/4/20 16:44
	 */
	@ApiOperation(value="上传化学危险品照片",notes="上传")
	@ApiImplicitParam(name="vo",value="Request")
	@PostMapping("/insertImage")
	public @ResponseBody Map<String, Object> uploadAttachment(HttpServletRequest request)
			throws UnsupportedEncodingException {
		Map<String, Object> result = new HashMap<String, Object>();
		int res = 0;
		try {
			byte[] buffer = null;
			File file = new File("d:/1.png");
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1)
			{
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();

			DangerVO dangerVO=new DangerVO();
			dangerVO.setFlagFile(buffer);

			dangerDAO.doInsertImg(dangerVO);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * @Description: 化危品新增
	 * @Param: [dangerVO]
	 * @Return: com.syfri.baseapi.model.ResultVO
	 * @Author: liurui
	 * @Modified By:
	 * @Date: 2018/6/22 14:13
	 */
	@ApiOperation(value="根据VO新增化学危险品",notes="新增")
	@ApiImplicitParam(name="vo",value="化学危险品VO")
	@RequiresPermissions("auxiliarydecision/danger:add")
	@PostMapping("/insertByVO")
	public @ResponseBody ResultVO insertByVO(@RequestBody DangerVO dangerVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(dangerService.doInsertByVO(dangerVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	@ApiOperation(value="根据VO删除化学危险品",notes="删除")
	@ApiImplicitParam(name="vo",value="化学危险品VO")
	@RequiresPermissions("auxiliarydecision/danger:delete")
	@PostMapping("/doDeleteDanger")
	public @ResponseBody ResultVO doDeleteDanger(@RequestBody List<DangerVO> dangerList) {
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(dangerService.doDeleteDanger(dangerList));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	@ApiOperation(value="根据VO修改化学危险品",notes="修改")
	@ApiImplicitParam(name="vo",value="化学危险品VO")
	@RequiresPermissions("auxiliarydecision/danger:edit")
	@PostMapping("/doUpdateDanger")
	public @ResponseBody ResultVO doUpdateDanger(@RequestBody DangerVO dangerVO) {
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(dangerService.doUpdateDanger(dangerVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	@ApiOperation(value="判断化危品名称是否已存在",notes="列表信息")
	@ApiImplicitParam(name="vo",value="化危品VO")
	@PostMapping("/doCheckName")
	public @ResponseBody ResultVO doCheckName(@RequestBody DangerVO dangerVO) {
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(dangerService.doCheckName(dangerVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}
}
