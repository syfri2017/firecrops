package com.syfri.digitalplan.utils;

import com.alibaba.druid.sql.visitor.functions.Char;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Calculator {
	static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");

	public static void main(String[] args) {
		String sor = "第一个参数*（第二个参数+第三个参数）=结果";
		String sor1 = "第一个参数*第二个参数+第三个参数=结果";
		String sor2 = "（第一个参数*第二个参数）+第三个参数=结果";
		String sor3 = "（第一个参数*第二个参数）+（第三个参数*第四个参数）+第五个参数=结果";
		String sor4 = "（第一个参数/第二个参数）+（第三个参数/第四个参数）+第五个参数=结果";

		String[] params = { "11.11", "22.22", "33", "44", "55" };

		try {
			String newSs = string2Calculation(sor3, params);
			System.out.println(newSs);
			System.out.println("结果为：" + jse.eval(newSs.toString()));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 判断算是与参数个数是否相等
	 * 
	 * @param source
	 *            源包含中文的算式
	 * @param params
	 *            中文参数
	 * @return true：相等，false：不相等
	 */
	public static boolean isCalculationParams(String source, String[] params) {
		// 获得纯四则运算的字符（去掉等号）
		String ss = filterChinese(source).replace("=", "");
		//去掉（）括号
		String[] tRegStr = ss.replace("(", "").replace(")", "").split("");
		if (tRegStr.length + 1 != params.length) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 转换为数字的算式
	 * 
	 * @param source
	 *            源包含中文的算式
	 * @param params
	 *            中文参数
	 * @return
	 */
	public static String string2Calculation(String source, String[] params) {
		// 中文（）转换为英文的()
		if (source.contains("（")) {
			source = source.replace("（", "(");
		}
		if (source.contains("）")) {
			source = source.replace("）", ")");
		}
		if (!isCalculationParams(source, params)) {
			throw new RuntimeException("算式与参数个数不符");
		}
		// 获得纯四则运算的字符（去掉等号）
		String ss = filterChinese(source.trim()).replace("=", "");
		// 字符串转字符串数组
		String[] cr = ss.split("");
		// 返回结果初始化
		StringBuffer newSs = new StringBuffer();
		// 数字参数索引
		int cc = 0;
		// 包含括号的情况
		if (ss.contains("(") && ss.contains(")")) {
			for (int i = 0; i < cr.length; i++) {
				// 判断左括号位置
				if (((i > 0 && i + 1 < cr.length && "(".equals(cr[i + 1])) || "(".equals(cr[i]))) {
					newSs.append(cr[i]);
				} else
				// 判断右括号位置
				if (i > 0 && ")".equals(cr[i - 1])) {
					// 先加运算符在加数字
					newSs.append(cr[i]).append(params[cc]);
				} else {
					// 先加数字在加运算符
					newSs.append(params[cc]).append(cr[i]);
					cc++;
				}
			}
		}
		// 不包含括号的情况
		if (!ss.contains("(") && !ss.contains(")")) {
			for (int i = 0; i < cr.length; i++) {
				newSs.append(params[i]).append(cr[i]);
			}
			newSs.append(params[params.length - 1]);
		}
		return newSs.toString();
	}

	/**
	 * 过滤掉中文
	 * 
	 * @param str
	 *            待过滤中文的字符串
	 * @return 过滤掉中文后字符串
	 */
	public static String filterChinese(String str) {
		// 用于返回结果
		String result = str;
		boolean flag = isContainChinese(str);
		if (flag) {// 包含中文
			// 用于拼接过滤中文后的字符
			StringBuffer sb = new StringBuffer();
			// 用于校验是否为中文
			boolean flag2 = false;
			// 用于临时存储单字符
			char chinese = 0;
			// 5.去除掉文件名中的中文
			// 将字符串转换成char[]
			char[] charArray = str.toCharArray();
			// 过滤到中文及中文字符
			for (int i = 0; i < charArray.length; i++) {
				chinese = charArray[i];
				flag2 = isChinese(chinese);
				boolean flag3 = isEnglish(chinese+"");
				boolean flag4 = isNumeric(chinese+"");
				if (!(flag2||flag3||flag4)) {// 不是中日韩文字及标点符号
					sb.append(chinese);
				}
			}
			result = sb.toString();
		}
		return result;
	}

	/**
	 * 判定输入的是否是汉字
	 * 
	 * @param c
	 *            被校验的字符
	 * @return true代表是汉字
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}
	//判断是否为数字
	public static boolean isNumeric(String str){
		for (int i = str.length();--i>=0;){
			if (!Character.isDigit(str.charAt(i))){
				return false;
			}
		}
		return true;
	}
	/**
	 * 是否是英文
	 * @param charaString
	 * @return
	 */
	public static boolean isEnglish(String charaString){

		return charaString.matches("^[a-zA-Z]*");

	}

	/**
	 * 判断字符串中是否包含中文
	 * 
	 * @param str
	 *            待校验字符串
	 * @return 是否为中文
	 * @warn 不能校验是否为中文标点符号
	 */
	public static boolean isContainChinese(String str) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}
}
