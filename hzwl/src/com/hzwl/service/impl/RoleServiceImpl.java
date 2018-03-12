package com.hzwl.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzwl.dao.IRoleDao;
import com.hzwl.entity.Function;
import com.hzwl.entity.Role;
import com.hzwl.service.IRoleService;
import com.hzwl.utils.PageBean;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService{
	@Resource
	private IRoleDao roleDao;
	@Autowired
	private IdentityService identityService;
	
	/**
	 * 保存角色
	 */
	public void save(Role role, String ids) {
		
		roleDao.save(role);
		Group group=new GroupEntity(role.getName());
		identityService.saveGroup(group);
		
		String[] functionIds = ids.split(",");
		for (String fid : functionIds) {
			Function function = new Function(fid);//托管,离线对象
			//角色关联权限
			role.getFunctions().add(function);
		}
	}
	public void pageQuery(PageBean pageBean) {
		roleDao.pageQuery(pageBean);
	}
	public List<Role> findAll() {
		return roleDao.findAll();
	}
	@Override
	public void deleteRole(String id) {
		// TODO Auto-generated method stub
		roleDao.delete(roleDao.findById(id));
	}

}
