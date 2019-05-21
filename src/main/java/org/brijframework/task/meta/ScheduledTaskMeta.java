package org.brijframework.task.meta;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.brijframework.meta.reflect.ClassMeta;
import org.brijframework.util.casting.TimeUtil;
import org.brijframework.util.validator.ValidationUtil;

public class ScheduledTaskMeta extends TaskInfo{

	public ScheduledTaskMeta(ClassMeta owner, Method target) {
		super(owner, target);
	}

	public TimeUnit unit=TimeUnit.MINUTES;// loop unit
	
	public String startOn = "";

	public String repeatOn = "";
	
	public String stopOn = "";

	public String format = "dd/MM/yyyy";
	
	@Override
	public void init() {
		this.setId(this.getOwner().getTarget().getSimpleName()+"_"+this.getTarget().getName());
		if (!ValidationUtil.isEmptyObject(this.startOn)) {
			java.util.Date crntDate=new Date();
			java.util.Date startDate = TimeUtil.getDateTime(this.startOn, this.format);
			System.out.println(startDate);
			long Initial=unit.convert(startDate.getTime(),TimeUnit.MILLISECONDS)-unit.convert(crntDate.getTime(),TimeUnit.MILLISECONDS);
			setInitial(Initial);
		}
	}
	
}
