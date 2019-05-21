package org.brijframework.task.group;

import java.util.concurrent.ConcurrentHashMap;

import org.brijframework.asm.group.DefaultGroup;
import org.brijframework.task.meta.TaskMeta;
import org.brijframework.util.asserts.Assertion;

public class TaskGroup implements DefaultGroup{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object groupKey;
	
	private ConcurrentHashMap<String,TaskMeta> cache=new ConcurrentHashMap<>();
	
	public TaskGroup(Object groupKey) {
		this.groupKey=groupKey;
		Assertion.notNull(this.groupKey, "Group key is required.");
	}

	@Override
	public Object getGroupKey() {
		return groupKey;
	}

	@Override
	public ConcurrentHashMap<String,TaskMeta> getCache() {
		return cache;
	}

	@Override
	public <T> T find(String parentID, Class<?> type) {
		return null;
	}
}
