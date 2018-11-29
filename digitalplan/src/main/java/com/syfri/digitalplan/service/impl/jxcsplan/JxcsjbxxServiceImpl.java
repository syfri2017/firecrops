package com.syfri.digitalplan.service.impl.jxcsplan;

import com.syfri.digitalplan.dao.digitalplan.DigitalplanlistDAO;
import com.syfri.digitalplan.dao.jxcsplan.JxcsdlyzDAO;
import com.syfri.digitalplan.dao.jxcsplan.JxcsxfssDAO;
import com.syfri.digitalplan.model.jxcsplan.JxcsdlyzVO;
import com.syfri.digitalplan.model.jxcsplan.JxcsjzxxVO;
import com.syfri.digitalplan.model.jxcsplan.JxcsxfssVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syfri.baseapi.service.impl.BaseServiceImpl;
import com.syfri.digitalplan.dao.jxcsplan.JxcsjbxxDAO;
import com.syfri.digitalplan.model.jxcsplan.JxcsjbxxVO;
import com.syfri.digitalplan.service.jxcsplan.JxcsjbxxService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Service("jxcsjbxxService")
public class JxcsjbxxServiceImpl extends BaseServiceImpl<JxcsjbxxVO> implements JxcsjbxxService {

    @Autowired
    private JxcsjbxxDAO jxcsjbxxDAO;
    @Autowired
    private JxcsdlyzDAO jxcsdlyzDAO;
    @Autowired
    private JxcsxfssDAO jxcsxfssDAO;
    @Autowired
    private DigitalplanlistDAO digitalplanlistDAO;


    @Override
    public JxcsjbxxDAO getBaseDAO() {
        return jxcsjbxxDAO;
    }

    public int doDeleteByVOList(List<JxcsjbxxVO> jxcsjbxxVOList) {
        int count = 0;
        for (JxcsjbxxVO vo : jxcsjbxxVOList) {
            //删除基本信息同时删除登录验证中间表中数据
            jxcsdlyzDAO.doDeleteDlyzByDwid(vo.getUuid());
            //删除基本信息同时逻辑删除基本信息-建筑信息中间表中数据
            jxcsdlyzDAO.doDeleteJbxxJzxxByDwid(vo.getUuid());
            //更新
            count = count + jxcsjbxxDAO.doUpdateByVO(vo);
        }
        return count;
    }

    //新增九小场所 add by yushch 20180920
    public JxcsjbxxVO doInsertJxcsByVO(JxcsjbxxVO vo) {
        jxcsjbxxDAO.doInsertByVO(vo);
        //向中间表插入统一社会信用代码
        JxcsdlyzVO jxcsdlyzVO1 = new JxcsdlyzVO();
        jxcsdlyzVO1.setDwid(vo.getUuid());
        jxcsdlyzVO1.setUnscid(vo.getUnscid());
        jxcsdlyzDAO.doInsertByVO(jxcsdlyzVO1);

        //向建筑表里插数据
        if(vo.getJzxxList().size() != 0){
            List<JxcsjzxxVO> jxcsjzxx = vo.getJzxxList();
            List<JxcsdlyzVO> jxcsdlyz =new ArrayList<JxcsdlyzVO>();
            for(int i=0;i<jxcsjzxx.size();i++){
                JxcsdlyzVO jxcsdlyzVO = new JxcsdlyzVO();
                jxcsdlyzVO.setJzid(jxcsjzxx.get(i).getJzid());
                jxcsdlyzVO.setDwid(vo.getUuid());
                jxcsdlyz.add(jxcsdlyzVO);
            }
            jxcsdlyzDAO.doBatchInsertByList(jxcsdlyz);
        }
        //向消防设施表插入数据
        if(vo.getXfssList().size() != 0){
            List<JxcsxfssVO> jxcsxfss = vo.getXfssList();
            for(int i=0;i<jxcsxfss.size();i++){
                jxcsxfss.get(i).setDwid(vo.getUuid());
                jxcsxfss.get(i).setCjrid(vo.getCjrid());
                jxcsxfss.get(i).setCjrmc(vo.getCjrmc());
                jxcsxfss.get(i).setJdh(vo.getJdh());
                jxcsxfss.get(i).setDatasource(vo.getDatasource());
            }
            jxcsxfssDAO.doBatchInsertByList(jxcsxfss);
        }
        return vo;
    }

    //编辑九小场所 yushch 20180929
    public JxcsjbxxVO doUpdateJxcsByVO(JxcsjbxxVO vo){
        jxcsjbxxDAO.doUpdateByVO(vo);
        String dwid = vo.getUuid();
        //修改dlyz中间中的统一社会信用代码 by yushch 20181121
        JxcsdlyzVO jxcsdlyzVO1 = new JxcsdlyzVO();
        jxcsdlyzVO1.setDwid(vo.getUuid());
        jxcsdlyzVO1.setUnscid(vo.getUnscid());
        int count = jxcsdlyzDAO.doUpdateUnscidByVO(jxcsdlyzVO1);
        if(count == 0){
            jxcsdlyzDAO.doInsertByVO(jxcsdlyzVO1);
        }
        //根据单位id物理删除中间表中信息
        jxcsdlyzDAO.doDeleteByDwid(dwid);
        //重新创建建筑中间表信息
        if(vo.getJzxxList().size() != 0){
            List<JxcsjzxxVO> jxcsjzxx = vo.getJzxxList();
            List<JxcsdlyzVO> jxcsdlyz =new ArrayList<JxcsdlyzVO>();
            for(int i=0;i<jxcsjzxx.size();i++){
                JxcsdlyzVO jxcsdlyzVO = new JxcsdlyzVO();
                jxcsdlyzVO.setJzid(jxcsjzxx.get(i).getJzid());
                jxcsdlyzVO.setDwid(vo.getUuid());
                jxcsdlyz.add(jxcsdlyzVO);
            }
            jxcsdlyzDAO.doBatchInsertByList(jxcsdlyz);
        }

    //消防设施
        //获取新消防设施list
        List<JxcsxfssVO> xfssList = vo.getXfssList();
        //获取旧的消防设施list
        List<JxcsxfssVO> xfssList_old = jxcsxfssDAO.doFindXfssByDwid(dwid);
        //删除旧的有，新的没有的消防设施
        for(JxcsxfssVO vo_old : xfssList_old){
            Boolean flag = true;
            for(JxcsxfssVO vo_new : xfssList){
                if(vo_old.getXfssid().equals(vo_new.getXfssid()) && vo_new.getXfssid() != null){
                    flag = false;
                }
            }
            if(flag){
                vo_old.setDeleteFlag("Y");
                vo_old.setXgrid(vo.getXgrid());
                vo_old.setXgrmc(vo.getXgrmc());
                jxcsxfssDAO.doUpdateByVO(vo_old);
            }
        }
        //新增或修改消防设施
        for(JxcsxfssVO vo_new : xfssList){
            if(!StringUtils.isEmpty(vo_new.getXfssid())){
                vo_new.setXgrid(vo.getXgrid());
                vo_new.setXgrmc(vo.getXgrmc());
                vo_new.setJdh(vo.getJdh());
                vo_new.setDatasource(vo.getDatasource());
                jxcsxfssDAO.doUpdateByVO(vo_new);
            }else {
                vo_new.setDwid(vo.getUuid());
                vo_new.setCjrid(vo.getXgrid());
                vo_new.setCjrmc(vo.getXgrmc());
                vo_new.setJdh(vo.getJdh());
                vo_new.setDatasource(vo.getDatasource());
                /*
                List<JxcsxfssVO> listNew = new ArrayList<>();
                listNew.add(vo_new);
                jxcsxfssDAO.doBatchInsertByList(listNew);
                */
                jxcsxfssDAO.doInsertByVO(vo_new);
            }
        }
        return vo;
    }

    @Override
    public List<JxcsjbxxVO> doSearchApproveListByVO(JxcsjbxxVO vo) {
        return this.jxcsjbxxDAO.doSearchApproveListByVO(vo);
    }

    @Override
    public JxcsjbxxVO doApproveUpdate(JxcsjbxxVO vo) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String shsj = sdf.format(date);
        vo.setShsj(shsj);
        String shzt = vo.getShzt();
        //如果选择“未通过”，预案状态变更为已驳回
        if (shzt.equals("02")) {
            vo.setSjzt("04");
        }
        //如果选择“已通过”，预案状态变更为已审核
        else if (shzt.equals("03")) {
            vo.setSjzt("05");
        }
        jxcsjbxxDAO.doUpdateByVO(vo);
        String shztmc = digitalplanlistDAO.doFindShztmcByShzt(shzt);
        vo.setShztmc(shztmc);
        return vo;
    }

    //查询九小场所基本信息 同时查询中间表统一社会信用代码 by yushch 20181121
    public JxcsjbxxVO doFindJbxxById(String id){
        JxcsjbxxVO vo = jxcsjbxxDAO.doFindById(id);
        String unscid = jxcsdlyzDAO.doFindUnscidByDwid(id);
        vo.setUnscid(unscid);
        return vo;

    }
}