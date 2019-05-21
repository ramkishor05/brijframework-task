package org.brijframework.task.container;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.brijframework.asm.container.DefaultContainer;
import org.brijframework.group.Group;
import org.brijframework.support.model.Assignable;
import org.brijframework.support.model.DepandOn;
import org.brijframework.task.factories.TaskFactory;
import org.brijframework.task.group.TaskGroup;
import org.brijframework.util.reflect.InstanceUtil;
import org.brijframework.util.reflect.MethodUtil;
import org.brijframework.util.reflect.ReflectionUtils;

public class TaskContainer implements DefaultContainer{
	
	
	private static TaskContainer container;
	
	private ConcurrentHashMap<Object, Group> cache = new ConcurrentHashMap<Object, Group>();

	public static TaskContainer getContainer() {
		if (container == null) {
			container = (TaskContainer) InstanceUtil.getInstance(TaskContainer.class);
			container.loadContainer();
		}
		return container;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TaskContainer loadContainer() {
		List<Class<? extends TaskFactory>> classes=new ArrayList<>();
		try {
			ReflectionUtils.getClassListFromExternal().forEach(cls->{
				if(TaskFactory.class.isAssignableFrom(cls) && !cls.isInterface() && cls.getModifiers() != Modifier.ABSTRACT) {
					classes.add((Class<? extends TaskFactory>) cls);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ReflectionUtils.getClassListFromInternal().forEach(cls->{
				if(TaskFactory.class.isAssignableFrom(cls) && !cls.isInterface() && cls.getModifiers() != Modifier.ABSTRACT) {
					classes.add((Class<? extends TaskFactory>) cls);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		classes.forEach((taskFactory)->{
			if(taskFactory.isAnnotationPresent(DepandOn.class)) {
			   DepandOn depandOn=taskFactory.getAnnotation(DepandOn.class);
			   loading(depandOn.depand());
			}
			loading(taskFactory);
		});
		return container;
	}

	private void loading(Class<?> cls) {
		boolean called=false;
		for(Method method:MethodUtil.getAllMethod(cls)) {
			if(method.isAnnotationPresent(Assignable.class)) {
				try {
					method.invoke(null);
					called=true;
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		if(!called) {
			try {
				TaskFactory container=(TaskFactory) cls.newInstance();
				container.loadFactory();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public ConcurrentHashMap<Object, Group> getCache() {
		return this.cache;
	}

	@Override
	public Group load(Object groupKey) {
		Group group=getCache().get(groupKey);
		if(group==null) {
			group=new TaskGroup(groupKey);
			getCache().put(groupKey, group);
		}
		return group;
	}


}
