package com.syfri.digitalplan.service.impl.jxcsplan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syfri.baseapi.service.impl.BaseServiceImpl;
import com.syfri.digitalplan.dao.jxcsplan.JxcsdlyzDAO;
import com.syfri.digitalplan.model.jxcsplan.JxcsdlyzVO;
import com.syfri.digitalplan.service.jxcsplan.JxcsdlyzService;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Service("jxcsdlyzService")
public class JxcsdlyzServiceImpl extends BaseServiceImpl<JxcsdlyzVO> implements JxcsdlyzService {

	@Autowired
	private JxcsdlyzDAO jxcsdlyzDAO;

	@Override
	public JxcsdlyzDAO getBaseDAO() {
		return jxcsdlyzDAO;
	}

	@Override
	public int doFindCountByUnscid(String unscid) {
		return jxcsdlyzDAO.doFindCountByUnscid(unscid);
	}
}