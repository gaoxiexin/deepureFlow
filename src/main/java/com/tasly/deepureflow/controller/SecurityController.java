package com.tasly.deepureflow.controller;

import java.awt.Color;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.tasly.deepureflow.enums.MenuEnum;
import com.tasly.deepureflow.exception.UserActiveException;
import com.tasly.deepureflow.util.VerifyCodeUtil;

/**
 * 
 * @ClassName:  SecurityController   
 * @Description:安全管理Controller（验证码及登录）
 * @author: 高燮訢  
 * @date:   Nov 24, 2016 2:53:08 PM   
 *
 */
@Controller
@RequestMapping("security")
public class SecurityController {
	
	private final Logger logger = Logger.getLogger(SecurityController.class.getName());
	
	@RequestMapping(method = RequestMethod.GET)
	public String home(HttpServletRequest request) {
		return "login";
	}

	/**
	 * 用户登录
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request,HttpSession session) {
//		String resultPageURL = InternalResourceViewResolver.FORWARD_URL_PREFIX+"/";
		String resultPageURL="login";
		String userCode = request.getParameter("userCode");
		String password = request.getParameter("password");
		// 获取HttpSession中的验证码
		String verifyCode = (String) request.getSession().getAttribute(
				"verifyCode");
		// 获取用户请求表单中输入的验证码
		String submitCode = WebUtils.getCleanParam(request, "verifyCode");
		logger.info("用户[" + userCode + "]登录时输入的验证码为[" + submitCode
				+ "],HttpSession中的验证码为[" + verifyCode + "]");
		if (StringUtils.isEmpty(submitCode)
				|| !StringUtils.equals(verifyCode, submitCode.toLowerCase())) {
			request.setAttribute("message_login", "验证码不正确");
			return resultPageURL;
		}
		UsernamePasswordToken token = new UsernamePasswordToken(userCode,
				password);
		token.setRememberMe(true);
		logger.info("为了验证登录用户而封装的token为"
				+ ReflectionToStringBuilder.toString(token,
						ToStringStyle.MULTI_LINE_STYLE));
		// 获取当前的Subject
		Subject currentUser = SecurityUtils.getSubject();
		try {
			// 在调用了loggerin方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
			// 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
			// 所以这一步在调用loggerin(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
			logger.info("对用户[" + userCode + "]进行登录验证..验证开始");
			currentUser.login(token);
			logger.info("对用户[" + userCode + "]进行登录验证..验证通过");

			boolean isAdmin = SecurityUtils.getSubject().hasRole(com.tasly.deepureflow.enums.RoleType.HEADQUARTERS_OPERATOR.getName());
			logger.info("是否包含总部营运权限："+isAdmin);
			session.setAttribute("selectItem",  MenuEnum.AGENT.getId());
			resultPageURL = "redirect:/terminal/terminalList";
		} catch (UnknownAccountException uae) {
			logger.error("对用户[" + userCode + "]进行登录验证..验证未通过,用户名或者密码不正确");
			request.setAttribute("message_login", "用户名或者密码不正确");
		} catch (UserActiveException eae) {
			logger.error("对用户[" + userCode + "]进行登录验证..用户未激活");
			request.setAttribute("message_login", "用户未激活，无法登陆");
		} catch (IncorrectCredentialsException ice) {
			logger.error("对用户[" + userCode + "]进行登录验证..验证未通过,错误的凭证");
			request.setAttribute("message_login", "密码不正确");
		} catch (LockedAccountException lae) {
			logger.error("对用户[" + userCode + "]进行登录验证..验证未通过,账户已锁定");
			request.setAttribute("message_login", "账户已锁定");
		} catch (ExcessiveAttemptsException eae) {
			logger.error("对用户[" + userCode + "]进行登录验证..验证未通过,错误次数过多");
			request.setAttribute("message_login", "用户名或密码错误次数过多");
		}  catch (AuthenticationException ae) {
			// 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
			logger.error("对用户[" + userCode + "]进行登录验证..验证未通过,堆栈轨迹如下");
			ae.printStackTrace();
			request.setAttribute("message_login", "用户名或密码不正确");
		}
		// 验证是否登录成功
		if (currentUser.isAuthenticated()) {
			logger.info("用户[" + userCode
					+ "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
		} else {
			token.clear();
		}
		return resultPageURL;
	}

	/**
	 * 用户登出
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		Subject subject=SecurityUtils.getSubject();
		if(subject.isAuthenticated()){
			subject.logout();
			
			//session会销毁，在sessionListener监听session销毁，清理权限缓存
			if(logger.isDebugEnabled()){
				logger.debug("用户"+request.getAttribute("user")+"退出登录");
			}
		}
		return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/";
	}

	/**
	 * 获取验证码图片和文本(验证码文本会保存在HttpSession中)
	 */
	@RequestMapping("/getVerifyCodeImage")
	public void getVerifyCodeImage(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 设置页面不缓存
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		String verifyCode = VerifyCodeUtil.generateTextCode(
				VerifyCodeUtil.TYPE_NUM_ONLY, 4, null);
		// 将验证码放到HttpSession里面
		request.getSession().setAttribute("verifyCode", verifyCode);
		logger.info("本次生成的验证码为[" + verifyCode + "],已存放到HttpSession中");
		// 设置输出的内容的类型为JPEG图像
		response.setContentType("image/jpeg");
		BufferedImage bufferedImage = VerifyCodeUtil.generateImageCode(
				verifyCode, 90, 30, 3, true, Color.WHITE, Color.BLACK, null);
		// 写给浏览器
		ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());
	}
}
