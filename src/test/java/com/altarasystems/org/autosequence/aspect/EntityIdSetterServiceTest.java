package com.altarasystems.org.autosequence.aspect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Method;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.junit.Before;
import org.junit.Test;

import com.altarasystems.orm.autosequence.aspect.EntityIdSetterService;

public class EntityIdSetterServiceTest
{
	public EntityIdSetterService entityIdSetterService;


	@Before
	public void setUp()
	{
		entityIdSetterService = new EntityIdSetterService();
	}


	@Test
	public void testSetId_EntityA1()
	{
		long id = 234L;

		EntityA1 entity = new EntityA1();
		assertEquals(-1L, entity.getId());

		entityIdSetterService.setId(entity, id);

		assertEquals(id, entity.getId());
	}


	@Test
	public void testSetId_EntityA2()
	{
		long id = 1532L;

		EntityA2 entity = new EntityA2();
		assertEquals(-1L, entity.getIdentity());

		entityIdSetterService.setId(entity, id);

		assertEquals(id, entity.getIdentity());
	}


	@Test
	public void testSetId_EntityA1_Cached1()
	{
		long id = 234L;

		EntityA1 entity = new EntityA1();
		assertEquals(-1L, entity.getId());

		Method idSetterMethod = entityIdSetterService.getIdSetterMethods().get(EntityA2.class);
		assertNull(idSetterMethod);
		entityIdSetterService.setId(entity, id);

		idSetterMethod = entityIdSetterService.getIdSetterMethods().get(EntityA1.class);
		assertNotNull(idSetterMethod);
		assertEquals("setId", idSetterMethod.getName());
	}


	@Test
	public void testSetId_EntityA2_Cached()
	{
		long id = 234L;

		EntityA2 entity = new EntityA2();

		Method idSetterMethod = entityIdSetterService.getIdSetterMethods().get(EntityA2.class);
		assertNull(idSetterMethod);
		entityIdSetterService.setId(entity, id);

		idSetterMethod = entityIdSetterService.getIdSetterMethods().get(EntityA2.class);
		assertNotNull(idSetterMethod);
		assertEquals("setIdentity", idSetterMethod.getName());
	}

	@Entity
	public class EntityA1
	{
		public long id = -1;


		@Id
		public long getId()
		{
			return id;
		}


		public void setId(long id)
		{
			this.id = id;
		}
	}

	@Entity
	public class EntityA2
	{
		public long identity = -1;


		@Id
		public long getIdentity()
		{
			return identity;
		}


		public void setIdentity(long identity)
		{
			this.identity = identity;
		}
	}
}
