package com.syfri.userservice.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import com.syfri.userservice.dao.PictureDAO;
import com.syfri.userservice.model.CodelistVO;
import com.syfri.userservice.model.PictureVO;
import com.syfri.userservice.service.CodelistService;
import com.syfri.userservice.utils.Base64ImageUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.syfri.userservice.service.PictureService;
import com.syfri.baseapi.controller.BaseController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("picture")
public class PictureController extends BaseController<PictureVO> {

    @Autowired
    protected Environment environment;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private CodelistService codelistService;

    @Autowired
    private PictureDAO pictureDAO;

    @Override
    public PictureService getBaseService() {
        return this.pictureService;
    }


    @ModelAttribute
    public void Model(Model model) {
        if (environment.containsProperty("server.context-path")) {
            model.addAttribute("contextPath", environment.getProperty("server.context-path"));
        } else {
            model.addAttribute("contextPath", "/");
        }
    }

    @GetMapping("")
    public String getImgUpload(Model model, @RequestParam(value = "index") String index) {
        model.addAttribute("index", index);
        return "system/picture";
    }

    /**
     * @Description: 获取所有的图片类型
     * @Param: []
     * @Return: com.syfri.baseapi.model.ResultVO
     * @Author: dongbo
     * @Modified By:
     * @Date: 2018/5/21 9:29
     */
    @ApiOperation(value = "获取所有的图片类型", notes = "查询")
    @GetMapping("/getAll")
    public @ResponseBody
    ResultVO findAll() {
        ResultVO resultVO = ResultVO.build();
        try {
            CodelistVO codelistVO = new CodelistVO();
            resultVO.setResult(codelistService.doSearchListByVO(codelistVO));
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            resultVO.setCode(EConstants.CODE.FAILURE);
        }
        return resultVO;
    }

    /**
     * @Description: 获取已存的图片类型
     * @Param: []
     * @Return: com.syfri.baseapi.model.ResultVO
     * @Author: dongbo
     * @Modified By:
     * @Date: 2018/5/21 9:29
     */
    @ApiOperation(value = "获取已存的图片类型", notes = "查询")
    @GetMapping("/getSaved")
    public @ResponseBody
    ResultVO findSaved() {
        ResultVO resultVO = ResultVO.build();
        try {
            resultVO.setResult(pictureService.doSearchSavedListByVO());
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            resultVO.setCode(EConstants.CODE.FAILURE);
        }
        return resultVO;
    }

    /**
     * @Description: 根据条件获取图片信息
     * @Param: [pictureVO]
     * @Return: com.syfri.baseapi.model.ResultVO
     * @Author: dongbo
     * @Modified By:
     * @Date: 2018/5/21 9:29
     */
    @ApiOperation(value = "根据条件获取图片信息", notes = "列表信息")
    @ApiImplicitParam(name = "pictureVO", value = "图片对象")
    @PostMapping("/findByVO")
    public @ResponseBody
    ResultVO findByVO(@RequestBody PictureVO pictureVO) {
        ResultVO resultVO = ResultVO.build();
        try {
            PageHelper.startPage(pictureVO.getPageNum(), pictureVO.getPageSize());
            List<PictureVO> list = pictureService.doSearchListByVO(pictureVO);
            PageInfo<PictureVO> pageInfo = new PageInfo<>(list);
            //imgstr里面附上图片地址信息的值
            for (PictureVO vo : pageInfo.getList()) {
                vo.setImgStr("data:image/png;base64," + Base64ImageUtil.byteArr2String(vo.getPicBlob()));
            }
            resultVO.setResult(pageInfo);
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            resultVO.setCode(EConstants.CODE.FAILURE);
        }
        return resultVO;
    }

    /**
     * @Description: 根据图片名查询图片数量
     * @Param: [picName]
     * @Return: com.syfri.baseapi.model.ResultVO
     * @Author: dongbo
     * @Modified By:
     * @Date: 2018/4/27 9:53
     */
    @ApiOperation(value = "根据图片名查询图片数量", notes = "查询")
    @ApiImplicitParam(name = "picName", value = "图片名")
    @GetMapping("/getNum/{picName}")
    public @ResponseBody
    ResultVO getNum(@PathVariable String picName) {
        ResultVO resultVO = ResultVO.build();
        try {
            PictureVO pictureVO = new PictureVO();
            pictureVO.setPicName(picName);
            if (pictureService.doSearchListByPicName(pictureVO).size() == 0) {
                resultVO.setResult(0);
            } else {
                resultVO.setResult(1);
            }
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            resultVO.setCode(EConstants.CODE.FAILURE);
        }
        return resultVO;
    }

    /**
     * @Description: 新增图片信息
     * @Param: [pictureVO]
     * @Return: com.syfri.baseapi.model.ResultVO
     * @Author: dongbo
     * @Modified By:
     * @Date: 2018/5/21 9:34
     */
    @ApiOperation(value = "新增图片信息", notes = "新增")
    @ApiImplicitParam(name = "vo", value = "图片对象")
    //@RequiresPermissions("system/imgupload:add")
    @PostMapping("/insertByVO")
    public @ResponseBody
    ResultVO insertByVO(@RequestBody PictureVO pictureVO) {
        ResultVO resultVO = ResultVO.build();
        try {
            resultVO.setResult(pictureService.doInsertImgByVO(pictureVO));
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            resultVO.setCode(EConstants.CODE.FAILURE);
        }
        return resultVO;
    }

    /**
     * @throws UnsupportedEncodingException
     * @Description: 附件上传
     * @Param: [request]
     * @Return: java.util.Map<java.lang.String,java.lang.Object>
     * @Author: dongbo
     * @Modified By:
     * @Date: 2018/4/20 16:44
     */
    @ApiOperation(value = "附件上传", notes = "列表信息")
    @ApiImplicitParam(name = "vo", value = "图片对象")
    @RequestMapping("/insertImage")
    @ResponseBody
    public boolean uploadAttachment(HttpServletRequest request, PictureVO UploadVO)
            throws UnsupportedEncodingException {
        Map<String, Object> result = new HashMap<String, Object>();
        int count = 0;
        try {
            byte[] buffer = null;
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iterator = multipartRequest.getFileNames();

            while (iterator.hasNext()) {
                MultipartFile multipartFile = multipartRequest.getFile(iterator.next());

                // 获取文件名
                String fileName = multipartFile.getOriginalFilename();

                if ("".equals(multipartFile.getOriginalFilename())) throw new RuntimeException("文件为空");
                InputStream fis = null;
                try {
                    fis = multipartFile.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] b = new byte[1024];
                int n;
                while ((n = fis.read(b)) != -1) {
                    bos.write(b, 0, n);
                }
                fis.close();
                bos.close();
                buffer = bos.toByteArray();

                PictureVO pictureVO = new PictureVO();
                pictureVO.setImgFile(buffer);
                pictureVO.setPicValue(UploadVO.getPicValue());
                pictureVO.setPicType(UploadVO.getPicType());

                count = count + pictureDAO.doInsertImg(pictureVO);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @Description: 删除图片
     * @Param: [id]
     * @Return: com.syfri.baseapi.model.ResultVO
     * @Author: dongbo
     * @Modified By:
     * @Date: 2018/5/21 9:35
     */
    @ApiOperation(value = "删除图片", notes = "删除")
    @ApiImplicitParam(name = "id", value = "图片主键")
//	@RequiresPermissions("system/imgupload:delete")
    @PostMapping("/doDeleteByVOList")
    public @ResponseBody
    ResultVO doDeleteByVOList(@RequestBody List<PictureVO> pictureVOList) {
        ResultVO resultVO = ResultVO.build();
        try {
            resultVO.setResult(pictureService.doDeleteByVOList(pictureVOList));
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            resultVO.setCode(EConstants.CODE.FAILURE);
        }
        return resultVO;
    }

    /**
     * @Description: 根据id获取图片信息
     * @Param: [pkid]
     * @Return: com.syfri.baseapi.model.ResultVO
     * @Author: dongbo
     * @Modified By:
     * @Date: 2018/5/21 9:35
     */
    @ApiOperation(value = "根据id获取图片信息", notes = "列表信息")
    @GetMapping("/doFindById/{pkid}")
    public @ResponseBody
    ResultVO getDetail(@PathVariable String pkid) {
        ResultVO resultVO = ResultVO.build();
        try {
            PictureVO result = pictureService.doFindById(pkid);
            if(!StringUtils.isEmpty(result.getPicBlob())){
                //将二进制转为Base64格式字符串
                String photo64 = "data:image/png;base64," + Base64ImageUtil.byteArr2String(result.getPicBlob());
                result.setPhoto64(photo64);
            }
            resultVO.setResult(result);
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            resultVO.setCode(EConstants.CODE.FAILURE);
        }
        return resultVO;
    }

    /**
     * @Description: 修改图片信息
     * @Param: [pictureVO]
     * @Return: com.syfri.baseapi.model.ResultVO
     * @Author: dongbo
     * @Modified By:
     * @Date: 2018/5/21 9:35
     */
    @ApiOperation(value = "修改图片信息", notes = "修改")
    @ApiImplicitParam(name = "vo", value = "图片对象")
//    @RequiresPermissions("system/imgupload:edit")
    @PostMapping("/updateByVO")
    public @ResponseBody
    ResultVO updateByVO(@RequestBody PictureVO pictureVO) {
        ResultVO resultVO = ResultVO.build();
        try {
            resultVO.setResult(pictureService.doUpdateByVO(pictureVO));
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            resultVO.setCode(EConstants.CODE.FAILURE);
        }
        return resultVO;
    }

    /**
     * @Description: 根据手动输入图片类型查询图片数量
     * @Param: [picName]
     * @Return: com.syfri.baseapi.model.ResultVO
     * @Author: dongbo
     * @Modified By:
     * @Date: 2018/4/27 9:53
     */
    @ApiOperation(value = "根据手动输入图片类型查询图片数量", notes = "查询")
    @ApiImplicitParam(name = "picType", value = "图片类型")
    @GetMapping("/getInputNum/{picType}")
    public @ResponseBody
    ResultVO getInputNum(@PathVariable String picType) {
        ResultVO resultVO = ResultVO.build();
        try {
            PictureVO pictureVO = new PictureVO();
            pictureVO.setPicType(picType);
            List<PictureVO> result = pictureService.doSearchListByInputPicType(pictureVO);
            resultVO.setResult(result);
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
            resultVO.setCode(EConstants.CODE.FAILURE);
        }
        return resultVO;
    }
}
