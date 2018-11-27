package com.syfri.digitalplan.service.impl.digitalplan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syfri.baseapi.service.impl.BaseServiceImpl;
import com.syfri.digitalplan.dao.digitalplan.DisastersetDAO;
import com.syfri.digitalplan.dao.digitalplan.ForcedevDAO;
import com.syfri.digitalplan.dao.digitalplan.KeypointsDAO;
import com.syfri.digitalplan.model.digitalplan.DisastersetVO;
import com.syfri.digitalplan.model.digitalplan.ForcedevVO;
import com.syfri.digitalplan.model.digitalplan.KeypointsVO;
import com.syfri.digitalplan.service.digitalplan.DisastersetService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Service("disastersetService")
public class DisastersetServiceImpl extends BaseServiceImpl<DisastersetVO> implements DisastersetService {

    @Autowired
    private DisastersetDAO disastersetDAO;

    @Autowired
    private ForcedevDAO forcedevDAO;

    @Autowired
    private KeypointsDAO keypointsDAO;

    @Override
    public DisastersetDAO getBaseDAO() {
        return disastersetDAO;
    }

    @Override
    public List<DisastersetVO> doFindByPlanId(String yaid) {
        List<DisastersetVO> resultList = disastersetDAO.doFindByPlanId(yaid);
        for (DisastersetVO vo : resultList) {
            List<ForcedevVO> forcedevList = forcedevDAO.doFindByPlanId(vo.getZqid());
            vo.setForcedevList(forcedevList);
            KeypointsVO keypointsMap = keypointsDAO.doFindByPlanId(vo.getZqid());
            if (keypointsMap == null) {
                vo.setKeypointsMap(new KeypointsVO());
            } else {
                vo.setKeypointsMap(keypointsMap);
            }
        }
        return resultList;
    }
}