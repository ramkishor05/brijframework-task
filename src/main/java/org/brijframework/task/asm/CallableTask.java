package org.brijframework.task.asm;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.brijframework.task.meta.TaskMeta;
import org.brijframework.util.reflect.InstanceUtil;
import org.brijframework.util.reflect.LogicUnit;

@SuppressWarnings("rawtypes")
public class CallableTask implements Callable {
	public volatile Future<?> taskFuture = null;
	public volatile TaskMeta testSetup;
	@Override
	public Object call() throws Exception {
		synchronized (taskFuture) {
		Object obj=null;
		preTask(testSetup);
		obj=excuteTesk(testSetup);
		postTask(testSetup);
		return obj;
	    }
	}
    private  void preTask(TaskMeta testSetup){
    	testSetup.getWaitTime();
    	System.err.println("Exceting Task ID    		      := "+testSetup.getId());
    }
    
    private  Object excuteTesk(TaskMeta testSetup) {
    	if(!testSetup.isExecute) {
    		 return null;
    	}
     	long t1 = System.currentTimeMillis();
    	Object object=InstanceUtil.getInstance(testSetup.getOwner());
		Method method= (Method) testSetup.getTarget();
		Object objectParam[]=testSetup.getParametors();
		Object result=LogicUnit.callMethod(object,method.getName(), objectParam); 
		testSetup.detail.totalExecutionTime=(((System.currentTimeMillis() - t1) / 100) / 10.0);
		return result;
	}

    private  void postTask(TaskMeta testSetup){
    }

	
}

