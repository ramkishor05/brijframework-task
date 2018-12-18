package org.brijframework.task.runner;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.brijframework.task.asm.CallableTask;
import org.brijframework.task.asm.RunnableTask;
import org.brijframework.util.reflect.InstanceUtil;

public class ExecutorServiceTask {
	static ExecutorServiceTask taskExecutor;
    private static ExecutorService executor = null;
    public static ExecutorServiceTask getExecutor(int pool){
    	if(taskExecutor==null){
    		taskExecutor=(ExecutorServiceTask) InstanceUtil.getInstance(ExecutorServiceTask.class);
    	}
    	 executor = Executors.newFixedThreadPool(pool);
    	 return taskExecutor;
    }
    
   
    public static void submitTasks(RunnableTask runnable) {
        if (runnable.taskFuture == null|| runnable.taskFuture.isDone()|| runnable.taskFuture.isCancelled()){
        	runnable.taskFuture = executor.submit(runnable);
        }
    }
  
    public static void submitTasks(List<RunnableTask> runnableList) {
		for(RunnableTask runnable:runnableList){
	        if (runnable.taskFuture == null|| runnable.taskFuture.isDone()|| runnable.taskFuture.isCancelled()){
	        	runnable.taskFuture = executor.submit(runnable);
	        }
		}
    }
    
    public static void executeTasks(RunnableTask runnable) {
   	     executor.execute(runnable);
	}
	
	public static void executeTasks(List<RunnableTask> runnableList) {
		for(RunnableTask runnable:runnableList){
	       executor.execute(runnable);
		}
	}

    public static void submitFutureTask(RunnableTask runnable) {
    	 if (runnable.taskFuture == null|| runnable.taskFuture.isDone()|| runnable.taskFuture.isCancelled()){
	       executor.submit(runnable, runnable.taskFuture);
    	 }
	}
    
    public static void submitFutureTasks(List<RunnableTask> runnableList) {
		for(RunnableTask runnable:runnableList){
	        if (runnable.taskFuture == null|| runnable.taskFuture.isDone()|| runnable.taskFuture.isCancelled()){
	        	runnable.taskFuture = executor.submit(runnable,runnable.taskFuture);
	        }
		}
    }
    

    @SuppressWarnings("unchecked")
	public static void submitCallTasks(CallableTask callable) {
        if (callable.taskFuture == null|| callable.taskFuture.isDone()|| callable.taskFuture.isCancelled()){
        	callable.taskFuture = executor.submit(callable);
        }
    }
  
    @SuppressWarnings("unchecked")
	public static void submitCallTasks(List<CallableTask> callableList) {
		for(CallableTask callable:callableList){
	        if (callable.taskFuture == null|| callable.taskFuture.isDone()|| callable.taskFuture.isCancelled()){
	        	callable.taskFuture = executor.submit(callable);
	        }
		}
    }
   
   
   
   
}
