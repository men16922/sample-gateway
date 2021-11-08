package kr.co.danal.gw.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;


/**
 * @author han, sung-ku
 * @version 1.0.0
 * @since 
 * <pre>
 * [REVISION]
 * 
 * </pre> 
 */
public class ParamUtil {
	
	
	
	/**
	 * @param value
	 * @return
	 */
	public static float toFloat(Object value) {
		return NumberUtils.toFloat(str(value));
		
	}
	
	
	/**
	 * @param value
	 * @return
	 */
	public static int toInt(Object value) {
		return NumberUtils.toInt(str(value));
		
	}
	
	
	/**
	 * @param value
	 * @return
	 */
	public static String str(Object value) {
		return str(value, null);
		
	}
	
	
	/**
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static String str(Object value, String defaultValue) {
		String ret = null;
		
		if(value == null) {
			return defaultValue;
			
		}
		
		ret = String.valueOf(value);
		
		if(StringUtils.isBlank(ret)) {
			return defaultValue;
			
		} else {
			return ret;
			
		}
		
	}
	
	
	
	public static BigDecimal strToBigDecimal(String amount) {
		return new BigDecimal(amount);
	}
	
	
	

}
