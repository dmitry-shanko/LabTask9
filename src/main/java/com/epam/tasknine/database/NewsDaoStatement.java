package com.epam.tasknine.database;

public enum NewsDaoStatement 
{
	collectNews("SELECT TITLE, BRIEF, CONTENT, NEWSDATE, ID FROM \"ROOT\".\"T_LAB_TASK_9_NEWS\" ORDER BY NEWSDATE desc"),
	collectNewsById("SELECT TITLE, BRIEF, CONTENT, NEWSDATE, ID FROM \"ROOT\".\"T_LAB_TASK_9_NEWS\" WHERE ID=?"),
	createNews("INSERT INTO \"ROOT\".\"T_LAB_TASK_9_NEWS\" (TITLE, BRIEF, CONTENT, NEWSDATE, ID) VALUES (?, ?, ?, ?, T_NEWS_SEQ.NEXTVAL)"),
	deleteNews("DELETE FROM \"ROOT\".\"T_LAB_TASK_9_NEWS\" WHERE ID IN ("),
	updateNews("UPDATE \"ROOT\".\"T_LAB_TASK_9_NEWS\" SET TITLE=?, BRIEF=?, CONTENT=?, NEWSDATE=? WHERE ID=?");
	private String statement;
	private NewsDaoStatement (String s)
	{
		this.statement = s;
	}
	
	public String getStatement()
	{
		return statement;
	}
}
