package com.epam.tasknine.database.constant;

public enum NewsFields 
{
	TITLE("TITLE"),
	BRIEF("BRIEF"),
	DATE("NEWSDATE"),
	CONTENT("CONTENT"),
	ID("ID");
	private String content;
	private NewsFields (String content) 
	{
		this.content = content;
	}
	
	public String getContent()
	{
		return content;
	}
}
