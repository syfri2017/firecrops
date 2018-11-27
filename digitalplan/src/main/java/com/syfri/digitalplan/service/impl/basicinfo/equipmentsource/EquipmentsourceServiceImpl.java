package com.syfri.digitalplan.service.impl.basicinfo.equipmentsource;

import com.syfri.digitalplan.model.basicinfo.equipmentsource.EquipengineVO;
import com.syfri.digitalplan.model.basicinfo.equipmentsource.EquipmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syfri.baseapi.service.impl.BaseServiceImpl;
import com.syfri.digitalplan.dao.basicinfo.equipmentsource.EquipmentsourceDAO;
import com.syfri.digitalplan.dao.basicinfo.equipmentsource.EquipengineDAO;
import com.syfri.digitalplan.service.basicinfo.equipmentsource.EquipmentsourceService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Service("equipmentsourceService")
public class EquipmentsourceServiceImpl extends BaseServiceImpl<EquipmentVO> implements EquipmentsourceService {

	@Autowired
	private EquipmentsourceDAO equipmentsourceDAO;
	@Autowired
	private EquipengineDAO equipengineDAO;

	@Override
	public EquipmentsourceDAO getBaseDAO() {
		return equipmentsourceDAO;
	}

	/*--条件查询：重点单位.--*/
	@Override
	public List<EquipmentVO> doFindlist(EquipmentVO equipmentVO){
		return equipmentsourceDAO.doSearchByVO(equipmentVO);
	}

	/*--根据重点单位id获取重点单位详情--*/
	@Override
	public List<EquipmentVO> doFindDetailById(String id) {
		return equipmentsourceDAO.doFindDetailById(id);
	}

	@Override
	public EquipmentVO doInsertEquipment(EquipmentVO equipmentVO) {
		equipmentsourceDAO.doInsertByVO(equipmentVO);
		for (EquipengineVO vo : equipmentVO.getEquipengineVOList()) {
			vo.setZbid(equipmentVO.getUuid());
			vo.setJdh(equipmentVO.getJdh());
			vo.setDatasource(equipmentVO.getDatasource());
			equipengineDAO.doInsertByVO(vo);
		}
		return equipmentVO;
	}

	public int doDeleteEquipment(List<EquipmentVO> equipmentList) {
		int count = 0;
		if (equipmentList.size() > 0) {
			for (EquipmentVO equipmentVO : equipmentList) {
				//装备车辆中间表删除
				EquipengineVO engineVO = new EquipengineVO();
				engineVO.setZbid(equipmentVO.getUuid());
				engineVO.setXgrid(equipmentVO.getXgrid());
				engineVO.setXgrmc(equipmentVO.getXgrmc());
				engineVO.setDeleteFlag("Y");
				equipengineDAO.doUpdateByZbid(engineVO);
				//装备删除
				EquipmentVO vo = new EquipmentVO();
				vo.setUuid(equipmentVO.getUuid());
				vo.setXgrid(equipmentVO.getXgrid());
				vo.setXgrmc(equipmentVO.getXgrmc());
				vo.setDeleteFlag("Y");
				count = count + equipmentsourceDAO.doUpdateByVO(vo);
			}
		}
		return count;
	}

	public EquipmentVO doUpdateEquipment(EquipmentVO equipmentVO) {
	    //装备主表修改更新
		equipmentsourceDAO.doUpdateByVO(equipmentVO);

        //车辆（新）
        List<EquipengineVO> equipengineList = equipmentVO.getEquipengineVOList();
        //车辆（旧）
        EquipengineVO engineVO = new EquipengineVO();
        engineVO.setZbid(equipmentVO.getUuid());
        List<EquipengineVO> enginelist = equipengineDAO.doSearchListByVO(engineVO);
        //车辆删除
        for (EquipengineVO vo1 : enginelist) {
            Boolean isDelete = true;
            for (EquipengineVO vo2 : equipengineList) {
                if (vo2.getUuid() != null && vo2.getUuid().equals(vo1.getUuid())) {
                    isDelete = false;
                    break;
                }
            }
            if (isDelete) {//删除
                vo1.setDeleteFlag("Y");
                vo1.setXgrid(equipmentVO.getXgrid());
                vo1.setXgrmc(equipmentVO.getXgrmc());
                equipengineDAO.doUpdateByVO(vo1);
            }
        }
        //车辆修改和新增
        for(EquipengineVO vo:equipengineList){
            if(vo.getUuid() != null && vo.getUuid() != ""){ // 修改
                vo.setXgrid(equipmentVO.getXgrid());
                vo.setXgrmc(equipmentVO.getXgrmc());
                equipengineDAO.doUpdateByVO(vo);
            }else{ //新增
                vo.setZbid(equipmentVO.getUuid());
                vo.setJdh(equipmentVO.getJdh());
                vo.setDatasource(equipmentVO.getDatasource());
                vo.setCjrid(equipmentVO.getXgrid());
                vo.setCjrmc(equipmentVO.getXgrmc());
                equipengineDAO.doInsertByVO(vo);
            }
        }
		return equipmentVO;
	}
}