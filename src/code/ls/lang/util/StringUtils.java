package code.ls.lang.util;


/**
 * @Title: StringUtils.java
 * @Package com.ls.common.string
 * @Description:
 * @author linsheng
 * @email hi.linsheng@foxmail.com
 * @date 2016年5月13日 下午5:23:11
 * @version V1.0
 */
public class StringUtils {

	public static boolean isEmpty(String value) {
		if ((value == null) || (value.length() == 0)) {
			return true;
		}
		return false;
	}

	public static boolean equals(String a, String b) {
		if (a == null) {
			return b == null;
		}
		return a.equals(b);
	}

	public static boolean equalsIgnoreCase(String a, String b) {
		if (a == null) {
			return b == null;
		}
		return a.equalsIgnoreCase(b);
	}

	public static String subString(String src, String start, String to) {
		
		int indexFrom = src.indexOf(start);
		if (indexFrom != -1  ) {
			indexFrom += start.length();
		}else{
			return null;
		}
		String subStr = src.substring(indexFrom);
		int indexTo = subStr.indexOf(to);
		if (indexTo != -1 ) {
			return subStr.substring(0, indexTo);
		}else{
			return null;
		}

	}

	/**
	 * @Title:prt
	 * @Description:控制台打印
	 * @param @param subString
	 * @return void
	 */
	public static void prt(Object obj) {
		System.out.println(obj);
	}
}
