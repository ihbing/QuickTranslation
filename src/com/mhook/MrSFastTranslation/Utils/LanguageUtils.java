package com.mhook.MrSFastTranslation.Utils;
import java.util.regex.*;

public class LanguageUtils
{
	
	/*
	 判断是日文

	 */
	public static boolean isjapen(String str)
	{
		StringBuffer sb=new StringBuffer();
		Matcher ma=Pattern.compile("[\u0800-\u4e00]").matcher(str);
		while (ma.find())
		{
			sb.append(ma.group());
		}
		System.out.println("日语:"+sb.length());
		System.out.println(str.length());
		if (sb.toString().length() >= ((float)str.length()/4))
		{return true;}
		else
		{


			return false;
		}
	}
	/*
	 判断是否是中文


	 */
	public static boolean ischina(String str)
	{System.out.println(str);
		StringBuffer sb=new StringBuffer();
		Matcher ma=Pattern.compile("[\u4E00-\u9FA5]").matcher(str);
		while (ma.find())
		{
			sb.append(ma.group());
		}
		System.out.println(sb.length());
		System.out.println(str.length());
		if (sb.toString().length() >= ((float)str.length()/4))
		{return true;}
		else
		{


			return false;
		}
	}
	
	
}
