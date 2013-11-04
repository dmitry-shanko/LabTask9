package com.epam.tasknine.database.util;

import org.hibernate.cfg.DefaultNamingStrategy;

public class CustomNamingStrategy extends DefaultNamingStrategy
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4021007185840634832L;

	public String classToTableName(String className)
	{
		return "T_LAB_TASK_9_" + super.classToTableName(className).toUpperCase();
	}

	public String propertyToColumnName(String proName)
	{
		return super.propertyToColumnName(proName).toUpperCase();
	}

	public String columnName(String columnName)
	{
		return columnName;
	}

	public String tableName(String tableName)
	{
		return tableName;
	}
}
