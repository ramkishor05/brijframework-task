package com.brijframework.task.beans;

import java.util.Map;

import org.brijframework.task.meta.TaskMeta;
import org.brijframework.util.reflect.InstanceUtil;
import org.brijframework.util.resouces.JSONUtil;

public abstract class TaskBean {

	public String details;
	
	public TaskMeta getTaskMeta() {
		Map<String, Object> json=JSONUtil.getMapFromJSONString(details);
		return (TaskMeta) InstanceUtil.getInstance(TaskMeta.class,json);
	}
	
	@SuppressWarnings("unchecked")
	public void setTaskSetup(TaskMeta taskSetup) {
		//Map<String, Object> json= (Map<String, Object>) GraphModelBulider.getBulider(taskSetup).objectGraph();
		this.details = JSONUtil.getJSONObjectFromObject("").toString();
	}
	
	public TaskMeta setTaskSetup(Map<String,Object> map) {
		this.details = JSONUtil.getJSONObjectFromObject(map).toString();
		Map<String, Object> json=JSONUtil.getMapFromJSONString(details);
		return (TaskMeta) InstanceUtil.getInstance(TaskMeta.class,json);
	}
	public TaskMeta updateTaskSetup(Map<String,Object> map) {
		this.details = JSONUtil.getJSONObjectFromObject(map).toString();
		Map<String, Object> json=JSONUtil.getMapFromJSONString(details);
		return (TaskMeta) InstanceUtil.getInstance(TaskMeta.class,json);
	}
}
