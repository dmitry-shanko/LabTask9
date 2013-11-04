package com.epam.tasknine.faces.manager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.tasknine.database.constant.NewsFields;
import com.epam.tasknine.database.exception.DaoException;
import com.epam.tasknine.model.News;
import com.epam.tasknine.presentation.facade.NewsFacade;
import com.epam.tasknine.util.DateFormatter;

public class NewsManagerImpl implements NewsManager
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4760656983447755887L;

	private static final Logger log = LoggerFactory.getLogger(NewsManagerImpl.class);
	private static final String newsList = "newsList";
	private static final String newsAdd = "newsAdd";
	private static final String newsView = "newsView";
	private static final String newsError = "newsError";
	private static final String redirect = "redirect";
	private static final String resources = "jsp.MessageResources";
	private static final String datePattern = "date.pattern";
	private NewsFacade newsFacade;

	private Locale currentLocale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
	private List<News> currentNewsList;
	private String prevPage; 
	private Map<Integer, Boolean> checkBoxesIndexes = new HashMap<Integer, Boolean>();;
	private News newsToView;
	private News newsToEdit;
	private String newsDateString;

	@SuppressWarnings("unused")
	private void init()
	{
		try 
		{
			this.currentNewsList = newsFacade.getNewsList();
		} 
		catch (DaoException e) 
		{
			log.error("Exception in initializing new {}, can't get any newsList from {}", getClass(), newsFacade);
		}
	}
	/**
	 * @return the newsFacade
	 */
	public NewsFacade getNewsFacade() 
	{
		return newsFacade;
	}
	/**
	 * @param newsFacade the newsFacade to set
	 */
	public void setNewsFacade(NewsFacade newsFacade) 
	{
		this.newsFacade = newsFacade;
	}

	/**
	 * @return the redirect
	 */
	public static String getRedirect() {
		return redirect;
	}
	/**
	 * @return the prevPage
	 */
	public String getPrevPage() 
	{
		if (prevPage == null)
		{
			this.prevPage = newsList;
		}
		return prevPage;
	}
	/**
	 * @param prevPage the prevPage to set
	 */
	public void setPrevPage(String prevPage) {
		this.prevPage = prevPage;
	}
	/**
	 * @return the currentLocale
	 */
	public Locale getCurrentLocale() 
	{
		return currentLocale;
	}
	/**
	 * @param currentLocale the currentLocale to set
	 */
	public void setCurrentLocale(Locale currentLocale) 
	{
		this.currentLocale = currentLocale;
	}
	/**
	 * @return the currentNewsList
	 */
	public List<News> getCurrentNewsList() {
		return currentNewsList;
	}
	/**
	 * @param currentNewsList the currentNewsList to set
	 */
	public void setCurrentNewsList(List<News> currentNewsList) {
		this.currentNewsList = currentNewsList;
	}

	/**
	 * @return the newsToView
	 */
	public News getNewsToView() 
	{
		return newsToView;
	}
	/**
	 * @param newsToView the newsToView to set
	 */
	public void setNewsToView(News newsToView) {
		this.newsToView = newsToView;
	}
	/**
	 * @return the newsToEdit
	 */
	public News getNewsToEdit() {
		return newsToEdit;
	}
	/**
	 * @param newsToEdit the newsToEdit to set
	 */
	public void setNewsToEdit(News newsToEdit) {
		this.newsToEdit = newsToEdit;
	}
	/**
	 * @return the newsDateString
	 */
	public String getNewsDateString() 
	{
		return newsDateString;
	}
	/**
	 * @param newsDateString the newsDateString to set
	 */
	public void setNewsDateString(String newsDateString) 
	{
		if (newsDateString == null)
		{
			newsDateString = sqlDateToString();
		}
		this.newsDateString = newsDateString;
	}

	/**
	 * @return the checkBoxesIndexes
	 */
	public Map<Integer, Boolean> getCheckBoxesIndexes() {
		return checkBoxesIndexes;
	}
	/**
	 * @param checkBoxesIndexes the checkBoxesIndexes to set
	 */
	public void setCheckBoxesIndexes(Map<Integer, Boolean> checkBoxesIndexes) {
		this.checkBoxesIndexes = checkBoxesIndexes;
	}

	@Override
	public String newsList() 
	{
		log.debug("Attempt to get newsList from {}", newsFacade);
		String outcome = newsError; 
		try 
		{
			this.currentNewsList = newsFacade.getNewsList();
			outcome = newsList;
			setPrevPage(newsList);
			checkBoxesIndexes = new HashMap<Integer, Boolean>();
			log.debug("News has been successfully collected. Current outcome={}, currentNewsList.size={}", outcome, currentNewsList.size());
		} 
		catch (DaoException e) 
		{
			log.error("Can't get news from newsFacade", e);
		}		
		return outcome;
	}

	@Override
	public String newsAdd()
	{
		String outcome = newsError;
		if (null != newsToEdit)
		{
			if ((newsToEdit.getBrief() != null) && (newsToEdit.getContent() != null) && (newsToEdit.getTitle() != null))
			{
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(stringToSqlDate().getTime());
				newsToEdit.setNewsDate(calendar);
			}
		}
		log.debug("Attempt to add news: news={}", newsToEdit);
		try
		{
			if ((newsToEdit.getId() == null) || (newsToEdit.getId() < 1))
			{
				newsFacade.save(newsToEdit);
			}
			else
			{
				newsFacade.updateNews(newsToEdit);
			}
			return newsView(newsToEdit.getId());
		}
		catch (DaoException e)
		{
			log.error("Can't save or update news=" + newsToEdit, e);
		}
		setNewsToEdit(null);
		return outcome;
	}

	@Override
	public String newsEdit(Integer id) 
	{
		log.debug("Attempt to edit news with such id: id={}", id);
		News news = null;
		try 
		{
			if (id != null)
			{
				news = newsFacade.fetchById(id);
			}
		} 
		catch (DaoException e) 
		{
			log.error("Can't fetchById news with ID=" + id, e);
		}	
		if (news == null)
		{
			news = new News();
		}
		setNewsDateString(sqlDateToString());
		setNewsToEdit(news);
		String outcome = newsAdd;
		return outcome;
	}

	@Override
	public String newsView(Integer id) 
	{
		log.debug("Attempt to view news with such id: id={}", id);
		try 
		{
			setNewsToView(newsFacade.fetchById(id));
		} 
		catch (DaoException e) 
		{
			log.error("Can't fetchById news with ID=" + id, e);
		}
		String outcome = newsView;
		setPrevPage(outcome);
		return outcome;
	}

	@Override
	public String newsDelete()
	{
		log.debug("Attempt to delete news with such ids: ids={}", checkBoxesIndexes);
		if ((checkBoxesIndexes != null) && (checkBoxesIndexes.containsValue(true)))
		{
			Set<Integer> keySet = checkBoxesIndexes.keySet();
			for (Integer i : keySet)
			{
				if (checkBoxesIndexes.get(i))
				{
					try 
					{
						newsFacade.remove(i);
					} 
					catch (DaoException e) 
					{
						log.error("Can't delete news with such id=" + i, e);
					}
				}
			}
		}
		return newsList();
	}

	@Override
	public String newsDelete(Integer... ids)
	{
		log.debug("Attempt to delete news with such ids: ids={}", ids);
		if (ids != null)
		{
			try 
			{
				newsFacade.remove(ids);
			} 
			catch (DaoException e) 
			{
				log.error("Can't delete news with such ids=" + ids, e);
			}
		}
		return newsList;
	}

	@Override
	public String newsCancel()
	{
		if (getPrevPage().equals(newsView))
		{
			return newsView(newsToView != null ? newsToView.getId() : 0);
		}
		return getPrevPage();
	}

	@Override
	public String changeLocale(String language) 
	{
		log.debug("Request for changing locale: currentLocale={}, language={}", currentLocale, language);
		UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		Iterator<Locale> supportedLocales = FacesContext.getCurrentInstance().getApplication().getSupportedLocales();
		while (supportedLocales.hasNext())
		{
			Locale locale = supportedLocales.next();
			if (locale.toLanguageTag().equalsIgnoreCase(language))
			{
				viewRoot.setLocale(locale);
				setCurrentLocale(locale);
				break;
			}
		}
		String outcome = viewRoot.getViewId();
		log.debug("Outcome info after changing locale: currentLocale={}, outcome={}", currentLocale, outcome);
		localeListening();
		return outcome;
	}

	@Override
	public String getStartPage() 
	{
		return newsList();
	}

	@Override
	public boolean editPage()
	{
		if (this.permissionToEdit())
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean listPage()
	{
		clearNewsToView();
		clearNewsToEdit();
		if (!newsList.equals(getPrevPage()))
		{
			newsList();
		}
		return true;
	}

	@Override
	public boolean viewPage()
	{
		if ((newsToView == null) || (newsToView.getId() == null ) || (newsToView.getId() < 1))
		{
			setNewsToView(null);
		}
		return true;
	}
	
	protected void clearNewsToView()
	{
		this.newsToView = null;
	}
	
	protected void clearNewsToEdit()
	{
		this.newsToEdit = null;
	}

	private boolean permissionToEdit()
	{
		if (newsToEdit == null)
		{
			setNewsToEdit(new News());
		}
		if (newsDateString == null)
		{
			setNewsDateString(null);
		}
		return true;
	}

	private void localeListening()
	{
		setNewsDateString(sqlDateToString());
	}

	private String sqlDateToString()
	{
		ResourceBundle bundle = ResourceBundle.getBundle(resources, currentLocale);
		String pattern = bundle.getString(datePattern);
		DateFormat dateFormat = new SimpleDateFormat(pattern);
		if ((newsToView != null) && (newsToView.getNewsDate() != null)) 
		{
			return dateFormat.format(newsToView.getNewsDate().getTime());
		}
		else
		{
			return dateFormat.format(new java.sql.Date(System.currentTimeMillis()));
		}
	}

	private java.sql.Date stringToSqlDate()
	{
		java.sql.Date sqlDate = null;
		if (newsDateString != null)
		{					
			ResourceBundle bundle = ResourceBundle.getBundle(resources, currentLocale);
			String pattern = bundle.getString(datePattern);
			DateFormat dateFormat = new SimpleDateFormat(pattern);
			java.util.Date utilDate = null;
			try 
			{
				log.debug("Attempt to parse String date into java.util.Date using pattern: date={} into format={}.", newsDateString, pattern);
				utilDate = dateFormat.parse(newsDateString);
			} 
			catch (ParseException e) 
			{
				log.error("Can't parse String ".concat(newsDateString).concat(" into java.util.Date ").concat(pattern).concat(". ParseException:"), e);
			}
			sqlDate = DateFormatter.utilDateToSqlDate(utilDate);
		}
		else
		{
			sqlDate = new java.sql.Date(System.currentTimeMillis());
		}
		return sqlDate;
	}
}