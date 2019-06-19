package org.brijframework.task.meta;

import org.brijframework.meta.asm.AbstractClassMetaInfo;
import org.brijframework.meta.info.ConstMetaInfo;
import org.brijframework.meta.info.FieldMetaInfo;

public class TaskClassMeta extends AbstractClassMetaInfo{

	@Override
	public FieldMetaInfo getPropertyInfo(String _key) {
		return null;
	}

	@Override
	public ConstMetaInfo getConstructor() {
		return null;
	}

	
}
