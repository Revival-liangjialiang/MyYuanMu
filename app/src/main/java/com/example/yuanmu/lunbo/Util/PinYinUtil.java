package com.example.yuanmu.lunbo.Util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PinYinUtil {
	/**
	 * 汉语拼音转换工具
	 * 
	 * @param chinese
	 * @return
	 */
	public static String converterToPinYin(String chinese) {
		String pinyinString = "";
		char[] charArray = chinese.toCharArray();
		// 根据需要定制输出格式，我用默认的即可
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		try {
			// 遍历数组，ASC码大于128进行转换
			for (int i = 0; i < charArray.length; i++) {
				if (charArray[i] > 128) {
					// charAt(0)取出首字母
					if (charArray[i] >= 0x4e00 && charArray[i] <= 0x9fa5) { // 判断是否中文
						pinyinString += PinyinHelper.toHanyuPinyinStringArray(
								charArray[i], defaultFormat)[0].charAt(0);
					} else { // 不是中文的打上未知，所以无法处理韩文日本等等其他文字
						pinyinString += "?";
					}
				} else {
					pinyinString += charArray[i];
				}
			}
			return pinyinString;
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 把单个英文字母或者字符串转换成数字ASCII码
	 * 
	 * @param input
	 * @return
	 */
	public static int character2ASCII(String input) {
		char[] temp = input.toCharArray();
		StringBuilder builder = new StringBuilder();
		for (char each : temp) {
			builder.append((int) each);
		}
		String result = builder.toString();
		return Integer.parseInt(result);
	}


	/**
	 * 判断空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return "".equals(str.trim());
	}

	/**
	 * 判断数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("^[0-9]*$");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 判读字母
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isLetter(String str) {
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 判断字母数字混合
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isAllLetter(String str) {
		Pattern pattern = Pattern.compile("^[A-Za-z0-9]+$");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		} else {
			return true;
		}
	}
}
