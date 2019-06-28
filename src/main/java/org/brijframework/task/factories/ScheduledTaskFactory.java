package org.brijframework.task.factories;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import org.brijframework.container.Container;
import org.brijframework.support.config.Assignable;
import org.brijframework.support.monitor.ScheduledTask;
import org.brijframework.task.jobs.ScheduledTaskRunner;
import org.brijframework.task.meta.ScheduledTaskMeta;
import org.brijframework.task.meta.TaskClassMeta;
import org.brijframework.task.runner.ScheduledTaskExecutorFactory;
import org.brijframework.task.util.TaskResourcesUtil;
import org.brijframework.util.reflect.MethodUtil;
import org.brijframework.util.reflect.ReflectionUtils;
import org.brijframework.util.support.Access;

public class ScheduledTaskFactory implements TaskFactory{

	private Container container;
	private ConcurrentHashMap<String, ScheduledTaskMeta> cache=new ConcurrentHashMap<>();
	private ScheduledTaskExecutorFactory timerTask=ScheduledTaskExecutorFactory.getFactory(100);
	
	public ScheduledTaskFactory() {
	}
	
	static ScheduledTaskFactory  factory;
	
	@Assignable
	public static ScheduledTaskFactory getFactory() {
		if(factory==null) {
			factory=new ScheduledTaskFactory();
		}
		return factory;
	}
	
	@Override
	public ScheduledTaskFactory loadFactory() {
		try {
			ReflectionUtils.getClassListFromExternal().forEach(target->{
				register(target);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ReflectionUtils.getClassListFromInternal().forEach(target->{
				register(target);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public void register(Class<?> cls) {
		MethodUtil.getAllMethod(cls, Access.PRIVATE).forEach(target -> {
			if (target.isAnnotationPresent(ScheduledTask.class)) {
				this.register(cls, target);
			}
		});
	}
	
	public void register(Class<?> cls, Method target) {
		TaskClassMeta owner=null;
		ScheduledTaskMeta taskMeta=TaskResourcesUtil.getScheduledTask(owner, target);
		System.err.println("Scheduled Task : "+taskMeta.getId());
		scheduled(taskMeta);
	}
	
	public void scheduled(ScheduledTaskMeta taskMeta) {
		System.out.println("Scheduled Task : "+taskMeta.getId());
		try {
			timerTask.scheduleTaskWithFixedDelay(new ScheduledTaskRunner(taskMeta));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Container getContainer() {
		return container;
	}

	@Override
	public void setContainer(Container container) {
		this.container=container;
	}

	@Override
	public ConcurrentHashMap<String, ScheduledTaskMeta> getCache() {
		return cache;
	}

	@Override
	public ScheduledTaskFactory clear() {
		getCache().clear();
		return this;
	}

}