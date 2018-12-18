package org.brijframework.task.runner;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.brijframework.task.asm.CallableTask;
import org.brijframework.task.asm.RunnableTask;
import org.brijframework.util.reflect.InstanceUtil;

public class ExecutorScheduledTask {

	private static ExecutorScheduledTask taskExecutor;

	private static ScheduledThreadPoolExecutor executor = null;

	public static ExecutorScheduledTask getExecutor(int pool) {
		if (taskExecutor == null) {
			taskExecutor = (ExecutorScheduledTask) InstanceUtil.getInstance(ExecutorScheduledTask.class);
		}
		executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(pool);
		return taskExecutor;
	}

	public static void submitTasks(RunnableTask runnable) {
		if (runnable.taskFuture == null || runnable.taskFuture.isDone() || runnable.taskFuture.isCancelled()) {
			runnable.testSetup.getWaitTime();
			runnable.taskFuture = executor.submit(runnable);
		}
	}

	public static void submitTasks(List<RunnableTask> runnableList) {
		for (RunnableTask runnable : runnableList) {
			if (runnable.taskFuture == null || runnable.taskFuture.isDone() || runnable.taskFuture.isCancelled()) {
				runnable.testSetup.getWaitTime();
				runnable.taskFuture = executor.submit(runnable);
			}
		}
	}

	public static void executeTasks(RunnableTask runnable) {
		runnable.testSetup.getWaitTime();
		executor.execute(runnable);
	}

	public static void executeTasks(List<RunnableTask> runnableList) {
		for (RunnableTask runnable : runnableList) {
			runnable.testSetup.getWaitTime();
			executor.execute(runnable);
		}
	}

	public static void submitFutureTask(RunnableTask runnable) {
		if (runnable.taskFuture == null || runnable.taskFuture.isDone() || runnable.taskFuture.isCancelled()) {
			runnable.testSetup.getWaitTime();
			executor.submit(runnable, runnable.taskFuture);
		}
	}

	public static void submitFutureTasks(List<RunnableTask> runnableList) {
		for (RunnableTask runnable : runnableList) {
			if (runnable.taskFuture == null || runnable.taskFuture.isDone() || runnable.taskFuture.isCancelled()) {
				runnable.testSetup.getWaitTime();
				runnable.taskFuture = executor.submit(runnable, runnable.taskFuture);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void submitCallTasks(CallableTask callable) {
		if (callable.taskFuture == null || callable.taskFuture.isDone() || callable.taskFuture.isCancelled()) {
			callable.testSetup.getWaitTime();
			callable.taskFuture = executor.submit(callable);
		}
	}

	@SuppressWarnings("unchecked")
	public static void submitCallTasks(List<CallableTask> callableList) {
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

	public void scheduleTaskWithFixedDelay(RunnableTask runnable) throws Exception {
		runnable.testSetup.getWaitTime();
		runnable.taskFuture = executor.scheduleWithFixedDelay(runnable, runnable.testSetup.initialDelay, runnable.testSetup.period, runnable.testSetup.periodUnit);
	}

	public void scheduleTasksWithFixedDelay(List<RunnableTask> runnableList) throws Exception {
		for (RunnableTask runnable : runnableList) {
			runnable.testSetup.getWaitTime();
			runnable.taskFuture = executor.scheduleWithFixedDelay(runnable, runnable.testSetup.initialDelay, runnable.testSetup.period, runnable.testSetup.periodUnit);
		}
	}

}
