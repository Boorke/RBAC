package com.rbac.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rbac.bean.AJAXResult;
import com.rbac.bean.Permission;
import com.rbac.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * 权限控制器
 * @author Little Prince 李文攀
 * 2018年6月18日下午12:13:25
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;

	@ResponseBody
	@RequestMapping("/delete")
	public Object delete( Permission permission ) {
		AJAXResult result = new AJAXResult();
		
		try {
			permissionService.deletePermission(permission);
			result.setSuccess(true);
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public Object update( Permission permission ) {
		AJAXResult result = new AJAXResult();
		
		try {
			permissionService.updatePermission(permission);
			result.setSuccess(true);
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/insert")
	public Object insert( Permission permission ) {
		AJAXResult result = new AJAXResult();
		
		try {
			permissionService.insertPermission(permission);
			result.setSuccess(true);
		} catch ( Exception e ) {
			e.printStackTrace();
			result.setSuccess(false);
		}
		
		return result;
	}

	@RequestMapping("/add")
	public String add() {
		return "permission/add";
	}
	
	@RequestMapping("/edit")
	public String edit( Integer id, Model model ) {
		
		Permission permission = permissionService.queryById(id);
		model.addAttribute("permission", permission);
		
		return "permission/edit";
	}
	
	@RequestMapping("/index")
	public String index() {
		return "permission/index";
	}
	
	@ResponseBody
	@RequestMapping("/loadAssignData")
	public Object loadAssignData( Integer roleid ) {
		List<Permission> permissions = new ArrayList<Permission>();
		List<Permission> ps = permissionService.queryAll();
		
		// 获取当前角色已经分配的许可信息
		List<Integer> permissionids = permissionService.queryPermissionidsByRoleid(roleid);
		
		Map<Integer, Permission> permissionMap = new HashMap<Integer, Permission>();
		for (Permission p : ps) {
			if ( permissionids.contains(p.getId()) ) {
				p.setChecked(true);
			} else {
				p.setChecked(false);
			}
			permissionMap.put(p.getId(), p);
		}
		for ( Permission p : ps ) {
			Permission child = p;
			if ( child.getPid() == 0 ) {
				permissions.add(p);
			} else {
				Permission parent = permissionMap.get(child.getPid());
				parent.getChildren().add(child);
			}
		}
		
		return permissions;
	}
	
	/**
	 * 异步加载数据
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/loadData")
	public Object loadData() {
		List<Permission> permissions = new ArrayList<Permission>();
		
		// 查询所有的许可数据
		List<Permission> ps = permissionService.queryAll();
		
		Map<Integer, Permission> permissionMap = new HashMap<Integer, Permission>();
		for (Permission p : ps) {
			System.out.println();
			permissionMap.put(p.getId(), p);
		}
		for ( Permission p : ps ) {
			Permission child = p;
			if ( child.getPid() == 0 ) {
				permissions.add(p);
			} else {
				Permission parent = permissionMap.get(child.getPid());
				parent.getChildren().add(child);
			}
		}
		
		return permissions;
	}
	
/*	*//**
	 * 递归查询许可信息
	 * 1） 方法自己调用自己
	 * 2）方法一定要存在跳出逻辑
	 * 3）方法调用时，参数之间应该有规律
	 * 4） 递归算法，效率比较低
	 * @param parent
	 *//*
	private void queryChildPermissions( Permission parent ) {
		List<Permission> childPermissions = permissionService.queryChildPermissions(parent.getId());
		
		for ( Permission permission : childPermissions ) {
			queryChildPermissions(permission);
		}
		
		parent.setChildren(childPermissions);
	}*/
}
