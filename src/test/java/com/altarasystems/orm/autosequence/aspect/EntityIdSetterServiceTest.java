package com.altarasystems.orm.autosequence.aspect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
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

		Method idSetterMethod = entityIdSetterService.getIdSetterMethods().get(EntityA1.class);
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


	@Test
	public void testSetId_EntityB1()
	{
		long id = 234L;

		EntityB1 entity = new EntityB1();
		assertEquals(-1L, entity.getId());

		entityIdSetterService.setId(entity, id);

		assertEquals(id, entity.getId());
	}


	@Test
	public void testSetId_EntityB2()
	{
		long id = 4124L;

		EntityB2 entity = new EntityB2();
		assertEquals(-1L, entity.getIdentity());

		entityIdSetterService.setId(entity, id);

		assertEquals(id, entity.getIdentity());
	}


	@Test
	public void testSetId_EntityB1_Cached1()
	{
		long id = 234L;

		EntityB1 entity = new EntityB1();
		assertEquals(-1L, entity.getId());

		Field idField = entityIdSetterService.getIdFields().get(EntityB1.class);
		assertNull(idField);
		entityIdSetterService.setId(entity, id);

		idField = entityIdSetterService.getIdFields().get(EntityB1.class);
		assertNotNull(idField);
		assertEquals("id", idField.getName());
	}


	@Test
	public void testSetId_EntityB2_Cached1()
	{
		long id = 234L;

		EntityB2 entity = new EntityB2();
		assertEquals(-1L, entity.getIdentity());

		Field idField = entityIdSetterService.getIdFields().get(EntityB2.class);
		assertNull(idField);
		entityIdSetterService.setId(entity, id);

		idField = entityIdSetterService.getIdFields().get(EntityB2.class);
		assertNotNull(idField);
		assertEquals("identity", idField.getName());
	}


	@Test
	public void testSetId_EntityC1()
	{
		long id = 234L;

		EntityC1 entity = new EntityC1();
		assertEquals(-1L, entity.getId());

		entityIdSetterService.setId(entity, id);

		assertEquals(id, entity.getId());
	}


	@Test
	public void testSetId_EntityC2()
	{
		long id = 1232L;

		EntityC2 entity = new EntityC2();
		assertEquals(-1L, entity.getIdentity());

		entityIdSetterService.setId(entity, id);

		assertEquals(id, entity.getIdentity());
	}


	@Test
	public void testSetId_EntityC1_Cached1()
	{
		long id = 234L;

		EntityC1 entity = new EntityC1();
		assertEquals(-1L, entity.getId());

		Method idSetterMethod = entityIdSetterService.getIdSetterMethods().get(EntityC1.class);
		assertNull(idSetterMethod);
		entityIdSetterService.setId(entity, id);

		idSetterMethod = entityIdSetterService.getIdSetterMethods().get(EntityC1.class);
		assertNotNull(idSetterMethod);
		assertEquals("setId", idSetterMethod.getName());
	}


	@Test
	public void testSetId_EntityC2_Cached1()
	{
		long id = 234L;

		EntityC2 entity = new EntityC2();
		assertEquals(-1L, entity.getIdentity());

		Method idSetterMethod = entityIdSetterService.getIdSetterMethods().get(EntityC2.class);
		assertNull(idSetterMethod);
		entityIdSetterService.setId(entity, id);

		idSetterMethod = entityIdSetterService.getIdSetterMethods().get(EntityC2.class);
		assertNotNull(idSetterMethod);
		assertEquals("setIdentity", idSetterMethod.getName());
	}


	@Test
	public void testSetId_EntityD1()
	{
		long id = 234L;

		EntityD1 entity = new EntityD1();
		assertEquals(-1L, entity.getId());

		entityIdSetterService.setId(entity, id);

		assertEquals(id, entity.getId());
	}


	@Test
	public void testSetId_EntityD2()
	{
		long id = 1532L;

		EntityD2 entity = new EntityD2();
		assertEquals(-1L, entity.getIdentity());

		entityIdSetterService.setId(entity, id);

		assertEquals(id, entity.getIdentity());
	}


	@Test
	public void testSetId_EntityE1()
	{
		long id = 234L;

		EntityE1 entity = new EntityE1();
		assertEquals(-1L, entity.getId());

		entityIdSetterService.setId(entity, id);

		assertEquals(id, entity.getId());
	}


	@Test
	public void testSetId_EntityE2()
	{
		long id = 1532L;

		EntityE2 entity = new EntityE2();
		assertEquals(-1L, entity.getIdentity());

		entityIdSetterService.setId(entity, id);

		assertEquals(id, entity.getIdentity());
	}


	@Test
	public void testSetId_NoIdEntity()
	{
		NoIdEntityA entity = new NoIdEntityA();
		try
		{
			entityIdSetterService.setId(entity, 2342L);
			fail("No @Id annotation is defined. Setting an Id should throw an exception");
		}
		catch (RuntimeException e)
		{
			String message = e.getMessage();
			assertEquals("Could not determine how to set ID of entity of type " + entity.getClass().getName(), message);
		}
	}

	@Entity
	public class EntityA1
	{
		private long id = -1;


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
		private long identity = -1;


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

	public class NoIdEntityA
	{
		private long id = -1;


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
	public class EntityB1
	{
		@Id
		private long id = -1;


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
	public class EntityB2
	{
		@Id
		private long identity = -1;


		public long getIdentity()
		{
			return identity;
		}


		public void setIdentity(long identity)
		{
			this.identity = identity;
		}
	}

	@Entity
	public abstract class AbstractEntityC1
	{
		private long id = -1;


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

	public class EntityC1
			extends AbstractEntityC1
	{

	}

	@Entity
	public abstract class AbstractEntityC2
	{
		private long identity = -1;


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

	public class EntityC2
			extends AbstractEntityC2
	{

	}

	@Entity
	public class EntityD1
	{
		private long id = -1;


		@Id
		protected long getId()
		{
			return id;
		}


		protected void setId(long id)
		{
			this.id = id;
		}
	}

	@Entity
	public class EntityD2
	{
		private long identity = -1;


		@Id
		protected long getIdentity()
		{
			return identity;
		}


		protected void setIdentity(long identity)
		{
			this.identity = identity;
		}
	}

	@Entity
	public abstract class AbstractEntityE1
	{
		@Id
		private long id = -1;


		public long getId()
		{
			return id;
		}


		public void setId(long id)
		{
			this.id = id;
		}
	}

	public class EntityE1
			extends AbstractEntityE1
	{

	}

	@Entity
	public abstract class AbstractEntityE2
	{
		@Id
		private long identity = -1;


		public long getIdentity()
		{
			return identity;
		}


		public void setIdentity(long identity)
		{
			this.identity = identity;
		}
	}

	@Entity
	public class EntityE2
			extends AbstractEntityE2
	{
	}

}
