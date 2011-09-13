package com.altarasystems.orm.autosequence.impl;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

import com.altarasystems.orm.autosequence.AutoSequenceFactory;

@Component
public class AutoSequenceFactorySimple
		implements AutoSequenceFactory
{
	private AtomicLong value = new AtomicLong(0L);


	public Long nextValue(String sequenceName)
	{
		return value.incrementAndGet();
	}
}
