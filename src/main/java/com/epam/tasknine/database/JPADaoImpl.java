package com.epam.tasknine.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.tasknine.database.exception.DaoException;

public class JPADaoImpl<T, ID extends Serializable> implements GeneralDao<T, ID>
{
	static
	{
		Locale.setDefault(Locale.US);
	}
	
	private static final Logger log = LoggerFactory.getLogger(JPADaoImpl.class);
	
	private String removeNamedQuery;

	private Class<T> persistanceType;
	private Class<ID> countType;
	private String persistanceTypeName;
	private String countTypeName;

	@PersistenceContext
	private EntityManager entityManager;

	private JPADaoImpl(Class<T> persistanceType, Class<ID> countType)
	{
		this.setPersistanceType(persistanceType);
		this.setCountType(countType);
		this.persistanceTypeName = persistanceType.getSimpleName();
		this.countTypeName = countType.getSimpleName();
	}
	
	@SuppressWarnings("unused")
	private void init()
	{
		log.debug("Debug message for {}:", getClass());
		log.debug("Params: entityManager={}, persistanceType={}, persistanceTypeName={}, countType={}, countTypeName={}", new Object[]{entityManager, persistanceType, persistanceTypeName, countType, countTypeName});
	}	
	/**
	 * @return the persistanceType
	 */
	public Class<T> getPersistanceType() 
	{
		return persistanceType;
	}
	/**
	 * @param persistanceType the persistanceType to set
	 */
	public void setPersistanceType(Class<T> persistanceType) 
	{
		this.persistanceType = persistanceType;
	}
	/**
	 * @return the countType
	 */
	public Class<ID> getCountType() 
	{
		return countType;
	}
	/**
	 * @param countType the countType to set
	 */
	public void setCountType(Class<ID> countType) 
	{
		this.countType = countType;
	}
	/**
	 * @return the persistanceTypeName
	 */
	public String getPersistanceTypeName() 
	{
		return persistanceTypeName;
	}
	/**
	 * @param persistanceTypeName the persistanceTypeName to set
	 */
	public void setPersistanceTypeName(String persistanceTypeName) 
	{
		this.persistanceTypeName = persistanceTypeName;
	}
	/**
	 * @return the countTypeName
	 */
	public String getCountTypeName() 
	{
		return countTypeName;
	}
	/**
	 * @param countTypeName the countTypeName to set
	 */
	public void setCountTypeName(String countTypeName) 
	{
		this.countTypeName = countTypeName;
	}
	/**
	 * @return the removeNamedQuery
	 */
	public String getRemoveNamedQuery() 
	{
		return removeNamedQuery;
	}
	/**
	 * @param removeNamedQuery the removeNamedQuery to set
	 */
	public void setRemoveNamedQuery(String removeNamedQuery) 
	{
		this.removeNamedQuery = removeNamedQuery;
	}

	@Override
	public List<T> getList() throws DaoException 
	{
		log.debug("com.epam.tasknine.database.JPADaoImpl public List<T> getList() throws DaoException");

		try
		{
			CriteriaQuery<T> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(persistanceType);
			criteriaQuery.from(persistanceType);
			TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
			return query.getResultList();
		}
		catch (PersistenceException e)
		{
			log.debug("Some PersistenceException catched: ", e.getMessage());
			throw new DaoException("Can't complete getList operation. Can't get all instances of " + persistanceType, e);
		}
	}

	@Override
	public T fetchById(ID id) throws DaoException 
	{
		log.debug("com.epam.tasknine.database.JPADaoImpl public T fetchById(ID id) throws DaoException: id={}", id);
		if (null == id)
		{
			return null;
		}
		try
		{
			T o = entityManager.find(persistanceType, id);
			log.debug("Got by id: persistanceTypeName={}, Object={}", persistanceTypeName, o);
			return o;
		}
		catch (PersistenceException e)
		{
			log.debug("Some PersistenceException catched: ", e.getMessage());
			throw new DaoException("Can't get by ID=" + id + " instance of " + persistanceTypeName, e);
		}
	}

	@Override
	public T save(T entity) throws DaoException 
	{		
		log.debug("com.epam.tasknine.database.JPADaoImpl public T save(T entity) throws DaoException: entity={}", entity);
		if (null == entity)
		{
			return null;
		}
		else
		{
			try
			{
				T mergedEntity = (T) entityManager.merge(entity);
				entityManager.flush();
				log.debug("Object has been merged: persistanceTypeName={}, mergedEntity={}", persistanceTypeName, mergedEntity);
				return mergedEntity;
			}
			catch (PersistenceException e)
			{
				log.debug("Some PersistenceException catched: ", e.getMessage());
				throw new DaoException("Can't complete save operation. Can't merge entity=" + entity, e);
			}
		}
	}

	@Override
	public void remove(ID[] ids) throws DaoException 
	{
		log.debug("com.epam.tasknine.database.JPADaoImpl public void remove(Integer[] ids) throws DaoException: ids={}", ids);
		if (ids != null)
		{
			if (removeNamedQuery == null)
			{
				try
				{
					for (ID id : ids)
					{
						entityManager.remove(fetchById(id));
					}
				}
				catch (PersistenceException e)
				{
					log.debug("Some PersistenceException catched: ", e.getMessage());
					throw new DaoException("Can't complete delete operation.", e);
				}
			}
			else
			{
				List<ID> idsList = new ArrayList<ID>();
				boolean isAdded = idsList.addAll(Arrays.asList(ids));
				if (isAdded) 
				{
					try 
					{
						log.debug("Using NamedQuery={}", removeNamedQuery);
						log.debug("Attempt to remove {}: ids={}", persistanceType, idsList);
						Query query = entityManager.createNamedQuery(removeNamedQuery).setParameter("param", idsList);			
						query.executeUpdate();
						entityManager.flush();

					} 
					catch (PersistenceException e) 
					{
						log.debug("Some PersistenceException catched: ", e.getMessage());
						throw new DaoException("Can't complete delete operation. Error in query or some problems with database.", e);
					}
				} 
			}
			log.debug("Attempt to remove {} was successfull: ids={}", persistanceType, ids);
		} 
	}

	@Override
	public T update(T entity) throws DaoException 
	{
		return this.save(entity);
	}
}