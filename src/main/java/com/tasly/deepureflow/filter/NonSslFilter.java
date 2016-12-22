package com.tasly.deepureflow.filter;


import org.apache.shiro.web.filter.authz.PortFilter;

public class NonSslFilter extends PortFilter {

	    public NonSslFilter() {
	        setPort(PortFilter.DEFAULT_HTTP_PORT);
	    }

	    @Override
	    protected String getScheme(String requestScheme, int port) {
	        return PortFilter.HTTP_SCHEME;

	    }

}
