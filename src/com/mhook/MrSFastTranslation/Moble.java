package com.mhook.MrSFastTranslation;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage.*;
import de.robv.android.xposed.callbacks.*;
import de.robv.android.xposed.*;
import java.util.regex.*;
import java.net.*;
import java.io.*;
import android.app.*;
import android.content.*;
import android.widget.*;
import android.os.*;
import android.content.pm.*;
import java.util.*;
import android.view.*;
import org.json.*;
import android.view.View.*;
import com.mhook.MrSFastTranslation.Utils.*;
import android.graphics.*;
import android.*;
import java.lang.reflect.*;
import android.inputmethodservice.*;
public class Moble implements IXposedHookLoadPackage
{   
	XSharedPreferences xsp_fanyi_mode;
	
	TextView textV;
	
	public static Context mContext;
	
	@Override
	public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable
	{		
		xsp_fanyi_mode = new XSharedPreferences(AllResources.pkgname, AllResources.sharename);

		if (lpparam.packageName.equals(AllResources.pkgname))
		{
			XC_MethodHook xm_hookmyself=new XC_MethodHook(){

				@Override
				protected void afterHookedMethod(MethodHookParam param) throws Throwable
				{
					param.setResult(true);
					
				}

			};
			XposedHelpers.findAndHookMethod(AllResources.pkgname+".SettingActivity", lpparam.classLoader, "isat39", xm_hookmyself);
		}
		
		XC_MethodHook XC_MethodHook3=new XC_MethodHook(){

			public void	beforeHookedMethod(XC_MethodHook.MethodHookParam param){
				
				Utils.printf("hook剪切板");
				
				Field Field1= XposedHelpers.findField(android.content.ClipboardManager.class,"mContext");

				Field1.setAccessible(true);

				try
				{
					mContext = (Context)Field1.get(param.thisObject);
				}
				catch (IllegalAccessException e)
				{}
				catch (IllegalArgumentException e)
				{}

				if(mContext==null){

					Utils.printf("错误:上下文未知");

					return;

				}
				
				if(System.currentTimeMillis()-(long)new SPUtils().get(mContext,"sys_tim",0L)<500){ 

					new SPUtils().put(mContext,"sys_tim",System.currentTimeMillis());

					return;

				}

				new SPUtils().put(mContext,"sys_tim",System.currentTimeMillis());

				Utils.printf("-------开始获取剪切板内容-------");

				Utils.printf("-------当前版本"+AllResources.ApkVersion+"--------");
				
				if(!(param.args[0] instanceof ClipData)){
					
					Utils.printf("不是clipdata");
					
					return;
					
				}
				
				Utils.printf("是clipdata");

				android.content.ClipData clipdata=(android.content.ClipData)param.args[0];
				if (clipdata == null){

					Utils.printf("错误:剪切板未知");

					return;

				}
				
				Utils.printf("剪切板存在！,数据为:"+clipdata.toString());
				
				String  label=((null==clipdata.getDescription())?"unkown":((null==clipdata.getDescription().getLabel())?"unkown":(clipdata.getDescription().getLabel().toString())));
				
				Utils.printf("label:"+label);
				
				if("fanyi".equals(label))return;
				
				String tolanguage=(label.equals("ja")|label.equals("ko"))?label:"";
				
				Utils.printf("language:"+tolanguage);

				String clip_str=clipdata.getItemAt(0).getText().toString();

	            Utils.printf("剪切板内容:"+clip_str);

				if(clip_str.equals("")){ 

					Utils.printf("错误:剪切板无内容");  

					return;

				}

				Utils.printf("当前包名:"+mContext.getPackageName());
				
				Bundle Bundle1=new Bundle();
				
				Bundle1.putString("tolanguage",tolanguage);
				
				Bundle1.putString("data",clip_str);
				
					BroadcastUtils.SendBroadcast(mContext,AllResources.ReceiverAction,Bundle1);

					Utils.printf("发送完毕！");
					
					}
				
		};

		XposedBridge.hookAllMethods(Class.forName("android.content.ClipboardManager"),"setPrimaryClip", XC_MethodHook3);

		XC_MethodHook XC_MethodHook2=new XC_MethodHook(){
			
			public void beforeHookedMethod(XC_MethodHook.MethodHookParam param){
				
				if(param.thisObject instanceof Context){
					
					mContext=(Context)param.thisObject;
					
					final BroadcastReceiver BroadcastReceiver1=new BroadcastReceiver(){
						
						@Override
						public void onReceive(Context p1, Intent p2)
						{
							
							XmlUtils XmlUtils1=new XmlUtils(AllResources.pkgname,AllResources.sharename);
							
							if(!XmlUtils1.GetBoolean(AllResources.ShareXposedEnable,true)){

								Utils.printf("模块未启用!");

								return;
								
							}
							
							if(null==p1){
								
								Utils.printf("接受者上下文未知");
								
								return;
								
							}
										
							synchronized(this){

								long systemtime=System.currentTimeMillis();

								if(systemtime-Long.parseLong(new SPUtils().get(p1,"systemtime",0L).toString())<500){

									return;

								}
								
								new SPUtils().put(p1,"systemtime",systemtime);

							}
						   
							Utils.printf("---接收成功！---");
							
							Bundle Bundle1=p2.getBundleExtra("bundle");
							
							String data=Bundle1.getString("data"),
							
								tolanguage=Bundle1.getString("tolanguage");
							
							if(p1!=null){
								
								kqwToast.closeKqwToast();
								
								runkuaiyi(data,tolanguage,this,p1);
								
								return;
								
							}
							
							Utils.printf("错误:翻译结果显示失败！");
							
							
							// TODO: Implement this method
						}
						
					};
					
					if(!AppUtils.CheckPermission(mContext,android.Manifest.permission.INTERNET)){

						//T.ShowToast(mContext,"系统ui无联网权限！");

						Utils.printf("错误:当前输入法无联网权限！包名:"+mContext.getPackageName());

						return;

					}
					
					if(!AppUtils.isFloatWindowOpAllowed(mContext)){
						
						Utils.printf("错误:当前输入法悬浮窗权限未开启！包名:"+mContext.getPackageName());
						
					}

//					if(!AppUtils.CheckPermission(mContext,android.Manifest.permission.SYSTEM_ALERT_WINDOW)){
//
//						//T.ShowToast(mContext,"系统ui无悬浮窗权限！包名:"+mContext.getPackageName());
//
//						Utils.printf("错误:当前输入法无悬浮窗权限！包名:"+mContext.getPackageName());
//
//						return;
//
//					}
					
					BroadcastUtils.Receiver(mContext,AllResources.ReceiverAction,BroadcastReceiver1);
					
					Utils.printf("创建完毕！");
					
				}
				
			}
			
			
		};
		
		//注册广播
		
		String[] inputpkgnames=xsp_fanyi_mode.getString(AllResources.ShareInputPkgName,"").split(",");
		
		for(String inputpkgname:inputpkgnames){
	
		if(lpparam.isFirstApplication&lpparam.packageName.equals(inputpkgname)&lpparam.processName.equals(inputpkgname)){
			
			XposedBridge.hookAllMethods(Application.class,"onCreate",XC_MethodHook2);
		
		}
		
		}
		
		/*
		 修复toast类型悬浮窗在api19以下不能点击的问题

		 */
		XC_MethodHook XC_MethodHook_hookpermission=new XC_MethodHook(){

			@Override
			public void beforeHookedMethod(XC_MethodHook.MethodHookParam param)
			{
				if (param.args[0] instanceof WindowManager.LayoutParams)
				{
					WindowManager.LayoutParams lyparams=(WindowManager.LayoutParams)param.args[0];
					if (lyparams.type == WindowManager.LayoutParams.TYPE_TOAST)
					{
						lyparams.type = 2;
					}
				}

			}

		};


		Class<?> clazz_hookpermission=Class.forName("com.android.internal.policy.impl.PhoneWindowManager");

		XposedBridge.hookAllMethods(clazz_hookpermission, "adjustWindowParamsLw", XC_MethodHook_hookpermission);

		
	
	}

	public void runkuaiyi(String strs, String toLanguage, final BroadcastReceiver broadcastreceiver, final Context ct){
		
		Utils.printf("整理前:"+strs);
		
		final String strs1=  strs.replaceAll("(?<=[a-z])(?=[A-Z][a-z])", " ").replaceAll("_", " ").replaceAll("'", "’").replaceAll("\"", "“");
		
        Utils.printf("整理后:"+strs1);
		
		//清除以前问题
		
	final	String   str=strs1.replaceAll("\0","");
	
		XmlUtils mXmlUtils1=new XmlUtils(AllResources.pkgname,AllResources.sharename);

		//黑名单处理
		
		if(mXmlUtils1.GetBoolean(AllResources.ShareOpenBlackRules,true)){

			Utils.printf("黑名单已开启");

			if (str.replaceAll(mXmlUtils1.GetString(AllResources.ShareBlackCharacter,AllResources.BlackCharacter).toString(), "").length()!=str.length()){

				Utils.printf("错误:符合黑名单");
				
				return ;

			}

		}else{

			Utils.printf("黑名单已关闭！");

		}
	
	//白名单处理
	
		if(mXmlUtils1.GetBoolean(AllResources.ShareOpenWhiteRules,true)){
			
			Utils.printf("白名单已开启");

		if (!str.replaceAll(mXmlUtils1.GetString(AllResources.ShareWhiteCharacter,AllResources.WhiteCharacter).toString(), "").equals("")){
			
			Utils.printf("错误:不符合白名单");
			
			return ;
			
		}
		
		}else{
			
			Utils.printf("白名单已关闭！");
			
		}
		
		final int x= new SPUtils().get(ct,"x", 0);
		
		final int y= new SPUtils().get(ct,"y", 120);
		
	if(toLanguage.equals("")|toLanguage==null){
		
		toLanguage=(LanguageUtils.isjapen(str)?"zh-CN":(LanguageUtils.ischina(str) ?"en": "zh-CN"));
		
	}
	
		Utils.printf("翻译后语言:"+toLanguage);
		
		final  String url="http://ainixiang.cn/fanyi/?sl=auto&tl=" +toLanguage + "&q=" + URLEncoder.encode(str);
		
		if(url.length()>1951){
			
			Utils.printf("错误:要翻译内容过长");
			
			return ;
			
		}
		
		new Thread(new Runnable(){

				@Override
				public void run()
				{ 
				
				synchronized(this){
				
					//修改尾巴

					AllResources.Tail="\n----来自谷歌翻译！";
				
				Utils.printf("新建翻译线程成功！");
				
					String fanyikey[][]={{"fanyissss","17056605"},{"fanyidjjdk","542884696"},{"fanyidjjdkhd","2021146716"},{"fanyidjjdkhdjx","996772878"},{"fanyidjjdkghhdjx","146039213"},{"hjggghg","944085764"},{"hjggghghh","1028323076"},{"hjggghghhhhg","1228861981"},{"hjgbkkgdd","888960650"},{"hjgbkkgddhjgg","577043732"}};
					
					int random=new Random().nextInt(10);
					
					String keyfrom=fanyikey[random][0];
					
					String key=fanyikey[random][1];
					
					String html="";
					
					html = htmlyuan.getHtml(url);

					try
					{
						JSONObject jsonobj=new JSONObject(html);
						html = new JSONArray(jsonobj.get("tran").toString()).get(0).toString();
						
						Utils.printf("返回谷歌翻译结果:"+html);
						
					}catch (JSONException e){
						
						Utils.printf("获取谷歌翻译结果失败:"+e);

					}

					if (html.equals("") || html.startsWith("{\"tran\":\"<!DOCTYPE html>")){
						
						Utils.printf("谷歌翻译:失败");
						
						html = htmlyuan.getHtml("http://fanyi.youdao.com/openapi.do?keyfrom=" + keyfrom + "&key=" + key + "&type=data&doctype=json&version=1.1&only=translate&q=" + URLEncoder.encode(str), "utf-8");
						
						Utils.printf("有道翻译结果:"+html);
						
						
					
					if (html.equals("")){
						
						Utils.printf("错误:获取有道翻译结果失败");
						
						return;
						
						}
						
						//修改尾巴

						AllResources.Tail="\n----来自有道翻译！";
						
					try
					{
						
						JSONObject jsonobj=new JSONObject(html);
						if(jsonobj.getInt("errorCode")!=0){
							
							Utils.printf("有道翻译错误代码:"+jsonobj.get("errorCode"));
							
							return;
							
							}
							
						JSONArray jsonarray=jsonobj.getJSONArray("translation");

						StringBuffer strbutt=new StringBuffer();
						for (int i= 0;i <  jsonarray.length();i++)
						{
							strbutt.append(jsonarray.get(i).toString());
							if (i != (jsonarray.length() - 1))
							{
								strbutt.append("\n");
							}

						}
						html = strbutt.toString();
						
						Utils.printf("有道翻译结果:"+html);
						
						//修改尾巴
						
						AllResources.Tail="\n----来自有道翻译！";

					}
					catch (JSONException e)
					{
						
						Utils.printf("获取有道翻译结果异常:"+e);
						
					}
					
					}
					
					if (!html.equals(""))
					{
						
						final String str1=html;
						
						if (!str1.trim().equals(str.trim())){
							
							Looper.prepare();
							Handler handler=new Handler(Looper.getMainLooper());
							handler.post(new Runnable(){
									public void run()
									{
										
										kqwToast kqwToast1=new kqwToast(ct,x,y);
										
										kqwToast1.showKqwToast(Utils.UnicodeToString(str1));
											
											Utils.printf("--------翻译完成-------");
											
											Utils.printf("当前输入法包名:"+ct.getPackageName());
											
											broadcastreceiver.abortBroadcast();
											
											return;
											
											}

								});
							Looper.loop();
						}

					}
					
					}

				}
			}).start();
			
	}

}
