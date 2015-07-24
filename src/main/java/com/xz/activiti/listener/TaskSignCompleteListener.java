package com.xz.activiti.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.commons.lang3.math.NumberUtils;
import org.nutz.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskSignCompleteListener implements TaskListener {

	Logger log = LoggerFactory.getLogger(TaskSignCompleteListener.class);

	@Override
	public void notify(DelegateTask delegateTask) {
		 String approved = Strings.sNull(delegateTask.getVariable("ok"));
	        String isOk = Strings.sNull(delegateTask.getVariable("isOk"));
	        log.info("isOk::"+isOk);
	        if (approved.equals("true")) {
	            int okNum = NumberUtils.toInt(Strings.sNull(delegateTask.getVariable("okNum")));
	            delegateTask.setVariable("okNum", okNum + 1);
	        }
//	        if(isOk.equals("false")){
//	            List<Task> list=taskService.createTaskQuery().().processInstanceId(delegateTask.getProcessInstanceId())..list();
//	            for (Task task:list){
//	                if(!task.getId().equals(delegateTask.getId())) {
//	                    log.info("id:::"+task.getId());
//	                    taskService.deleteTask(task.getId());
//	                }
//	            }
//	        }

	}

}
