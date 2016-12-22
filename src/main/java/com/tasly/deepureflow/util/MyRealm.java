package com.tasly.deepureflow.util;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.tasly.deepureflow.domain.security.SecurityInfo;
import com.tasly.deepureflow.domain.user.User;
import com.tasly.deepureflow.exception.BizException;
import com.tasly.deepureflow.exception.UserActiveException;
import com.tasly.deepureflow.service.ISecurityService;
import com.tasly.deepureflow.service.IUserService;
import com.tasly.deepureflow.service.IZoneService;

public class MyRealm extends AuthorizingRealm {

	private final Logger logger = Logger.getLogger(MyRealm.class.getName());
	@Autowired
	private IUserService userService;

	@Autowired
	private ISecurityService securityService;
	
	@Autowired
	private IZoneService zoneService;
	@Override
	public AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		User currentUser = (User) principals.getPrimaryPrincipal(); ;
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

		SecurityInfo securityInfo = null;
		try {
			securityInfo = securityService.getSecurityInfo(currentUser.getId());
		} catch (BizException e) {
			logger.error(e.getMessage(), e);
		}
		if (securityInfo != null) {
			authorizationInfo.setRoles(securityInfo.getRoleNames());
			authorizationInfo.setStringPermissions(securityInfo
					.getPermissions());
			SecurityUtils.getSubject().getSession()
					.setAttribute("menus", securityInfo.getMenus());
		}

		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		AuthenticationInfo authcInfo = null;
		String userCode = token.getUsername();
		String userPass = MD5Encrypt.createPassword(new String(token
				.getPassword()));

		User user = userService.loginUser(userCode, userPass);
		if (null != user) {
			if(user.getIsactive()==false){
				throw new UserActiveException();
			}
			if(zoneService.findZoneByUser(user.getId())==null){
				throw new UserActiveException();
			}
			logger.info(user.getUsername() + "登陆成功");
			authcInfo = new SimpleAuthenticationInfo(user,
					token.getPassword(), this.getName());
			this.setSession("user", user);
			SecurityUtils.getSubject().getSession().setAttribute("user", user);
			// doGetAuthorizationInfo(SecurityUtils.getSubject()
			// .getPrincipals());
			SimplePrincipalCollection principals = new SimplePrincipalCollection(
					user, super.getName());
			super.clearCache(principals);
			return authcInfo;
		}

		return authcInfo;
	}

	private void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession();
			logger.info("Session超时时间[" + session.getTimeout() + "]");
			if (null != session) {
				session.setAttribute(key, value);
			}
		}
	}

	public void clearAuthz() {
		// TODO Auto-generated method stub
		super.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
	}
	
}
