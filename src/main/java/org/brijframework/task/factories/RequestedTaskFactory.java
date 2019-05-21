package org.brijframework.task.factories;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

import org.brijframework.container.Container;
import org.brijframework.support.model.Assignable;
import org.brijframework.support.monitor.RequestedTask;
import org.brijframework.task.jobs.RequestedTaskRunner;
import org.brijframework.task.meta.RequestedTaskMeta;
import org.brijframework.task.meta.TaskClassMeta;
import org.brijframework.task.runner.ScheduledTaskExecutorFactory;
import org.brijframework.task.util.TaskResourcesUtil;
import org.brijframework.util.reflect.MethodUtil;
import org.brijframework.util.reflect.ReflectionUtils;
import org.brijframework.util.support.Access;

public class RequestedTaskFactory implements TaskFactory{

	Container container;
	
	ConcurrentHashMap<String, RequestedTaskMeta> cache=new ConcurrentHashMap<>();
	ScheduledTaskExecutorFactory timerTask=ScheduledTaskExecutorFactory.getFactory(100);
	
	public RequestedTaskFactory() {
	}
	
	static RequestedTaskFactory  factory;
	
	@Assignable
	public static RequestedTaskFactory getFactory() {
		if(factory==null) {
			factory=new RequestedTaskFactory();
			factory.loadFactory();
		}
		return factory;
	}
	
	@Override
	public RequestedTaskFactory loadFactory() {
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
			if (target.isAnnotationPresent(RequestedTask.class)) {
				this.register(cls, target);
			}
		});
	}
	
	private void register(Class<?> cls, Method target) {
		TaskClassMeta owner=null;
		RequestedTaskMeta taskMeta=TaskResourcesUtil.getRequestedTask(owner, target);
		System.out.println("Requested Task : "+taskMeta.getId());
		getCache().put(taskMeta.getId(), taskMeta);
		try {
			timerTask.scheduleTaskWithFixedDelay(new RequestedTaskRunner(taskMeta));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Called");
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
	public ConcurrentHashMap<String, RequestedTaskMeta> getCache() {
		return cache;
	}

	@Override
	public RequestedTaskFactory clear() {
		getCache().clear();
		return this;
	}

}
