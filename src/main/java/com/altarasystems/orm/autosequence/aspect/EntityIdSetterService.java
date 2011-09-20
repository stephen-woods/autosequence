package com.altarasystems.orm.autosequence.aspect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Id;

import org.apache.log4j.Logger;

public class EntityIdSetterService
{
	protected Logger log = Logger.getLogger(this.getClass());

	protected HashMap<Class<?>, Method> idSetterMethods = new HashMap<Class<?>, Method>();

	protected HashMap<Class<?>, Field> idFields = new HashMap<Class<?>, Field>();


	public void setId(	final Object entity,
						long id)
	{
		final Class<?> clazz = entity.getClass();
		Method idSetterMethod = idSetterMethods.get(clazz);
		if (idSetterMethod != null)
		{
			// We know what the setter method is already. Just call it.
			invokeSetter(idSetterMethod, entity, id);
			return;
		}

		Field idField = idFields.get(clazz);
		if (idField != null)
		{
			// We know what the id field is already. Just assign a new value to
			// it.
			assignField(idField, entity, id);
			return;
		}

		idSetterMethod = findIdSetterMethod(entity);
		if (idSetterMethod != null)
		{
			// We now know what the setter method is. Call it and cache it for
			// later use.
			invokeSetter(idSetterMethod, entity, id);
			idSetterMethods.put(clazz, idSetterMethod);
			return;
		}

		idField = findIdField(entity);
		if (idField != null)
		{
			// We now know what the id field is. Assign a new value to
			// it, and cache it for later use.
			assignField(idField, entity, id);
			idFields.put(clazz, idField);
			return;
		}

		// Throw an error
		throw new RuntimeException("Could not determine how to set ID of entity of type " + entity.getClass().getName());
	}


	private Field findIdField(final Object entity)
	{
		Field idField = null;

		ArrayList<Field> allDeclaredFields = new ArrayList<Field>();
		Class<?> declaringClass = entity.getClass();
		while (declaringClass != null)
		{
			List<Field> fields = Arrays.asList(declaringClass.getDeclaredFields());
			allDeclaredFields.addAll(fields);
			declaringClass = declaringClass.getSuperclass();
		}

		for (Field field : allDeclaredFields)
		{
			Id id = field.getAnnotation(Id.class);
			if (id != null)
			{
				idField = field;
				idField.setAccessible(true);
				break;
			}
		}
		return idField;
	}


	private Method findIdSetterMethod(final Object entity)
	{
		Method idSetterMethod = null;

		Method getterMethod = null;

		// Get declared methods, including inherited methods
		ArrayList<Method> allDeclaredMethods = new ArrayList<Method>();
		Class<?> declaringClass = entity.getClass();
		while (declaringClass != null)
		{
			List<Method> methods = Arrays.asList(declaringClass.getDeclaredMethods());
			allDeclaredMethods.addAll(methods);
			declaringClass = declaringClass.getSuperclass();
		}

		for (Method method : allDeclaredMethods)
		{
			Id id = method.getAnnotation(Id.class);
			if (id != null)
			{
				getterMethod = method;
				method.setAccessible(true);
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

			// Find the setter, looking in super classes if need be
			declaringClass = entity.getClass();
			while (declaringClass != null)
			{
				try
				{
					idSetterMethod = declaringClass.getDeclaredMethod(setterName, long.class);
					idSetterMethod.setAccessible(true);
					break;
				}
				catch (NoSuchMethodException e)
				{
					declaringClass = declaringClass.getSuperclass();
				}
			}

			if (idSetterMethod == null)
			{
				String error = "@Id annotated getter method " + entity.getClass().getName() + "." + getterName
						+ " does not have a corresponding setter method or field";
				log.error(error);
				throw new RuntimeException(error);
			}
		}
		return idSetterMethod;
	}


	private void invokeSetter(	final Method idSetterMethod,
								final Object entity,
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


	private void assignField(	final Field idField,
								final Object entity,
								long id)
	{
		try
		{
			idField.setLong(entity, id);
		}
		catch (IllegalArgumentException e)
		{
			String error = "Error occurred when assigning @Id annotated field method:" + entity.getClass().getName() + "." + idField.getName();
			log.error(error);
			throw new RuntimeException(error, e);
		}
		catch (IllegalAccessException e)
		{
			String error = "Error occurred when assigning @Id annotated field method:" + entity.getClass().getName() + "." + idField.getName();
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
