package com.syfri.digitalplan.model.digitalplan;

import java.io.Serializable;
import java.util.List;

import com.syfri.baseapi.model.ValueObject;

public class DigitalplanlistVO extends ValueObject implements Serializable{

	private static final long serialVersionUID = 1L;

	private String uuid;	//主键
	private String yabm;	//预案编号
	private String yalx;	//预案类型代码
	private String yamc;	//预案名称
	private String yazt;	//预案状态
	private String yajb;	//预案级别
	private String dxid;	//对象ID
	private String dxmc;	//对象名称
	private String jgid;	//制作机构ID
	private String jgbm;	//制作机构编码
	private String jgmc;	//制作机构名称
	private String zzrid;	//制作人ID
	private String zzrmc;	//制作人名称
	private String zzsj;	//制作时间
	private String shzt;	//审核状态[1]审核中，[2]已通过，[3]未通过
	private String shrid;	//审核人ID
	private String shrmc;	//审核人名称
	private String shsj;	//审核时间
	private String xgrid;	//修改人ID
	private String xgrmc;	//修改人姓名
	private String xgsj;	//修改时间
	private String deleteFlag;	//删除标志
	private String datasource;	//数据来源[100000一体化]
	private String bz;	//备注
	private String jdh;	//节点号
	private String sjc;	//时间戳
	private String reserve1;	//备用字段1
	private String reserve2;	//备用字段2
	private String reserve3;	//备用字段3
	private String reserve4;	//备用字段4

	private String begintime;	//开始时间
	private String endtime;	//结束时间
	private String yadxType;  //高级搜索预案对象类别（1为重点单位，2为消防保卫警卫，3为其他对象）
	private String yashztButtonType;  //预案审核状态按钮样式（已审核为绿色，未审核为红色，未通过为黄色）


	public String getBegintime(){
		return begintime;
	}
	public void setBegintime(String begintime){
		this.begintime = begintime;
	}
	public String getEndtime(){
		return endtime;
	}
	public void setEndtime(String endtime){
		this.endtime = endtime;
	}


	public String getUuid(){
		return uuid;
	}
	public void setUuid(String uuid){
		this.uuid = uuid;
	}
	public String getYabm(){
		return yabm;
	}
	public void setYabm(String yabm){
		this.yabm = yabm;
	}
	public String getYalx(){
		return yalx;
	}
	public void setYalx(String yalx){
		this.yalx = yalx;
	}
	public String getYamc(){
		return yamc;
	}
	public void setYamc(String yamc){
		this.yamc = yamc;
	}
	public String getYazt(){
		return yazt;
	}
	public void setYazt(String yazt){
		this.yazt = yazt;
	}
	public String getYajb(){
		return yajb;
	}
	public void setYajb(String yajb){
		this.yajb = yajb;
	}
	public String getDxid(){
		return dxid;
	}
	public void setDxid(String dxid){
		this.dxid = dxid;
	}
	public String getDxmc(){
		return dxmc;
	}
	public void setDxmc(String dxmc){
		this.dxmc = dxmc;
	}
	public String getJgid(){
		return jgid;
	}
	public void setJgid(String jgid){
		this.jgid = jgid;
	}
	public String getJgbm(){
		return jgbm;
	}
	public void setJgbm(String jgbm){
		this.jgbm = jgbm;
	}
	public String getJgmc(){
		return jgmc;
	}
	public void setJgmc(String jgmc){
		this.jgmc = jgmc;
	}
	public String getZzrid(){
		return zzrid;
	}
	public void setZzrid(String zzrid){
		this.zzrid = zzrid;
	}
	public String getZzrmc(){
		return zzrmc;
	}
	public void setZzrmc(String zzrmc){
		this.zzrmc = zzrmc;
	}
	public String getZzsj(){
		return zzsj;
	}
	public void setZzsj(String zzsj){
		this.zzsj = zzsj;
	}
	public String getShzt(){
		return shzt;
	}
	public void setShzt(String shzt){
		this.shzt = shzt;
	}
	public String getShrid(){
		return shrid;
	}
	public void setShrid(String shrid){
		this.shrid = shrid;
	}
	public String getShrmc(){
		return shrmc;
	}
	public void setShrmc(String shrmc){
		this.shrmc = shrmc;
	}
	public String getShsj(){
		return shsj;
	}
	public void setShsj(String shsj){
		this.shsj = shsj;
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
	public String getDeleteFlag(){
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag){
		this.deleteFlag = deleteFlag;
	}
	public String getDatasource(){
		return datasource;
	}
	public void setDatasource(String datasource){
		this.datasource = datasource;
	}
	public String getBz(){
		return bz;
	}
	public void setBz(String bz){
		this.bz = bz;
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

	private String yalxmc;	//预案类型
	private String yaztmc;	//预案状态
	private String yajbmc;	//预案级别
	private String shztmc;	//审核状态
	private String zzjgmc;  //制作机构名称

	private List<DisastersetVO> disasterListVo; //灾情设定
	public List<DisastersetVO> getDisasterListVo() {
		return disasterListVo;
	}
	public void setDisasterListVo(List<DisastersetVO> disasterListVo) {
		this.disasterListVo = disasterListVo;
	}

	private List disasterList; //灾情设定
	public List getDisasterList(){
		return disasterList;
	}
	public void setDisasterList(List disasterList){
		this.disasterList = disasterList;
	}

	public String getYalxmc(){
		return yalxmc;
	}
	public void setYalxmc(String yalxmc){
		this.yalxmc = yalxmc;
	}
	public String getYaztmc(){
		return yaztmc;
	}
	public void setYaztmc(String yaztmc){
		this.yaztmc = yaztmc;
	}
	public String getYajbmc(){
		return yajbmc;
	}
	public void setYajbmc(String yajbmc){
		this.yajbmc = yajbmc;
	}
	public String getShztmc(){
		return shztmc;
	}
	public void setShztmc(String shztmc){
		this.shztmc = shztmc;
	}
	public String getZzjgmc() {
		return zzjgmc;
	}
	public void setZzjgmc(String zzjgmc) {
		this.zzjgmc = zzjgmc;
	}

	public String getYadxType() {
		return yadxType;
	}
	public void setYadxType(String yadxType) {
		this.yadxType = yadxType;
	}

	public String getYashztButtonType() {
		return yashztButtonType;
	}
	public void setYashztButtonType(String yashztButtonType) {
		this.yashztButtonType = yashztButtonType;
	}

	private List yafjxzList;//预案附件列表
	public List getYafjxzList() {
		return yafjxzList;
	}
	public void setYafjxzList(List yafjxzList) {
		this.yafjxzList = yafjxzList;
	}
}