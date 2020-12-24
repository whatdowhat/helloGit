package com.keep.going;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Ignore;
import org.junit.Test;

import com.keep.going.no.CustomUser;



public class ApcheCommonTest {

	private String[] strs = new String[] {"aaa","bbb"};
	
	private String[][] person = new String[][] {
		{"chain","faithful"},
		{"sanga","pretty"},
		{"jungsu","good"},
		{"kyngmin","smary"}
	};
	private int[] nums = new int[] {5,4,2,6,1};
	
	private Date today = new Date();

	
	@Test
	public void loombok() {
		
		CustomUser customUser = CustomUser.builder().id(123).name("스트링").build();
		System.out.println(customUser);
		
		//simple edit
	}
	
	@Ignore @Test
	public void randomTest() {
		
		System.out.println(RandomStringUtils.randomAlphabetic(5));
		System.out.println(RandomStringUtils.randomAlphanumeric(5));
		System.out.println(RandomStringUtils.randomNumeric(5));
		
	}
	
	@Ignore @Test
	public void testNumberUtil() {
		
		int min = NumberUtils.min(nums);
		int max = NumberUtils.max(nums);
		
		System.out.println("min :"+min + "// max :"+max);
		
		
		
		String nstr = "1234";
		String nullstr = null;
		if(StringUtils.isNumeric(strs[0])) 
			System.out.println("createNumber :" + NumberUtils.createNumber(strs[0]));
		System.out.println("createNumber :" + NumberUtils.createNumber(nstr) );
		
		System.out.println("null:"+NumberUtils.toInt(nullstr,3));
		
		
	}
	
	@Ignore @Test
	public void testDate() {
		
		
		Date yesterday = DateUtils.addDays(today, -1);
		Date tomorrow = DateUtils.addDays(today, +1);
		Date nextyear = DateUtils.addYears(today, 1);
		
		System.out.println("y="+yesterday);
		System.out.println("t="+tomorrow);
		System.out.println("n="+nextyear);
		
		
	}
	
	@Ignore @Test
	public void testArrayUtil() {
		System.out.println(Arrays.toString(person));
		System.out.println(ArrayUtils.toString(person));
		
		System.out.println(Arrays.toString(strs));
		System.out.println(ArrayUtils.toString(strs));
		
		
		Map<Object, Object> map = ArrayUtils.toMap(person);
		
		System.out.println("chan is "+ map.get("chain"));
		
		
		System.out.println(Arrays.toString(nums));
		ArrayUtils.reverse(nums);
		System.out.println(Arrays.toString(nums));
		Arrays.sort(nums);
		System.out.println(Arrays.toString(nums));
		
		
		System.out.println(ArrayUtils.contains(nums, 1));
		System.out.println(ArrayUtils.contains(nums, 20));
		
		
		
		
		
	}
	
}
