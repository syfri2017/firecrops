package com.syfri.userservice.service.impl;

import com.syfri.userservice.model.AccountVO;
import com.syfri.userservice.service.AccountService;
import com.syfri.userservice.service.OrganizationService;
import com.syfri.userservice.utils.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syfri.baseapi.service.impl.BaseServiceImpl;
import com.syfri.userservice.dao.UserDAO;
import com.syfri.userservice.model.UserVO;
import com.syfri.userservice.service.UserService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<UserVO> implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private AccountService accountService;

	@Override
	public UserDAO getBaseDAO() {
		return userDAO;
	}

	/*--查询：根据用户获取用户及其角色--.*/
	@Override
	public List<UserVO> doFindUserRoles(UserVO userVO) {
		return userDAO.doFindUserRoles(userVO);
	}

	/*--新增：增加用户并为用户赋予角色.--*/
	@Override
	public UserVO doInsertUserRoles(UserVO userVO){

		//向账户表SYS_ACCOUNT插入账户信息
		AccountVO accountVO = new AccountVO();
		accountVO.setUsername(userVO.getUsername());
		accountVO.setPassword(userVO.getPassword());
		accountVO.setRealname(userVO.getRealname());
		accountService.doInsertAccountByVO(accountVO);

		//向用户表SYS_USER插入用户信息
		userVO.setCreateId(CurrentUserUtil.getCurrentUserId());
		userVO.setCreateName(CurrentUserUtil.getCurrentUserName());
		userVO.setUserid(accountVO.getUserid());
		userDAO.doInsertByVO(userVO);
		if(!userVO.getOrganizationId().isEmpty()){
			userVO.setOrganizationName(organizationService.doFindById(userVO.getOrganizationId()).getJgjc());
		}

		//向中间表中插入账户角色情况
		accountService.doInsertAccountRolesBatch(accountVO.getUserid(), userVO.getRoles());
		return userVO;
	}

	/*--修改：修改用户并修改用户角色.--*/
	@Override
	public UserVO doUpdateUserRoles(UserVO userVO){
		//修改账户表
		AccountVO accountVO = new AccountVO(userVO.getUserid(), userVO.getUsername(), userVO.getPassword(), userVO.getRealname());
		accountService.doUpdateAccountByVO(accountVO);

		//修改用户表基本信息
		userDAO.doUpdateByVO(userVO);
		if(!userVO.getOrganizationId().isEmpty()){
			userVO.setOrganizationName(organizationService.doFindById(userVO.getOrganizationId()).getJgjc());
		}

		//修改角色中间表信息
		accountService.doDeleteAccountRoles(userVO.getUserid());
		accountService.doInsertAccountRolesBatch(accountVO.getUserid(), userVO.getRoles());
		return userVO;
	}

	/*--删除：删除用户同时删除其角色.--*/
	@Override
	public int doDeleteUserRoles(List<UserVO> list){
		int num = 0;
		for(UserVO vo : list){
			String pkid = vo.getPkid();
			String userid = userDAO.doFindById(pkid).getUserid();
			//删除用户表
			userDAO.doDeleteById(pkid);
			//删除账户表
			accountService.doDeleteById(userid);
			//删除账户角色中间表
			accountService.doDeleteAccountRoles(userid);
			num++;
		}
		return num;
	}

	/*--查询：获取未绑定组织机构的用户信息.--*/
	@Override
	public List<UserVO> findUsersNoOrg(UserVO userVO) {
		return userDAO.findUsersNoOrg(userVO);
	}

}