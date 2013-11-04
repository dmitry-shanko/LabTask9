package com.epam.tasknine.faces.bean;

import java.io.Serializable;

import com.epam.tasknine.faces.manager.NewsManager;

public class NewsBean implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6427627654774164460L;

	private NewsManager newsManager;

	/**
	 * @return the newsManager
	 */
	public NewsManager getNewsManager() 
	{
		return newsManager;
	}

	/**
	 * @param newsManager the newsManager to set
	 */
	public void setNewsManager(NewsManager newsManager) 
	{
		this.newsManager = newsManager;
	}

}
