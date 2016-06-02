/**
 * @Title: StringValidata.java 
 * @Package com.ls.test.utils 
 * @Description:
 * @author linsheng 
 * @email hi.linsheng@foxmail.com   
 * @date 2015年11月24日 上午9:49:36 
 * @version V1.0   
 */
package code.ls.lang.util;

/**
 * @Title: StringValidata.java
 * @Package com.ls.test.utils
 * @Description:
 * @author linsheng
 * @email hi.linsheng@foxmail.com
 * @date 2015年11月24日 上午9:49:36
 * @version V1.0
 */
public class StringValidata {

	public static final String IP_REGEX = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
	public static final String IP_LIKE_REGEX = "^(\\d{1,3}\\.){3}\\d{1,3}$";
	public static final String MASK_REGEX = "^(254|252|248|240|224|192|128|0)\\.0\\.0\\.0|255\\.(254|252|248|240|224|192|128|0)\\.0\\.0|255\\.255\\.(254|252|248|240|224|192|128|0)\\.0|255\\.255\\.255\\.(254|252|248|240|224|192|128|0)$";
	public static final String MAC_REGEX = "^[A-F0-9]{2}(-[A-F0-9]{2}){5}$";
	public static final String NUM_REGEX = "^[0-9]+$";
	public static final String LETTER_REGEX = "^[a-zA-Z]*$";
	public static final String NUM_OR_LETTER_REGEX = "^[0-9a-zA-Z]*$";
	public static final String NUM_AND_LETTER_REGEX = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]$";
	public static final String PHONE_REGEX = "^1\\d{10}$";
	public static final String EMAIL_REGEX = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";

	public static boolean isIP(String ip) {
		return ip.matches(IP_REGEX);
	}

	public static boolean isLikeIp(String ip_like) {
		return ip_like.matches(IP_LIKE_REGEX);
	}

	public static boolean isMask(String mask) {
		return mask.matches(MASK_REGEX);
	}

	public static boolean isMac(String mac) {
		return mac.matches(MAC_REGEX);
	}

	public static boolean isNum(String num) {
		return num.matches(NUM_REGEX);
	}

	public static boolean isLetter(String letter) {
		return letter.matches(LETTER_REGEX);
	}

	public static boolean isNumOrletter(String numOrLetter) {
		return numOrLetter.matches(NUM_OR_LETTER_REGEX);
	}

	public static boolean isNumAndletter(String numAndLetter) {
		return numAndLetter.matches(NUM_AND_LETTER_REGEX);
	}

	public static boolean isMobile(String phone) {
		return phone.matches(PHONE_REGEX);
	}

	public static boolean isEmail(String email) {
		return email.matches(EMAIL_REGEX);
	}
}
