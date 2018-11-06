package com.syfri.userservice.filter;

import com.syfri.userservice.config.InfoCollectToken;
import com.syfri.userservice.config.LoginType;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 验证码过滤器
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {

	//登陆验证
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws  Exception{
		//登陆类型
		String loginType = request.getParameter("loginType");
		if(LoginType.MYSHIRO.toString().equals(loginType)){
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpSession session = httpServletRequest.getSession();
			//取出Session中验证码
			String code = (String) session.getAttribute("code");
			//取出输入的验证码
			String validateCode = httpServletRequest.getParameter("validateCode");
			if(code!=null && validateCode!=null && !validateCode.equals(code)){
				httpServletRequest.setAttribute("shiroLoginFailure","kaptchaValidateFailed");
				return true;
			}
			//return super.onAccessDenied(request, response);
		}
		return super.onAccessDenied(request, response);
	}

	//创建Token
	@Override
	protected InfoCollectToken createToken(ServletRequest request, ServletResponse response){
		String username = getUsername(request);
		String password = getPassword(request);
		String loginType = request.getParameter("loginType");
		String unscid = request.getParameter("unscid");
		if(LoginType.MYSHIRO.toString().equals(loginType)){
			return new InfoCollectToken(username, password, LoginType.MYSHIRO.toString());
		} else {
			return new InfoCollectToken(unscid, LoginType.INFOCOLLECT.toString());
		}
	}

	//成功登陆
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
		//登陆方式
		String loginType = ((InfoCollectToken) token).getLoginType();
		//loginType = "MyShiro";
		if(LoginType.INFOCOLLECT.toString().equals(loginType)){
			//HttpServletRequest req = (HttpServletRequest) request;
			//req.getSession().setAttribute("loginType", "infoCollect");
			WebUtils.getAndClearSavedRequest(request);
			String infoCollectUrl = "/jxcsplan/jxcsplanAll";
			WebUtils.redirectToSavedRequest(request, response, infoCollectUrl);
		}else{
			issueSuccessRedirect(request, response);
		}
		return false;
	}

	/**
	 * lxy
	 * 解决
	 * 1.如果是访问其他已存在的页面被拦截到登录页面，登录后就会跳转到之前的页面。
	 * 2.如果是直接访问登录页面或者是通过退出登录到登录页面，再次登录就会跳转到“/”。
	 * 3.不管怎么样，都没有跳转到successUrl指定的url。
	 * http://www.cnblogs.com/ginponson/p/5205962.html%20
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
		String successUrl = "/index";
		WebUtils.issueRedirect(request, response, successUrl, null, true);
	}
}
