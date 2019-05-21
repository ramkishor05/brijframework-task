package org.brijframework.task.meta;
import java.lang.reflect.Method;

import org.brijframework.meta.asm.AbstractKeyInfo;
import org.brijframework.meta.reflect.ClassMeta;
public class TaskKeyInfo extends AbstractKeyInfo<Method>{

	private ClassMeta owner;
	
	@Override
	public ClassMeta getOwner() {
		return owner;
	}
}
