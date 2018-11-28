package com.syfri.userservice.service;

import com.syfri.baseapi.service.BaseService;
import com.syfri.userservice.model.PictureVO;

import java.util.List;

public interface PictureService extends BaseService<PictureVO>{
    /**
     * @Description: 获取所有图片类型
     * @Param: []
     * @Return: java.util.List<com.syfri.userservice.model.PictureVO>
     * @Author: dongbo
     * @Modified By:
     * @Date: 2018/5/17 11:04
     */
    List<PictureVO> doFindAll();

    /**
     * @Description: 查询图片名称是否已存在
     * @Param: [vo]
     * @Return: java.util.List<com.syfri.userservice.model.PictureVO>
     * @Author: dongbo
     * @Modified By:
     * @Date: 2018/5/21 10:27
     */
    List<PictureVO> doSearchListByPicName(PictureVO vo);

    /**
     * @Description: 查询已存的图片类型
     * @Param: [vo]
     * @Return: java.util.List<com.syfri.userservice.model.PictureVO>
     * @Author: dongbo
     * @Modified By:
     * @Date: 2018/5/28 11:03
     */
    List<PictureVO> doSearchSavedListByVO();

    /**
     * @Description: 查询手动输入的类型是否存在
     * @Param: [vo]
     * @Return: java.util.List<com.syfri.userservice.model.PictureVO>
     * @Author: dongbo
     * @Modified By:
     * @Date: 2018/5/31 15:43
     */
    List<PictureVO> doSearchListByInputPicType(PictureVO vo);

    /**
     * @Description: 新增图片
     * @Param: [vo]
     * @Return: int
     * @Author: dongbo
     * @Modified By:
     * @Date: 2018/6/7 10:57
     */
    int doInsertImgByVO(PictureVO vo);
    
    /**
     * @Description: 修改图片
     * @Param: [vo]
     * @Return: int
     * @Author: dongbo
     * @Modified By:
     * @Date: 2018/6/7 11:12
     */
    int doUpdateImgByVO(PictureVO vo);
}