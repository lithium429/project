package com.xz.activiti.ext;

import javax.annotation.Resource;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;
import org.activiti.engine.impl.persistence.entity.GroupIdentityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class CustomGroupEntityManagerFactory implements SessionFactory {
	
	Logger log=LoggerFactory.getLogger(CustomGroupEntityManagerFactory.class);

	private GroupEntityManager groupEntityManager;

	public void setGroupEntityManager(GroupEntityManager groupEntityManager) {
		this.groupEntityManager = groupEntityManager;
	}

	public Class<?> getSessionType() {
		return GroupIdentityManager.class;
	}

	public Session openSession() {
		return groupEntityManager;
	}

}
