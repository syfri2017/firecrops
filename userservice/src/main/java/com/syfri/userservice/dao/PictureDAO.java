package com.syfri.userservice.dao;

import com.syfri.baseapi.dao.BaseDAO;
import com.syfri.userservice.model.PictureVO;

import java.util.List;

public interface PictureDAO extends BaseDAO<PictureVO>{

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
     * @Description: 上传图片
     * @Param: [pictureVO]
     * @Return: void
     * @Author: dongbo
     * @Modified By:
     * @Date: 2018/4/20 16:45
     */
    int doInsertImg(PictureVO pictureVO);

    /**
     * @Description: 查询图片名称是否已存在
     * @Param: [vo]
     * @Return: java.util.List<com.syfri.userservice.model.PictureVO>
     * @Author: dongbo
     * @Modified By:
     * @Date: 2018/5/21 10:26
     */
    List<PictureVO> doSearchListByPicName(PictureVO vo);

    /**
     * @Description: 查询已存的图片类型
     * @Param: []
     * @Return: java.util.List<com.syfri.userservice.model.PictureVO>
     * @Author: dongbo
     * @Modified By:
     * @Date: 2018/5/28 11:05
     */
    List<PictureVO> doSearchSavedListByVO();

    /**
     * @Description: 查询手动输入的类型是否存在
     * @Param: [vo]
     * @Return: java.util.List<com.syfri.userservice.model.PictureVO>
     * @Author: dongbo
     * @Modified By:
     * @Date: 2018/5/31 15:45
     */
    List<PictureVO> doSearchListByInputPicType(PictureVO vo);
}