package org.brijframework.task.jobs;

import java.util.concurrent.Future;

import org.brijframework.task.meta.RequestedTaskMeta;

public class RequestedTaskRunner implements Runnable {
	RequestedTaskMeta task;
	public Future<?> future;
	
	public RequestedTaskRunner() {
	}

	public RequestedTaskRunner(RequestedTaskMeta task) {
		this.task=task;
	}

	@Override
	public void run() {
		
	}

	public RequestedTaskMeta getTask() {
		return task;
	}

}
