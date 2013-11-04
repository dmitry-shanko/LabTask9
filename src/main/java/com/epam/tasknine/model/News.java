package com.epam.tasknine.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table
@NamedQueries({
@NamedQuery(name = "news.getList", query = "FROM News ORDER BY date DESC"),
@NamedQuery(name="news.remove", query="DELETE FROM News WHERE id IN (:param)")
})
@SequenceGenerator(name = "PK", sequenceName = "T_NEWS_SEQ")
@Cacheable(true)
public class News implements Serializable, Comparable<News>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8303915975000106941L;
	@Column
	private String title;
	@Column
	private Calendar newsDate;
//	@Column
//	private Date newsDate;
	@Column
	private String brief;
	@Column
	private String content;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "PK")
	private Integer id;

	public News()
	{
		id = new Integer(0);
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the date
	 */
	public Calendar getNewsDate() {
		return newsDate;
	}
	/**
	 * @param date the date to set
	 */
	public void setNewsDate(Calendar newsDate) {
		this.newsDate = newsDate;
	}
	/**
	 * @return the brief
	 */
	public String getBrief() {
		return brief;
	}
	/**
	 * @param brief the brief to set
	 */
	public void setBrief(String brief) {
		this.brief = brief;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		if (id == null)
		{
			this.id = new Integer(0);
		}
		else
		{
			if (id > 0)
			{
				this.id = id;
			}
		}
	}
	/**
	 * Overridden Object.toString()<p>
	 * @return String presentation of this bean.
	 */
	@Override
	public String toString() 
	{
		StringBuilder sb = new StringBuilder();
		sb.append("(NEWS: {ID=");
		sb.append(id);
		sb.append("; TITLE=");
		sb.append(title);
		sb.append("; DATE=");
		sb.append(newsDate);
		sb.append("; BRIEF=");
		sb.append(brief);
		sb.append("; CONTENT=");
		sb.append(content);
		sb.append("});");
		return sb.toString();
	}
	/**
	 * Overridden Object.equals()<p>
	 * @return false if any field is not equals to the field of comparing object.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass())
		{
			return false;
		}
		News news = (News) obj;
		if (title != null ? !title.equals(news.title) : news.title != null) 
		{
			return false;
		}
		if (content != null ? !content.equals(news.content) : news.content != null) 
		{
			return false;
		}
		if (id != news.id)
		{
			return false;
		}
		if (brief != null ? !brief.equals(news.brief) : news.brief != null)
		{
			return false;
		}
		if (newsDate != null ? !newsDate.equals(news.newsDate) : news.newsDate != null)
		{
			return false;
		}
		return true;
	}
	/**
	 * Overridden Object.hashCode()<p>
	 * final int hash = 1;<p>
	 *	int result = 11;<p>
	 *	result = hash * result + id;<p>
	 *	result = hash * result + ((null != content) ? content.hashCode() : 1);<p>
	 *	result = hash * result + ((null != title) ? title.hashCode() : 1);<p>
	 *	result = hash * result + ((null != date) ? date.hashCode() : 1);<p>
	 *	result = hash * result + ((null != brief) ? brief.hashCode() : 1);<p>
	 *	@ return result;
	 */
	@Override
	public int hashCode() 
	{
		final int hash = 1;
		int result = 11;
		result = hash * result + id;
		result = hash * result + ((null != content) ? content.hashCode() : 1);
		result = hash * result + ((null != title) ? title.hashCode() : 1);
		result = hash * result + ((null != newsDate) ? newsDate.hashCode() : 1);
		result = hash * result + ((null != brief) ? brief.hashCode() : 1);
		return result;
	}
//	@Override
//	public int compare(News o1, News o2) 
//	{
//		// TODO Auto-generated method stub
//		return 0;
//	}
	@Override
	public int compareTo(News o) 
	{
		if (o == null)
		{
			return Integer.MAX_VALUE;
		}
		return (getNewsDate() != null ? o.getNewsDate().compareTo(getNewsDate()) : (o.getNewsDate() != null ? Integer.MIN_VALUE : 0));
	}
}
