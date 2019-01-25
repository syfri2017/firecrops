package com.syfri.digitalplan.service.impl.digitalplan;

import com.syfri.digitalplan.dao.digitalplan.DigitalplanlistDAO;
import com.syfri.digitalplan.dao.digitalplan.DistributeDAO;
import com.syfri.digitalplan.dao.digitalplan.DisastersetDAO;
import com.syfri.digitalplan.dao.digitalplan.KeypointsDAO;
import com.syfri.digitalplan.dao.digitalplan.ForcedevDAO;
import com.syfri.digitalplan.dao.buildingzoning.BuildingDAO;

import com.syfri.digitalplan.dao.yafjxz.YafjxzDAO;
import com.syfri.digitalplan.dao.yafjxz.YaxxzlDAO;
import com.syfri.digitalplan.model.digitalplan.DigitalplanlistVO;
import com.syfri.digitalplan.model.digitalplan.DistributeVO;
import com.syfri.digitalplan.model.digitalplan.DisastersetVO;
import com.syfri.digitalplan.model.buildingzoning.BuildingVO;
import com.syfri.digitalplan.model.digitalplan.ForcedevVO;
import com.syfri.digitalplan.model.digitalplan.KeypointsVO;
import com.syfri.digitalplan.model.yafjxz.YafjxzVO;
import com.syfri.digitalplan.model.yafjxz.YaxxzlVO;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syfri.baseapi.service.impl.BaseServiceImpl;

import com.syfri.digitalplan.service.digitalplan.DigitalplanlistService;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Service("digitalplanlistService")
public class DigitalplanlistServiceImpl extends BaseServiceImpl<DigitalplanlistVO> implements DigitalplanlistService {

    @Autowired
    private DigitalplanlistDAO digitalplanlistDAO;
    @Autowired
    private DisastersetDAO disastersetDAO;
    @Autowired
    private KeypointsDAO keypointsDAO;
    @Autowired
    private ForcedevDAO forcedevDAO;
    @Autowired
    private BuildingDAO buildingDAO;
    @Autowired
    private DistributeDAO distributeDAO;
    @Autowired
    private YaxxzlDAO yaxxzlDAO;
    @Autowired
    private YafjxzDAO yafjxzDAO;


    @Override
    public DigitalplanlistDAO getBaseDAO() {
        return digitalplanlistDAO;
    }


    /*
    * 预案审批（更新部分字段）
    * by yushch 20180426
    */
    public DigitalplanlistVO doApproveUpdate(DigitalplanlistVO digitalplanlistVO) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String shsj = sdf.format(date);
        digitalplanlistVO.setShsj(shsj);
        String shzt = digitalplanlistVO.getShzt();
        //如果选择“未通过”，预案状态变更为已驳回
        if (shzt.equals("02")) {
            digitalplanlistVO.setYazt("04");
        }
        //如果选择“已通过”，预案状态变更为已审核
        else if (shzt.equals("03")) {
            digitalplanlistVO.setYazt("05");
        }
        digitalplanlistDAO.doUpdateByVO(digitalplanlistVO);
        String shztmc = digitalplanlistDAO.doFindShztmcByShzt(shzt);
        digitalplanlistVO.setShztmc(shztmc);
        switch (shzt) {
            case "01":
                digitalplanlistVO.setYashztButtonType("danger");
                break;
            case "02":
                digitalplanlistVO.setYashztButtonType("warning");
                break;
            case "03":
                digitalplanlistVO.setYashztButtonType("success");
                break;
            default:
                digitalplanlistVO.setYashztButtonType("text");
                break;
        }
        return digitalplanlistVO;
    }

    /**
     * @Description: 新增：预案
     * @Param: [digitalplanlistVO]
     * @Return: com.syfri.digitalplan.model.digitalplan.DigitalplanlistVO
     * @Author: liurui
     * @Modified By:
     * @Date: 2018/5/2 16:05
     */
    @Override
    public DigitalplanlistVO doInsertDigitalplan(DigitalplanlistVO digitalplanlistVO) {
        //预案插入
        String yabm = ((DigitalplanlistService) AopContext.currentProxy()).createPlanCode(digitalplanlistVO.getJgbm(),"01");//灾害类型暂定为火灾01
        digitalplanlistVO.setYabm(yabm);
        digitalplanlistDAO.doInsertByVO(digitalplanlistVO);
        //灾情插入
        List disasterList = digitalplanlistVO.getDisasterList();

        for (int i = 0; i < disasterList.size(); i++) {
            Map ds = (Map) disasterList.get(i);
            DisastersetVO dsVO = new DisastersetVO();
            dsVO.setYaid(digitalplanlistVO.getUuid());//预案id
            dsVO.setDatasource(digitalplanlistVO.getDatasource());
            dsVO.setJdh(digitalplanlistVO.getJdh());
            dsVO.setZdbwid(ds.get("zdbwid") != null ? ds.get("zdbwid").toString() : "");//重点部位id
            dsVO.setJzid(ds.get("jzid") != null ? ds.get("jzid").toString() : "");
            dsVO.setZqbw(ds.get("zqbw") != null ? ds.get("zqbw").toString() : "");
            dsVO.setQhyy(ds.get("qhyy") != null ? ds.get("qhyy").toString() : "");
            dsVO.setGyjzhzwxx(ds.get("gyjzhzwxx") != null ? ds.get("gyjzhzwxx").toString() : "");
            dsVO.setZhcs(ds.get("zhcs") != null ? ds.get("zhcs").toString() : "");
            dsVO.setQhbwgd(ds.get("qhbwgd") != null ? ds.get("qhbwgd").toString() : "");
            dsVO.setRsmj(ds.get("rsmj") != null ? ds.get("rsmj").toString() : "");
            dsVO.setZqms(ds.get("zqms") != null ? ds.get("zqms").toString() : "");
            dsVO.setZqsdyj(ds.get("zqsdyj") != null ? ds.get("zqsdyj").toString() : "");
            dsVO.setCjrid(digitalplanlistVO.getZzrid());
            dsVO.setCjrmc(digitalplanlistVO.getZzrmc());
            dsVO.setDeleteFlag("N");
            //灾情等级
            if (ds.get("zqdj") != "" && ds.get("zqdj") != null) {
                List zqdj = (List) ds.get("zqdj");//灾情等级
                if (zqdj.size() >= 1) {
                    dsVO.setZqdj(zqdj.get(zqdj.size() - 1).toString());
                }
            }
            //燃烧物质
            if (ds.get("rswz") != "" && ds.get("rswz") != null) {
                List rswz = (List) ds.get("rswz");//燃烧物质
                if (rswz.size() >= 1) {
                    dsVO.setRswz(rswz.get(rswz.size() - 1).toString());
                }
            }
            disastersetDAO.doInsertByVO(dsVO);
            //力量部署插入
            List forceList = (List) ds.get("forcedevList");
            for (int k = 0; k < forceList.size(); k++) {
                Map fd = (Map) forceList.get(k);
                ForcedevVO fdVO = new ForcedevVO();
                fdVO.setZqid(dsVO.getZqid());
                fdVO.setJdh(dsVO.getJdh());
                fdVO.setDatasource(dsVO.getDatasource());
                fdVO.setDjfalx(fd.get("djfalx") != null ? fd.get("djfalx").toString() : "");
                fdVO.setDzid(fd.get("dzid") != null ? fd.get("dzid").toString() : "");
                fdVO.setTkwz(fd.get("tkwz") != null ? fd.get("tkwz").toString() : "");
                fdVO.setClzbts(fd.get("clzbts") != null ? fd.get("clzbts").toString() : "");
                fdVO.setCjrid(digitalplanlistVO.getZzrid());
                fdVO.setCjrmc(digitalplanlistVO.getZzrmc());
                fdVO.setDeleteFlag("N");
                forcedevDAO.doInsertByVO(fdVO);
            }
            //要点提示插入
            Map kp = (Map) ds.get("keypointsMap");
            if ((kp.get("zsyd") != "" && kp.get("zsyd") != null) || (kp.get("tbjs") != "" && kp.get("tbjs") != null)) {
                KeypointsVO kpVO = new KeypointsVO();
                kpVO.setZqid(dsVO.getZqid());
                kpVO.setJdh(dsVO.getJdh());
                kpVO.setDatasource(dsVO.getDatasource());
                kpVO.setZsyd(kp.get("zsyd") != null ? kp.get("zsyd").toString() : "");
                kpVO.setTbjs(kp.get("tbjs") != null ? kp.get("tbjs").toString() : "");
                kpVO.setCjrid(digitalplanlistVO.getZzrid());
                kpVO.setCjrmc(digitalplanlistVO.getZzrmc());
                kpVO.setDeleteFlag("N");
                keypointsDAO.doInsertByVO(kpVO);
            }
        }
        return digitalplanlistVO;
    }

    /**
     * @Description: 修改：预案
     * @Param: [digitalplanlistVO]
     * @Return: com.syfri.digitalplan.model.digitalplan.DigitalplanlistVO
     * @Author: liurui
     * @Modified By:
     * @Date: 2018/5/2 16:05
     */
    @Override
    public DigitalplanlistVO doUpdateDigitalplan(DigitalplanlistVO digitalplanlistVO) {
        //预案
        digitalplanlistVO.setXgrid(digitalplanlistVO.getZzrid());
        digitalplanlistVO.setXgrmc(digitalplanlistVO.getZzrmc());
        digitalplanlistDAO.doUpdateByVO(digitalplanlistVO);
        //灾情（新）
        List disasterList = digitalplanlistVO.getDisasterList();
        //灾情（旧）
        List<DisastersetVO> dslist = disastersetDAO.doFindByPlanId(digitalplanlistVO.getUuid());
        for (DisastersetVO vo : dslist) {
            List<ForcedevVO> forcedevList = forcedevDAO.doFindByPlanId(vo.getZqid());
            vo.setForcedevList(forcedevList);
            KeypointsVO keypointsMap = keypointsDAO.doFindByPlanId(vo.getZqid());
            if (keypointsMap == null) {
                vo.setKeypointsMap(new KeypointsVO());
            } else {
                vo.setKeypointsMap(keypointsMap);
            }
        }
        //灾情删除
        for (DisastersetVO dsVo : dslist) {
            Boolean isDeleteDs = true;

            DisastersetVO deleteDsVo = new DisastersetVO();
            deleteDsVo.setZqid(dsVo.getZqid());
            deleteDsVo.setXgrid(digitalplanlistVO.getZzrid());
            deleteDsVo.setXgrmc(digitalplanlistVO.getZzrmc());
            deleteDsVo.setDeleteFlag("Y");

            ForcedevVO deleteFdVo = new ForcedevVO();
            deleteFdVo.setZqid(dsVo.getZqid());
            deleteFdVo.setXgrid(digitalplanlistVO.getZzrid());
            deleteFdVo.setXgrmc(digitalplanlistVO.getZzrmc());
            deleteFdVo.setDeleteFlag("Y");

            KeypointsVO deleteKpVo = new KeypointsVO();
            deleteKpVo.setZqid(dsVo.getZqid());
            deleteKpVo.setXgrid(digitalplanlistVO.getZzrid());
            deleteKpVo.setXgrmc(digitalplanlistVO.getZzrmc());
            deleteKpVo.setDeleteFlag("Y");

            for (int i = 0; i < disasterList.size(); i++) {
                Map ds = (Map) disasterList.get(i);
                if (ds.get("zqid") != null && ds.get("zqid").equals(dsVo.getZqid())) {
                    isDeleteDs = false;
                    if (dsVo.getForcedevList() != null) {
                        for (ForcedevVO fdVo : dsVo.getForcedevList()) {
                            Boolean isDeleteFd = true;
                            List forceList = (List) ds.get("forcedevList");
                            for (int k = 0; k < forceList.size(); k++) {
                                Map fd = (Map) forceList.get(k);
                                if (fd.get("uuid") != null && fd.get("uuid").equals(fdVo.getUuid())) {
                                    isDeleteFd = false;
                                }
                            }
                            if (isDeleteFd) {
                                deleteFdVo.setUuid(fdVo.getUuid());
                                forcedevDAO.doUpdateByVO(deleteFdVo);
                            }
                        }
                    }
                }
            }
            if (isDeleteDs) {
                disastersetDAO.doUpdateByVO(deleteDsVo);
                forcedevDAO.doDeleteByVO(deleteFdVo);
                keypointsDAO.doDeleteByVO(deleteKpVo);
            } else {

            }
        }
        //灾情修改和新增
        for (int i = 0; i < disasterList.size(); i++) {
            Map ds = (Map) disasterList.get(i);
            DisastersetVO dsVO = new DisastersetVO();
            dsVO.setYaid(digitalplanlistVO.getUuid());//预案id
            dsVO.setZdbwid(ds.get("zdbwid") != null ? ds.get("zdbwid").toString() : "");
            dsVO.setJzid(ds.get("jzid") != null ? ds.get("jzid").toString() : "");
            dsVO.setZqbw(ds.get("zqbw") != null ? ds.get("zqbw").toString() : "");
            dsVO.setQhyy(ds.get("qhyy") != null ? ds.get("qhyy").toString() : "");
            dsVO.setGyjzhzwxx(ds.get("gyjzhzwxx") != null ? ds.get("gyjzhzwxx").toString() : "");
            dsVO.setZhcs(ds.get("zhcs") != null ? ds.get("zhcs").toString() : "");
            dsVO.setQhbwgd(ds.get("qhbwgd") != null ? ds.get("qhbwgd").toString() : "");
            dsVO.setRsmj(ds.get("rsmj") != null ? ds.get("rsmj").toString() : "");
            dsVO.setZqms(ds.get("zqms") != null ? ds.get("zqms").toString() : "");
            dsVO.setZqsdyj(ds.get("zqsdyj") != null ? ds.get("zqsdyj").toString() : "");
            //灾情等级
            if (ds.get("zqdj") != "" && ds.get("zqdj") != null) {
                List zqdj = (List) ds.get("zqdj");
                if (zqdj.size() >= 1) {
                    dsVO.setZqdj(zqdj.get(zqdj.size() - 1).toString());
                }
            }
            //燃烧物质
            if (ds.get("rswz") != "" && ds.get("rswz") != null) {
                List rswz = (List) ds.get("rswz");
                if (rswz.size() >= 1) {
                    dsVO.setRswz(rswz.get(rswz.size() - 1).toString());
                }
            }
            if (ds.get("zqid") != null && ds.get("zqid") != "") { //灾情修改
                dsVO.setZqid(ds.get("zqid").toString());
                dsVO.setXgrid(digitalplanlistVO.getZzrid());
                dsVO.setXgrmc(digitalplanlistVO.getZzrmc());
                dsVO.setXgsj("1");
                dsVO.setDeleteFlag("N");
                disastersetDAO.doUpdateByVO(dsVO);
            } else { //灾情新增
                dsVO.setJdh(digitalplanlistVO.getJdh());
                dsVO.setDatasource(digitalplanlistVO.getDatasource());
                dsVO.setCjrid(digitalplanlistVO.getZzrid());
                dsVO.setCjrmc(digitalplanlistVO.getZzrmc());
                dsVO.setDeleteFlag("N");
                disastersetDAO.doInsertByVO(dsVO);
            }
            //力量部署
            List forceList = (List) ds.get("forcedevList");
            for (int k = 0; k < forceList.size(); k++) {
                Map fd = (Map) forceList.get(k);
                ForcedevVO fdVO = new ForcedevVO();
                fdVO.setZqid(dsVO.getZqid());
                fdVO.setDjfalx(fd.get("djfalx") != null ? fd.get("djfalx").toString() : "");
                fdVO.setDzid(fd.get("dzid") != null ? fd.get("dzid").toString() : "");
                fdVO.setTkwz(fd.get("tkwz") != null ? fd.get("tkwz").toString() : "");
                fdVO.setClzbts(fd.get("clzbts") != null ? fd.get("clzbts").toString() : "");
                if (fd.get("uuid") != null && fd.get("uuid") != "") { //力量部署修改
                    fdVO.setUuid(fd.get("uuid").toString());
                    fdVO.setXgrid(digitalplanlistVO.getZzrid());
                    fdVO.setXgrmc(digitalplanlistVO.getZzrmc());
                    fdVO.setDeleteFlag("N");
                    forcedevDAO.doUpdateByVO(fdVO);
                } else { //力量部署新增
                    fdVO.setJdh(digitalplanlistVO.getJdh());
                    fdVO.setDatasource(digitalplanlistVO.getDatasource());
                    fdVO.setCjrid(digitalplanlistVO.getZzrid());
                    fdVO.setCjrmc(digitalplanlistVO.getZzrmc());
                    fdVO.setDeleteFlag("N");
                    forcedevDAO.doInsertByVO(fdVO);
                }
            }
            //要点提示
            Map kp = (Map) ds.get("keypointsMap");
            if ((kp.get("zsyd") != "" && kp.get("zsyd") != null) || (kp.get("tbjs") != "" && kp.get("tbjs") != null)) {
                KeypointsVO kpVO = new KeypointsVO();
                kpVO.setZqid(dsVO.getZqid());
                kpVO.setZsyd(kp.get("zsyd") != null ? kp.get("zsyd").toString() : "");
                kpVO.setTbjs(kp.get("tbjs") != null ? kp.get("tbjs").toString() : "");
                if (kp.get("uuid") != null && kp.get("uuid") != "") { //要点提示修改
                    kpVO.setUuid(kp.get("uuid").toString());
                    kpVO.setXgrid(digitalplanlistVO.getZzrid());
                    kpVO.setXgrmc(digitalplanlistVO.getZzrmc());
                    kpVO.setDeleteFlag("N");
                    keypointsDAO.doUpdateByVO(kpVO);
                } else { //要点提示新增
                    kpVO.setJdh(digitalplanlistVO.getJdh());
                    kpVO.setDatasource(digitalplanlistVO.getDatasource());
                    kpVO.setCjrid(digitalplanlistVO.getZzrid());
                    kpVO.setCjrmc(digitalplanlistVO.getZzrmc());
                    kpVO.setDeleteFlag("N");
                    keypointsDAO.doInsertByVO(kpVO);
                }
            }
        }

        return digitalplanlistVO;
    }

    /***
     * @Description: 删除：预案
     * @Param: [voList]
     * @Return: java.util.List<com.syfri.digitalplan.model.digitalplan.DigitalplanlistVO>
     * @Author: liurui
     * @Modified By:
     * @Date: 2018/5/26 13:53
     */
    @Override
    public int doDeleteDigitalplan(List<DigitalplanlistVO> digitalplanList, DigitalplanlistVO digitalplanVo) {
        String xgrid = digitalplanVo.getXgrid();
        String xgrmc = digitalplanVo.getXgrmc();
        int num = 0;
        if (digitalplanList.size() > 0) {
            for (DigitalplanlistVO dpVo : digitalplanList) {
                List<DisastersetVO> disasterList = disastersetDAO.doFindByPlanId(dpVo.getUuid());
                if (disasterList.size() > 0) {
                    for (DisastersetVO dsVo : disasterList) {
                        ForcedevVO fdVo = new ForcedevVO();
                        fdVo.setZqid(dsVo.getZqid());
                        fdVo.setXgrid(xgrid);
                        fdVo.setXgrmc(xgrmc);
                        fdVo.setDeleteFlag("Y");
                        forcedevDAO.doDeleteByVO(fdVo);//力量部署删除

                        KeypointsVO kpVo = new KeypointsVO();
                        kpVo.setZqid(dsVo.getZqid());
                        kpVo.setXgrid(xgrid);
                        kpVo.setXgrmc(xgrmc);
                        kpVo.setDeleteFlag("Y");
                        keypointsDAO.doDeleteByVO(kpVo);//要点提示删除
                    }
                }
                DisastersetVO dsVo = new DisastersetVO();
                dsVo.setYaid(dpVo.getUuid());
                dsVo.setXgrid(xgrid);
                dsVo.setXgrmc(xgrmc);
                dsVo.setDeleteFlag("Y");
                disastersetDAO.doDeleteByVO(dsVo);//灾情设定删除

                dpVo.setXgrid(xgrid);
                dpVo.setXgrmc(xgrmc);
                dpVo.setDeleteFlag("Y");
                num = num + digitalplanlistDAO.doUpdateByVO(dpVo);//预案删除

                distributeDAO.doDeleteByYaid(dpVo.getUuid());//分发删除
            }
        }
        return num;
    }

    /***
     * @Description: 查询：通过重点单位id查询建筑分区list
     * @Param: [zddwid]
     * @Return: java.util.List<com.syfri.digitalplan.model.buildingzoning.BuildingVO>
     * @Author: liurui
     * @Modified By:
     * @Date: 2018/5/23 10:20
     */
    public List<BuildingVO> doSearchJzListByZddwId(BuildingVO buildingVO) {
        List<BuildingVO> resultList = buildingDAO.doSearchJzListByZddwId(buildingVO);
        return resultList;
    }

    /***
     * @Description: 查询：通过重点单位id查询预案list
     * @Param: [zddwid]
     * @Return: java.util.List<com.syfri.digitalplan.model.buildingzoning.BuildingVO>
     * @Author: liurui
     * @Modified By:
     * @Date: 2018/5/23 10:20
     */
    public List<DigitalplanlistVO> doFindListByZddwId(String zddwId) {
        List<DigitalplanlistVO> resultList = digitalplanlistDAO.doFindListByZddwId(zddwId);
        return resultList;
    }

    /*审核加载预案List
   * by yushch
   * 20180604
   **/
    public List<DigitalplanlistVO> doSearchApproveListByVO(DigitalplanlistVO vo) {
        return digitalplanlistDAO.doSearchApproveListByVO(vo);
    }

    public String createPlanCode(String jgid, String zhlx) {
        String yabmPre = jgid + zhlx;
        int count = digitalplanlistDAO.doFindCountByJgAndZhlx(yabmPre);
        String yabm = yabmPre + String.format("%04d", count + 1);

        return yabm;
    }

    @Override
    public List<YaxxzlVO> doFindHisPlanListByVo(DigitalplanlistVO vo) {
        List<YaxxzlVO> resultList = new ArrayList<>();
        String yaid = vo.getUuid();
        String yajdh = vo.getJdh();
        YaxxzlVO yaxxzlVO = new YaxxzlVO();
        yaxxzlVO.setYaid(yaid);
        List<YaxxzlVO> yaxxzlList =yaxxzlDAO.doSearchListByVO(yaxxzlVO);
        resultList.addAll(yaxxzlList);
        if(resultList.size() > 0){
            for( int i = 0 ; i < resultList.size() ; i++) {
                resultList.get(i).setYajdh(yajdh);
            }
        }
        return resultList;
    }

    @Override
    public List<DigitalplanlistVO> doFindListWithFJListByVo(DigitalplanlistVO vo) {
        List<DigitalplanlistVO> resultList = digitalplanlistDAO.doSearchListByVO(vo);
        if(resultList.size() > 0){
            for(int i=0;i<resultList.size();i++){
                String yaid = resultList.get(i).getUuid();
                String kzm = ".zip";
                YafjxzVO yafjxzVO = new YafjxzVO();
                yafjxzVO.setYaid(yaid);
                yafjxzVO.setKzm(kzm);
                List<YafjxzVO> yafjxzList = yafjxzDAO.doSearchListByVO(yafjxzVO);
                resultList.get(i).setYafjxzList(yafjxzList);
            }
        }
        return resultList;
    }
}