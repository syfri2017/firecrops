package com.syfri.userservice.service;

import com.syfri.baseapi.service.BaseService;
import com.syfri.userservice.model.PermissionVO;
import com.syfri.userservice.model.ResourceVO;

import java.util.List;

public interface PermissionService  extends BaseService<PermissionVO>{

	/*--根据资源列表获取权限列表.--*/
	List<PermissionVO> doFindPermissionByResourceList(List<ResourceVO> resourceList);

	/*--根据资源ID获取权限列表.--*/
	List<PermissionVO> doFindPermissionByResourceId(String resourceid);

	/*--新增；权限.--*/
	PermissionVO doInsertPermission(PermissionVO permissionVO);

	/*--修改：权限.--*/
	PermissionVO doUpdatePermission(PermissionVO permissionVO);

	/*--删除：权限.--*/
	int doDeletePermissions(List<PermissionVO> list);

	/*--获取所有的权限.--*/
	List<PermissionVO> doFindAll();
}