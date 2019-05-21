package org.brijframework.task.meta;

import java.util.Map;

import org.brijframework.meta.asm.AbstractClassMeta;
import org.brijframework.meta.reflect.ConstMeta;
import org.brijframework.meta.reflect.FieldMeta;

public class TaskClassMeta extends AbstractClassMeta{

	@Override
	public void papulate(Map<String, Object> map) {
	}

	@Override
	public FieldMeta getPropertyInfo(String _key) {
		return null;
	}

	@Override
	public ConstMeta getConstructor() {
		return null;
	}

	
}
