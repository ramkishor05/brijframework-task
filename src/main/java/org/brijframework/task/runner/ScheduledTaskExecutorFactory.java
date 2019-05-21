package org.brijframework.task.runner;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.brijframework.task.asm.CallableTask;
import org.brijframework.task.asm.RunnableTask;
import org.brijframework.task.jobs.RequestedTaskRunner;
import org.brijframework.task.jobs.ScheduledTaskRunner;
import org.brijframework.util.reflect.InstanceUtil;

public class ScheduledTaskExecutorFactory {

	private static ScheduledTaskExecutorFactory factory;

	private static ScheduledThreadPoolExecutor executor = null;

	public static ScheduledTaskExecutorFactory getFactory(int pool) {
		if (factory == null) {
			factory = (ScheduledTaskExecutorFactory) InstanceUtil.getInstance(ScheduledTaskExecutorFactory.class);
			executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(pool);
		}
		return factory;
	}
	
	public static ScheduledTaskExecutorFactory getFactory() {
		return getFactory(100);
	}

	public void submitTasks(RunnableTask runnable) {
		if (runnable.taskFuture == null || runnable.taskFuture.isDone() || runnable.taskFuture.isCancelled()) {
			runnable.testSetup.getWaitTime();
			runnable.taskFuture = executor.submit(runnable);
		}
	}

	public void submitTasks(List<RunnableTask> runnableList) {
		for (RunnableTask runnable : runnableList) {
			submitTasks(runnable);
		}
	}

	public  void executeTasks(RunnableTask runnable) {
		runnable.testSetup.getWaitTime();
		executor.execute(runnable);
	}

	public  void executeTasks(List<RunnableTask> runnableList) {
		for (RunnableTask runnable : runnableList) {
			executeTasks(runnable);
		}
	}

	public void submitFutureTask(RunnableTask runnable) {
		if (runnable.taskFuture == null || runnable.taskFuture.isDone() || runnable.taskFuture.isCancelled()) {
			runnable.testSetup.getWaitTime();
			runnable.taskFuture=executor.submit(runnable, runnable.taskFuture);
		}
	}

	public  void submitFutureTasks(List<RunnableTask> runnableList) {
		for (RunnableTask runnable : runnableList) {
			if (runnable.taskFuture == null || runnable.taskFuture.isDone() || runnable.taskFuture.isCancelled()) {
				runnable.testSetup.getWaitTime();
				runnable.taskFuture = executor.submit(runnable, runnable.taskFuture);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public  void submitCallTasks(CallableTask callable) {
		if (callable.taskFuture == null || callable.taskFuture.isDone() || callable.taskFuture.isCancelled()) {
			callable.testSetup.getWaitTime();
			callable.taskFuture = executor.submit(callable);
		}
	}

	@SuppressWarnings("unchecked")
	public  void submitCallTasks(List<CallableTask> callableList) {
		for (CallableTask callable : callableList) {
			if (callable.taskFuture == null || callable.taskFuture.isDone() || callable.taskFuture.isCancelled()) {
				callable.testSetup.getWaitTime();
				callable.taskFuture = executor.submit(callable);
			}
		}
	}

	public void scheduleTask(RunnableTask runnable) throws Exception {
		runnable.testSetup.getWaitTime();
		runnable.taskFuture = executor.schedule(runnable, runnable.testSetup.period, runnable.testSetup.periodUnit);
	}

	public void scheduleTasks(List<RunnableTask> runnableList) throws Exception {
		for (RunnableTask runnable : runnableList) {
			runnable.testSetup.getWaitTime();
			runnable.taskFuture = executor.schedule(runnable, runnable.testSetup.period, runnable.testSetup.periodUnit);
		}
	}

	public void scheduleTaskAtFixedRate(RunnableTask runnable) throws Exception {
		runnable.testSetup.getWaitTime();
		runnable.taskFuture = executor.scheduleAtFixedRate(runnable, runnable.testSetup.initialDelay, runnable.testSetup.period, runnable.testSetup.periodUnit);
	}

	public void scheduleTasksAtFixedRate(List<RunnableTask> runnableList) throws Exception {
		for (RunnableTask runnable : runnableList) {
			runnable.testSetup.getWaitTime();
			runnable.taskFuture = executor.scheduleAtFixedRate(runnable, runnable.testSetup.initialDelay, runnable.testSetup.period, runnable.testSetup.periodUnit);
		}
	}

	public void scheduleTaskWithFixedDelay(RequestedTaskRunner runnable) throws Exception {
		runnable.future = executor.scheduleWithFixedDelay(runnable, runnable.getTask().initial(), runnable.getTask().interval(), runnable.getTask().unit());
	}
	
	public void scheduleTaskWithFixedDelay(ScheduledTaskRunner runnable) throws Exception {
		runnable.future = executor.scheduleWithFixedDelay(runnable, runnable.getTask().initial(), runnable.getTask().interval(), runnable.getTask().unit());
	}


	public void scheduleTasksWithFixedDelay(List<RunnableTask> runnableList) throws Exception {
		for (RunnableTask runnable : runnableList) {
			runnable.testSetup.getWaitTime();
			runnable.taskFuture = executor.scheduleWithFixedDelay(runnable, runnable.testSetup.initialDelay, runnable.testSetup.period, runnable.testSetup.periodUnit);
		}
	}

}
