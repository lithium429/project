package com.xz.activiti.cmd;

import java.io.InputStream;

import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;

import com.xz.activiti.utils.CustomProcessDiagramGenerator;

public class HistoryProcessInstanceDiagramCmd implements Command<InputStream> {
	
	protected String historyProcessInstanceId;
	
	public HistoryProcessInstanceDiagramCmd(String historyProcessInstanceId) {
        this.historyProcessInstanceId = historyProcessInstanceId;
    }
	@Override
	 public InputStream execute(CommandContext commandContext) {
	        try {
	            CustomProcessDiagramGenerator customProcessDiagramGenerator = new CustomProcessDiagramGenerator();

	            return customProcessDiagramGenerator
	                    .generateDiagram(historyProcessInstanceId);
	        } catch (Exception ex) {
	            throw new RuntimeException(ex);
	        }
	    }
	
}
