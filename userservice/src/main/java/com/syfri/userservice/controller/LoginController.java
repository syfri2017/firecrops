package com.syfri.userservice.controller;

import com.syfri.baseapi.model.ResultVO;
import com.syfri.userservice.config.InfoCollectToken;
import com.syfri.userservice.config.LoginType;
import com.syfri.userservice.model.*;
import com.syfri.userservice.utils.CurrentUserUtil;
import com.syfri.userservice.utils.ImageCodeUtil;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

@Api(value = "登录",tags = "登录API",description = "登录")
@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	protected Environment environment;

	@Autowired
	private RedisSessionDAO redisSessionDAO;

//	@ModelAttribute
//	public void Model(Model model){
//		if (environment.containsProperty("server.context-path")) {
//			model.addAttribute("contextPath", environment.getProperty("server.context-path"));
//		}else{
//			model.addAttribute("contextPath", "/");
//		}
//	}

	@RequestMapping("/home")
	public String bigscreen(Model model, @RequestParam(value="index", required = false) String index){
		if(index == null || "".equals(index)){
			index = "1";
		}
		model.addAttribute("index", index);
		return "/index";
	}

	@GetMapping("/index")
	public String index(){
		return "bigscreen/big_screen_all";
	}

	@GetMapping({"/","/login"})
	public String login(){
		logger.info("-----GET请求方式登录-----");
		if(SecurityUtils.getSubject().isAuthenticated()){
			return "redirect:/index";
		}
		return "/login";
	}

	/**
	 * 采用Controller登陆验证方式
	 * by li.xue 2018/11/29 11:14
	 */
	@PostMapping("/login2")
	public @ResponseBody String login(HttpServletRequest request, Map<String,Object> map, @RequestBody AccountVO vo){
		logger.info("-----POST请求方式登录222-----");
		Subject subject = SecurityUtils.getSubject();
		//测试当前用户是否被验证
		String msg;
		String messages;
		if (!subject.isAuthenticated()) {
			String code = (String)request.getSession().getAttribute("code");
			if (LoginType.MYSHIRO.toString().equals(vo.getLoginType()) && code != null && !code.equals(vo.getValidateCode())){
				msg = "kaptchaValidateFailed --> 验证码错误";
				messages = "kaptcha";
			}else {
				InfoCollectToken token;
				if (LoginType.INFOCOLLECT.toString().equals(vo.getLoginType())) {
					token = new InfoCollectToken(vo.getUnscid(), vo.getLoginType());
				} else {
					token = new InfoCollectToken(vo.getUsername(), vo.getPassword(), vo.getLoginType());
				}
				try {
					subject.login(token);
					map.put("token", subject.getSession().getId());
					msg = "登陆成功";
					messages = "success";
				} catch (UnknownAccountException e) {
					msg = "UnknownAccountException --> 账号不存在";
					messages = "unknown";
				} catch (IncorrectCredentialsException e) {
					msg = "IncorrectCredentialsException --> 密码不正确";
					messages = "incorrect";
				} catch (ExcessiveAttemptsException e) {
					msg = "ExcessiveAttemptsException --> 密码输入错误次数超过5次";
					messages = "excessive";
				}
			}
		}else{
			msg = "Session有效";
			messages = "session";
		}
		map.put("msg", msg);
		return messages;
	}

	/**
	 * 此方法不处理登录成功的情况，由shiro进行处理
	 * 采用Shiro自带的登陆方式登陆，Shiro验证登陆  by li.xue 2018/11/29 14:08
	 */
	@PostMapping("/login")
	public String login(HttpServletRequest request, Map<String,Object> map) throws Exception{
		logger.info("-----POST请求方式登录-----");
		ShiroUser user = CurrentUserUtil.getCurrentUser();
		if(user != null){
			return "/index";
		}else{
			String exception = (String) request.getAttribute("shiroLoginFailure");
			logger.info("【loginController】" + exception);
			String msg = "";
			if(exception != null){
				if(UnknownAccountException.class.getName().equals(exception)){
					msg = "UnknownAccountException --> 账号不存在";
				}else if(IncorrectCredentialsException.class.getName().equals(exception)){
					msg = "IncorrectCredentialsException --> 密码不正确";
				}else if(ExcessiveAttemptsException.class.getName().equals(exception)){
					msg = "ExcessiveAttemptsException --> 密码输入错误次数超过5次";
				}else if("kaptchaValidateFailed".equals(exception)){
					msg = "kaptchaValidateFailed --> 验证码错误";
				}else{
					msg = "else --> " + exception;
				}
			}
			map.put("msg",msg);
			//以下是登录错误时返回login.html页面
			//后台工程  by li.xue 2018/07/03
			//return "/login";
			//前台工程  by li.xue 2018/07/03
			return "redirect:templates/login.html";
		}
	}

	@GetMapping("/403")
	public String unauthorizedRole(){
		logger.info("-----没有权限-----");
		return "/403";
	}

	/**
	 * 验证码
	 */
	@GetMapping("/imageCode")
	public void reloadCode(HttpServletRequest request, HttpServletResponse response) throws IOException{

		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		OutputStream os = response.getOutputStream();
		Map<String,Object> map = ImageCodeUtil.getImageCode(60,20,os);
		HttpSession session = request.getSession();
		session.setAttribute("code", map.get("strEnsure").toString().toLowerCase());
		session.setAttribute("codeTime", new Date().getTime());
		try{
			ImageIO.write((BufferedImage) map.get("image"), "JPEG", os);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * logout
	 */
	@GetMapping("/logout")
	public String logout(){
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "redirect:/login";
	}

	@RequestMapping("/shiro")
	@ResponseBody
	public ShiroUser getShiroUser(){
		Subject subject = SecurityUtils.getSubject();
		return (ShiroUser)subject.getPrincipal();
	}

	@GetMapping("/getMenu")
	public @ResponseBody ResultVO getMenu(){
		List<ResourceTree> menus = CurrentUserUtil.getCurrentUser().getResourceTrees();
		ResultVO resultVO = ResultVO.build();
		resultVO.setResult(menus);
		return resultVO;
	}

	/**
	 * 查看Session是否有效
	 * by li.xue 2018/12/4 9:38
	 */
	@GetMapping("/getSession")
	public @ResponseBody String getSession(HttpServletRequest request){
		String sessionId = request.getSession().getId();
		Session session;
		try{
			session = redisSessionDAO.readSession(sessionId);
			Collection collection = session.getAttributeKeys();
			if(collection.size() == 0){
				return "0";
			}
		}catch(Exception e){
			return "0";
		}
		return "1";
	}
}
