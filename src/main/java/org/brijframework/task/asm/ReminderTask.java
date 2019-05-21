package org.brijframework.task.asm;

import java.lang.reflect.Method;
import java.util.TimerTask;
import java.util.concurrent.Future;

import org.brijframework.task.meta.TaskMeta;
import org.brijframework.util.reflect.InstanceUtil;
import org.brijframework.util.reflect.LogicUnit;

public class ReminderTask extends TimerTask{
	public volatile Future<?> taskFuture = null;
	public volatile TaskMeta testSetup;
	
	public ReminderTask(TaskMeta testSetup) {
		this.testSetup=testSetup;
	}
	
	@Override
    public void run() {
		excuteTesk(testSetup);
    }
    
    public  void preTask(TaskMeta testSetup){
    	testSetup.getWaitTime();
    	System.err.println("Exceting Task ID    		      := "+testSetup.getId());
    }
    
    public  void excuteTesk(TaskMeta testSetup) {
    	preTask(testSetup);
    	long t1 = System.currentTimeMillis();
    	Object object=InstanceUtil.getInstance(testSetup.getOwner().getTarget());
		Method method= (Method) testSetup.getTarget();
		Object objectParam[]=testSetup.getParametors();
		LogicUnit.callMethod(object,method.getName(), objectParam);  
        testSetup.detail.totalExecutionTime=(((System.currentTimeMillis() - t1) / 100) / 10.0);
        postTask(testSetup);
	}

    public  void postTask(TaskMeta testSetup){
    	testSetup.detail.lastExecutionTime=testSetup.detail.totalExecutionTime;
    }
}
