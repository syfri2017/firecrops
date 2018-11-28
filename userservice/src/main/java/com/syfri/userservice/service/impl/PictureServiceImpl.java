package com.syfri.userservice.service.impl;

import com.syfri.userservice.model.PictureVO;
import com.syfri.userservice.utils.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syfri.baseapi.service.impl.BaseServiceImpl;
import com.syfri.userservice.dao.PictureDAO;
import com.syfri.userservice.service.PictureService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Service("pictureService")
public class PictureServiceImpl extends BaseServiceImpl<PictureVO> implements PictureService {

	@Autowired
	private PictureDAO pictureDAO;

	@Override
	public PictureDAO getBaseDAO() {
		return pictureDAO;
	}

	/**
	 * @Description: 获取所有图片类型
	 * @Param: []
	 * @Return: java.util.List<com.syfri.userservice.model.PictureVO>
	 * @Author: dongbo
	 * @Modified By:
	 * @Date: 2018/5/17 11:05
	 */
	@Override
	public List<PictureVO> doFindAll(){
		return pictureDAO.doFindAll();
	}

	/**
	 * @Description: 查询图片名称是否已存在
	 * @Param: [vo]
	 * @Return: java.util.List<com.syfri.userservice.model.PictureVO>
	 * @Author: dongbo
	 * @Modified By:
	 * @Date: 2018/5/21 10:26
	 */
	public List<PictureVO> doSearchListByPicName(PictureVO vo){return pictureDAO.doSearchListByPicName(vo);}

	/**
	 * @Description: 查询已存的图片类型
	 * @Param: []
	 * @Return: java.util.List<com.syfri.userservice.model.PictureVO>
	 * @Author: dongbo
	 * @Modified By:
	 * @Date: 2018/5/28 11:03
	 */
	public List<PictureVO> doSearchSavedListByVO(){
		return pictureDAO.doSearchSavedListByVO();
	}

	/**
	 * @Description: 查询手动输入的类型是否存在
	 * @Param: [vo]
	 * @Return: java.util.List<com.syfri.userservice.model.PictureVO>
	 * @Author: dongbo
	 * @Modified By:
	 * @Date: 2018/5/31 15:44
	 */
	public List<PictureVO> doSearchListByInputPicType(PictureVO vo){return pictureDAO.doSearchListByInputPicType(vo);}

	/**
	 * @Description: 新增图片
	 * @Param: [pictureVO]
	 * @Return: int
	 * @Author: dongbo
	 * @Modified By:
	 * @Date: 2018/6/7 11:06
	 */
	public int doInsertImgByVO(PictureVO pictureVO) {
		pictureVO.setCreateId(CurrentUserUtil.getCurrentUserId());
		pictureVO.setCreateName(CurrentUserUtil.getCurrentUserName());

		return pictureDAO.doInsertByVO(pictureVO);
	}

	/**
	 * @Description: 修改图片
	 * @Param: [pictureVO]
	 * @Return: int
	 * @Author: dongbo
	 * @Modified By:
	 * @Date: 2018/6/7 11:12
	 */
	public int doUpdateImgByVO(PictureVO pictureVO){
		pictureVO.setAlterId(CurrentUserUtil.getCurrentUserId());
		pictureVO.setAlterName(CurrentUserUtil.getCurrentUserName());

		return pictureDAO.doUpdateByVO(pictureVO);
	}
}