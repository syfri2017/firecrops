package com.syfri.digitalplan.service.impl.firefacilities;

import com.syfri.baseapi.model.ValueObject;
import com.syfri.digitalplan.model.firefacilities.*;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syfri.baseapi.service.impl.BaseServiceImpl;
import com.syfri.digitalplan.dao.firefacilities.FirefacilitiesDAO;
import com.syfri.digitalplan.service.firefacilities.FirefacilitiesService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Service("firefacilitiesService")
public class FirefacilitiesServiceImpl extends BaseServiceImpl<FirefacilitiesVO> implements FirefacilitiesService {

    @Autowired
    private FirefacilitiesDAO firefacilitiesDAO;

    @Override
    public FirefacilitiesDAO getBaseDAO() {
        return firefacilitiesDAO;
    }

    /*
    * 查询安全设施列表，分类关联从表查询从表信息
    * 返回map<消防设施类型，类型对应的从表信息list>
    * by yushch 20180509
    * */
    public Map<String, List> doFindlist(FirefacilitiesVO vo) {
        List<FirefacilitiesVO> firefacilities = firefacilitiesDAO.doSearchListByVO(vo);
        Map<String, List> map = new HashMap<String, List>();
        List aqck = new ArrayList();
        List sslt = new ArrayList();
        List xfdt = new ArrayList();
        List bnc = new ArrayList();
        List yjgb = new ArrayList();
        List xfbf = new ArrayList();
        List xfsx = new ArrayList();
        List xfsc = new ArrayList();
        List snxhs = new ArrayList();
        List swxhs = new ArrayList();
        List sbjhq = new ArrayList();
        List plxt = new ArrayList();
        List lqsxt = new ArrayList();
        List gdsp = new ArrayList();
        List bgdss = new ArrayList();
        List pmbf = new ArrayList();
        List pmxhs = new ArrayList();
        List gdpmp = new ArrayList();
        List pmfsq = new ArrayList();
        List pm_bgdss = new ArrayList();
        List gds = new ArrayList();
        List bgds = new ArrayList();
        List xfkzs = new ArrayList();
        List pycyk = new ArrayList();
        List fpyxt = new ArrayList();
        List fhfq = new ArrayList();
        List qtmhxt = new ArrayList();
        List gfmhxt = new ArrayList();
        List qtxfss = new ArrayList();
        for (FirefacilitiesVO data : firefacilities) {
            String xfsslx = data.getJbxx_xfsslx();
            String xfssid = data.getJbxx_xfssid();
            switch (xfsslx) {
                //安全疏散措施
                case "1000":
                    break;
                case "1001":
                    List list_1001 = firefacilitiesDAO.doFindAqckList(xfssid);
                    for (int i = 0; i < list_1001.size(); i++) {
                        aqck.add(list_1001.get(i));
                    }
                    break;
                case "1002":
                    List list_1002 = firefacilitiesDAO.doFindSsltList(xfssid);
                    for (int i = 0; i < list_1002.size(); i++) {
                        sslt.add(list_1002.get(i));
                    }
                    break;
                case "1003":
                    List list_1003 = firefacilitiesDAO.doFindXfdtList(xfssid);
                    for (int i = 0; i < list_1003.size(); i++) {
                        xfdt.add(list_1003.get(i));
                    }
                    break;
                case "1004":
                    List list_1004 = firefacilitiesDAO.doFindBncList(xfssid);
                    for (int i = 0; i < list_1004.size(); i++) {
                        bnc.add(list_1004.get(i));
                    }
                    break;
                case "1005":
                    List list_1005 = firefacilitiesDAO.doFindYjgbList(xfssid);
                    for (int i = 0; i < list_1005.size(); i++) {
                        yjgb.add(list_1005.get(i));
                    }
                    break;
                //消防水系统
                case "2000":
                    break;
                case "2001":
                    List list_2001 = firefacilitiesDAO.doFindXfbfList(xfssid);
                    for (int i = 0; i < list_2001.size(); i++) {
                        xfbf.add(list_2001.get(i));
                    }
                    break;
                case "2002":
                    List list_2002 = firefacilitiesDAO.doFindXfsxList(xfssid);
                    for (int i = 0; i < list_2002.size(); i++) {
                        xfsx.add(list_2002.get(i));
                    }
                    break;
                case "2003":
                    List list_2003 = firefacilitiesDAO.doFindXfscList(xfssid);
                    for (int i = 0; i < list_2003.size(); i++) {
                        xfsc.add(list_2003.get(i));
                    }
                    break;
                case "2004":
                    List list_2004 = firefacilitiesDAO.doFindSnxhsList(xfssid);
                    for (int i = 0; i < list_2004.size(); i++) {
                        snxhs.add(list_2004.get(i));
                    }
                    break;
                case "2005":
                    List list_2005 = firefacilitiesDAO.doFindSwxhsList(xfssid);
                    for (int i = 0; i < list_2005.size(); i++) {
                        swxhs.add(list_2005.get(i));
                    }
                    break;
                case "2006":
                    List list_2006 = firefacilitiesDAO.doFindSbjhqList(xfssid);
                    for (int i = 0; i < list_2006.size(); i++) {
                        sbjhq.add(list_2006.get(i));
                    }
                    break;
                case "2007":
                    List list_2007 = firefacilitiesDAO.doFindPlxtList(xfssid);
                    for (int i = 0; i < list_2007.size(); i++) {
                        plxt.add(list_2007.get(i));
                    }
                    break;
                case "2008":
                    List list_2008 = firefacilitiesDAO.doFindLqsxtList(xfssid);
                    for (int i = 0; i < list_2008.size(); i++) {
                        lqsxt.add(list_2008.get(i));
                    }
                    break;
                case "2009":
                    List list_2009 = firefacilitiesDAO.doFindGdspList(xfssid);
                    for (int i = 0; i < list_2009.size(); i++) {
                        gdsp.add(list_2009.get(i));
                    }
                    break;
                case "2010":
                    List list_2010 = firefacilitiesDAO.doFindBgdssList(xfssid);
                    for (int i = 0; i < list_2010.size(); i++) {
                        bgdss.add(list_2010.get(i));
                    }
                    break;
                //泡沫系统
                case "3000":
                    break;
                case "3001":
                    List list_3001 = firefacilitiesDAO.doFindPmbfList(xfssid);
                    for (int i = 0; i < list_3001.size(); i++) {
                        pmbf.add(list_3001.get(i));
                    }
                    break;
                case "3002":
                    List list_3002 = firefacilitiesDAO.doFindPmxhsList(xfssid);
                    for (int i = 0; i < list_3002.size(); i++) {
                        pmxhs.add(list_3002.get(i));
                    }
                    break;
                case "3003":
                    List list_3003 = firefacilitiesDAO.doFindGdpmpList(xfssid);
                    for (int i = 0; i < list_3003.size(); i++) {
                        gdpmp.add(list_3003.get(i));
                    }
                    break;
                case "3004":
                    List list_3004 = firefacilitiesDAO.doFindPmfsqList(xfssid);
                    for (int i = 0; i < list_3004.size(); i++) {
                        pmfsq.add(list_3004.get(i));
                    }
                    break;
                case "3005":
                    List list_3005 = firefacilitiesDAO.doFindPmBgdssList(xfssid);
                    for (int i = 0; i < list_3005.size(); i++) {
                        pm_bgdss.add(list_3005.get(i));
                    }
                    break;
                //蒸汽灭火系统
                case "4000":
                    break;
                case "4001":
                    List list_4001 = firefacilitiesDAO.doFindGdsList(xfssid);
                    for (int i = 0; i < list_4001.size(); i++) {
                        gds.add(list_4001.get(i));
                    }
                    break;
                case "4002":
                    List list_4002 = firefacilitiesDAO.doFindBgdsList(xfssid);
                    for (int i = 0; i < list_4002.size(); i++) {
                        bgds.add(list_4002.get(i));
                    }
                    break;
                //消防控制室
                case "5000":
                    List list_5000 = firefacilitiesDAO.doFindXfkzsList(xfssid);
                    for (int i = 0; i < list_5000.size(); i++) {
                        xfkzs.add(list_5000.get(i));
                    }
                    break;
                //防排烟设施
                case "6000":
                    break;
                case "6001":
                    List list_6001 = firefacilitiesDAO.doFindPycykList(xfssid);
                    for (int i = 0; i < list_6001.size(); i++) {
                        pycyk.add(list_6001.get(i));
                    }
                    break;
                case "6002":
                    List list_6002 = firefacilitiesDAO.doFindFpyxtList(xfssid);
                    for (int i = 0; i < list_6002.size(); i++) {
                        fpyxt.add(list_6002.get(i));
                    }
                    break;
                //防火分区
                case "7000":
                    List list_7000 = firefacilitiesDAO.doFindFhfqList(xfssid);
                    for (int i = 0; i < list_7000.size(); i++) {
                        fhfq.add(list_7000.get(i));
                    }
                    break;
                //其他灭火系统
                case "8000":
                    break;
                case "8001":
                    List list_8001 = firefacilitiesDAO.doFindQtmhxtList(xfssid);
                    for (int i = 0; i < list_8001.size(); i++) {
                        qtmhxt.add(list_8001.get(i));
                    }
                    break;
                case "8002":
                    List list_8002 = firefacilitiesDAO.doFindGfmhxtList(xfssid);
                    for (int i = 0; i < list_8002.size(); i++) {
                        gfmhxt.add(list_8002.get(i));
                    }
                    break;
                //其他消防设施
                case "9000":
                    List list_9000 = firefacilitiesDAO.doFindQtxfssList(xfssid);
                    for (int i = 0; i < list_9000.size(); i++) {
                        qtxfss.add(list_9000.get(i));
                    }
                    break;
            }
        }
        if (!aqck.isEmpty())
            map.put("1001", aqck);
        if (!sslt.isEmpty())
            map.put("1002", sslt);
        if (!xfdt.isEmpty())
            map.put("1003", xfdt);
        if (!bnc.isEmpty())
            map.put("1004", bnc);
        if (!yjgb.isEmpty())
            map.put("1005", yjgb);
        if (!xfbf.isEmpty())
            map.put("2001", xfbf);
        if (!xfsx.isEmpty())
            map.put("2002", xfsx);
        if (!xfsc.isEmpty())
            map.put("2003", xfsc);
        if (!snxhs.isEmpty())
            map.put("2004", snxhs);
        if (!swxhs.isEmpty())
            map.put("2005", swxhs);
        if (!sbjhq.isEmpty())
            map.put("2006", sbjhq);
        if (!plxt.isEmpty())
            map.put("2007", plxt);
        if (!lqsxt.isEmpty())
            map.put("2008", lqsxt);
        if (!gdsp.isEmpty())
            map.put("2009", gdsp);
        if (!bgdss.isEmpty())
            map.put("2010", bgdss);
        if (!pmbf.isEmpty())
            map.put("3001", pmbf);
        if (!pmxhs.isEmpty())
            map.put("3002", pmxhs);
        if (!gdpmp.isEmpty())
            map.put("3003", gdpmp);
        if (!pmfsq.isEmpty())
            map.put("3004", pmfsq);
        if (!pm_bgdss.isEmpty())
            map.put("3005", pm_bgdss);
        if (!gds.isEmpty())
            map.put("4001", gds);
        if (!bgds.isEmpty())
            map.put("4002", bgds);
        if (!xfkzs.isEmpty())
            map.put("5000", xfkzs);
        if (!pycyk.isEmpty())
            map.put("6001", pycyk);
        if (!fpyxt.isEmpty())
            map.put("6002", fpyxt);
        if (!fhfq.isEmpty())
            map.put("7000", fhfq);
        if (!qtmhxt.isEmpty())
            map.put("8001", qtmhxt);
        if (!gfmhxt.isEmpty())
            map.put("8002", gfmhxt);
        if (!qtxfss.isEmpty())
            map.put("9000", qtxfss);
        return map;
    }

    public ValueObject doFindXfssDetail(FirefacilitiesVO vo) {
        ValueObject detailVo = new ValueObject();
        String xfsslx = vo.getJbxx_xfsslx();
        String xfssid = vo.getJbxx_xfssid();
        switch (xfsslx) {
            //安全疏散措施
            case "1000":
                break;
            case "1001":
                List<Firefacilities_aqsscs_aqckVO> list_1001 = firefacilitiesDAO.doFindAqckList(xfssid);
                if (list_1001.size() > 0) {
                    detailVo = list_1001.get(0);
                }
                break;
            case "1002":
                List<Firefacilities_aqsscs_ssltVO> list_1002 = firefacilitiesDAO.doFindSsltList(xfssid);
                if (list_1002.size() > 0) {
                    detailVo = list_1002.get(0);
                }
                break;
            case "1003":
                List<Firefacilities_aqsscs_xfdtVO> list_1003 = firefacilitiesDAO.doFindXfdtList(xfssid);
                if (list_1003.size() > 0) {
                    detailVo = list_1003.get(0);
                }
                break;
            case "1004":
                List<Firefacilities_aqsscs_bncVO> list_1004 = firefacilitiesDAO.doFindBncList(xfssid);
                if (list_1004.size() > 0) {
                    detailVo = list_1004.get(0);
                }
                break;
            case "1005":
                List<Firefacilities_aqsscs_yjgbVO> list_1005 = firefacilitiesDAO.doFindYjgbList(xfssid);
                if (list_1005.size() > 0) {
                    detailVo = list_1005.get(0);
                }
                break;
            //消防水系统
            case "2000":
                break;
            case "2001":
                List<Firefacilities_xfsxt_xfbfVO> list_2001 = firefacilitiesDAO.doFindXfbfList(xfssid);
                if (list_2001.size() > 0) {
                    detailVo = list_2001.get(0);
                }
                break;
            case "2002":
                List<Firefacilities_xfsxt_xfsxVO> list_2002 = firefacilitiesDAO.doFindXfsxList(xfssid);
                if (list_2002.size() > 0) {
                    detailVo = list_2002.get(0);
                }
                break;
            case "2003":
                List<Firefacilities_xfsxt_xfscVO> list_2003 = firefacilitiesDAO.doFindXfscList(xfssid);
                if (list_2003.size() > 0) {
                    detailVo = list_2003.get(0);
                }
                break;
            case "2004":
                List<Firefacilities_xfsxt_snxhsVO> list_2004 = firefacilitiesDAO.doFindSnxhsList(xfssid);
                if (list_2004.size() > 0) {
                    detailVo = list_2004.get(0);
                }
                break;
            case "2005":
                List<Firefacilities_xfsxt_swxhsVO> list_2005 = firefacilitiesDAO.doFindSwxhsList(xfssid);
                if (list_2005.size() > 0) {
                    detailVo = list_2005.get(0);
                }
                break;
            case "2006":
                List<Firefacilities_xfsxt_sbjhqVO> list_2006 = firefacilitiesDAO.doFindSbjhqList(xfssid);
                if (list_2006.size() > 0) {
                    detailVo = list_2006.get(0);
                }
            case "2007":
                List<Firefacilities_xfsxt_plxtVO> list_2007 = firefacilitiesDAO.doFindPlxtList(xfssid);
                if (list_2007.size() > 0) {
                    detailVo = list_2007.get(0);
                }
                break;
            case "2008":
                List<Firefacilities_xfsxt_lqsxtVO> list_2008 = firefacilitiesDAO.doFindLqsxtList(xfssid);
                if (list_2008.size() > 0) {
                    detailVo = list_2008.get(0);
                }
            case "2009":
                List<Firefacilities_xfsxt_gdspVO> list_2009 = firefacilitiesDAO.doFindGdspList(xfssid);
                if (list_2009.size() > 0) {
                    detailVo = list_2009.get(0);
                }
                break;
            case "2010":
                List<Firefacilities_xfsxt_bgdssVO> list_2010 = firefacilitiesDAO.doFindBgdssList(xfssid);
                if (list_2010.size() > 0) {
                    detailVo = list_2010.get(0);
                }
                break;
            //泡沫系统
            case "3000":
                break;
            case "3001":
                List<Firefacilities_pmxt_pmbfVO> list_3001 = firefacilitiesDAO.doFindPmbfList(xfssid);
                if (list_3001.size() > 0) {
                    detailVo = list_3001.get(0);
                }
                break;
            case "3002":
                List<Firefacilities_pmxt_pmxhsVO> list_3002 = firefacilitiesDAO.doFindPmxhsList(xfssid);
                if (list_3002.size() > 0) {
                    detailVo = list_3002.get(0);
                }
                break;
            case "3003":
                List<Firefacilities_pmxt_gdpmpVO> list_3003 = firefacilitiesDAO.doFindGdpmpList(xfssid);
                if (list_3003.size() > 0) {
                    detailVo = list_3003.get(0);
                }
            case "3004":
                List<Firefacilities_pmxt_pmfsqVO> list_3004 = firefacilitiesDAO.doFindPmfsqList(xfssid);
                if (list_3004.size() > 0) {
                    detailVo = list_3004.get(0);
                }
                break;
            case "3005":
                List<Firefacilities_pmxt_bgdssVO> list_3005 = firefacilitiesDAO.doFindPmBgdssList(xfssid);
                if (list_3005.size() > 0) {
                    detailVo = list_3005.get(0);
                }
                break;
            //蒸汽灭火系统
            case "4000":
                break;
            case "4001":
                List<Firefacilities_zqmhxt_gdsVO> list_4001 = firefacilitiesDAO.doFindGdsList(xfssid);
                if (list_4001.size() > 0) {
                    detailVo = list_4001.get(0);
                }
                break;
            case "4002":
                List<Firefacilities_zqmhxt_bgdsVO> list_4002 = firefacilitiesDAO.doFindBgdsList(xfssid);
                if (list_4002.size() > 0) {
                    detailVo = list_4002.get(0);
                }
                break;
            //消防控制室
            case "5000":
                List<Firefacilities_xfkzsVO> list_5000 = firefacilitiesDAO.doFindXfkzsList(xfssid);
                if (list_5000.size() > 0) {
                    detailVo = list_5000.get(0);
                }
                break;
            //防排烟设施
            case "6000":
                break;
            case "6001":
                List<Firefacilities_fpycs_pycykVO> list_6001 = firefacilitiesDAO.doFindPycykList(xfssid);
                if (list_6001.size() > 0) {
                    detailVo = list_6001.get(0);
                }
                break;
            case "6002":
                List<Firefacilities_fpycs_fpyxtVO> list_6002 = firefacilitiesDAO.doFindFpyxtList(xfssid);
                if (list_6002.size() > 0) {
                    detailVo = list_6002.get(0);
                }
                break;
            //防火分区
            case "7000":
                List<Firefacilities_fhfqVO> list_7000 = firefacilitiesDAO.doFindFhfqList(xfssid);
                if (list_7000.size() > 0) {
                    detailVo = list_7000.get(0);
                }
                break;
            //其他灭火系统
            case "8000":
                break;
            case "8001":
                List<Firefacilities_qtmhxt_qtmhxtVO> list_8001 = firefacilitiesDAO.doFindQtmhxtList(xfssid);
                if (list_8001.size() > 0) {
                    detailVo = list_8001.get(0);
                }
                break;
            case "8002":
                List<Firefacilities_qtmhxt_gfmhxtVO> list_8002 = firefacilitiesDAO.doFindGfmhxtList(xfssid);
                if (list_8002.size() > 0) {
                    detailVo = list_8002.get(0);
                }
                break;
            //其他消防设施
            case "9000":
                List<Firefacilities_qtxfssVO> list_9000 = firefacilitiesDAO.doFindQtxfssList(xfssid);
                if (list_9000.size() > 0) {
                    detailVo = list_9000.get(0);
                }
                break;
        }
        return detailVo;
    }

    //消防设施新增
    public FirefacilitiesVO doInsertFirefacilities(FirefacilitiesVO firefacilitiesVO) {
        int count = 0;
        count = firefacilitiesDAO.doInsertByVO(firefacilitiesVO);//主表新增
        if (count > 0) {
            ((FirefacilitiesService) AopContext.currentProxy()).doInsertFirefacilitiesDetail(firefacilitiesVO);//从表新增
        }
        return firefacilitiesVO;
    }

    //消防设施从表新增
    public FirefacilitiesVO doInsertFirefacilitiesDetail(FirefacilitiesVO firefacilitiesVO) {
        String xfsslx = firefacilitiesVO.getJbxx_xfsslx();
        String xfssid = firefacilitiesVO.getJbxx_xfssid();
        String jdh = firefacilitiesVO.getJbxx_jdh();
        String datasource = firefacilitiesVO.getJbxx_datasource();
        Map detailMap = firefacilitiesVO.getDetailMap();
        switch (xfsslx) {
            //安全疏散措施
            case "1000":
                break;
            case "1001":
                Firefacilities_aqsscs_aqckVO aqckVO = new Firefacilities_aqsscs_aqckVO();
                aqckVO.setXfssid(xfssid);
                aqckVO.setJdh(jdh);
                aqckVO.setDatasource(datasource);
                aqckVO.setSl(detailMap.get("sl") != null ? detailMap.get("sl").toString() : "");
                aqckVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                firefacilitiesDAO.doInsertAqckByVO(aqckVO);
                break;
            case "1002":
                Firefacilities_aqsscs_ssltVO ssltVO = new Firefacilities_aqsscs_ssltVO();
                ssltVO.setXfssid(xfssid);
                ssltVO.setJdh(jdh);
                ssltVO.setDatasource(datasource);
                ssltVO.setSl(detailMap.get("sl") != null ? detailMap.get("sl").toString() : "");
                ssltVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                firefacilitiesDAO.doInsertSsltByVO(ssltVO);
                break;
            case "1003":
                Firefacilities_aqsscs_xfdtVO xfdtVO = new Firefacilities_aqsscs_xfdtVO();
                xfdtVO.setXfssid(xfssid);
                xfdtVO.setJdh(jdh);
                xfdtVO.setDatasource(datasource);
                xfdtVO.setSl(detailMap.get("sl") != null ? detailMap.get("sl").toString() : "");
                xfdtVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                firefacilitiesDAO.doInsertXfdtByVO(xfdtVO);
                break;
            case "1004":
                Firefacilities_aqsscs_bncVO bncVO = new Firefacilities_aqsscs_bncVO();
                bncVO.setXfssid(xfssid);
                bncVO.setJdh(jdh);
                bncVO.setDatasource(datasource);
                bncVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                firefacilitiesDAO.doInsertBncByVO(bncVO);
                break;
            case "1005":
                Firefacilities_aqsscs_yjgbVO yjgbVO = new Firefacilities_aqsscs_yjgbVO();
                yjgbVO.setXfssid(xfssid);
                yjgbVO.setJdh(jdh);
                yjgbVO.setDatasource(datasource);
                yjgbVO.setYwyjgb(detailMap.get("ywyjgb") != null ? detailMap.get("ywyjgb").toString() : "");
                yjgbVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                firefacilitiesDAO.doInsertYjgbByVO(yjgbVO);
                break;
            //消防水系统
            case "2000":
                break;
            case "2001":
                Firefacilities_xfsxt_xfbfVO xfbfVO = new Firefacilities_xfsxt_xfbfVO();
                xfbfVO.setXfssid(xfssid);
                xfbfVO.setJdh(jdh);
                xfbfVO.setDatasource(datasource);
                xfbfVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                xfbfVO.setPlbsl(detailMap.get("plbsl") != null ? detailMap.get("plbsl").toString() : "");
                xfbfVO.setPlbzdll(detailMap.get("plbzdll") != null ? detailMap.get("plbzdll").toString() : "");
                xfbfVO.setXhssl(detailMap.get("xhssl") != null ? detailMap.get("xhssl").toString() : "");
                xfbfVO.setXhszdll(detailMap.get("xhszdll") != null ? detailMap.get("xhszdll").toString() : "");
                firefacilitiesDAO.doInsertXfbfByVO(xfbfVO);
                break;
            case "2002":
                Firefacilities_xfsxt_xfsxVO xfsxVO = new Firefacilities_xfsxt_xfsxVO();
                xfsxVO.setXfssid(xfssid);
                xfsxVO.setJdh(jdh);
                xfsxVO.setDatasource(datasource);
                xfsxVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                xfsxVO.setBjsd(detailMap.get("bjsd") != null ? detailMap.get("bjsd").toString() : "");
                xfsxVO.setSxrl(detailMap.get("sxrl") != null ? detailMap.get("sxrl").toString() : "");
                firefacilitiesDAO.doInsertXfsxByVO(xfsxVO);
                break;
            case "2003":
                Firefacilities_xfsxt_xfscVO xfscVO = new Firefacilities_xfsxt_xfscVO();
                xfscVO.setXfssid(xfssid);
                xfscVO.setJdh(jdh);
                xfscVO.setDatasource(datasource);
                xfscVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                xfscVO.setYwqsj(detailMap.get("ywqsj") != null ? detailMap.get("ywqsj").toString() : "");
                xfscVO.setBjsd(detailMap.get("bjsd") != null ? detailMap.get("bjsd").toString() : "");
                xfscVO.setQsjwz(detailMap.get("qsjwz") != null ? detailMap.get("qsjwz").toString() : "");
                xfscVO.setSxrl(detailMap.get("sxrl") != null ? detailMap.get("sxrl").toString() : "");
                firefacilitiesDAO.doInsertXfscByVO(xfscVO);
                break;
            case "2004":
                Firefacilities_xfsxt_snxhsVO snxhsVO = new Firefacilities_xfsxt_snxhsVO();
                snxhsVO.setXfssid(xfssid);
                snxhsVO.setJdh(jdh);
                snxhsVO.setDatasource(datasource);
                snxhsVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                snxhsVO.setSl(detailMap.get("sl") != null ? detailMap.get("sl").toString() : "");
                snxhsVO.setSfky(detailMap.get("sfky") != null ? detailMap.get("sfky").toString() : "");
                firefacilitiesDAO.doInsertSnxhsByVO(snxhsVO);
                break;
            case "2005":
                Firefacilities_xfsxt_swxhsVO swxhsVO = new Firefacilities_xfsxt_swxhsVO();
                swxhsVO.setXfssid(xfssid);
                swxhsVO.setJdh(jdh);
                swxhsVO.setDatasource(datasource);
                swxhsVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                swxhsVO.setSl(detailMap.get("sl") != null ? detailMap.get("sl").toString() : "");
                swxhsVO.setSfky(detailMap.get("sfky") != null ? detailMap.get("sfky").toString() : "");
                firefacilitiesDAO.doInsertSwxhsByVO(swxhsVO);
                break;
            case "2006":
                Firefacilities_xfsxt_sbjhqVO sbjhqVO = new Firefacilities_xfsxt_sbjhqVO();
                sbjhqVO.setXfssid(xfssid);
                sbjhqVO.setJdh(jdh);
                sbjhqVO.setDatasource(datasource);
                sbjhqVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                sbjhqVO.setAzxs(detailMap.get("azxs") != null ? detailMap.get("azxs").toString() : "");
                sbjhqVO.setXh(detailMap.get("xh") != null ? detailMap.get("xh").toString() : "");
                sbjhqVO.setJskcc(detailMap.get("jskcc") != null ? detailMap.get("jskcc").toString() : "");
                firefacilitiesDAO.doInsertSbjhqByVO(sbjhqVO);
                break;
            case "2007":
                Firefacilities_xfsxt_plxtVO plxtVO = new Firefacilities_xfsxt_plxtVO();
                plxtVO.setXfssid(xfssid);
                plxtVO.setJdh(jdh);
                plxtVO.setDatasource(datasource);
                plxtVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                plxtVO.setYwplxt(detailMap.get("ywplxt") != null ? detailMap.get("ywplxt").toString() : "");
                firefacilitiesDAO.doInsertPlxtByVO(plxtVO);
                break;
            case "2008":
                Firefacilities_xfsxt_lqsxtVO lqsxtVO = new Firefacilities_xfsxt_lqsxtVO();
                lqsxtVO.setXfssid(xfssid);
                lqsxtVO.setJdh(jdh);
                lqsxtVO.setDatasource(datasource);
                lqsxtVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                lqsxtVO.setGsqd(detailMap.get("gsqd") != null ? detailMap.get("gsqd").toString() : "");
                lqsxtVO.setYwlqsxt(detailMap.get("ywlqsxt") != null ? detailMap.get("ywlqsxt").toString() : "");
                firefacilitiesDAO.doInsertLqsxtByVO(lqsxtVO);
                break;
            case "2009":
                Firefacilities_xfsxt_gdspVO gdspVO = new Firefacilities_xfsxt_gdspVO();
                gdspVO.setXfssid(xfssid);
                gdspVO.setJdh(jdh);
                gdspVO.setDatasource(datasource);
                gdspVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                gdspVO.setSl(detailMap.get("sl") != null ? detailMap.get("sl").toString() : "");
                gdspVO.setIsky(detailMap.get("isky") != null ? detailMap.get("isky").toString() : "");
                firefacilitiesDAO.doInsertGdspByVO(gdspVO);
                break;
            case "2010":
                Firefacilities_xfsxt_bgdssVO bgdssVO = new Firefacilities_xfsxt_bgdssVO();
                bgdssVO.setXfssid(xfssid);
                bgdssVO.setJdh(jdh);
                bgdssVO.setDatasource(datasource);
                bgdssVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                bgdssVO.setSl(detailMap.get("sl") != null ? detailMap.get("sl").toString() : "");
                firefacilitiesDAO.doInsertBgdssByVO(bgdssVO);
                break;
            //泡沫系统
            case "3000":
                break;
            case "3001":
                Firefacilities_pmxt_pmbfVO pmbfVO = new Firefacilities_pmxt_pmbfVO();
                pmbfVO.setXfssid(xfssid);
                pmbfVO.setJdh(jdh);
                pmbfVO.setDatasource(datasource);
                pmbfVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                pmbfVO.setPmylx(detailMap.get("pmylx") != null ? detailMap.get("pmylx").toString() : "");
                pmbfVO.setPmycl(detailMap.get("pmycl") != null ? detailMap.get("pmycl").toString() : "");
                pmbfVO.setPmbzdll(detailMap.get("pmbzdll") != null ? detailMap.get("pmbzdll").toString() : "");
                firefacilitiesDAO.doInsertPmbfByVO(pmbfVO);
                break;
            case "3002":
                Firefacilities_pmxt_pmxhsVO pmxhsVO = new Firefacilities_pmxt_pmxhsVO();
                pmxhsVO.setXfssid(xfssid);
                pmxhsVO.setJdh(jdh);
                pmxhsVO.setDatasource(datasource);
                pmxhsVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                pmxhsVO.setIsky(detailMap.get("isky") != null ? detailMap.get("isky").toString() : "");
                firefacilitiesDAO.doInsertPmxhsByVO(pmxhsVO);
                break;
            case "3003":
                Firefacilities_pmxt_gdpmpVO gdpmpVO = new Firefacilities_pmxt_gdpmpVO();
                gdpmpVO.setXfssid(xfssid);
                gdpmpVO.setJdh(jdh);
                gdpmpVO.setDatasource(datasource);
                gdpmpVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                gdpmpVO.setSl(detailMap.get("sl") != null ? detailMap.get("sl").toString() : "");
                gdpmpVO.setSfky(detailMap.get("sfky") != null ? detailMap.get("sfky").toString() : "");
                firefacilitiesDAO.doInsertGdpmpByVO(gdpmpVO);
                break;
            case "3004":
                Firefacilities_pmxt_pmfsqVO pmfsqVO = new Firefacilities_pmxt_pmfsqVO();
                pmfsqVO.setXfssid(xfssid);
                pmfsqVO.setJdh(jdh);
                pmfsqVO.setDatasource(datasource);
                pmfsqVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                pmfsqVO.setSl(detailMap.get("sl") != null ? detailMap.get("sl").toString() : "");
                pmfsqVO.setXh(detailMap.get("xh") != null ? detailMap.get("xh").toString() : "");
                pmfsqVO.setLl(detailMap.get("ll") != null ? detailMap.get("ll").toString() : "");
                firefacilitiesDAO.doInsertPmfsqByVO(pmfsqVO);
                break;
            case "3005":
                Firefacilities_pmxt_bgdssVO PmBgdssVO = new Firefacilities_pmxt_bgdssVO();
                PmBgdssVO.setXfssid(xfssid);
                PmBgdssVO.setJdh(jdh);
                PmBgdssVO.setDatasource(datasource);
                PmBgdssVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                PmBgdssVO.setSl(detailMap.get("sl") != null ? detailMap.get("sl").toString() : "");
                firefacilitiesDAO.doInsertPmBgdssByVO(PmBgdssVO);
                break;
            //蒸汽灭火系统
            case "4000":
                break;
            case "4001":
                Firefacilities_zqmhxt_gdsVO gdsVO = new Firefacilities_zqmhxt_gdsVO();
                gdsVO.setXfssid(xfssid);
                gdsVO.setJdh(jdh);
                gdsVO.setDatasource(datasource);
                gdsVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                gdsVO.setSl(detailMap.get("sl") != null ? detailMap.get("sl").toString() : "");
                gdsVO.setEdyl(detailMap.get("edyl") != null ? detailMap.get("edyl").toString() : "");
                firefacilitiesDAO.doInsertGdsByVO(gdsVO);
                break;
            case "4002":
                Firefacilities_zqmhxt_bgdsVO bgdsVO = new Firefacilities_zqmhxt_bgdsVO();
                bgdsVO.setXfssid(xfssid);
                bgdsVO.setJdh(jdh);
                bgdsVO.setDatasource(datasource);
                bgdsVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                bgdsVO.setSl(detailMap.get("sl") != null ? detailMap.get("sl").toString() : "");
                bgdsVO.setEdyl(detailMap.get("edyl") != null ? detailMap.get("edyl").toString() : "");
                firefacilitiesDAO.doInsertBgdsByVO(bgdsVO);
                break;
            //消防控制室
            case "5000":
                Firefacilities_xfkzsVO xfkzsVO = new Firefacilities_xfkzsVO();
                xfkzsVO.setXfssid(xfssid);
                xfkzsVO.setJdh(jdh);
                xfkzsVO.setDatasource(datasource);
                xfkzsVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                xfkzsVO.setIsldkz(detailMap.get("isldkz") != null ? detailMap.get("isldkz").toString() : "");
                xfkzsVO.setIszdbj(detailMap.get("iszdbj") != null ? detailMap.get("iszdbj").toString() : "");
                firefacilitiesDAO.doInsertXfkzsByVO(xfkzsVO);
                break;
            //防排烟设施
            case "6000":
                break;
            case "6001":
                Firefacilities_fpycs_pycykVO pycykVO = new Firefacilities_fpycs_pycykVO();
                pycykVO.setXfssid(xfssid);
                pycykVO.setJdh(jdh);
                pycykVO.setDatasource(datasource);
                pycykVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                firefacilitiesDAO.doInsertPycykByVO(pycykVO);
                break;
            case "6002":
                Firefacilities_fpycs_fpyxtVO fpyxtVO = new Firefacilities_fpycs_fpyxtVO();
                fpyxtVO.setXfssid(xfssid);
                fpyxtVO.setJdh(jdh);
                fpyxtVO.setDatasource(datasource);
                fpyxtVO.setIsky(detailMap.get("isky") != null ? detailMap.get("isky").toString() : "");
                fpyxtVO.setQbwz(detailMap.get("qbwz") != null ? detailMap.get("qbwz").toString() : "");
                firefacilitiesDAO.doInsertFpyxtByVO(fpyxtVO);
                break;
            //防火分区
            case "7000":
                Firefacilities_fhfqVO fhfqVO = new Firefacilities_fhfqVO();
                fhfqVO.setXfssid(xfssid);
                fhfqVO.setJdh(jdh);
                fhfqVO.setDatasource(datasource);
                fhfqVO.setQymj(detailMap.get("qymj") != null ? detailMap.get("qymj").toString() : "");
                fhfqVO.setQywz(detailMap.get("qywz") != null ? detailMap.get("qywz").toString() : "");
                fhfqVO.setFgss(detailMap.get("fgss") != null ? detailMap.get("fgss").toString() : "");
                fhfqVO.setFgwz(detailMap.get("fgwz") != null ? detailMap.get("fgwz").toString() : "");
                firefacilitiesDAO.doInsertFhfqByVO(fhfqVO);
                break;
            //其他灭火系统
            case "8000":
                break;
            case "8001":
                Firefacilities_qtmhxt_qtmhxtVO qtmhxtVO = new Firefacilities_qtmhxt_qtmhxtVO();
                qtmhxtVO.setXfssid(xfssid);
                qtmhxtVO.setJdh(jdh);
                qtmhxtVO.setDatasource(datasource);
                qtmhxtVO.setQbwz(detailMap.get("qbwz") != null ? detailMap.get("qbwz").toString() : "");
                qtmhxtVO.setZyfw(detailMap.get("zyfw") != null ? detailMap.get("zyfw").toString() : "");
                firefacilitiesDAO.doInsertQtmhxtByVO(qtmhxtVO);
                break;
            case "8002":
                Firefacilities_qtmhxt_gfmhxtVO gfmhxtVO = new Firefacilities_qtmhxt_gfmhxtVO();
                gfmhxtVO.setXfssid(xfssid);
                gfmhxtVO.setJdh(jdh);
                gfmhxtVO.setDatasource(datasource);
                gfmhxtVO.setQbwz(detailMap.get("qbwz") != null ? detailMap.get("qbwz").toString() : "");
                gfmhxtVO.setZyfw(detailMap.get("zyfw") != null ? detailMap.get("zyfw").toString() : "");
                firefacilitiesDAO.doInsertGfmhxtByVO(gfmhxtVO);
                break;
            //其他消防设施
            case "9000":
                Firefacilities_qtxfssVO qtxfssVO = new Firefacilities_qtxfssVO();
                qtxfssVO.setXfssid(xfssid);
                qtxfssVO.setJdh(jdh);
                qtxfssVO.setDatasource(datasource);
                qtxfssVO.setMs(detailMap.get("ms") != null ? detailMap.get("ms").toString() : "");
                qtxfssVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                firefacilitiesDAO.doInsertQtxfssByVO(qtxfssVO);
                break;
        }
        return firefacilitiesVO;
    }

    //消防设施主从表修改
    public FirefacilitiesVO doUpdateFirefacilities(FirefacilitiesVO firefacilitiesVO) {
        String xfsslx = firefacilitiesVO.getJbxx_xfsslx();
        String xfssid = firefacilitiesVO.getJbxx_xfssid();
        FirefacilitiesVO vo1 = firefacilitiesDAO.doFindById(xfssid);//原主表数据查询
        firefacilitiesDAO.doUpdateByVO(firefacilitiesVO);//新主表数据修改
        if (!vo1.getJbxx_xfsslx().equals(xfsslx)) {//消防设施类型改变
            ((FirefacilitiesService) AopContext.currentProxy()).doDeleteFirefacilitiesDetails(vo1);//删除原类型从表数据
            ((FirefacilitiesService) AopContext.currentProxy()).doInsertFirefacilitiesDetail(firefacilitiesVO);//增加新类型从表数据
        } else {//消防设施类型未改变，做从表修改
            Map detailMap = firefacilitiesVO.getDetailMap();
            switch (xfsslx) {
                //安全疏散措施
                case "1000":
                    break;
                case "1001":
                    Firefacilities_aqsscs_aqckVO aqckVO = new Firefacilities_aqsscs_aqckVO();
                    aqckVO.setXfssid(xfssid);
                    aqckVO.setSl(detailMap.get("sl") != null ? detailMap.get("sl").toString() : "");
                    aqckVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                    firefacilitiesDAO.doUpdateAqckByVO(aqckVO);
                    break;
                case "1002":
                    Firefacilities_aqsscs_ssltVO ssltVO = new Firefacilities_aqsscs_ssltVO();
                    ssltVO.setXfssid(xfssid);
                    ssltVO.setSl(detailMap.get("sl") != null ? detailMap.get("sl").toString() : "");
                    ssltVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                    firefacilitiesDAO.doUpdateSsltByVO(ssltVO);
                    break;
                case "1003":
                    Firefacilities_aqsscs_xfdtVO xfdtVO = new Firefacilities_aqsscs_xfdtVO();
                    xfdtVO.setXfssid(xfssid);
                    xfdtVO.setSl(detailMap.get("sl") != null ? detailMap.get("sl").toString() : "");
                    xfdtVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                    firefacilitiesDAO.doUpdateXfdtByVO(xfdtVO);
                    break;
                case "1004":
                    Firefacilities_aqsscs_bncVO bncVO = new Firefacilities_aqsscs_bncVO();
                    bncVO.setXfssid(xfssid);
                    bncVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                    firefacilitiesDAO.doUpdateBncByVO(bncVO);
                    break;
                case "1005":
                    Firefacilities_aqsscs_yjgbVO yjgbVO = new Firefacilities_aqsscs_yjgbVO();
                    yjgbVO.setXfssid(xfssid);
                    yjgbVO.setYwyjgb(detailMap.get("ywyjgb") != null ? detailMap.get("ywyjgb").toString() : "");
                    yjgbVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                    firefacilitiesDAO.doUpdateYjgbByVO(yjgbVO);
                    break;
                //消防水系统
                case "2000":
                    break;
                case "2001":
                    Firefacilities_xfsxt_xfbfVO xfbfVO = new Firefacilities_xfsxt_xfbfVO();
                    xfbfVO.setXfssid(xfssid);
                    xfbfVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                    xfbfVO.setPlbsl(detailMap.get("plbsl") != null ? detailMap.get("plbsl").toString() : "");
                    xfbfVO.setPlbzdll(detailMap.get("plbzdll") != null ? detailMap.get("plbzdll").toString() : "");
                    xfbfVO.setXhssl(detailMap.get("xhssl") != null ? detailMap.get("xhssl").toString() : "");
                    xfbfVO.setXhszdll(detailMap.get("xhszdll") != null ? detailMap.get("xhszdll").toString() : "");
                    firefacilitiesDAO.doUpdateXfbfByVO(xfbfVO);
                    break;
                case "2002":
                    Firefacilities_xfsxt_xfsxVO xfsxVO = new Firefacilities_xfsxt_xfsxVO();
                    xfsxVO.setXfssid(xfssid);
                    xfsxVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                    xfsxVO.setBjsd(detailMap.get("bjsd") != null ? detailMap.get("bjsd").toString() : "");
                    xfsxVO.setSxrl(detailMap.get("sxrl") != null ? detailMap.get("sxrl").toString() : "");
                    firefacilitiesDAO.doUpdateXfsxByVO(xfsxVO);
                    break;
                case "2003":
                    Firefacilities_xfsxt_xfscVO xfscVO = new Firefacilities_xfsxt_xfscVO();
                    xfscVO.setXfssid(xfssid);
                    xfscVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                    xfscVO.setYwqsj(detailMap.get("ywqsj") != null ? detailMap.get("ywqsj").toString() : "");
                    xfscVO.setBjsd(detailMap.get("bjsd") != null ? detailMap.get("bjsd").toString() : "");
                    xfscVO.setQsjwz(detailMap.get("qsjwz") != null ? detailMap.get("qsjwz").toString() : "");
                    xfscVO.setSxrl(detailMap.get("sxrl") != null ? detailMap.get("sxrl").toString() : "");
                    firefacilitiesDAO.doUpdateXfscByVO(xfscVO);
                    break;
                case "2004":
                    Firefacilities_xfsxt_snxhsVO snxhsVO = new Firefacilities_xfsxt_snxhsVO();
                    snxhsVO.setXfssid(xfssid);
                    snxhsVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                    snxhsVO.setSl(detailMap.get("sl") != null ? detailMap.get("sl").toString() : "");
                    snxhsVO.setSfky(detailMap.get("sfky") != null ? detailMap.get("sfky").toString() : "");
                    firefacilitiesDAO.doUpdateSnxhsByVO(snxhsVO);
                    break;
                case "2005":
                    Firefacilities_xfsxt_swxhsVO swxhsVO = new Firefacilities_xfsxt_swxhsVO();
                    swxhsVO.setXfssid(xfssid);
                    swxhsVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                    swxhsVO.setSl(detailMap.get("sl") != null ? detailMap.get("sl").toString() : "");
                    swxhsVO.setSfky(detailMap.get("sfky") != null ? detailMap.get("sfky").toString() : "");
                    firefacilitiesDAO.doUpdateSwxhsByVO(swxhsVO);
                    break;
                case "2006":
                    Firefacilities_xfsxt_sbjhqVO sbjhqVO = new Firefacilities_xfsxt_sbjhqVO();
                    sbjhqVO.setXfssid(xfssid);
                    sbjhqVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                    sbjhqVO.setAzxs(detailMap.get("azxs") != null ? detailMap.get("azxs").toString() : "");
                    sbjhqVO.setXh(detailMap.get("xh") != null ? detailMap.get("xh").toString() : "");
                    sbjhqVO.setJskcc(detailMap.get("jskcc") != null ? detailMap.get("jskcc").toString() : "");
                    firefacilitiesDAO.doUpdateSbjhqByVO(sbjhqVO);
                    break;
                case "2007":
                    Firefacilities_xfsxt_plxtVO plxtVO = new Firefacilities_xfsxt_plxtVO();
                    plxtVO.setXfssid(xfssid);
                    plxtVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                    plxtVO.setYwplxt(detailMap.get("ywplxt") != null ? detailMap.get("ywplxt").toString() : "");
                    firefacilitiesDAO.doUpdatePlxtByVO(plxtVO);
                    break;
                case "2008":
                    Firefacilities_xfsxt_lqsxtVO lqsxtVO = new Firefacilities_xfsxt_lqsxtVO();
                    lqsxtVO.setXfssid(xfssid);
                    lqsxtVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                    lqsxtVO.setGsqd(detailMap.get("gsqd") != null ? detailMap.get("gsqd").toString() : "");
                    lqsxtVO.setYwlqsxt(detailMap.get("ywlqsxt") != null ? detailMap.get("ywlqsxt").toString() : "");
                    firefacilitiesDAO.doUpdateLqsxtByVO(lqsxtVO);
                    break;
                case "2009":
                    Firefacilities_xfsxt_gdspVO gdspVO = new Firefacilities_xfsxt_gdspVO();
                    gdspVO.setXfssid(xfssid);
                    gdspVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                    gdspVO.setSl(detailMap.get("sl") != null ? detailMap.get("sl").toString() : "");
                    gdspVO.setIsky(detailMap.get("isky") != null ? detailMap.get("isky").toString() : "");
                    firefacilitiesDAO.doUpdateGdspByVO(gdspVO);
                    break;
                case "2010":
                    Firefacilities_xfsxt_bgdssVO bgdssVO = new Firefacilities_xfsxt_bgdssVO();
                    bgdssVO.setXfssid(xfssid);
                    bgdssVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                    bgdssVO.setSl(detailMap.get("sl") != null ? detailMap.get("sl").toString() : "");
                    firefacilitiesDAO.doUpdateBgdssByVO(bgdssVO);
                    break;
                //泡沫系统
                case "3000":
                    break;
                case "3001":
                    Firefacilities_pmxt_pmbfVO pmbfVO = new Firefacilities_pmxt_pmbfVO();
                    pmbfVO.setXfssid(xfssid);
                    pmbfVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                    pmbfVO.setPmylx(detailMap.get("pmylx") != null ? detailMap.get("pmylx").toString() : "");
                    pmbfVO.setPmycl(detailMap.get("pmycl") != null ? detailMap.get("pmycl").toString() : "");
                    pmbfVO.setPmbzdll(detailMap.get("pmbzdll") != null ? detailMap.get("pmbzdll").toString() : "");
                    firefacilitiesDAO.doUpdatePmbfByVO(pmbfVO);
                    break;
                case "3002":
                    Firefacilities_pmxt_pmxhsVO pmxhsVO = new Firefacilities_pmxt_pmxhsVO();
                    pmxhsVO.setXfssid(xfssid);
                    pmxhsVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                    pmxhsVO.setIsky(detailMap.get("isky") != null ? detailMap.get("isky").toString() : "");
                    firefacilitiesDAO.doUpdatePmxhsByVO(pmxhsVO);
                    break;
                case "3003":
                    Firefacilities_pmxt_gdpmpVO gdpmpVO = new Firefacilities_pmxt_gdpmpVO();
                    gdpmpVO.setXfssid(xfssid);
                    gdpmpVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                    gdpmpVO.setSl(detailMap.get("sl") != null ? detailMap.get("sl").toString() : "");
                    gdpmpVO.setSfky(detailMap.get("sfky") != null ? detailMap.get("sfky").toString() : "");
                    firefacilitiesDAO.doUpdateGdpmpByVO(gdpmpVO);
                    break;
                case "3004":
                    Firefacilities_pmxt_pmfsqVO pmfsqVO = new Firefacilities_pmxt_pmfsqVO();
                    pmfsqVO.setXfssid(xfssid);
                    pmfsqVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                    pmfsqVO.setSl(detailMap.get("sl") != null ? detailMap.get("sl").toString() : "");
                    pmfsqVO.setXh(detailMap.get("xh") != null ? detailMap.get("xh").toString() : "");
                    pmfsqVO.setLl(detailMap.get("ll") != null ? detailMap.get("ll").toString() : "");
                    firefacilitiesDAO.doUpdatePmfsqByVO(pmfsqVO);
                    break;
                case "3005":
                    Firefacilities_pmxt_bgdssVO PmBgdssVO = new Firefacilities_pmxt_bgdssVO();
                    PmBgdssVO.setXfssid(xfssid);
                    PmBgdssVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                    PmBgdssVO.setSl(detailMap.get("sl") != null ? detailMap.get("sl").toString() : "");
                    firefacilitiesDAO.doUpdatePmBgdssByVO(PmBgdssVO);
                    break;
                //蒸汽灭火系统
                case "4000":
                    break;
                case "4001":
                    Firefacilities_zqmhxt_gdsVO gdsVO = new Firefacilities_zqmhxt_gdsVO();
                    gdsVO.setXfssid(xfssid);
                    gdsVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                    gdsVO.setSl(detailMap.get("sl") != null ? detailMap.get("sl").toString() : "");
                    gdsVO.setEdyl(detailMap.get("edyl") != null ? detailMap.get("edyl").toString() : "");
                    firefacilitiesDAO.doUpdateGdsByVO(gdsVO);
                    break;
                case "4002":
                    Firefacilities_zqmhxt_bgdsVO bgdsVO = new Firefacilities_zqmhxt_bgdsVO();
                    bgdsVO.setXfssid(xfssid);
                    bgdsVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                    bgdsVO.setSl(detailMap.get("sl") != null ? detailMap.get("sl").toString() : "");
                    bgdsVO.setEdyl(detailMap.get("edyl") != null ? detailMap.get("edyl").toString() : "");
                    firefacilitiesDAO.doUpdateBgdsByVO(bgdsVO);
                    break;
                //消防控制室
                case "5000":
                    Firefacilities_xfkzsVO xfkzsVO = new Firefacilities_xfkzsVO();
                    xfkzsVO.setXfssid(xfssid);
                    xfkzsVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                    xfkzsVO.setIsldkz(detailMap.get("isldkz") != null ? detailMap.get("isldkz").toString() : "");
                    xfkzsVO.setIszdbj(detailMap.get("iszdbj") != null ? detailMap.get("iszdbj").toString() : "");
                    firefacilitiesDAO.doUpdateXfkzsByVO(xfkzsVO);
                    break;
                //防排烟设施
                case "6000":
                    break;
                case "6001":
                    Firefacilities_fpycs_pycykVO pycykVO = new Firefacilities_fpycs_pycykVO();
                    pycykVO.setXfssid(xfssid);
                    pycykVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                    firefacilitiesDAO.doUpdatePycykByVO(pycykVO);
                    break;
                case "6002":
                    Firefacilities_fpycs_fpyxtVO fpyxtVO = new Firefacilities_fpycs_fpyxtVO();
                    fpyxtVO.setXfssid(xfssid);
                    fpyxtVO.setIsky(detailMap.get("isky") != null ? detailMap.get("isky").toString() : "");
                    fpyxtVO.setQbwz(detailMap.get("qbwz") != null ? detailMap.get("qbwz").toString() : "");
                    firefacilitiesDAO.doUpdateFpyxtByVO(fpyxtVO);
                    break;
                //防火分区
                case "7000":
                    Firefacilities_fhfqVO fhfqVO = new Firefacilities_fhfqVO();
                    fhfqVO.setXfssid(xfssid);
                    fhfqVO.setQymj(detailMap.get("qymj") != null ? detailMap.get("qymj").toString() : "");
                    fhfqVO.setQywz(detailMap.get("qywz") != null ? detailMap.get("qywz").toString() : "");
                    fhfqVO.setFgss(detailMap.get("fgss") != null ? detailMap.get("fgss").toString() : "");
                    fhfqVO.setFgwz(detailMap.get("fgwz") != null ? detailMap.get("fgwz").toString() : "");
                    firefacilitiesDAO.doUpdateFhfqByVO(fhfqVO);
                    break;
                //其他灭火系统
                case "8000":
                    break;
                case "8001":
                    Firefacilities_qtmhxt_qtmhxtVO qtmhxtVO = new Firefacilities_qtmhxt_qtmhxtVO();
                    qtmhxtVO.setXfssid(xfssid);
                    qtmhxtVO.setQbwz(detailMap.get("qbwz") != null ? detailMap.get("qbwz").toString() : "");
                    qtmhxtVO.setZyfw(detailMap.get("zyfw") != null ? detailMap.get("zyfw").toString() : "");
                    firefacilitiesDAO.doUpdateQtmhxtByVO(qtmhxtVO);
                    break;
                case "8002":
                    Firefacilities_qtmhxt_gfmhxtVO gfmhxtVO = new Firefacilities_qtmhxt_gfmhxtVO();
                    gfmhxtVO.setXfssid(xfssid);
                    gfmhxtVO.setQbwz(detailMap.get("qbwz") != null ? detailMap.get("qbwz").toString() : "");
                    gfmhxtVO.setZyfw(detailMap.get("zyfw") != null ? detailMap.get("zyfw").toString() : "");
                    firefacilitiesDAO.doUpdateGfmhxtByVO(gfmhxtVO);
                    break;
                //其他消防设施
                case "9000":
                    Firefacilities_qtxfssVO qtxfssVO = new Firefacilities_qtxfssVO();
                    qtxfssVO.setXfssid(xfssid);
                    qtxfssVO.setMs(detailMap.get("ms") != null ? detailMap.get("ms").toString() : "");
                    qtxfssVO.setWz(detailMap.get("wz") != null ? detailMap.get("wz").toString() : "");
                    firefacilitiesDAO.doUpdateQtxfssByVO(qtxfssVO);
                    break;
            }
        }
        return firefacilitiesVO;
    }

    //消防设施删除
    public int doDeleteFirefacilities(List<FirefacilitiesVO> facilitiesList) {
        int count = 0;
        for (FirefacilitiesVO facilitiesVO : facilitiesList) {
            facilitiesVO.setJbxx_deleteFlag("Y");
            ((FirefacilitiesService) AopContext.currentProxy()).doDeleteFirefacilitiesDetails(facilitiesVO);//删除从表信息
            count = count + firefacilitiesDAO.doUpdateByVO(facilitiesVO);//删除主表信息
        }
        return count;
    }

    //消防设施从表删除
    public FirefacilitiesVO doDeleteFirefacilitiesDetails(FirefacilitiesVO firefacilitiesVO) {
        String xfsslx = firefacilitiesVO.getJbxx_xfsslx();
        String xfssid = firefacilitiesVO.getJbxx_xfssid();
        switch (xfsslx) {
            //安全疏散措施
            case "1000":
                break;
            case "1001":
                Firefacilities_aqsscs_aqckVO aqckVO = new Firefacilities_aqsscs_aqckVO();
                aqckVO.setXfssid(xfssid);
                aqckVO.setDeleteFlag("Y");
                firefacilitiesDAO.doUpdateAqckByVO(aqckVO);
                break;
            case "1002":
                Firefacilities_aqsscs_ssltVO ssltVO = new Firefacilities_aqsscs_ssltVO();
                ssltVO.setXfssid(xfssid);
                ssltVO.setDeleteFlag("Y");
                firefacilitiesDAO.doUpdateSsltByVO(ssltVO);
                break;
            case "1003":
                Firefacilities_aqsscs_xfdtVO xfdtVO = new Firefacilities_aqsscs_xfdtVO();
                xfdtVO.setXfssid(xfssid);
                xfdtVO.setDeleteFlag("Y");
                firefacilitiesDAO.doUpdateXfdtByVO(xfdtVO);
                break;
            case "1004":
                Firefacilities_aqsscs_bncVO bncVO = new Firefacilities_aqsscs_bncVO();
                bncVO.setXfssid(xfssid);
                bncVO.setDeleteFlag("Y");
                firefacilitiesDAO.doUpdateBncByVO(bncVO);
                break;
            case "1005":
                Firefacilities_aqsscs_yjgbVO yjgbVO = new Firefacilities_aqsscs_yjgbVO();
                yjgbVO.setXfssid(xfssid);
                yjgbVO.setDeleteFlag("Y");
                firefacilitiesDAO.doUpdateYjgbByVO(yjgbVO);
                break;
            //消防水系统
            case "2000":
                break;
            case "2001":
                Firefacilities_xfsxt_xfbfVO xfbfVO = new Firefacilities_xfsxt_xfbfVO();
                xfbfVO.setXfssid(xfssid);
                xfbfVO.setDeleteFlag("Y");
                firefacilitiesDAO.doUpdateXfbfByVO(xfbfVO);
                break;
            case "2002":
                Firefacilities_xfsxt_xfsxVO xfsxVO = new Firefacilities_xfsxt_xfsxVO();
                xfsxVO.setXfssid(xfssid);
                xfsxVO.setDeleteFlag("Y");
                firefacilitiesDAO.doUpdateXfsxByVO(xfsxVO);
                break;
            case "2003":
                Firefacilities_xfsxt_xfscVO xfscVO = new Firefacilities_xfsxt_xfscVO();
                xfscVO.setXfssid(xfssid);
                xfscVO.setDeleteFlag("Y");
                firefacilitiesDAO.doUpdateXfscByVO(xfscVO);
                break;
            case "2004":
                Firefacilities_xfsxt_snxhsVO snxhsVO = new Firefacilities_xfsxt_snxhsVO();
                snxhsVO.setXfssid(xfssid);
                snxhsVO.setDeleteFlag("Y");
                firefacilitiesDAO.doUpdateSnxhsByVO(snxhsVO);
                break;
            case "2005":
                Firefacilities_xfsxt_swxhsVO swxhsVO = new Firefacilities_xfsxt_swxhsVO();
                swxhsVO.setXfssid(xfssid);
                swxhsVO.setDeleteFlag("Y");
                firefacilitiesDAO.doUpdateSwxhsByVO(swxhsVO);
                break;
            case "2006":
                Firefacilities_xfsxt_sbjhqVO sbjhqVO = new Firefacilities_xfsxt_sbjhqVO();
                sbjhqVO.setXfssid(xfssid);
                sbjhqVO.setDeleteFlag("Y");
                firefacilitiesDAO.doUpdateSbjhqByVO(sbjhqVO);
                break;
            case "2007":
                Firefacilities_xfsxt_plxtVO plxtVO = new Firefacilities_xfsxt_plxtVO();
                plxtVO.setXfssid(xfssid);
                plxtVO.setDeleteFlag("Y");
                firefacilitiesDAO.doUpdatePlxtByVO(plxtVO);
                break;
            case "2008":
                Firefacilities_xfsxt_lqsxtVO lqsxtVO = new Firefacilities_xfsxt_lqsxtVO();
                lqsxtVO.setXfssid(xfssid);
                lqsxtVO.setDeleteFlag("Y");
                firefacilitiesDAO.doUpdateLqsxtByVO(lqsxtVO);
                break;
            case "2009":
                Firefacilities_xfsxt_gdspVO gdspVO = new Firefacilities_xfsxt_gdspVO();
                gdspVO.setXfssid(xfssid);
                gdspVO.setDeleteFlag("Y");
                firefacilitiesDAO.doUpdateGdspByVO(gdspVO);
                break;
            case "2010":
                Firefacilities_xfsxt_bgdssVO bgdssVO = new Firefacilities_xfsxt_bgdssVO();
                bgdssVO.setXfssid(xfssid);
                bgdssVO.setDeleteFlag("Y");
                firefacilitiesDAO.doUpdateBgdssByVO(bgdssVO);
                break;
            //泡沫系统
            case "3000":
                break;
            case "3001":
                Firefacilities_pmxt_pmbfVO pmbfVO = new Firefacilities_pmxt_pmbfVO();
                pmbfVO.setXfssid(xfssid);
                pmbfVO.setDeleteFlag("Y");
                firefacilitiesDAO.doUpdatePmbfByVO(pmbfVO);
                break;
            case "3002":
                Firefacilities_pmxt_pmxhsVO pmxhsVO = new Firefacilities_pmxt_pmxhsVO();
                pmxhsVO.setXfssid(xfssid);
                pmxhsVO.setDeleteFlag("Y");
                firefacilitiesDAO.doUpdatePmxhsByVO(pmxhsVO);
                break;
            case "3003":
                Firefacilities_pmxt_gdpmpVO gdpmpVO = new Firefacilities_pmxt_gdpmpVO();
                gdpmpVO.setXfssid(xfssid);
                gdpmpVO.setDeleteFlag("Y");
                firefacilitiesDAO.doInsertGdpmpByVO(gdpmpVO);
                break;
            case "3004":
                Firefacilities_pmxt_pmfsqVO pmfsqVO = new Firefacilities_pmxt_pmfsqVO();
                pmfsqVO.setXfssid(xfssid);
                pmfsqVO.setDeleteFlag("Y");
                firefacilitiesDAO.doInsertPmfsqByVO(pmfsqVO);
                break;
            case "3005":
                Firefacilities_pmxt_bgdssVO PmBgdssVO = new Firefacilities_pmxt_bgdssVO();
                PmBgdssVO.setXfssid(xfssid);
                PmBgdssVO.setDeleteFlag("Y");
                firefacilitiesDAO.doInsertPmBgdssByVO(PmBgdssVO);
                break;
            //蒸汽灭火系统
            case "4000":
                break;
            case "4001":
                Firefacilities_zqmhxt_gdsVO gdsVO = new Firefacilities_zqmhxt_gdsVO();
                gdsVO.setXfssid(xfssid);
                gdsVO.setDeleteFlag("Y");
                firefacilitiesDAO.doInsertGdsByVO(gdsVO);
                break;
            case "4002":
                Firefacilities_zqmhxt_bgdsVO bgdsVO = new Firefacilities_zqmhxt_bgdsVO();
                bgdsVO.setXfssid(xfssid);
                bgdsVO.setDeleteFlag("Y");
                firefacilitiesDAO.doInsertBgdsByVO(bgdsVO);
                break;
            //消防控制室
            case "5000":
                Firefacilities_xfkzsVO xfkzsVO = new Firefacilities_xfkzsVO();
                xfkzsVO.setXfssid(xfssid);
                xfkzsVO.setDeleteFlag("Y");
                firefacilitiesDAO.doInsertXfkzsByVO(xfkzsVO);
                break;
            //防排烟设施
            case "6000":
                break;
            case "6001":
                Firefacilities_fpycs_pycykVO pycykVO = new Firefacilities_fpycs_pycykVO();
                pycykVO.setXfssid(xfssid);
                pycykVO.setDeleteFlag("Y");
                firefacilitiesDAO.doInsertPycykByVO(pycykVO);
                break;
            case "6002":
                Firefacilities_fpycs_fpyxtVO fpyxtVO = new Firefacilities_fpycs_fpyxtVO();
                fpyxtVO.setXfssid(xfssid);
                fpyxtVO.setDeleteFlag("Y");
                firefacilitiesDAO.doInsertFpyxtByVO(fpyxtVO);
                break;
            //防火分区
            case "7000":
                Firefacilities_fhfqVO fhfqVO = new Firefacilities_fhfqVO();
                fhfqVO.setXfssid(xfssid);
                fhfqVO.setDeleteFlag("Y");
                firefacilitiesDAO.doInsertFhfqByVO(fhfqVO);
                break;
            //其他灭火系统
            case "8000":
                break;
            case "8001":
                Firefacilities_qtmhxt_qtmhxtVO qtmhxtVO = new Firefacilities_qtmhxt_qtmhxtVO();
                qtmhxtVO.setXfssid(xfssid);
                qtmhxtVO.setDeleteFlag("Y");
                firefacilitiesDAO.doInsertQtmhxtByVO(qtmhxtVO);
                break;
            case "8002":
                Firefacilities_qtmhxt_gfmhxtVO gfmhxtVO = new Firefacilities_qtmhxt_gfmhxtVO();
                gfmhxtVO.setXfssid(xfssid);
                gfmhxtVO.setDeleteFlag("Y");
                firefacilitiesDAO.doInsertGfmhxtByVO(gfmhxtVO);
                break;
            //其他消防设施
            case "9000":
                Firefacilities_qtxfssVO qtxfssVO = new Firefacilities_qtxfssVO();
                qtxfssVO.setXfssid(xfssid);
                qtxfssVO.setDeleteFlag("Y");
                firefacilitiesDAO.doInsertQtxfssByVO(qtxfssVO);
                break;
        }
        return firefacilitiesVO;
    }

}