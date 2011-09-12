package com.altarasystems.orm.autosequence.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.altarasystems.orm.autosequence.AutoSequenceFactory;

@Component
public class AutoSequenceFactoryOracle
		implements AutoSequenceFactory
{
	@Autowired
	private JdbcTemplate jdbcTemplate;


	public Long nextValue(String sequenceName)
	{
		String sql = "SELECT " + sequenceName + ".NEXTVAL FROM DUAL";
		return jdbcTemplate.queryForLong(sql);
	}


	public void setJdbcTemplate(JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
	}
}
