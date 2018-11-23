package com.syfri.userservice.service;

import com.syfri.baseapi.service.BaseService;
import com.syfri.userservice.model.*;

import java.util.List;

public interface RoleService  extends BaseService<RoleVO>{
	/*根据用户取角色.*/
	List<RoleVO> doFindRoleByUserid(String userid);

	/*--查询：获取角色资源，如果roleVO为null，则获取所有用户资源.--*/
	List<RoleVO> doFindRoleResources(RoleVO roleVO);

	/*--新增：增加角色并为用户赋予资源.--*/
	RoleVO doInsertRoleResources(RoleVO roleVO);

	/*--修改：修改角色并修改用户资源.--*/
	RoleVO doUpdateRoleResources(RoleVO roleVO);

	/*--删除：删除角色同时删除其资源.--*/
	int doDeleteRoleResources(List<RoleVO> list);

	/*--向角色资源中间表中批量增加数据.--*/
	int insertRoleResourcesBatch(String roleid, List<ResourceVO> resources);

	/*--获取所有角色.--*/
	List<RoleVO> doFindAll();

	/*--根据用户ID取角色Map.--*/
	List<String> getRolesByUserid(String userid);

	/*--获取角色资源中间表对象.--*/
	RoleResourceVO getRoleResourceVO(String roleid, String resourceid);

	/*--判断一个List对象是否含有某个对象.--*/
	boolean getContainsResult(List<RoleResourceVO> list, RoleResourceVO roleResourceVO);
}