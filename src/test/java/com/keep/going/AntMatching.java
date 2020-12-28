package com.keep.going;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.util.AntPathMatcher;

public class AntMatching {

	@Test
	public void antStylePatternTest() {
		// double asterisks
		assertThat(true, is(checkAntPattern("/css/**", "/css/app.3a41a065.css")));


	

	}

	private boolean checkAntPattern(String pattern, String inputStr) {
		return matches(pattern, inputStr);
	}

	public static boolean matches(String pattern, String inputStr) {
		AntPathMatcher antPathMatcher = new AntPathMatcher();
		return antPathMatcher.match(pattern, inputStr);
	}
	
}
