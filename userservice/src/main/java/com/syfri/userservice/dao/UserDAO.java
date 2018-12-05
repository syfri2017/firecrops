package com.syfri.userservice.dao;

import com.syfri.baseapi.dao.BaseDAO;
import com.syfri.userservice.model.AccountRoleVO;
import com.syfri.userservice.model.UserVO;

import java.util.List;

public interface UserDAO extends BaseDAO<UserVO> {

    /*获取用户，如果userVO为null，则获取所有用户.*/
    List<UserVO> doFindUserRoles(UserVO userVO);

    /*获取未绑定组织机构的用户信息.*/
    List<UserVO> findUsersNoOrg(UserVO userVO);

    /*根据组织机构id解绑用户.*/
    int doUpdateByJgid(UserVO userVO);
}