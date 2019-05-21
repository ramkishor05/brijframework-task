package org.brijframework.task.runner;

import java.util.List;
import java.util.Timer;

import org.brijframework.task.asm.ReminderTask;
import org.brijframework.util.reflect.InstanceUtil;

public class ExecutorTimerTask {
	private static Timer executor = null;
	
	private static ExecutorTimerTask taskExecutor = null;

	public static ExecutorTimerTask getExecutor(int pool) {
		if (executor == null) {
			taskExecutor = (ExecutorTimerTask) InstanceUtil.getInstance(ExecutorTimerTask.class);
		}
		executor = new Timer();
		return taskExecutor;
	}
	public  void scheduleTasks(ReminderTask runnable) {
		executor.schedule(runnable, runnable.testSetup.initialDelay);
	}

	public  void scheduleTasks(List<ReminderTask> runnableList) {
		for (ReminderTask runnable : runnableList) {
			executor.schedule(runnable, runnable.testSetup.initialDelay);
		}
	}

	public void schedulePeriodTask(ReminderTask runnable) {
		executor.schedule(runnable, runnable.testSetup.initialDelay, runnable.testSetup.period);
	}

	public  void schedulePeriodTasks(List<ReminderTask> runnableList) {
		for (ReminderTask runnable : runnableList) {
			executor.schedule(runnable, runnable.testSetup.initialDelay, runnable.testSetup.period);
		}
	}
	
	public  void scheduleDateTask(ReminderTask runnable) {
		executor.schedule(runnable, runnable.testSetup.detail.startDate, runnable.testSetup.period);
	}

	public  void scheduleDateTasks(List<ReminderTask> runnableList) {
		for (ReminderTask runnable : runnableList) {
			executor.schedule(runnable, runnable.testSetup.detail.startDate, runnable.testSetup.period);
		}
	}

	public  void scheduleAtFixedDelayTask(ReminderTask runnable) {
		executor.scheduleAtFixedRate(runnable, runnable.testSetup.initialDelay, runnable.testSetup.period);
	}

	public  void scheduleAtFixedDelayTasks(List<ReminderTask> runnableList) {
		for (ReminderTask runnable : runnableList) {
			executor.scheduleAtFixedRate(runnable, runnable.testSetup.initialDelay, runnable.testSetup.period);
		}
	}

	public  void scheduleAtFixedDateTask(ReminderTask runnable) {
		executor.scheduleAtFixedRate(runnable, runnable.testSetup.detail.startDate, runnable.testSetup.period);
	}

	public  void scheduleAtFixedDateTasks(List<ReminderTask> runnableList) {
		for (ReminderTask runnable : runnableList) {
			executor.scheduleAtFixedRate(runnable, runnable.testSetup.detail.startDate, runnable.testSetup.period);
		}
	}
}
