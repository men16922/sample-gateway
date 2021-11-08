package kr.co.danal.gw.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class StringUtil {

  private static final Logger logger = LogManager.getLogger(StringUtil.class);
  @Getter
  private static final ObjectMapper objectMapper = new ObjectMapper();

  private StringUtil(){}

  /**
   * null 또는 "" 이 아닌지 여부
   *
   * @param s 문자열
   * @return null 또는 "" 이 아닌지 여부
   */
  public static boolean isNotNull(String s) {
    if (s == null || "".equals(s)) {
      return false;
    }

    return true;
  }

  /**
   * null 또는 "" 인지 여부
   *
   * @param s 문자열
   * @return null 또는 "" 인지 여부
   */
  public static boolean isNull(String s) {
    return (!isNotNull(s));
  }

  /**
   * 입력 문자열이 null or "", " " 인지 여부.
   *
   * @param cs 입력문자열
   * @return 입력 문자열이 null or "", " " 인지 여부
   */
  public static boolean isBlank(final CharSequence cs) {
    int strLen;

    if (cs == null || (strLen = cs.length()) == 0) {
      return true;
    }

    for (int i = 0; i < strLen; i++) {
      if (!Character.isWhitespace(cs.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * 입력문자열 str이 null, "", " " 일 경우 defaultStr 값으로 리턴해준다.
   *
   * @param str        입력문자열
   * @param defaultStr 리턴기본문자열
   * @return
   */
  public static <T extends CharSequence> T defaultIfBlank(final T str, final T defaultStr) {
    return StringUtil.isBlank(str) ? defaultStr : str;
  }

  /**
   * mainStr이 null 또는 ""일 경우 changeStr 치환한다.
   *
   * @param mainStr   입력문자열
   * @param changeStr 치환문자열
   * @return
   */
  public static String nullReplace(String mainStr, String changeStr) {
    return (isNull(mainStr)) ? changeStr : mainStr;
  }

  /**
   * <b>String형태를 순서보장되는 map형태로 변환</b>
   * <br>키와 값의 구분자 및 키와값의 쌍의 구분자형태인 string을 map으로 변환
   * <pre>
   * String s = "key1=value1&key2=value2";
   * String s2 = "key1*value1;key2*value2";
   *
   * stringToMap(s, "=", "&")  -> Map형태 {key, value}, {key2, value2}
   * stringToMap(s2, "*", ";") -> Map형태 {key, value}, {key2, value2}
   * </pre>
   *
   * @param str
   * @param nvSeparator
   * @param groupSeparator
   * @return
   * @throws Exception
   */
  public static Map<String, String> stringToMap(String str, String nvSeparator,
      String groupSeparator) throws Exception {
    Map<String, String> resultMap = new LinkedHashMap<>();

    if (isNull(str) || isNull(nvSeparator) || isNull(groupSeparator)) {
      throw new Exception("Confirm parameter");
    }

    try {
      String[] colums = str.split(groupSeparator);

      for (String col : colums) {
        int idx = col.indexOf(nvSeparator);
        int len = col.length();

        String key = col.substring(0, idx).trim();
        String value = col.substring(idx + 1).trim();

        resultMap.put(defaultIfEmpty(key, "temp"), defaultIfEmpty(value, null));
      }
    } catch (Exception e) {
      throw new Exception("String to Map convert Exception");
    }

    return resultMap;
  }

  /**
   * 입력문자열 str이 null, "" 일 경우 defaultStr 값으로 리턴해준다.
   *
   * @param str        입력문자열
   * @param defaultStr 리턴기본문자열
   * @return
   */
  public static <T extends CharSequence> T defaultIfEmpty(final T str, final T defaultStr) {
    return StringUtil.isEmpty(str) ? defaultStr : str;
  }

  /**
   * 입력 문자열이 null or "" 인지 여부
   *
   * @param cs 입력문자열
   * @return 입력 문자열이 null or "" 인지 여부
   */
  public static boolean isEmpty(final CharSequence cs) {
    return cs == null || cs.length() == 0;
  }

  private static boolean regionMatches(CharSequence cs, boolean ignoreCase, int thisStart,
      CharSequence substring, int start, int length) {
    if (cs instanceof String && substring instanceof String) {
      return ((String) cs)
          .regionMatches(ignoreCase, thisStart, ((String) substring), start, length);
    } else {
      return cs.toString()
          .regionMatches(ignoreCase, thisStart, substring.toString(), start, length);
    }
  }

  /**
   * 입력 문자열이 cs1와 cs2가 일치하는지 여부
   *
   * @param cs1 입력문자열1
   * @param cs2 입력문자열2
   * @return 일치하는지 여부
   */
  public static boolean equals(final CharSequence cs1, final CharSequence cs2) {
    if (cs1 == cs2) {
      return true;
    }

    if (cs1 == null || cs2 == null) {
      return false;
    }

    if (cs1 instanceof String && cs2 instanceof String) {
      return cs1.equals(cs2);
    }

    return regionMatches(cs1, false, 0, cs2, 0, Math.max(cs1.length(), cs2.length()));

  }

  /**
   * url Encoding
   *
   * @param source
   * @param enc
   * @return encoded String
   */
  public static String urlEncode(String source, String enc) {
    try {
      return URLEncoder.encode(source, enc);
    } catch (Exception e) {
      logger.error("{} Exception : urlEncode function", StringUtil.class.getSimpleName());
      return "";
    }
  }

  /**
   * <b>순서보장된 map형태를 String형태로 변환</b>
   * <br>키와 값의 구분자 및 키와값의 쌍의 구분자형태로 string을 반환
   * <pre>
   * Map m = new HashMap();
   * m.put("key1", "value1");
   * m.put("key2", "value2");
   *
   * mapToString(m, "=", "&") -> key1=value1&key2=value2
   * mapToString(m, "*", ";") -> key1*value1;key2*value2
   * </pre>
   *
   * @param map
   * @param nvSeparator
   * @param groupSeparator
   * @return
   * @throws Exception
   */
  public static String mapToString(Map<String, String> map, String nvSeparator,
      String groupSeparator) throws Exception {
    StringBuffer result = new StringBuffer();
    String temp;

    if (map == null || map.size() == 0 || isNull(nvSeparator) || isNull(groupSeparator)) {
      throw new Exception("Confirm parameter");
    }

    try {
      for (Object key : map.keySet().toArray()) {
        temp = String.valueOf(map.getOrDefault(key, ""));
        result.append(key).append(nvSeparator).append(temp).append(groupSeparator);
      }

      result.delete(result.length() - 1, result.length());

      return result.toString();
    } catch (Exception e) {
      throw new Exception("Map to String convert Exception");
    }
  }
}
