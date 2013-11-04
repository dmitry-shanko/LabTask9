package com.epam.tasknine.faces.manager;

import java.io.Serializable;

public interface NewsManager extends Serializable
{
	String newsList();
	String newsAdd();
	String newsEdit(Integer id);
	String newsView(Integer id);
	String newsDelete();
	String newsDelete(Integer... ids);
	String newsCancel();
	String changeLocale(String locale);
	String getStartPage();
	boolean listPage();
	boolean editPage();
	boolean viewPage();
	
}
