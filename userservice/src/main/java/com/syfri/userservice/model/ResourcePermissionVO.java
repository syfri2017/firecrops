package com.syfri.userservice.model;

import java.io.Serializable;

import com.syfri.baseapi.model.ValueObject;

public class ResourcePermissionVO extends ValueObject implements Serializable{

	private static final long serialVersionUID = 1L;

	private String pkid;	//主键
	private String resourceid;	//资源ID
	private String permissionid;	//权限ID
	private String dataauth;	//数据权限
	private String deleteFlag;	//删除标志
	private String createId;	//创建人ID
	private String createName;	//创建人
	private String createTime;	//创建时间
	private String alterId;	//修改人ID
	private String alterName;	//修改人
	private String alterTime;	//修改时间
	private String reserve1;	//备用1
	private String reserve2;	//备用2
	private String reserve3;	//备用3

	public String getPkid(){
		return pkid;
	}
	public void setPkid(String pkid){
		this.pkid = pkid;
	}
	public String getResourceid(){
		return resourceid;
	}
	public void setResourceid(String resourceid){
		this.resourceid = resourceid;
	}
	public String getPermissionid(){
		return permissionid;
	}
	public void setPermissionid(String permissionid){
		this.permissionid = permissionid;
	}
	public String getDataauth(){
		return dataauth;
	}
	public void setDataauth(String dataauth){
		this.dataauth = dataauth;
	}
	public String getDeleteFlag(){
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag){
		this.deleteFlag = deleteFlag;
	}
	public String getCreateId(){
		return createId;
	}
	public void setCreateId(String createId){
		this.createId = createId;
	}
	public String getCreateName(){
		return createName;
	}
	public void setCreateName(String createName){
		this.createName = createName;
	}
	public String getCreateTime(){
		return createTime;
	}
	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}
	public String getAlterId(){
		return alterId;
	}
	public void setAlterId(String alterId){
		this.alterId = alterId;
	}
	public String getAlterName(){
		return alterName;
	}
	public void setAlterName(String alterName){
		this.alterName = alterName;
	}
	public String getAlterTime(){
		return alterTime;
	}
	public void setAlterTime(String alterTime){
		this.alterTime = alterTime;
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
}