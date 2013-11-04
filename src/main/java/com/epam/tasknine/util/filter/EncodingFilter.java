package com.epam.tasknine.util.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.tasknine.util.GlobalConstants;

public class EncodingFilter implements Filter 
{
	private String encoding;
	private static final Logger log = LoggerFactory.getLogger(EncodingFilter.class);

	public void init(FilterConfig fConfig) throws ServletException 
	{
		encoding = fConfig.getInitParameter(GlobalConstants.ENCODING_PARAM.getContent());
	}

	public void destroy() 
	{
		encoding = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		String encodingRequest = request.getCharacterEncoding();
		if (null != encoding && !encoding.equalsIgnoreCase(encodingRequest)) 
		{
			request.setCharacterEncoding(encoding);
		}
		try
		{
			chain.doFilter(request, response);
		}
		catch (Exception e)
		{
			log.error("{} has encountered an error. Can't proceed, throws an exception:{}.", getClass(), ServletException.class);
			log.error("Exception: ", e);
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

}
