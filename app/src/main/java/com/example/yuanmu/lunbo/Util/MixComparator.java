package com.example.yuanmu.lunbo.Util;

import java.util.Comparator;

/**
 * @author 怨幕
 * 混合排序工具
 */
public class MixComparator implements Comparator<String> {
	/* 
	 * 排序
	 */
	public int compare(String o1, String o2) {
		if (PinYinUtil.isEmpty(o1) && PinYinUtil.isEmpty(o2))
			return 0;
		if (PinYinUtil.isEmpty(o1))//
			return -1;
		if (PinYinUtil.isEmpty(o2))
			return 1;
		String str1 = "";
		String str2 = "";
		try {
			str1 = (o1.toUpperCase()).substring(0, 1);
			str2 = (o2.toUpperCase()).substring(0, 1);
		} catch (Exception e) {
		}
		if (PinYinUtil.isLetter(str1) && PinYinUtil.isLetter(str2)) { // 字母
			return str1.compareTo(str2);
		} else if (PinYinUtil.isNumeric(str1) && PinYinUtil.isLetter(str2)) { // 数字字母
			return 1;
		} else if (PinYinUtil.isNumeric(str2) && PinYinUtil.isLetter(str1)) {
			return -1;
		} else if (PinYinUtil.isNumeric(str1) && PinYinUtil.isNumeric(str2)) { // 数字数字
			if (Integer.parseInt(str1) > Integer.parseInt(str2)) {
				return 1;
			} else {
				return -1;
			}
		} else if (PinYinUtil.isAllLetter(str1)
				&& (!PinYinUtil.isAllLetter(str2))) { // 数字字母 其他字符
			return -1;
		} else if ((!PinYinUtil.isAllLetter(str1))
				&& PinYinUtil.isLetter(str2)) {
			return 1;
		} else {
			return 1;
		}
	}
}