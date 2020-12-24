package com.keep.going.security;



import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	private String errorPage;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);
	
	@Override
	public void handle(HttpServletRequest request,	HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

		LOGGER.debug("CustomAccessDeniedHandler handle SecurityContextHolder.getContext()/"+SecurityContextHolder.getContext().getAuthentication());
		if(SecurityContextHolder.getContext().getAuthentication()==null){
			
			request.setAttribute("errormsg", accessDeniedException);
			request.getRequestDispatcher(errorPage).forward(request, response);
		}else{
			
			Object temPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
			MemberInfo userDetails = (MemberInfo)temPrincipal;

			LOGGER.debug("CustomAccessDeniedHandler getUsername/"+userDetails.getUsername());
			
			
			Set<GrantedAuthority> authorities = (Set<GrantedAuthority>) userDetails.getAuthorities();
			
			Iterator<GrantedAuthority> it = authorities.iterator(); // Iterator(반복자) 생성

			while (it.hasNext()) { // hasNext() : 데이터가 있으면 true 없으면 false
				String tem = it.next().toString().toUpperCase();
				
				LOGGER.debug("CustomAccessDeniedHandler GrantedAuthority/"+tem);
				
				if(tem.equals("ADMIN")){
					LOGGER.debug("CustomAccessDeniedHandler isAdmin/");
					request.getRequestDispatcher("/cmm/main/mainPage.do").forward(request, response);
					break;
				}
				
			}
			
			
		

			
			
			// TODO Auto-generated method stub
			// Ajax를 통해 들어온것인지 파악한다
			String ajaxHeader = request.getHeader("X-Ajax-call");
			String result = "";
			LOGGER.debug("CustomAccessDeniedHandler enter");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.setCharacterEncoding("UTF-8");
			
			if(ajaxHeader == null){					// null로 받은 경우는 X-Ajax-call 헤더 변수가 없다는 의미이기 때문에 ajax가 아닌 일반적인 방법으로 접근했음을 의미한다
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				Object principal = auth.getPrincipal();
				if (principal instanceof UserDetails) {
					String username = ((UserDetails) principal).getUsername();
					request.setAttribute("username", username);
				}
				request.setAttribute("errormsg", accessDeniedException);
				request.getRequestDispatcher(errorPage).forward(request, response);
			}else{
				if("true".equals(ajaxHeader)){		// true로 값을 받았다는 것은 ajax로 접근했음을 의미한다
					result = "{\"result\" : \"fail\", \"message\" : \"" + accessDeniedException.getMessage() + "\"}";
				}else{								// 헤더 변수는 있으나 값이 틀린 경우이므로 헤더값이 틀렸다는 의미로 돌려준다
					result = "{\"result\" : \"fail\", \"message\" : \"Access Denied(Header Value Mismatch)\"}";
				}
				response.getWriter().print(result);
				response.getWriter().flush();
			}
			
		}
		
	}
	
	public void setErrorPage(String errorPage) {
        if ((errorPage != null) && !errorPage.startsWith("/")) {
            throw new IllegalArgumentException("errorPage must begin with '/'");
        }

        this.errorPage = errorPage;
    }

}