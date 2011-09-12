package com.altarasystems.orm.autosequence;

public interface AutoSequenceFactory
{
	Long nextValue(String sequenceName);
}
