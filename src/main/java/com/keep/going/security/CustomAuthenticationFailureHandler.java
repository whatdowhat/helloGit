package com.keep.going.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private String loginidname;			// 로그인 id값이 들어오는 input 태그 name
	private String loginpasswdname;		// 로그인 password 값이 들어오는 input 태그 name
	private String loginredirectname;		// 로그인 성공시 redirect 할 URL이 지정되어 있는 input 태그 name
	private String exceptionmsgname;		// 예외 메시지를 request의 Attribute에 저장할 때 사용될 key 값
	private String defaultFailureUrl;		// 화면에 보여줄 URL(로그인 화면)
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);


	
	public CustomAuthenticationFailureHandler(){
		this.loginidname = "id";
		this.loginpasswdname = "password";
		this.loginredirectname = "loginRedirect";
		this.exceptionmsgname = "securityexceptionmsg";
		this.defaultFailureUrl = "/uat/uia/actionMain.do?login_error=1";
	}
	
	
	public String getLoginidname() {
		return loginidname;
	}


	public void setLoginidname(String loginidname) {
		this.loginidname = loginidname;
	}


	public String getLoginpasswdname() {
		return loginpasswdname;
	}


	public void setLoginpasswdname(String loginpasswdname) {
		this.loginpasswdname = loginpasswdname;
	}

	public String getExceptionmsgname() {
		return exceptionmsgname;
	}

	public String getLoginredirectname() {
		return loginredirectname;
	}


	public void setLoginredirectname(String loginredirectname) {
		this.loginredirectname = loginredirectname;
	}


	public void setExceptionmsgname(String exceptionmsgname) {
		this.exceptionmsgname = exceptionmsgname;
	}

	public String getDefaultFailureUrl() {
		return defaultFailureUrl;
	}


	public void setDefaultFailureUrl(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}


	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		
		LOGGER.debug("CustomAuthenticationFailureHandler enter ");
		// Request 객체의 Attribute에 사용자가 실패시 입력했던 로그인 ID와 비밀번호를 저장해두어 로그인 페이지에서 이를 접근하도록 한다
		String loginid = request.getParameter(loginidname);
		String loginpasswd = request.getParameter(loginpasswdname);
		String loginRedirect = request.getParameter(loginredirectname);
		
		LOGGER.debug(" loginidname :"+loginid);
		LOGGER.debug(" loginpasswdname :"+loginpasswd);
		LOGGER.debug(" loginredirectname :"+loginRedirect);
		LOGGER.debug(" defaultFailureUrl :"+defaultFailureUrl);
		
		LOGGER.debug(" bCryptPasswordEncoder.encode :"+bCryptPasswordEncoder.encode(loginpasswd));
		
		request.setAttribute(loginidname, loginid);
		request.setAttribute(loginpasswdname, loginpasswd);
		request.setAttribute(loginredirectname, loginRedirect);
		
		
		
		// Request 객체의 Attribute에 예외 메시지 저장
		request.setAttribute(exceptionmsgname, exception.getMessage());
		
		request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
	}

}