package com.syfri.userservice.service.impl;

import com.syfri.userservice.model.*;
import com.syfri.userservice.service.ResourceService;
import com.syfri.userservice.utils.CurrentUserUtil;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.syfri.baseapi.service.impl.BaseServiceImpl;
import com.syfri.userservice.dao.RoleDAO;
import com.syfri.userservice.service.RoleService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Service("roleService")
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class RoleServiceImpl extends BaseServiceImpl<RoleVO> implements RoleService {

	@Autowired
	private RoleDAO roleDAO;

	@Autowired
	private ResourceService resourceService;

	@Override
	public RoleDAO getBaseDAO() {
		return roleDAO;
	}

	@Override
	public List<RoleVO> doFindRoleByUserid(String userid) {
		return roleDAO.doFindRoleByUserid(userid);
	}

	/*--查询：获取用户资源，如果roleVO为null，则获取所有用户资源.--*/
	@Override
	public List<RoleVO> doFindRoleResources(RoleVO roleVO){
		return roleDAO.doFindRoleResources(roleVO);
	}

	/*--新增：增加角色并为用户赋予资源.--*/
	@Override
	public RoleVO doInsertRoleResources(RoleVO roleVO){

		//向角色表SYS_ROLE中增加数据
		roleVO.setCreateId(CurrentUserUtil.getCurrentUserId());
		roleVO.setCreateName(CurrentUserUtil.getCurrentUserName());
		roleDAO.doInsertByVO(roleVO);

		//向角色资源中间表SYS_ROLE_RESOURCE中增加数据
		((RoleService)AopContext.currentProxy()).insertRoleResourcesBatch(roleVO.getRoleid(), roleVO.getResources());

		return roleVO;
	}

	/*--修改：修改角色并修改用户资源.--*/
	@Override
	public RoleVO doUpdateRoleResources(RoleVO roleVO) {

		//修改角色表SYS_ROLE数据
		roleVO.setAlterId(CurrentUserUtil.getCurrentUserId());
		roleVO.setAlterName(CurrentUserUtil.getCurrentUserName());
		roleDAO.doUpdateByVO(roleVO);

		//修改角色资源中间表SYS_ROLE_RESOURCE数据
		String roleid = roleVO.getRoleid();
		roleDAO.doDeleteRoleResources(roleid);
		((RoleService)AopContext.currentProxy()).insertRoleResourcesBatch(roleid, roleVO.getResources());

		return roleVO;
	}

	/*--删除：删除角色同时删除其资源.--*/
	@Override
	public int doDeleteRoleResources(List<RoleVO> list){
		int num = 0;
		for(RoleVO vo : list){
			String roleid = vo.getRoleid();
			//删除角色表int
			roleDAO.doDeleteById(roleid);
			//删除角色资源中间表SYS_ROLE_RESOURCE
			roleDAO.doDeleteRoleResourcesBatch(roleid);
			num++;
		}
		return num;
	}

	/*批量向角色权限中间表插入数据.*/
	@Override
	public int insertRoleResourcesBatch(String roleid, List<ResourceVO> resources){
		List<RoleResourceVO> list = new ArrayList<>();
		if(resources!=null && resources.size()>0){
			for(ResourceVO resource : resources){
				RoleResourceVO temp = ((RoleService)AopContext.currentProxy()).getRoleResourceVO(roleid, resource.getResourceid());
				list.add(temp);

				ResourceVO resourceVO1 = resourceService.doFindByVO(new ResourceVO(resource.getResourceid()));
				if(resourceVO1!=null){
					String parentid1 = resourceVO1.getParentid();
					if(!"-1".equals(parentid1)){
						RoleResourceVO parent1 = ((RoleService)AopContext.currentProxy()).getRoleResourceVO(roleid, parentid1);
						if(!((RoleService)AopContext.currentProxy()).getContainsResult(list, parent1)){
							list.add(parent1);
						}

						ResourceVO resourceVO2 = resourceService.doFindByVO(new ResourceVO(resourceVO1.getParentid()));
						if(resourceVO2!=null){
							String parentid2 = resourceVO2.getParentid();
							if(!"-1".equals(parentid2)){
								RoleResourceVO parent2 = ((RoleService)AopContext.currentProxy()).getRoleResourceVO(roleid, parentid2);
								if(!((RoleService)AopContext.currentProxy()).getContainsResult(list, parent2)){
									list.add(parent2);
								}
							}
						}
					}
				}
			}
			return roleDAO.doInsertRoleResourcesBatch(list);
		}else{
			return -1;
		}
	}

	/*--获取所有角色.--*/
	@Override
	public List<RoleVO> doFindAll(){
		return roleDAO.doFindAll();
	}

	/*--根据用户ID取角色Map.--*/
	@Override
	public List<String> getRolesByUserid(String userid){
		List<String> list = new ArrayList<>();
		List<RoleVO> roles = roleDAO.doFindRoleByUserid(userid);
		for(RoleVO role : roles){
			list.add(role.getRolename());
		}
		return list;
	}

	/*--获取角色资源中间表对象.--*/
	@Override
	public RoleResourceVO getRoleResourceVO(String roleid, String resourceid){
		RoleResourceVO roleResourceVO = new RoleResourceVO();
		roleResourceVO.setRoleid(roleid);
		roleResourceVO.setResourceid(resourceid);
		roleResourceVO.setCreateId(CurrentUserUtil.getCurrentUserId());
		roleResourceVO.setCreateName(CurrentUserUtil.getCurrentUserName());
		return roleResourceVO;
	}

	/*--判断一个List对象是否含有某个对象.--*/
	public boolean getContainsResult(List<RoleResourceVO> list, RoleResourceVO roleResourceVO){
		for(RoleResourceVO temp : list){
			if(temp.getResourceid().equals(roleResourceVO.getResourceid())){
				return true;
			}
		}
		return false;
	}
}