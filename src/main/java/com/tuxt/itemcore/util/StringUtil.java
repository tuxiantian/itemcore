package com.tuxt.itemcore.util;

import org.apache.commons.lang3.StringUtils;




/**
 * 字符串工具类
 * 
 * @author 
 * @since 2015-03-17 21:09:43
 */
public final class StringUtil extends StringUtils{
	/** Private Constructor **/
	private StringUtil(){
	}
	
	/**
	 * 判断字符串是否非null && 非空字符 
	 * 
	 * @param param
	 * @return
	 */
	public static boolean isNotEmpty(String param) {
		return param != null && !"".equals(param.trim());
	}

	/**
	 * 判断字符串是否为null || 空字符串
	 * 
	 * @param param
	 * @return
	 */
	public static boolean isEmpty(String param) {
		return param == null || "".equals(param.trim()) || param.equals("null");
	}
	
	public static boolean isEmptyExcludeNull(String param) {
		String str = trim(param);
		return str.length() < 1;
	}
	
	public static String trim(String str) {
		return trim(str, "");
	}
	
	public static String delStrAllQuo(String str,int length) {
		if (StringUtil.isEmptyExcludeNull(str)) {
			return "";
		}
		str = str.replaceAll("\\n"," ").replaceAll("\\s{2,}"," ").replaceAll("\\\\\"", "").replaceAll("[\"']", "");
		if (str.length() >= length) {
			str = str.substring(0, Math.max(length-10,0));
		}
		return new String(str);
	}

	public static String trim2Empty(String str) {
		return isEmpty(str) ? "" : str.trim();
	}
	/**
     * <p>Removes a substring if it is at the start or end of a source string,
     * otherwise returns the source string.</p>
     *
     * <p>A {@code null} source string will return {@code null}.
     * An empty ("") source string will return the empty string.
     * A {@code null} search string will return the source string.</p>
     *
     * <pre>
     * StringUtil.trim("|a|b|", "|")      = "a|b"
     * StringUtil.trim("", *)        = ""
     * StringUtil.trim(*, null)      = *
     * StringUtil.trim("www.domain.com", ".com.")  = "www.domain.com"
     * StringUtil.trim("www.domain.com", ".com")   = "www.domain"
     * StringUtil.trim("www.domain.com", "domain") = "www.domain.com"
     * StringUtil.trim("abc", "")    = "abc"
     * </pre>
     *
     * @param str  the source String to search, may be null
     * @param remove  the String to search for and remove, may be null
     * @return the substring with the string removed if found,
     *  {@code null} if null String input
     * @since 2.1
     */
	public static String trim(String str, final String remove) {
		if (isEmpty(str) || isEmpty(remove)) {
            return str;
        }
        if (str.startsWith(remove)){
        	str=str.substring(remove.length());
        }
        if (str.endsWith(remove)) {
        	str=str.substring(0, str.length() - remove.length());
        }
        return str;
	}
}
