package com.keep.going.login;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.keep.going.comm.CustomMessageSource;
import com.keep.going.security.CustomCSRFToken;

@Controller
public class LoginController {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	/** customMessageSource */
	@Resource(name = "customMessageSource")
	CustomMessageSource customMessageSource;
	
	
	
	/**
	 * 로그인 후 메인화면으로 들어간다
	 * @param
	 * @return 로그인 페이지
	 * @exception Exception
	 */
	@RequestMapping(value = "/uat/uia/actionMain.do")
	public String actionMain(HttpServletResponse response, HttpServletRequest request, ModelMap model) throws Exception {
		
		LOGGER.debug("actionMain:enter");
		LOGGER.debug("actionMain:"+request.getParameter("login_error"));
		
		LOGGER.debug("CSRFToken:"+CustomCSRFToken.getTokenForSession(request.getSession()));
		
		//LOGGER.debug("actionMain:"+request.getParameter("CSRFToken"));
		//request.getSession().setAttribute("CSRF_TOKEN",UUID.randomUUID().toString());
		LOGGER.debug("actionMain:"+request.getParameter("login_error"));
		

		//로그인 성공
		if(request.getParameter("login_error") == null){ 
			model.addAttribute("message", customMessageSource.getMessage("welcom.common.message"));
		}else{
		//로그인 실패
			model.addAttribute("message", customMessageSource.getMessage("fail.common.try"));
			
		}
		return "uat/uia/EgovLoginUsr";
		
		/*if(new CustRequestValidation().tryUrlValidate(request)){
			
			model.addAttribute("message", customMessageSource.getMessage("welcom.common.message"));
			return "uat/uia/EgovLoginUsr";
			
		}else{
			if(request.getParameter("login_error").equals("1")){
				LOGGER.debug("actionMain:"+"error 1");
				model.addAttribute("message", customMessageSource.getMessage("fail.common.try"));
				Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
				return "uat/uia/EgovLoginUsr";
			}
		}
		//return "uat/uia/EgovLoginUsr";
		return "forward:/cmm/main/mainPage.do";*/
		// 1. Spring Security 사용자권한 처리
/*		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			//model.addAttribute("message", customMessageSource.getMessage("fail.common.login"));
			model.addAttribute("message", customMessageSource.getMessage("welcom.common.message"));
			
			return "uat/uia/EgovLoginUsr";
		}
*/
		// 2. 메인 페이지 이동
		//return "forward:/cmm/main/mainPage.do";
	}
	
}
