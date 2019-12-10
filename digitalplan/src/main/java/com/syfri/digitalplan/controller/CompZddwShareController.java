package com.syfri.digitalplan.controller;

import com.github.pagehelper.StringUtil;
import com.syfri.digitalplan.config.properties.YafjxzProperties;
import com.syfri.digitalplan.model.buildingzoning.BuildingVO;
import com.syfri.digitalplan.model.digitalplan.DigitalplanlistVO;
import com.syfri.digitalplan.model.digitalplan.DisastersetVO;
import com.syfri.digitalplan.model.firefacilities.*;
import com.syfri.digitalplan.model.planobject.ImportantunitsVO;
import com.syfri.digitalplan.model.yafjxz.YafjxzVO;
import com.syfri.digitalplan.utils.Pic;
import com.syfri.digitalplan.utils.VelocityUtil;
import com.syfri.digitalplan.utils.*;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.io.File;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.syfri.digitalplan.service.planobject.ImportantunitsService;
import com.syfri.digitalplan.service.digitalplan.DigitalplanlistService;
import com.syfri.digitalplan.service.digitalplan.DisastersetService;
import com.syfri.digitalplan.service.yafjxz.YafjxzService;
import com.syfri.digitalplan.service.digitalplan.KeypointsService;
import com.syfri.digitalplan.service.importantparts.ImportantpartsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import net.sf.json.JSONObject;

/**
 * @Description: 数字化预案分享
 * @Param:
 * @Return:
 * @Author: liurui
 * @Modified By:
 * @Date: 2018/5/8 10:24
 */

@Controller
@RequestMapping("/planShare")
@Api(value = "重点单位共享" ,tags = "重点单位共享API" ,description = "CompZddwShareController")
public class CompZddwShareController {
    private static final Logger logger = LoggerFactory.getLogger(CompZddwShareController.class);

//    @Autowired
//    ShareConfig shareConfig;

    @Autowired
    private DigitalplanlistService digitalplanlistService;

    @Autowired
    private ImportantunitsService importantunitsService;

    @Autowired
    private DisastersetService disastersetService;

    @Autowired
    private YafjxzService yafjxzService;

    @Autowired
    private ImportantpartsService importantpartsService;

    @Autowired
    private KeypointsService keypointsService;
    @Autowired
    private YafjxzProperties yafjxzProperties;

    @ApiOperation(value = "获取预案分享信息", notes = "根据url的districtCode和uuid来指定获取对象，并根据传过来的target信息来确定输出目标")
    @ApiImplicitParams({
//            @ApiImplicitParam(name = "districtCode", value = "行政区域", required = true, dataType = "String", paramType
//                    = "path"),
            @ApiImplicitParam(name = "uuid", value = "重点单位标识", required = true, dataType = "String", paramType =
                    "path"),
            @ApiImplicitParam(name = "target", value = "输出目标(PC端:web/移动端:dev)", required = true, dataType = "String",
                    paramType = "path")
    })
    @GetMapping("/page/{uuid}/{type}/{target}")
    public String getPage(Model model,
                          @PathVariable("uuid") String uuid,
                          @PathVariable("type") String type,
                          @PathVariable("target") String target) {
        String result = null;

        logger.debug(String.format("[IN] uuid: %s type: %s target: %s", uuid, type, target));

//        Map<String, Object> conditions = new HashMap<String, Object>();
//        conditions.put("uuid", uuid);

        Map<String, ImportantunitsVO> zddwWrapperMap = new HashMap<String, ImportantunitsVO>();
//        String url = shareConfig.getUrlPrefix() + shareConfig.getZddwPostfix();
//        JSONObject params = new JSONObject(conditions);
//        MediaType mediaType = MediaType.APPLICATION_FORM_URLENCODED;
//        String jsonStr = RestTemplateUtil.post(url, params, mediaType, String.class);

//        zddwWrapperMap = convertJson2Map(jsonStr);
//        if (zddwWrapperMap == null) {
//            logger.warn(Pic.getImageStr(String.format(" zddwWrapperMap is null !"));
//
//            return "error";
//        }
//        zddwWrapperMap.put("compZddwInfo",importantpartsService.doFindById(uuid));
        String zddwid = digitalplanlistService.doFindById(uuid).getDxid();
        ImportantunitsVO vo = importantunitsService.doFindById(zddwid);
        // 预案基础信息
        model.addAttribute("compPlanInfo", digitalplanlistService.doFindById(uuid));
        // 重点单位基础信息
        model.addAttribute("compZddwInfo", vo);
        // 灾情设定、力量部署、要点提示
        model.addAttribute("disastersetList", disastersetService.doFindByPlanId(uuid));

        if (type.equals("detail")) {
            // 重点部位（建筑类）
            model.addAttribute("jzlpartsList", importantpartsService.doFindJzlListByZddwId(zddwid));
            // 重点部位（装置类）
            model.addAttribute("zzlpartsList", importantpartsService.doFindZzlListByZddwId(zddwid));
            // 重点部位（储罐类）
            model.addAttribute("cglpartsList", importantpartsService.doFindCglListByZddwId(zddwid));
            // 建筑分区和消防设施
            model.addAttribute("areaBuildingList", importantunitsService.doFindBuildingDetailsAndFirefacilitiesByVo(vo));
        }
        // 附图
        YafjxzVO yafjxzVO = new YafjxzVO();
        yafjxzVO.setKzm("pic");
        yafjxzVO.setYaid(uuid);
        model.addAttribute("pictureList", yafjxzService.doFindByPlanId(yafjxzVO));
        //lxy添加vue服务器路径
        model.addAttribute("vueServerUrl", yafjxzProperties.getVueServerUrl());
        model.addAttribute("serverUrl", yafjxzProperties.getFileServerUrl());
//        // 功能分区-装置
//        model.addAttribute("areaDeviceList", zddwWrapperMap.get("areaDeviceList"));
//        // 消防设施
//        model.addAttribute("facilityList", zddwWrapperMap.get("facilityList"));
//        // 重点区域
//        model.addAttribute("regionList", zddwWrapperMap.get("regionList"));
//        // 数据字典
//        model.addAttribute("dictionaryMap", zddwWrapperMap.get("dictionaryMap"));
//        // 所属功能区，所属楼层
//        model.addAttribute("belongMap", zddwWrapperMap.get("belongMap"));
//        // 关键图示
//        model.addAttribute("keyIllustrationList", zddwWrapperMap.get("keyIllustrationList"));
//        // 全景漫游
//        model.addAttribute("panoList", zddwWrapperMap.get("panoList"));
//        // 旧版预案 // 现场照片
//        model.addAttribute("pictureList", zddwWrapperMap.get("pictureList"));
//        model.addAttribute("attachList", zddwWrapperMap.get("attachList"));
//
//        // 功能区类型转换
//        model.addAttribute("areaPartTypeMap", zddwWrapperMap.get("areaPartTypeMap"));
//        // 使用性质转换
//        model.addAttribute("syxzMap", zddwWrapperMap.get("syxzMap"));
//        // 所在平面图转换
//        model.addAttribute("szpmtMap", zddwWrapperMap.get("szpmtMap"));

//        result = shareConfig.getZddwTemplateNamePrefix() + "-" + target;
        result = "compPlan" + "-" + type + "-" + target;

        logger.debug(String.format("[OUT]"));

        return result;
    }

    @ApiOperation(value = "获取重点单位分享信息", notes = "根据url的districtCode和uuid来指定获取对象，并根据传过来的target信息来确定输出目标")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uuid", value = "重点单位标识", required = true, dataType = "String", paramType =
                    "path"),
            @ApiImplicitParam(name = "target", value = "输出目标(PC端:web/移动端:dev)", required = true, dataType = "String",
                    paramType = "path")
    })
    @GetMapping("/pageZddw/{uuid}/{target}")
    public String getPageZddw(Model model, @PathVariable("uuid") String uuid, @PathVariable("target") String target) {
        String result = null;

        logger.debug(String.format("[IN] uuid: %s target: %s", uuid, target));

        // 重点单位基础信息
        ImportantunitsVO vo = importantunitsService.doFindById(uuid);
        model.addAttribute("compZddwInfo", vo);
        // 预案基础信息
        List<DigitalplanlistVO> planVo = digitalplanlistService.doFindListByZddwId(uuid);
        for(DigitalplanlistVO pVo :planVo){
            // 灾情设定、力量部署、要点提示
            List<DisastersetVO> dsVo = disastersetService.doFindByPlanId(pVo.getUuid());
            pVo.setDisasterListVo(dsVo);
        }
        model.addAttribute("compPlanInfo", planVo);
        // 重点部位（建筑类）
        model.addAttribute("jzlpartsList", importantpartsService.doFindJzlListByZddwId(uuid));
        // 重点部位（装置类）
        model.addAttribute("zzlpartsList", importantpartsService.doFindZzlListByZddwId(uuid));
        // 重点部位（储罐类）
        model.addAttribute("cglpartsList", importantpartsService.doFindCglListByZddwId(uuid));
        // 建筑分区和消防设施
        model.addAttribute("areaBuildingList", importantunitsService.doFindBuildingDetailsAndFirefacilitiesByVo(vo));

        result = "compZddw-" + target;

        logger.debug(String.format("[OUT]"));

        return result;
    }

//    private Map<String, Object> convertJson2Map(String jsonStr) {
//        Map<String, Object> res = null;
//        try {
//            Gson gson = new Gson();
//            res = gson.fromJson(jsonStr, new TypeToken<Map<String, Object>>() {
//            }.getType());
//        } catch (JsonSyntaxException e) {
//        }
//
//        return res;
//    }

    /**
     * 重点单位导出html模板数据
     */

    @GetMapping("/exportData/{uuid}")
    public void exportData(HttpServletRequest request, HttpServletResponse response, @PathVariable("uuid") String uuid) {
        String basePath = yafjxzProperties.getSavePath() + "pic/%s.png";
        try {
            Map<String,Object> vc = new HashMap<String,Object>();

            String zddwid = digitalplanlistService.doFindById(uuid).getDxid();
            ImportantunitsVO vo = importantunitsService.doFindById(zddwid);
            // 预案基础信息
            vc.put("compPlanInfo", digitalplanlistService.doFindById(uuid));
            // 重点单位基础信息
            vc.put("compZddwInfo", importantunitsService.doFindById(zddwid));
            // 灾情设定 力量部署 要点提示
            vc.put("disastersetList", disastersetService.doFindByPlanId(uuid));
            // 重点部位（建筑类）
            vc.put("jzlpartsList", importantpartsService.doFindJzlListByZddwId(zddwid));
            // 重点部位（装置类）
            vc.put("zzlpartsList", importantpartsService.doFindZzlListByZddwId(zddwid));
            //单位消防力量
            vc.put("xfllList",importantunitsService.doFindXfllListByZddwId(zddwid));
            // 重点部位（储罐类）
            vc.put("cglpartsList", importantpartsService.doFindCglListByZddwId(zddwid));
            // 建筑分区和消防设施
            List<BuildingVO> bvs = importantunitsService.doFindBuildingDetailsAndFirefacilitiesByVo(vo);
            vc.put("areaBuildingList", doJxsl(bvs));
            //文件系统地址（http://localhost:80）
            vc.put("serverUrl", yafjxzProperties.getFileServerUrl());
            // 附图
            YafjxzVO yafjxzVO = new YafjxzVO();
            yafjxzVO.setKzm("pic");
            yafjxzVO.setYaid(uuid);
            vc.put("pictureList", yafjxzService.doFindByPlanId(yafjxzVO));

            vc.put("msg", "yes");
            JSONObject jsonObject = JSONObject.fromObject(vc);

            //3、将json对象转化为json字符串
            String result = jsonObject.toString();
            result=new StringBuffer("var data =").append(result).toString();
            String targerPath=new StringBuffer(yafjxzProperties.getSavePath()).append("/temp/")
                    .append(System.currentTimeMillis()).append("/").toString();
            String targerDwmcPath=new StringBuffer(targerPath).append(vo.getDwmc()).toString();
            String targerDwmcZipPath=new StringBuffer(targerDwmcPath).append(".zip").toString();
            try{
                //获取resource下文件
                File sfile1 = ResourceUtils.getFile("classpath:plantemplate");
                File folder = new File(targerPath);
                //先创建文件夹
                if(!folder.exists()){
                    folder.mkdirs();
                }
                //拷贝文件到临时文件夹
                CreateFileUtil.copyDir(sfile1.toPath().toString(),targerDwmcPath);
                //创建json文件
                CreateFileUtil.createJsonFile(result,targerDwmcPath,"shuju");
                //压缩文件
                ZipUtil.zip(targerDwmcPath,targerPath,vo.getDwmc()+".zip");

                DownloadUtil.zip(( new FileInputStream(new File(targerDwmcZipPath))),  request,  response,  vo.getDwmc()+".zip");
            }catch (Exception e){
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载预案信息word
     *
     * @param uuid 主健
     * @return
     */
    @GetMapping("/downWord/{uuid}/{titles}")
    public String downWord(HttpServletRequest request, HttpServletResponse response, @PathVariable("uuid") String uuid
            , @PathVariable("titles") String titles) {
        String basePath = yafjxzProperties.getSavePath();
        try {
            VelocityContext vc = new VelocityContext();
            DigitalplanlistVO dv=digitalplanlistService.doFindById(uuid);
            String zddwid = dv.getDxid();
            ImportantunitsVO vo = importantunitsService.doFindById(zddwid);
            // 预案基础信息
            vc.put("compPlanInfo", dv);
            // 重点单位基础信息
            vc.put("compZddwInfo", importantunitsService.doFindById(zddwid));
            // 灾情设定 力量部署 要点提示
            vc.put("disastersetList", disastersetService.doFindByPlanId(uuid));
            // 重点部位（建筑类）
            vc.put("jzlpartsList", importantpartsService.doFindJzlListByZddwId(zddwid));
            // 重点部位（装置类）
            vc.put("zzlpartsList", importantpartsService.doFindZzlListByZddwId(zddwid));
            // 重点部位（储罐类）
            vc.put("cglpartsList", importantpartsService.doFindCglListByZddwId(zddwid));
            // 建筑分区和消防设施

            List<BuildingVO> bvs = importantunitsService.doFindBuildingDetailsAndFirefacilitiesByVo(vo);
            vc.put("areaBuildingList", doJxsl(bvs));
            vc.put("titleNum", doTitleNum(titles));
            vc.put("titles", titles);
            if (titles.contains("tp")) {
                // 附图
                YafjxzVO yafjxzVO = new YafjxzVO();
                yafjxzVO.setYaid(uuid);
                yafjxzVO.setKzm("pic");
                //获取图片列表
                List<YafjxzVO> yafjxzVOs=yafjxzService.doFindByPlanId(yafjxzVO);
                for(YafjxzVO yv:yafjxzVOs){
                    //图片二进制编码存放在Reserve1，图片标题存放在Reserve2
                    yv.setReserve1(Pic.getImageStr(new StringBuffer(basePath).append(yv.getXzlj()).toString()));
                    String wjm=yv.getWjm().substring(0,yv.getWjm().indexOf("."));
                    yv.setReserve2(wjm);
                }
                vc.put("pictureList", yafjxzVOs);
            }
            VelocityUtil.createDoc("yuanxiazai.vm", vc, request, response,
                    dv.getYamc()+".doc", -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 标题计数
     *
     * @param select 标题选中列表
     * @return
     */
    public Map<String, Object> doTitleNum(String select) {
        Map<String, Object> titleNumMap = new HashMap<String, Object>();

        int c = 1;
        if (select.contains("dwjbqk")) {
            titleNumMap.put("dwjbqkNum", toChinese(c + ""));
            c++;
        }
        if (select.contains("dwjzxx")) {
            titleNumMap.put("dwjzxxNum", toChinese(c + ""));
            c++;
        }
        if (select.contains("zdbw")) {
            titleNumMap.put("zdbwNum", toChinese(c + ""));
            c++;
        }
        if (select.contains("zqsd")) {
            titleNumMap.put("zqsdNum", toChinese(c + ""));
        }

        return titleNumMap;
    }

    /**
     * 数字转中文
     *
     * @param string 源数字
     * @return 中文数字
     */
    private String toChinese(String string) {
        String[] s1 = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] s2 = {"十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千"};

        String result = "";

        int n = string.length();
        for (int i = 0; i < n; i++) {

            int num = string.charAt(i) - '0';

            if (i != n - 1 && num != 0) {
                result += s1[num] + s2[n - 2 - i];
            } else {
                result += s1[num];
            }
        }
        return result;

    }

    /**
     * 信息统计
     *
     * @param bvs
     * @return
     */
    public List<BuildingVO> doJxsl(List<BuildingVO> bvs) {

        for (BuildingVO bv : bvs) {
            Map<String, Object> data = new HashMap<String, Object>();
            int aqcksl = 0;
            int ssltsl = 0;
            int xfdtsl = 0;
            String bnc = "";
            String yjgb = "";
            String xfkzs = "";
            String fpyss = "无";
            String qtxfss = "";

            Map<String, List> ls = bv.getFirefacilites();
            //安全出口
            List aqck = ls.get("1001");
            if (aqck != null) {
                for (int i = 0; i < aqck.size(); i++) {
                    Firefacilities_aqsscs_aqckVO ff = (Firefacilities_aqsscs_aqckVO) aqck.get(i);
                    if (ff != null && StringUtil.isNotEmpty(ff.getSl())) {
                        aqcksl += Integer.parseInt(ff.getSl());
                    }

                }
            }

            //疏散楼梯
            List sslts = ls.get("1002");
            if (sslts != null) {
                for (int i = 0; i < sslts.size(); i++) {
                    Firefacilities_aqsscs_ssltVO ff = (Firefacilities_aqsscs_ssltVO) sslts.get(i);
                    if (ff != null && StringUtil.isNotEmpty(ff.getSl())) {
                        ssltsl += Integer.parseInt(ff.getSl());
                    }
                }
            }

            //消防电梯
            List xfdts = ls.get("1003");
            if (xfdts != null) {
                for (int i = 0; i < xfdts.size(); i++) {
                    Firefacilities_aqsscs_xfdtVO ff = (Firefacilities_aqsscs_xfdtVO) xfdts.get(i);
                    if (ff != null && StringUtil.isNotEmpty(ff.getSl())) {
                        xfdtsl += Integer.parseInt(ff.getSl());
                    }

                }
            }
            //避难层
            //bnc
            List bncs = ls.get("1004");
            if (bncs != null) {
                bnc = bncs.size() + "个/";
                for (int i = 0; i < bncs.size(); i++) {
                    Firefacilities_aqsscs_bncVO ff = (Firefacilities_aqsscs_bncVO) bncs.get(i);
                    if (ff != null && StringUtil.isNotEmpty(ff.getWz())) {
                        bnc = bnc + ff.getWz() + "、";
                    }
                }
                bnc = bnc.substring(0, bnc.length() - 1);
            }
            //应急广播
            List yjgbs = ls.get("1005");
            if (yjgbs != null) {
                if (yjgbs.size() > 0) {
                    yjgb = "有/";
                }
                for (int i = 0; i < yjgbs.size(); i++) {
                    Firefacilities_aqsscs_yjgbVO ff = (Firefacilities_aqsscs_yjgbVO) yjgbs.get(i);
                    if (ff != null && StringUtil.isNotEmpty(ff.getWz())) {
                        yjgb = yjgb + ff.getWz() + "、";
                    }
                }
                yjgb = yjgb.substring(0, yjgb.length() - 1);
            }

            //消防控制室
            List xfkzss = ls.get("5000");
            if (xfkzss != null) {
                if (xfkzss.size() > 0) {
                    xfkzs = "有/";
                }
                for (int i = 0; i < xfkzss.size(); i++) {
                    Firefacilities_xfkzsVO ff = (Firefacilities_xfkzsVO) xfkzss.get(i);
                    if (ff != null && StringUtil.isNotEmpty(ff.getWz())) {
                        xfkzs = xfkzs + ff.getWz() + "、";
                    }
                }
                xfkzs = xfkzs.substring(0, xfkzs.length() - 1);
            }
            //防排烟设施
            List fpysss = ls.get("6002");
            if (fpysss != null) {
                if (fpysss.size() > 0) {
                    fpyss = "有";
                }
            }
            //其他消防设施
            List qtxfsss = ls.get("9000");
            if (qtxfsss != null) {
                if (qtxfsss.size() > 0) {
                    qtxfss = "有/";
                }
                for (int i = 0; i < qtxfsss.size(); i++) {
                    Firefacilities_qtxfssVO ff = (Firefacilities_qtxfssVO) qtxfsss.get(i);
                    if (ff != null && StringUtil.isNotEmpty(ff.getWz())) {
                        qtxfss = qtxfss + ff.getWz() + "、";
                    }
                }
                qtxfss = qtxfss.substring(0, qtxfss.length() - 1);
            }
            data.put("aqcksl", aqcksl);
            data.put("ssltsl", ssltsl);
            data.put("xfdtsl", xfdtsl);
            data.put("bnc", bnc);
            data.put("qtxfss", qtxfss);
            data.put("yjgb", yjgb);
            data.put("xfkzs", xfkzs);
            data.put("fpyss", fpyss);
            data.put("qtxfss", qtxfss);

            bv.setAqssss(data);
        }

        return bvs;
    }
}
