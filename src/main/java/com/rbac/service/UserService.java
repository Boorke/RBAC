package com.rbac.service;

import com.rbac.bean.User;

import java.util.List;
import java.util.Map;



/**
 * 用户业务接口
 * @author Little Prince 李文攀
 * 2018年6月18日下午3:12:47
 */
public interface UserService {

	List<User> queryAll();

	User query4Login(User user);

	List<User> pageQueryData(Map<String, Object> map);

	int pageQueryCount(Map<String, Object> map);

	void insertUser(User user);

	User queryById(Integer id);

	void updateUser(User user);

	void deleteUserById(Integer id);

	void deleteUsers(Map<String, Object> map);

	void deleteUserRoles(Map<String, Object> map);

	void insertUserRoles(Map<String, Object> map);

	List<Integer> queryRoleidsByUserid(Integer id);

}
