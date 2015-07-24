package com.xz.activiti.listener;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TaskCategoryTaskListener extends DefaultTaskListener {
	
	Logger log = LoggerFactory.getLogger(TaskCategoryTaskListener.class);

	@Override
	public void onCreate(DelegateTask delegateTask) throws Exception {
		RepositoryService repositoryService = delegateTask.getExecution()
				.getEngineServices().getRepositoryService();
		String category = repositoryService.getProcessDefinition(
				delegateTask.getProcessDefinitionId()).getCategory();
		delegateTask.setCategory(category);
	}
}
