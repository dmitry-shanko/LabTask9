package com.epam.tasknine.util;

public enum GlobalConstants 
{
	ENCODING_PARAM("encoding"),
	LANG_PARAM_REQUEST("lang"),
	COUNTRY_RU("RU"),
	COUNTRY_EN("US")
	;

	private String content;
	private GlobalConstants (String content) 
	{
		this.content = content;
	}

	public String getContent()
	{
		return content;
	}
}
