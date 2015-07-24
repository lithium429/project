package com.xz.activiti.listener;


import com.xz.activiti.cmd.JoinTaskCmd;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTaskListener implements TaskListener {

    Logger logger= LoggerFactory.getLogger(TestTaskListener.class);

    @Override
    public void notify(DelegateTask delegateTask) {
        String eventName = delegateTask.getEventName();
        logger.debug("{}", this);
        logger.debug("{} : {}", eventName, delegateTask);

        if ("create".equals(eventName)) {
            try {
                this.onCreate(delegateTask);
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    }

    public void onCreate(DelegateTask delegateTask){
        System.out.println(this.getClass().getName()+" : exec");
    }
}
