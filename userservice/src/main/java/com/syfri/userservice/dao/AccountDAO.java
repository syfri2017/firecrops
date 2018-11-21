package com.syfri.userservice.dao;

import com.syfri.baseapi.dao.BaseDAO;
import com.syfri.userservice.model.AccountRoleVO;
import com.syfri.userservice.model.AccountVO;

import java.util.List;

public interface AccountDAO extends BaseDAO<AccountVO>{

	/*--新增账户时批量插入账户角色.--*/
	int doBatchInsertAccountRoles(List<AccountRoleVO> accountRoles);

	/*--.新增账户时插入初始角色信息--*/
	int doInsertAccoutRoleInitial(AccountRoleVO accountRoleVO);

	/*--修改用户时删除用户中间表数据.--*/
	int doDeleteAccountRoles(String userid);

	/*--通过统一社会信用代码查询单位ID by li.xue 2019/11/21.--*/
	String doFindDwidByUnscid(String unscid);
}