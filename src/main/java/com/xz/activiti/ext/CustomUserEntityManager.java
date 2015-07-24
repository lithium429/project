package com.xz.activiti.ext;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.xz.project.core.domain.entity.Role;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.xz.project.core.domain.entity.UserRole;
import com.xz.project.core.service.user.UserService;
@Component
public class CustomUserEntityManager extends UserEntityManager {

	
	private UserService userService;
	
	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	Logger logger = LoggerFactory.getLogger(CustomUserEntityManager.class);



	@Override
	public User findUserById(String userId) {
		com.xz.project.core.domain.entity.User user = userService
				.findById(Integer.valueOf(userId));
		UserEntity userEntity = new UserEntity();
		userEntity.setId(userId);
		userEntity.setFirstName(user.getReal_name());
		userEntity.setRevision(1);
		userEntity.setEmail(user.getEmail());
		return userEntity;
	}

	@Override
	public List<Group> findGroupsByUser(String userId) {
		List<Group> groupList = new ArrayList<Group>();
		System.out.println(userService);
		List<Role> list = userService.findRoleListByUser(userId);
		for (Role role : list) {
			GroupEntity group = new GroupEntity();
			group.setId(String.valueOf(role.getId()));
			group.setName(role.getName());
			group.setType("assignment");
			group.setRevision(1);
			groupList.add(group);
		}
		return groupList;
	}


}
