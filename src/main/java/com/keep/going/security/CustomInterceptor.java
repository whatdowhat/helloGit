package com.keep.going.security;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;



@Component
public class CustomInterceptor extends HandlerInterceptorAdapter{
    
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomInterceptor.class);
	@Resource(name = "messageSource")
	MessageSource messageSource;
	
	/** EgovPropertyService */
	@Resource(name = "cSRFTokenManager")
	protected CustomCSRFToken csrf;
	
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
    	LOGGER.debug("===== before(interceptor) =====");
    	
    	LOGGER.debug("RequestURL sessionToken nana: "+request.getParameter("egovframework.com.cmm.filter.CustomCSRFToken.tokenval"));
    	//LOGGER.debug("RequestURL message: "+EgovProperties.getProperty("Globals.project")); 
    	 LOGGER.debug("RequestURL CustomSystemFilter: "+request.getRequestURL());
         
 	    String sessionToken = CustomCSRFToken.getTokenForSession(request.getSession());
 	    String requestToken = CustomCSRFToken.getTokenFromRequest(request);
 	 //  LOGGER.debug("RequestURL sessionToken: "+sessionToken);
	  //  LOGGER.debug("RequestURL requestToken: "+requestToken);
	    
    	 
	    //LOGGER.debug("RequestURL message: "+egovMessageSource.getMessage("Globals.project")); 
	    
	    
	    
	  /*  if(!excludedUrls.contains(request.getRequestURI().toString()))
	    {
	        // 제외할 url들이 아니면 동작할 로직
	    }
	    */
	    
	    if (sessionToken.equals(requestToken)) {
	    	LOGGER.debug("RequestURL [pass]");
	    	//  chain.doFilter(req, res);
	    } else {
	    	//((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "Bad or missing CSRF value");
	      
	      //chain.doFilter(req, res);
	      
	   }
	    
        return true;
    }
    
    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
    	LOGGER.debug("===== after(interceptor) =====");
    }
 
    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    	LOGGER.debug("===== afterCompletion =====");
    }
}