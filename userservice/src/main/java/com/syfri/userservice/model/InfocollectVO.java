package com.syfri.userservice.model;

import java.io.Serializable;

import com.syfri.baseapi.model.ValueObject;

public class InfocollectVO extends ValueObject implements Serializable{

	private static final long serialVersionUID = 1L;

	private String uuid;	//主键
	private String dwid;	//单位ID
	private String unscid;	//统一社会信用代码
	private String deleteFlag;	//删除标志
	private String datasource;	//Datasource
	private String jdh;	//节点号
	private String sjc;	//时间戳
	private String reserve1;	//备注1
	private String reserve2;	//备注2
	private String reserve3;	//备注3
	private String reserve4;	//备注4

	public InfocollectVO() {
	}

	public InfocollectVO(String unscid) {
		this.unscid = unscid;
	}

	public String getUuid(){
		return uuid;
	}
	public void setUuid(String uuid){
		this.uuid = uuid;
	}
	public String getDwid(){
		return dwid;
	}
	public void setDwid(String dwid){
		this.dwid = dwid;
	}
	public String getUnscid(){
		return unscid;
	}
	public void setUnscid(String unscid){
		this.unscid = unscid;
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
}