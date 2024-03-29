package com.altarasystems.orm.autosequence.aspect;

import org.springframework.beans.factory.annotation.Autowired;

import com.altarasystems.orm.autosequence.AutoSequence;
import com.altarasystems.orm.autosequence.AutoSequenceWithinCode;
import com.altarasystems.orm.autosequence.AutoSequenceFactory;

public aspect AutoSequenceAspect
{
	private AutoSequenceFactory autoSequenceFactory;
	
	private EntityIdSetterService entityIdSetterService;

	after() returning (Object entity): call((@AutoSequence+ *).new(..)) && withincode(@AutoSequenceWithinCode * *(..)) {

		// Get AutoSequence annotation
		AutoSequence autoSequence = entity.getClass().getAnnotation(AutoSequence.class);

		long id = autoSequenceFactory.nextValue(autoSequence.sequenceName());
		entityIdSetterService.setId(entity, id);
	}
	
	@Autowired
	public void setAutoSequenceFactory(AutoSequenceFactory autoSequenceFactory)
	{
		this.autoSequenceFactory = autoSequenceFactory;
	}
	
	@Autowired
	public void setEntityIdSetterService(EntityIdSetterService entityIdSetterService)
	{
		this.entityIdSetterService = entityIdSetterService;
	}
}