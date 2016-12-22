package com.tasly.deepureflow.exception;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tasly.deepureflow.dto.BaseResult;


@Provider
@Produces(MediaType.APPLICATION_JSON)
public class RestExceptionHandler implements ExceptionMapper<WebApplicationException> {

	private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);
	@Context HttpServletRequest request;
	
	@Override
	public Response toResponse(WebApplicationException exception) {
		logger.error("访问出错，url:"+request.getRequestURL(), exception);
		ResponseBuilder rb = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
		rb.type(MediaType.APPLICATION_JSON);
		BaseResult<Object> response = new BaseResult<Object>(false,exception.getMessage());
		rb.entity(response);
		return rb.build();
	}

}
