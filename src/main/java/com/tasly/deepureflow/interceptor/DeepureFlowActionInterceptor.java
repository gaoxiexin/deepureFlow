package com.tasly.deepureflow.interceptor;

import java.util.Date;
import java.util.Enumeration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tasly.deepureflow.client.ISystemActionMapper;
import com.tasly.deepureflow.domain.system.SystemAction;
import com.tasly.deepureflow.domain.user.User;

/**
 * @author 作者 gxx E-mail:
 * @version 创建时间：Nov 10, 2016 2:21:31 PM 类说明 帝泊洱流向操作记录拦截器
 */
public class DeepureFlowActionInterceptor extends HandlerInterceptorAdapter {
	
	private final Logger logger = Logger.getLogger(DeepureFlowActionInterceptor.class.getName());
	
	@Resource(name = "sqlSession")
	private SqlSession sqlSession;
	/**
	 * 
	 * <p>
	 * Title: postHandle
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @param modelAndView
	 * @throws Exception
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#postHandle(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object,
	 *      org.springframework.web.servlet.ModelAndView)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HttpSession session = request.getSession();  
		Date now=new Date();
        String requestUri = request.getRequestURI();  
        String contextPath = request.getContextPath();  
        String url = requestUri.substring(contextPath.length());  
        if (!StringUtils.endsWithIgnoreCase(url, "page")&&((StringUtils.contains(url, "add")&&!StringUtils.contains(url, "address"))|| StringUtils.contains(url, "edit") || StringUtils  
                .contains(url, "del")||StringUtils.contains(url, "import"))) {  
        	SystemAction action=new SystemAction();
        	User user = session.getAttribute("user") != null ? (User) session
					.getAttribute("user") : null;
            StringBuffer sb = new StringBuffer();  
			Enumeration<String> a = null;
			a = request.getParameterNames();
			while (a.hasMoreElements()) {
				String key = a.nextElement();
				sb.append(key + ":" + request.getParameter(key) + ", ");
			}
            
            int actionType=getActionTypeByUrl(url);
            
        	action.setActionUrl(requestUri);
            action.setActionType(actionType);
        	action.setActionMessage(StringUtils.removeEnd(StringUtils.trim(sb.toString()), ","));
        	action.setActionOperationName(user.getUsername());
        	action.setActionOperationTime(now);
        	
        	ISystemActionMapper systemActionMapper = sqlSession.getMapper(ISystemActionMapper.class);
        	int count=systemActionMapper.insert(action);
        	
        	if(count>0){
        		logger.info("创建帝泊洱流向操作记录成功,内容为："+String.format("FBI response warning! user: %s, url: %s, models: {%s}", user.getUsername(),  
                        url, StringUtils.removeEnd(StringUtils.trim(sb.toString()), ",")));
        	}else{
        		logger.info("创建帝泊洱流向操作记录失败,内容为："+String.format("FBI response warning! user: %s, url: %s, models: {%s}", user.getUsername(),  
                        url, StringUtils.removeEnd(StringUtils.trim(sb.toString()), ",")));
        	}
        }  
	}

	private int getActionTypeByUrl(String url) {
		int actionType=-1;
		if(StringUtils.contains(url, "add")){
			actionType=0;
        }else if(StringUtils.contains(url, "edit")){
        	actionType=1;
        }else if(StringUtils.contains(url, "del")){
        	actionType=2;
        }else if(StringUtils.contains(url, "import")){
        	actionType=3;
        }
		return actionType;
	}

	/**
	 * 
	 * <p>
	 * Title: preHandle
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
//		HttpSession session = request.getSession();
//		String requestUri = request.getRequestURI();
//		System.out.println("request uri:" + requestUri);
//		String contextPath = request.getContextPath();
//		String url = requestUri.substring(contextPath.length());
//		if (StringUtils.contains(url, "add")
//				|| StringUtils.contains(url, "edit")
//				|| StringUtils.contains(url, "del")) {
//			User user = session.getAttribute("user") != null ? (User) session
//					.getAttribute("user") : null;
//			StringBuffer sb = new StringBuffer();
//			Enumeration<String> a = null;
//			a = request.getParameterNames();
//			while (a.hasMoreElements()) {
//				String key = a.nextElement();
//				sb.append(key + ":" + request.getParameter(key) + ", ");
//			}
//			System.out
//					.println(String
//							.format("FBI request warning! user: %s, url: %s, params: {%s}",
//									user.getUsername(), url, StringUtils.removeEnd(
//											StringUtils.trim(sb.toString()),
//											",")));
//		}
		return true;
	}

}
