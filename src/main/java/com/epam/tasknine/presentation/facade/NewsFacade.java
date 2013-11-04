package com.epam.tasknine.presentation.facade;

import java.util.List;

import com.epam.tasknine.database.exception.DaoException;
import com.epam.tasknine.model.News;

public interface NewsFacade 
{
	
	List<News> getNewsList() throws DaoException;
	News fetchById(Integer id) throws DaoException;
	News save(News entity) throws DaoException;
	void remove(Integer... ids) throws DaoException;
	News updateNews(News entity) throws DaoException;
}
