package com.syfri.digitalplan.model.jxcsplan;

import java.io.Serializable;
import java.util.List;
import com.syfri.baseapi.model.ValueObject;

public class JxcsjbxxVO extends ValueObject implements Serializable{

	private static final long serialVersionUID = 1L;

	private String uuid;	//主键（单位ID）
	private String dwmc;	//单位名称
	private String jxdwlx;	//单位类型
	private String dwdz;	//单位地址
	private String dwgk;	//单位概况
	private String xzqh;	//行政区划
	private String zbdh;	//值班电话
	private String xfgx;	//消防管辖
	private String xqfzr;	//辖区负责人
	private String xqfzrdh;	//辖区负责人电话
	private String jzfl;	//单位建筑分类
	private String jzsl;	//建筑数量
	private String zdmj;	//占地面积
	private String jzmj;	//建筑面积
	private String lon;	//经度
	private String lat;	//纬度
	private String plqkd;	//毗邻情况(东)
	private String plqkn;	//毗邻情况(南)
	private String plqkx;	//毗邻情况(西)
	private String plqkb;	//毗邻情况(北)
	private String gnfqms;	//功能分区描述
	private String zdbwms;	//重点部位描述
	private String zbxftd;	//周边消防通道
	private String bz;	//备注
	private String deleteFlag;	//删除标志
	private String cjrid;	//创建人ID
	private String cjrmc;	//创建人名称
	private String cjsj;	//创建时间
	private String xgrid;	//修改人ID
	private String xgrmc;	//修改人姓名
	private String xgsj;	//修改时间
	private String datasource;	//数据来源[10000000一体化]
	private String jdh;	//节点号
	private String sjc;	//时间戳
	private String reserve1;	//备用字段1
	private String reserve2;	//备用字段2
	private String reserve3;	//备用字段3
	private String reserve4;	//备用字段4

	private String sjzt; //数据状态
	private String shzt; //审核状态
	private String shrid; //审核人id
	private String shrmc; //审核人名称
	private String shrzw; //审核人职务
	private String shsj;  //审核时间
	private String sjztmc; //数据状态mc
	private String shztmc; //审核状态mc

	private String jxdwlxmc;	//单位类型
	private String xfgxmc;	//消防管辖
	private String xzqhmc;	//行政区划
	private String jzflmc;	//单位建筑分类

	private List<JxcsjzxxVO> jzxxList;//九小建筑信息列表
	private List<JxcsxfssVO> xfssList;//九小消防设施列表

	private String unscid;//统一社会信用代码

	public String getUuid(){
		return uuid;
	}
	public void setUuid(String uuid){
		this.uuid = uuid;
	}
	public String getDwmc(){
		return dwmc;
	}
	public void setDwmc(String dwmc){
		this.dwmc = dwmc;
	}
	public String getJxdwlx(){
		return jxdwlx;
	}
	public void setJxdwlx(String jxdwlx){
		this.jxdwlx = jxdwlx;
	}
	public String getDwdz(){
		return dwdz;
	}
	public void setDwdz(String dwdz){
		this.dwdz = dwdz;
	}
	public String getDwgk(){
		return dwgk;
	}
	public void setDwgk(String dwgk){
		this.dwgk = dwgk;
	}
	public String getXzqh(){
		return xzqh;
	}
	public void setXzqh(String xzqh){
		this.xzqh = xzqh;
	}
	public String getZbdh(){
		return zbdh;
	}
	public void setZbdh(String zbdh){
		this.zbdh = zbdh;
	}
	public String getXfgx(){
		return xfgx;
	}
	public void setXfgx(String xfgx){
		this.xfgx = xfgx;
	}
	public String getXqfzr() {
		return xqfzr;
	}
	public void setXqfzr(String xqfzr) {
		this.xqfzr = xqfzr;
	}
	public String getXqfzrdh() {
		return xqfzrdh;
	}
	public void setXqfzrdh(String xqfzrdh) {
		this.xqfzrdh = xqfzrdh;
	}
	public String getJzfl(){
		return jzfl;
	}
	public void setJzfl(String jzfl){
		this.jzfl = jzfl;
	}
	public String getJzsl(){
		return jzsl;
	}
	public void setJzsl(String jzsl){
		this.jzsl = jzsl;
	}
	public String getZdmj(){
		return zdmj;
	}
	public void setZdmj(String zdmj){
		this.zdmj = zdmj;
	}
	public String getJzmj(){
		return jzmj;
	}
	public void setJzmj(String jzmj){
		this.jzmj = jzmj;
	}
	public String getLon(){
		return lon;
	}
	public void setLon(String lon){
		this.lon = lon;
	}
	public String getLat(){
		return lat;
	}
	public void setLat(String lat){
		this.lat = lat;
	}
	public String getPlqkd(){
		return plqkd;
	}
	public void setPlqkd(String plqkd){
		this.plqkd = plqkd;
	}
	public String getPlqkn(){
		return plqkn;
	}
	public void setPlqkn(String plqkn){
		this.plqkn = plqkn;
	}
	public String getPlqkx(){
		return plqkx;
	}
	public void setPlqkx(String plqkx){
		this.plqkx = plqkx;
	}
	public String getPlqkb(){
		return plqkb;
	}
	public void setPlqkb(String plqkb){
		this.plqkb = plqkb;
	}
	public String getGnfqms(){
		return gnfqms;
	}
	public void setGnfqms(String gnfqms){
		this.gnfqms = gnfqms;
	}
	public String getZdbwms(){
		return zdbwms;
	}
	public void setZdbwms(String zdbwms){
		this.zdbwms = zdbwms;
	}
	public String getZbxftd(){
		return zbxftd;
	}
	public void setZbxftd(String zbxftd){
		this.zbxftd = zbxftd;
	}
	public String getBz(){
		return bz;
	}
	public void setBz(String bz){
		this.bz = bz;
	}
	public String getDeleteFlag(){
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag){
		this.deleteFlag = deleteFlag;
	}
	public String getCjrid(){
		return cjrid;
	}
	public void setCjrid(String cjrid){
		this.cjrid = cjrid;
	}
	public String getCjrmc(){
		return cjrmc;
	}
	public void setCjrmc(String cjrmc){
		this.cjrmc = cjrmc;
	}
	public String getCjsj(){
		return cjsj;
	}
	public void setCjsj(String cjsj){
		this.cjsj = cjsj;
	}
	public String getXgrid(){
		return xgrid;
	}
	public void setXgrid(String xgrid){
		this.xgrid = xgrid;
	}
	public String getXgrmc(){
		return xgrmc;
	}
	public void setXgrmc(String xgrmc){
		this.xgrmc = xgrmc;
	}
	public String getXgsj(){
		return xgsj;
	}
	public void setXgsj(String xgsj){
		this.xgsj = xgsj;
	}
	public String getDatasource(){
		return datasource;
	}
	public void setDatasource(String datasource){
		this.datasource = datasource;
	}
	public String getJdh(){
		return jdh;
	}
	public void setJdh(String jdh){
		this.jdh = jdh;
	}
	public String getSjc(){
		return sjc;
	}
	public void setSjc(String sjc){
		this.sjc = sjc;
	}
	public String getReserve1(){
		return reserve1;
	}
	public void setReserve1(String reserve1){
		this.reserve1 = reserve1;
	}
	public String getReserve2(){
		return reserve2;
	}
	public void setReserve2(String reserve2){
		this.reserve2 = reserve2;
	}
	public String getReserve3(){
		return reserve3;
	}
	public void setReserve3(String reserve3){
		this.reserve3 = reserve3;
	}
	public String getReserve4(){
		return reserve4;
	}
	public void setReserve4(String reserve4){
		this.reserve4 = reserve4;
	}

	public String getSjzt() {
		return sjzt;
	}
	public void setSjzt(String sjzt) {
		this.sjzt = sjzt;
	}
	public String getShzt() {
		return shzt;
	}
	public void setShzt(String shzt) {
		this.shzt = shzt;
	}
	public String getShrid() {
		return shrid;
	}
	public void setShrid(String shrid) {
		this.shrid = shrid;
	}
	public String getShrmc() {
		return shrmc;
	}
	public void setShrmc(String shrmc) {
		this.shrmc = shrmc;
	}
	public String getShrzw() {
		return shrzw;
	}
	public void setShrzw(String shrzw) {
		this.shrzw = shrzw;
	}
	public String getShsj() {
		return shsj;
	}
	public void setShsj(String shsj) {
		this.shsj = shsj;
	}
	public String getSjztmc() {
		return sjztmc;
	}
	public void setSjztmc(String sjztmc) {
		this.sjztmc = sjztmc;
	}
	public String getShztmc() {
		return shztmc;
	}
	public void setShztmc(String shztmc) {
		this.shztmc = shztmc;
	}

	public String getJxdwlxmc() {
		return jxdwlxmc;
	}
	public void setJxdwlxmc(String jxdwlxmc) {
		this.jxdwlxmc = jxdwlxmc;
	}
	public String getXfgxmc() {
		return xfgxmc;
	}
	public void setXfgxmc(String xfgxmc) {
		this.xfgxmc = xfgxmc;
	}
	public String getXzqhmc() {
		return xzqhmc;
	}
	public void setXzqhmc(String xzqhmc) {
		this.xzqhmc = xzqhmc;
	}
	public String getJzflmc() {
		return jzflmc;
	}
	public void setJzflmc(String jzflmc) {
		this.jzflmc = jzflmc;
	}

	public List<JxcsjzxxVO> getJzxxList() {
		return jzxxList;
	}
	public void setJzxxList(List<JxcsjzxxVO> jzxxList) {
		this.jzxxList = jzxxList;
	}
	public List<JxcsxfssVO> getXfssList() {
		return xfssList;
	}
	public void setXfssList(List<JxcsxfssVO> xfssList) {
		this.xfssList = xfssList;
	}

	public String getUnscid() {
		return unscid;
	}
	public void setUnscid(String unscid) {
		this.unscid = unscid;
	}
}