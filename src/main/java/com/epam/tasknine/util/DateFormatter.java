package com.epam.tasknine.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum DateFormatter 
{
	;
	private static final Logger log = LoggerFactory.getLogger(com.epam.tasknine.util.DateFormatter.class);

	/**
	 * This method convert <h1>util.Date</h1> into  <h1>sql.Date</h1>
	 * @param utilDate java.util.Date
	 * @return java.sql.Date
	 */
	public static java.sql.Date utilDateToSqlDate(java.util.Date utilDate) 
	{
		if (null == utilDate)
		{
			log.debug("Arg in com.epam.testapp.util.DateFormatter public static java.sql.Date utilDateToSqlDate(java.util.Date utilDate)  is null: utilDate={}", utilDate);
			return new java.sql.Date(System.currentTimeMillis());
		}
		long value = utilDate.getTime();
		java.sql.Date sqlDate = new java.sql.Date(value);
		return sqlDate;
	}
	/**
	 * This method convert  <h1>sql.Date</h1> into   <h1>util.Date</h1>
	 * @param sqlDate java.sql.Date
	 * @return java.util.Date
	 */
	public static java.util.Date sqlDateToUtilDate(java.sql.Date sqlDate) 
	{
		if (null == sqlDate)
		{
			log.debug("Arg in com.epam.testapp.util.DateFormatter public static java.util.Date sqlDateToUtilDate(java.sql.Date sqlDate) is null: sqlDate={}", sqlDate);
			return new java.util.Date(System.currentTimeMillis());
		}
		java.util.Date utilDate = sqlDate;
		return utilDate;
	}

	/**
	 * 
	 * @param date String with date 
	 * @param dateFormat format of date
	 * @param sqlDateFormat format of date in database
	 * @return java.sql.Date
	 * @throws DateConverterException
	 */
	public static java.sql.Date stringToSqlDate(String date, String dateFormat, String sqlDateFormat)
	{
		if ((null == date) || (null == dateFormat) || (null == sqlDateFormat))
		{
			log.debug("Some args in com.epam.testapp.util.DateFormatter public static java.sql.Date stringToSqlDate(String date, String dateFormat, String sqlDateFormat) are null: date={}, dateFormat={}, sqlDateFormat=".concat(sqlDateFormat), date, dateFormat);
			return new java.sql.Date(System.currentTimeMillis());
		}
		DateFormat formatter = new SimpleDateFormat(dateFormat);
		java.util.Date utilDate = new java.util.Date();
		try 
		{
			log.debug("Attempt to convert String date into java.sql.Date using dateFormat and sqlDateFormat: date={} into format={}", date, dateFormat);
			utilDate = formatter.parse(date);
		} 
		catch (ParseException e) 
		{
			log.error("Can't convert ".concat(date).concat(" into ").concat(dateFormat).concat(". ParseException:"), e);
			return new java.sql.Date(System.currentTimeMillis());
		} 
		DateFormat format = new SimpleDateFormat(sqlDateFormat);
		String stringDate = format.format(utilDate);
		java.sql.Date sqlDate = java.sql.Date.valueOf(stringDate);
		return sqlDate;
	}

	/**
	 * 
	 * @param date String
	 * @param format format date
	 * @return java.util.Date
	 * @throws DateConverterException
	 */
	public static java.util.Date stringToUtilDate(String date, String format) 
	{
		if ((null == date) || (null == format))
		{
			log.debug("Some args in com.epam.testapp.util.DateFormatter public static java.util.Date stringToUtilDate(String date, String format) are null: date={}, format={}", date, format);

			return new java.util.Date(System.currentTimeMillis());
		}
		DateFormat formatter = new SimpleDateFormat(format);
		java.util.Date utilDate = new java.util.Date();
		try 
		{
			log.debug("Attempt to convert String date into java.util.Date using format: date={} into format={}.", date, format);
			utilDate = formatter.parse(date);
		} 
		catch (ParseException e) 
		{
			log.error("Can't convert ".concat(date).concat(" into ").concat(format).concat(". ParseException:"), e);
			return new java.util.Date(System.currentTimeMillis());
		}
		return utilDate;
	}
}
