package com.syfri.digitalplan.controller.jxcsfjxz;

import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import com.syfri.digitalplan.config.properties.JxcsfjxzProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.syfri.digitalplan.model.jxcsfjxz.JxcsfjxzVO;
import com.syfri.digitalplan.service.jxcsfjxz.JxcsfjxzService;
import com.syfri.baseapi.controller.BaseController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

/******************
 @author by yushch
 @date   20180930
 @Title 九小场所附件
******************/
@Api(value = "九小场所附件下载管理" ,tags = "九小场所附件下载管理API" ,description = "jxcsfjxz")
@RestController
@RequestMapping("jxcsfjxz")
public class JxcsfjxzController  extends BaseController<JxcsfjxzVO>{

	@Autowired
	private JxcsfjxzService jxcsfjxzService;

	@Override
	public JxcsfjxzService getBaseService() {
		return this.jxcsfjxzService;
	}
	@Autowired
	private JxcsfjxzProperties jxcsfjxzProperties;

	//附件上传 by yushch 20180930
	@ApiOperation(value="九小场所附件下载",notes="下载")
	@GetMapping(value = "/upload")
	@ResponseBody
	public boolean upload(HttpServletRequest request, JxcsfjxzVO jxcsfjxzVO) {
		System.out.println("id:" + jxcsfjxzVO.getDwid());
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iterator = multipartRequest.getFileNames();

		while (iterator.hasNext()) {
			MultipartFile multipartFile = multipartRequest.getFile(iterator.next());
			// 获取文件名
			String fileName = multipartFile.getOriginalFilename();

			if ("".equals(multipartFile.getOriginalFilename())) throw new RuntimeException("文件为空");
			InputStream is = null;
			try {
				is = multipartFile.getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 文件上传固定的路径
			StringBuffer relativePath = new StringBuffer(jxcsfjxzProperties.getSavePath());
			//新建的文件夹名称（预案UUID/预案创建时间）

			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String zzsj = sdf.format(date);
			StringBuffer new_folder = new StringBuffer();

			// 获取文件的后缀名
			String suffixName = fileName.substring(fileName.lastIndexOf("."));
			if (suffixName.equals(".png") || suffixName.equals(".jpg")) {
				new_folder = new StringBuffer(jxcsfjxzVO.getDwid()).append("/").append("pictures").append("/").append(zzsj).append("/");
			} else {
				new_folder = new StringBuffer(jxcsfjxzVO.getDwid()).append("/").append("videos").append("/").append(zzsj).append("/");
			}

			String folderName = relativePath.append("jxcsFjxx").append("/").append(new_folder).toString();
			//创建文件夹
			File dir = new File(folderName);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			//数据库要存的数据
			String dbPath = new_folder.append(fileName).toString();
			System.out.println(dbPath);

			//文件全路径
			StringBuffer allPath = new StringBuffer(folderName).append(fileName);

			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(allPath.toString());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			byte[] b = new byte[1024];
			try {
				while ((is.read(b)) != -1) {
					fos.write(b);
				}
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				fos.close();
				is.close();

				//插入数据
				JxcsfjxzVO jxcsfjxz = new JxcsfjxzVO();
				jxcsfjxz.setDwid(jxcsfjxzVO.getDwid());
				jxcsfjxz.setCjrid(jxcsfjxzVO.getCjrid());
				jxcsfjxz.setFjlj(dbPath);

				if (suffixName.equals(".png") || suffixName.equals(".jpg")|| suffixName.equals(".bmp")|| suffixName.equals(".jpeg")) {
					jxcsfjxz.setFjxs("1");//附件形式[1]照片，[2]视频
				} else if (suffixName.equals(".mp4")) {
					jxcsfjxz.setFjxs("2");
				}
				jxcsfjxz.setKzm(suffixName);
				jxcsfjxz.setWjm(fileName);
				jxcsfjxz.setDeleteFlag("N");
				jxcsfjxzService.doInsertByVO(jxcsfjxz);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * 根据dwid查询附件信息
	 */
	@ApiOperation(value = "根据单位id获取信息", notes = "列表信息")
	@ApiImplicitParam(name="vo",value="九小场所附件VO")
	@RequestMapping("/doFindByDwid")
	public @ResponseBody
	ResultVO getDetail(@RequestBody JxcsfjxzVO jxcsfjxzVO) {
		ResultVO resultVO = ResultVO.build();
		try {
			resultVO.setResult(jxcsfjxzService.doFindByDwid(jxcsfjxzVO));
		} catch (Exception e) {
			logger.error("{}", e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	@ApiOperation(value = "根据单位id更新附件信息", notes = "修改")
	@ApiImplicitParam(name="list",value="九小场所附件list")
	@RequestMapping("/doUpdateByVO")
	public @ResponseBody ResultVO doUpdateByVO(@RequestBody List<JxcsfjxzVO> list) {
		ResultVO resultVO = ResultVO.build();
		try {
			resultVO.setResult(jxcsfjxzService.doUpdateByVOList(list));
		} catch (Exception e) {
			logger.error("{}", e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}
}
