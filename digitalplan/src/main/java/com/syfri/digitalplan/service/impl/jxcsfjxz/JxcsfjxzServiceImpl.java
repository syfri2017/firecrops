package com.syfri.digitalplan.service.impl.jxcsfjxz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syfri.baseapi.service.impl.BaseServiceImpl;
import com.syfri.digitalplan.dao.jxcsfjxz.JxcsfjxzDAO;
import com.syfri.digitalplan.model.jxcsfjxz.JxcsfjxzVO;
import com.syfri.digitalplan.service.jxcsfjxz.JxcsfjxzService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Service("jxcsfjxzService")
public class JxcsfjxzServiceImpl extends BaseServiceImpl<JxcsfjxzVO> implements JxcsfjxzService {

	@Autowired
	private JxcsfjxzDAO jxcsfjxzDAO;

	@Override
	public JxcsfjxzDAO getBaseDAO() {
		return jxcsfjxzDAO;
	}

	@Override
	public List<JxcsfjxzVO> doFindByDwid(JxcsfjxzVO vo) {
		List<JxcsfjxzVO> resultList = null;
		if(vo.getKzm().equals("pic")){
			resultList = jxcsfjxzDAO.doFindPicsByDwid(vo);
		}else if(vo.getKzm().equals("video")){
			resultList = jxcsfjxzDAO.doFindVideosByDwid(vo);
		}

		return resultList;
	}

	public int doUpdateByVOList(List<JxcsfjxzVO> voList) {
		int count = 0;
		for (JxcsfjxzVO vo : voList) {
			count = count + jxcsfjxzDAO.doUpdateByVO(vo);
		}
		return count;
	}
}