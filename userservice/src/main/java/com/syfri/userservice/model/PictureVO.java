package com.syfri.userservice.model;

import java.io.Serializable;

import com.syfri.baseapi.model.ValueObject;

public class PictureVO extends ValueObject implements Serializable{

	private static final long serialVersionUID = 1L;

	private String pkid;	//主键
	private String picType;	//图片类型
	private String picTypename;	//图片类型名称
	private String picValue;	//图片代码
	private String picName;	//图片名称
	private byte[] picBlob;	//图片
	private byte[] imgFile;	//图片二进制流
	private String photo64;	//Base64格式图片
	private String deleteFlag;	//删除标志(默认'N')
	private String remark;	//备注
	private String createId;	//创建人ID
	private String createName;	//创建人
	private String createTime;	//创建时间
	private String alterId;	//修改人ID
	private String alterName;	//修改人
	private String alterTime;	//修改时间
	private String imgStr;	//列表图片
	private String reserve1;	//
	private String reserve2;	//
	private String reserve3;	//
	private String reserve4;

	public String getImgStr() {
		return imgStr;
	}

	public void setImgStr(String imgStr) {
		this.imgStr = imgStr;
	}

	public byte[] getImgFile() {
		return imgFile;
	}

	public void setImgFile(byte[] imgFile) {
		this.imgFile = imgFile;
	}

	public String getPhoto64() {
		return photo64;
	}

	public void setPhoto64(String photo64) {
		this.photo64 = photo64;
	}

	public String getPkid(){
		return pkid;
	}
	public void setPkid(String pkid){
		this.pkid = pkid;
	}
	public String getPicType(){
		return picType;
	}
	public void setPicType(String picType){
		this.picType = picType;
	}
	public String getPicTypename(){
		return picTypename;
	}
	public void setPicTypename(String picTypename){
		this.picTypename = picTypename;
	}
	public String getPicValue(){
		return picValue;
	}
	public void setPicValue(String picValue){
		this.picValue = picValue;
	}
	public String getPicName(){
		return picName;
	}
	public void setPicName(String picName){
		this.picName = picName;
	}
	public byte[] getPicBlob(){
		return picBlob;
	}
	public void setPicBlob(byte[] picBlob){
		this.picBlob = picBlob;
	}
	public String getDeleteFlag(){
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag){
		this.deleteFlag = deleteFlag;
	}
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
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

	public String getReserve1() {
		return reserve1;
	}

	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}

	public String getReserve2() {
		return reserve2;
	}

	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}

	public String getReserve3() {
		return reserve3;
	}

	public void setReserve3(String reserve3) {
		this.reserve3 = reserve3;
	}

	public String getReserve4() {
		return reserve4;
	}

	public void setReserve4(String reserve4) {
		this.reserve4 = reserve4;
	}
}