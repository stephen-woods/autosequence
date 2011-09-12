package com.altarasystems.orm.autosequence.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

@RunWith(MockitoJUnitRunner.class)
public class AutoSequenceFactoryOracleTest
{
	public AutoSequenceFactoryOracle autoSequenceFactoryOracle;

	@Mock
	public JdbcTemplate jdbcTemplate;


	@Before
	public void setUp()
	{
		autoSequenceFactoryOracle = new AutoSequenceFactoryOracle();
		autoSequenceFactoryOracle.setJdbcTemplate(jdbcTemplate);
	}


	@Test
	public void testNextValueResult1()
	{
		long expectedValue = 10L;

		when(jdbcTemplate.queryForLong(anyString())).thenReturn(expectedValue);
		long result = autoSequenceFactoryOracle.nextValue("SOME_SEQUENCE");
		assertEquals(expectedValue, result);
	}


	@Test
	public void testNextValueResult2()
	{
		long expectedValue = 1412L;

		when(jdbcTemplate.queryForLong(anyString())).thenReturn(expectedValue);
		long result = autoSequenceFactoryOracle.nextValue("SOME_SEQUENCE");
		assertEquals(expectedValue, result);
	}


	@Test
	public void testNextValueSuppliedSequence1()
	{
		autoSequenceFactoryOracle.nextValue("SOME_SEQUENCE");

		ArgumentCaptor<String> sqlCaptor = ArgumentCaptor.forClass(String.class);
		verify(jdbcTemplate).queryForLong(sqlCaptor.capture());
		assertEquals("SELECT SOME_SEQUENCE.NEXTVAL FROM DUAL", sqlCaptor.getValue());
	}


	@Test
	public void testNextValueSuppliedSequence2()
	{
		autoSequenceFactoryOracle.nextValue("SOME_OTHER_SEQUENCE");

		ArgumentCaptor<String> sqlCaptor = ArgumentCaptor.forClass(String.class);
		verify(jdbcTemplate).queryForLong(sqlCaptor.capture());
		assertEquals("SELECT SOME_OTHER_SEQUENCE.NEXTVAL FROM DUAL", sqlCaptor.getValue());
	}
}
