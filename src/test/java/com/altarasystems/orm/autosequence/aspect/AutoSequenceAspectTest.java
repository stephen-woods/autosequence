package com.altarasystems.orm.autosequence.aspect;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.altarasystems.orm.autosequence.AutoSequence;
import com.altarasystems.orm.autosequence.AutoSequenceWithinCode;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/testApplicationContext.xml" })
public class AutoSequenceAspectTest
{
	@AutoSequenceWithinCode
	@Test
	public void testWithinCode()
	{
		SampleEntity entity = new SampleEntity();
		assertFalse(entity.getId() == 0);
	}


	@Test
	public void testNotWithinCode()
	{
		SampleEntity entity = new SampleEntity();
		assertTrue(entity.getId() == 0);
	}

	@Entity
	@AutoSequence(sequenceName = "SOME_SEQUENCE")
	public class SampleEntity
	{
		@Id
		public long id = 0;


		protected long getId()
		{
			return id;
		}


		protected void setId(long id)
		{
			this.id = id;
		}
	}
}
