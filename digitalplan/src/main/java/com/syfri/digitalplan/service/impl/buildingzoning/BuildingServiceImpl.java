package com.syfri.digitalplan.service.impl.buildingzoning;

import com.syfri.digitalplan.model.buildingzoning.ChuguanVO;
import com.syfri.digitalplan.model.buildingzoning.WeixianjiezhiVO;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syfri.baseapi.service.impl.BaseServiceImpl;
import com.syfri.digitalplan.dao.buildingzoning.BuildingDAO;
import com.syfri.digitalplan.model.buildingzoning.BuildingVO;
import com.syfri.digitalplan.service.buildingzoning.BuildingService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Service("buildingService")
public class BuildingServiceImpl extends BaseServiceImpl<BuildingVO> implements BuildingService {

    @Autowired
    private BuildingDAO buildingDAO;

    @Override
    public BuildingDAO getBaseDAO() {
        return buildingDAO;
    }

    @Override
    public BuildingVO doFindFqDetailByVo(BuildingVO buildingVO) {
        String fqlx = buildingVO.getJzlx();
        BuildingVO vo = new BuildingVO();
        switch (fqlx) {
            case "10":
            case "20":
                //分区类型为10和20 查找建筑分区详情关联建筑分区-建筑类
                vo = buildingDAO.doFindFqAndJzDetailByVo(buildingVO);
                break;
            case "30":
                //查找建筑分区详情关联建筑分区-装置类
                vo = buildingDAO.doFindFqAndZzDetailByVo(buildingVO);
                break;
            case "40":
                //查找建筑分区详情关联建筑分区-储罐类
                vo = buildingDAO.doFindFqAndCgDetailByVo(buildingVO);
                ChuguanVO chuguanVO = new ChuguanVO();
                chuguanVO.setPkid(vo.getCgl_uuid());
                List<ChuguanVO> chuguanList = this.buildingDAO.doFindChuGuanList(chuguanVO);
                vo.setChuguanList(chuguanList);
                break;
            default:
                break;
        }
        return vo;
    }

    //delete删除从表功能
    public int doDeleteBuildingzoningDetail(BuildingVO buildingVO) {
        int count = 0;
        String jzlx = buildingVO.getJzlx();
        String jzid = buildingVO.getJzid();
        //删除从表
        if (!jzlx.isEmpty()) {
            switch (jzlx) {
                case "10":
                case "20":
                    count = count + buildingDAO.doDeleteJzlById(jzid);
                    break;
                case "30":
                    count = count + buildingDAO.doDeleteZzlById(jzid);
                    break;
                case "40":
                    buildingDAO.doDeleteChuguanById(jzid);
                    count = count + buildingDAO.doDeleteCglById(jzid);
                    break;
            }
        }
        return count;
    }

    //新增
    public BuildingVO doInsertDetailByVO(BuildingVO buildingVO) {
        String jzid = buildingVO.getJzid();
        String jzlx = buildingVO.getJzlx();

        switch (jzlx) {
            case "10":
            case "20":
                buildingVO.setJzl_jzid(jzid);
                buildingVO.setJzl_jdh(buildingVO.getJdh());
                buildingVO.setJzl_datasource(buildingVO.getDatasource());
                buildingDAO.doInsertJzlByVO(buildingVO);
                break;
            case "30":
                buildingVO.setZzl_jzid(jzid);
                buildingVO.setZzl_jdh(buildingVO.getJdh());
                buildingVO.setZzl_datasource(buildingVO.getDatasource());
                buildingDAO.doInsertZzlByVO(buildingVO);
                break;
            case "40":
                buildingVO.setCgl_jzid(jzid);
                buildingVO.setCgl_jdh(buildingVO.getJdh());
                buildingVO.setCgl_datasource(buildingVO.getDatasource());
                buildingDAO.doInsertCglByVO(buildingVO);
                for (ChuguanVO vo : buildingVO.getChuguanList()) {
                    vo.setPkid(buildingVO.getCgl_uuid());
                    vo.setJdh(buildingVO.getJdh());
                    vo.setDatasource(buildingVO.getDatasource());
                    buildingDAO.doInsertChuguanByVO(vo);
                }
                break;
        }
        return buildingVO;
    }
    //删除建筑
    public int doDeleteBuildingzoning(List<BuildingVO> buildingList){
        int count = 0;
        if (buildingList.size() > 0) {
            for (BuildingVO buildingVO : buildingList) {
                buildingVO.setDeleteFlag("Y");
                //删除主表
                count = count + this.doUpdateByVO(buildingVO);
                //删除从表
                ((BuildingService) AopContext.currentProxy()).doDeleteBuildingzoningDetail(buildingVO);
                //删除重点单位建筑中间表
                buildingDAO.doDeleteZjbByJzid(buildingVO.getJzid());
            }
        }
        return count;
    }

    //update修改
    public BuildingVO doUpdateBuildingzoning(BuildingVO buildingVO) {
        String jzid = buildingVO.getJzid();
        String jzlx = buildingVO.getJzlx();
        BuildingVO oldVO = buildingDAO.doFindById(jzid);
        buildingDAO.doUpdateByVO(buildingVO);
        if (!oldVO.getJzlx().equals(jzlx)) {
            this.doDeleteBuildingzoningDetail(oldVO);
            this.doInsertDetailByVO(buildingVO);
        } else {
            switch (jzlx) {
                case "10":
                case "20":
                    //分区类型为10和20 查找建筑分区详情关联建筑分区-建筑类
                    buildingVO.setJzl_jzid(jzid);
                    buildingDAO.doUpdateJzlByVO(buildingVO);
                    break;
                case "30":
                    //查找建筑分区详情关联建筑分区-装置类
                    buildingVO.setZzl_jzid(jzid);
                    buildingDAO.doUpdateZzlByVO(buildingVO);
                    break;
                case "40":
                    //查找建筑分区详情关联建筑分区-储罐类
                    buildingVO.setCgl_jzid(jzid);
                    buildingDAO.doUpdateCglByVO(buildingVO);
                    ChuguanVO vo = new ChuguanVO();
                    vo.setPkid(buildingVO.getCgl_uuid());
                    List<ChuguanVO> oldCgList = buildingDAO.doFindChuGuanList(vo);//旧储罐List
                    List<ChuguanVO> newCgList = buildingVO.getChuguanList();//新的储罐List
                    //删除
                    Boolean isDelete = true;
                    for (ChuguanVO vo1 : oldCgList) {
                        for (ChuguanVO vo2 : newCgList) {
                            if (vo1.getUuid().equals(vo2.getUuid()) && vo2.getUuid() != null) {
                                isDelete = false;
                            }
                        }
                        if (isDelete) {
                            buildingDAO.doDeleteCgById(vo1.getUuid());
                        }
                    }
                    //新增和修改
                    for (ChuguanVO newVO : newCgList) {
                        if (newVO.getUuid() != null && newVO.getUuid() != "") {
                            //修改
                            buildingDAO.doUpdateChuguanByVO(newVO);
                        }else{
                            //新增
                            newVO.setPkid(buildingVO.getCgl_uuid());
                            newVO.setJdh(buildingVO.getJdh());
                            newVO.setDatasource(buildingVO.getDatasource());
                            buildingDAO.doInsertChuguanByVO(newVO);
                        }
                    }

                    break;
            }
        }
        return buildingVO;
    }

}