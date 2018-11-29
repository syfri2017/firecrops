package com.syfri.userservice.config;

import com.syfri.userservice.model.*;
import com.syfri.userservice.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class MyShiroRealm extends AuthorizingRealm{

	private static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

	@Resource
	private AccountService accountService;

	@Resource
	private RoleService roleService;

	@Resource
	private ResourceService resourceService;

	@Resource
	private PermissionService permissionService;

	@Resource
	private OrganizationService organizationService;

	/**
	 * 认证信息（身份验证）
	 * Authentication 是用来验证用户身份
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		logger.info("【MyShiroRealm】身份验证");

		String username = (String) token.getPrincipal();
		AccountVO accountVO = new AccountVO();
		accountVO.setUsername(username);
		List<AccountVO> accounts = accountService.doSearchListByVO(accountVO);
		if(accounts == null || accounts.size() == 0){
			return null;
		}
		AccountVO account = accounts.get(0);

		ShiroUser shiroUser = new ShiroUser(account.getUserid(), account.getUsername(), account.getRealname());

		//根据用户ID获取其组织机构
		shiroUser.setOrganizationVO(organizationService.doFindOrganizationByUserid(account.getUserid()));

		List<String> roles = new ArrayList();
		List<String> permissions = new ArrayList();

		//根据用户ID获取用户角色
		List<RoleVO> roleList = roleService.doFindRoleByUserid(account.getUserid());

		//根据用户角色列表获取用户资源
		List<ResourceVO> resourceList = resourceService.doFindResourceByRoleList(roleList);

		//根据资源列表获取用户权限
		List<PermissionVO> permissionList = permissionService.doFindPermissionByResourceList(resourceList);

		for(RoleVO role : roleList){
			roles.add(role.getRolename());
		}

		for(PermissionVO permission : permissionList){
			permissions.add(permission.getPermissionname());
		}

		shiroUser.setRoles(roles);
		shiroUser.setPermissions(permissions);

		//根据用户角色列表获取角色资源树
		List<ResourceTree> resourceTrees = resourceService.getMenuTree(roleList);
		shiroUser.setResourceTrees(resourceTrees);

		//获取权限信息

		//账号判断（明文）
		/*
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				user,
				user.getPassword(),
				getName()
		);
		*/
		//账号判断（密文）
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				shiroUser,
				account.getPassword(),
				ByteSource.Util.bytes(account.getSalt()),
				getName()
		);
		Session session = SecurityUtils.getSubject().getSession();
		session.setAttribute("realname", shiroUser.getRealName());
		return authenticationInfo;
	}

	/**
	 * 授权管理 1.如果用户正常退出，缓存自动清空；2。如何用户非正常退出，缓存自动清空；
	 * 3.如果我们修改了用户的权限，而用户不退出系统	，修改的权限无法立即生效，再权限修改后调用realm中的方法，二realm由spring管理，所以从spring中调用clearCached方法，再service中调用
	 * 此方法调用hasRole、hasPermission时才会进行回调
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		logger.info("【MyShiroRealm】权限管理");
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

		ShiroUser shiroUser = (ShiroUser) principalCollection.getPrimaryPrincipal();
		if(shiroUser != null){
			authorizationInfo.addRoles(shiroUser.getRoles());
			for(int i=0; i<shiroUser.getPermissions().size(); i++){
				authorizationInfo.addStringPermission(shiroUser.getPermissions().get(i));
			}
			return authorizationInfo;
		}
		/*
		UserVO userVO = (UserVO) principalCollection.getPrimaryPrincipal();
		if(userVO != null){
			//根据用户取角色
			List<RoleVO> roleList = roleService.doFindRoleByUserid(userVO);
			for(RoleVO role : roleList){
				authorizationInfo.addRole(role.getRolename());
				List<PermissionVO> permissionList = permissionService.doFindPermissionByRoleid(role);
				for(PermissionVO permission : permissionList){
					authorizationInfo.addStringPermission(permission.getPermissionname());
				}
			}
			return authorizationInfo;
		}
		*/
		return null;
	}

	@Override
	public void onLogout(PrincipalCollection principals){
		super.clearCachedAuthorizationInfo(principals);
		ShiroUser shiroUser = (ShiroUser)principals.getPrimaryPrincipal();
		removeUserCache(shiroUser);
	}

	/**
	 * 清除用户缓存
	 * @param shiroUser
	 */
	public void removeUserCache(ShiroUser shiroUser){
		removeUserCache(shiroUser.getUsername());
	}

	/**
	 * 清除用户缓存
	 * @param username
	 */
	public void removeUserCache(String username){
		SimplePrincipalCollection principals = new SimplePrincipalCollection();
		principals.add(username, super.getName());
		super.clearCachedAuthenticationInfo(principals);
	}

}
