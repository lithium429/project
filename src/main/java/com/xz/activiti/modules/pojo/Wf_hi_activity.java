package com.xz.activiti.modules.pojo;

public class Wf_hi_activity {
    private int id;
    private String processInstanceId;
    private String processDefinitionId;
    private String executionId;
    private String nowActivityId;
    private String nextActivityId;
    private String jsonInfo;
    private boolean isAfter;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getNowActivityId() {
        return nowActivityId;
    }

    public void setNowActivityId(String nowActivityId) {
        this.nowActivityId = nowActivityId;
    }

    public String getNextActivityId() {
        return nextActivityId;
    }

    public void setNextActivityId(String nextActivityId) {
        this.nextActivityId = nextActivityId;
    }

    public String getJsonInfo() {
        return jsonInfo;
    }

    public void setJsonInfo(String jsonInfo) {
        this.jsonInfo = jsonInfo;
    }

    public boolean isAfter() {
        return isAfter;
    }

    public boolean getIsAfter() {
        return isAfter;
    }

    public void setIsAfter(boolean isAfter) {
        this.isAfter = isAfter;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

}
