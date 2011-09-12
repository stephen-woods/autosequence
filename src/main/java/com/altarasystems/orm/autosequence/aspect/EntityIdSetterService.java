package com.altarasystems.orm.autosequence.aspect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.persistence.Id;

import org.apache.log4j.Logger;

public class EntityIdSetterService
{
	protected Logger log = Logger.getLogger(this.getClass());

	protected HashMap<Class<?>, Method> idSetterMethods = new HashMap<Class<?>, Method>();

	protected HashMap<Class<?>, Field> idFields = new HashMap<Class<?>, Field>();


	public void setId(	Object entity,
						long id)
	{
		Method idSetterMethod = idSetterMethods.get(entity.getClass());
		if (idSetterMethod != null)
		{
			// We know what the setter method is already. Just call it.
			return;
		}

		Field idField = idFields.get(entity.getClass());
		if (idField != null)
		{
			// We know what the id field is already. Just assign a new value to
			// it.
			return;
		}

		idSetterMethod = findIdSetterMethod(entity);
		if (idSetterMethod != null)
		{
			// We now know what the setter method is. Call it and cache it for
			// later use.
			invokeSetter(idSetterMethod, entity, id);
			idSetterMethods.put(entity.getClass(), idSetterMethod);
			return;
		}

		idField = findIdField(entity.getClass());
		if (idField != null)
		{
			// We now know what the id field is. Assign a new value to
			// it, and cache it for later use.
			return;
		}

		// Throw an error
		throw new RuntimeException("Could not determine how to set ID of entity of type " + entity.getClass().getName());
	}


	private Field findIdField(Object entity)
	{
		Field idField = null;
		return idField;
	}


	private Method findIdSetterMethod(Object entity)
	{
		Method idSetterMethod = null;

		Method getterMethod = null;
		for (Method method : entity.getClass().getMethods())
		{
			Id id = method.getAnnotation(Id.class);
			if (id != null)
			{
				getterMethod = method;
				break;
			}
		}
		if (getterMethod != null)
		{
			String getterName = getterMethod.getName();
			if (!getterName.startsWith("get"))
			{
				String error = "@Id annotated method " + entity.getClass().getName() + "." + getterName + " is not a getter method.";
				log.error(error);
				throw new RuntimeException(error);
			}

			String fieldName = getterName.substring(3);
			String setterName = "set" + fieldName;
			try
			{
				idSetterMethod = entity.getClass().getMethod(setterName, long.class);
			}
			catch (NoSuchMethodException e)
			{
				String error = "@Id annotated getter method " + entity.getClass().getName() + "." + getterName
						+ " does not have a corresponding setter method or field";
				log.error(error);
				throw new RuntimeException(error);
			}
		}
		return idSetterMethod;
	}


	private void invokeSetter(	Method idSetterMethod,
								Object entity,
								long id)
	{
		try
		{
			idSetterMethod.invoke(entity, id);
		}
		catch (InvocationTargetException e)
		{
			String error = "Error occurred when calling setter for @Id annotated getter method:" + entity.getClass().getName() + "." + idSetterMethod.getName();
			log.error(error);
			throw new RuntimeException(error, e);
		}
		catch (IllegalAccessException e)
		{
			String error = "Error occurred when calling setter for @Id annotated getter method:" + entity.getClass().getName() + "." + idSetterMethod.getName();
			log.error(error);
			throw new RuntimeException(error, e);
		}
	}


	public HashMap<Class<?>, Method> getIdSetterMethods()
	{
		return idSetterMethods;
	}


	public HashMap<Class<?>, Field> getIdFields()
	{
		return idFields;
	}

}
