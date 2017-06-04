package com.mhook.MrSFastTranslation.Utils;

import java.util.*;
import android.content.pm.*;
import android.content.*;
import android.net.*;
import android.provider.*;
import android.app.*;
import java.io.*;
import android.content.pm.PackageManager.*;
import android.view.inputmethod.*;
import java.lang.reflect.*;
import android.os.*;

public class AppUtils
{
	
	/**
     * 判断悬浮窗权限
     *
     * @param context 上下文
     */public static boolean isFloatWindowOpAllowed(Context context) {
        final int version = Build.VERSION.SDK_INT;

        if (version >= 19) {
            return checkOp(context, 24);//24表示悬浮窗权限在AppOpsManager中
        } else {

            //0x8000000表示1000000000000000000000000000如果&第28位所得值为1则该位置被置为1，悬浮窗打开

			return (context.getApplicationInfo().flags & 0x8000000) == 1<<27;

        }
    }

    protected static boolean checkOp(Context context, int op) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService("appops");
            try {
                Object object = invokeMethod(manager, "checkOp", op, Binder.getCallingUid(), getPackageName(context));
                return AppOpsManager.MODE_ALLOWED == (Integer) object;
            } catch (Exception e) {
				Utils.printf("CheckMIUI:"+e.toString());
            }
        } else {
            Utils.printf("Below API 19 cannot invoke!");
        }
        return false;
    }

	static String getPackageName(Context context) throws PackageManager.NameNotFoundException {
        PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        return pInfo.packageName;
    }

    static Object invokeMethod(AppOpsManager manager, String method, int op, int uid, String packageName){
        Class c = manager.getClass();
        try {
            Class[] classes = new Class[] {int.class, int.class, String.class};
            Object[] x2 = {op, uid, packageName};
            Method m = c.getDeclaredMethod(method, classes);
            return m.invoke(manager, x2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
	
	
	/*复制内容到剪切板*/
	
	public static void SetPrimaryClip(Context con,String str){
		
		ClipboardManager ClipboardManager1=(ClipboardManager)con.getSystemService(Context.CLIPBOARD_SERVICE);
		
		ClipboardManager1.setPrimaryClip(ClipData.newPlainText("text/plain",str));
		
	}

	/*判断权限*/
	public static boolean CheckPermission(Context ct,String pmn){

		PackageManager pm = ct.getPackageManager();
		boolean permission = (PackageManager.PERMISSION_GRANTED == 
			pm.checkPermission(pmn,ct.getApplicationInfo().packageName));
		return permission;
	}

	//判断网络状态
	public static boolean isNetworkAvailable(Context context) {   
        ConnectivityManager cm = (ConnectivityManager) context   
			.getSystemService(Context.CONNECTIVITY_SERVICE);   
        if (cm == null) {   
        } else {
			//如果仅仅是用来判断网络连接
			//则可以使用 cm.getActiveNetworkInfo().isAvailable();  
            NetworkInfo[] info = cm.getAllNetworkInfo();   
            if (info != null) {   
                for (int i = 0; i < info.length; i++) {   
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {   
                        return true;   
                    }   
                }   
            }   
        }   
        return false;   
    } 
	
	
	//获取uid
	public static String GetUid(Context mct,String pkgname){

		return GetAppInfo(mct,pkgname,3);
		
	}
	
	public static String GetUid(Context mct){

		return GetAppInfo(mct,mct.getPackageName(),3);

	}
	
	public static String GetPid(Context mct,String pkgname){

		return GetAppInfo(mct,pkgname,2);

	}
	
	public static String GetPid(Context mct){

		return GetAppInfo(mct,mct.getPackageName(),2);

	}
	
	public static String GetProcessName(Context mct,String pkgname){

		return GetAppInfo(mct,pkgname,1);

	}
	
	public static String GetProcessName(Context mct){

		return GetAppInfo(mct,mct.getPackageName(),1);

	}
	
	//获取pid
	
	public static String GetAppInfo( Context mct, String pkgname,int returntype){
		ApplicationInfo ai = null;
		try {
			PackageManager pm = mct.getPackageManager();
			ai = pm.getApplicationInfo(pkgname, PackageManager.GET_ACTIVITIES);

		} catch (Exception e) {
			e.printStackTrace();
		}
		ActivityManager am = (ActivityManager)mct. getSystemService(Context.ACTIVITY_SERVICE);

		List<ActivityManager.  RunningAppProcessInfo> run = am.getRunningAppProcesses();
		for (ActivityManager.RunningAppProcessInfo runningProcess : run) {
			if ((runningProcess.processName != null) && runningProcess.processName.equals(ai.processName)) {
				
				switch(returntype){
					
					case 1:
						
						return runningProcess.processName;
						
					case 2:

						return String.valueOf(runningProcess.pid);
						
					case 3:

						return String.valueOf(runningProcess.uid);
						
					
				}
				
			}
		}
		return "";
	}
	
	
	//获取应用信息

	//获取最后一次更新时间

	public static long GetLastUpdateTime(Context con){


		try
		{
			return  con.getPackageManager().getPackageInfo(con.getPackageName(), 0).lastUpdateTime;
		}
		catch (PackageManager.NameNotFoundException e)
		{

			return 0;

		}
	}


	//清除自身数据

	public static void ClearSelfShared_PrefsData(Context con){

		ClearSelfData(con,0);

	}

	public static void ClearSelfCacheData(Context con){

		ClearSelfData(con,1);

	}

	public static void ClearSelfFilesData(Context con){

		ClearSelfData(con,2);

	}

	public static void ClearSelfDataAll(Context con){

		ClearSelfData(con,0);

		ClearSelfData(con,1);

		ClearSelfData(con,2);

	}

	static void ClearSelfData(Context con ,int i){

		switch(i){

			case 0:
				//删除配置文件目录shared_prefs；
				//路径：/data/data/packagename
				String path =con. getFilesDir().getParent();
				deleteFolder(new File(path + "/shared_prefs"));

				break;

			case 1:

				//清空缓存目录；
				//路径 /data/data/packagename/cache
				File file_cache =con. getCacheDir();
				deleteFolder(file_cache);

				break;

			case 2:

				//清空file目录；
				//路径 /data/data/packagename/files
				File file_file =con. getFilesDir();
				deleteFolder(file_file);

				break;

			default:

				break;

		}

	}

	/**递归删除*/
    public static void deleteFolder(File file) {
        if (!file.exists())
            return;

        if (file.isDirectory()) {
            File files[] = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFolder(files[i]);
            }
        }
        file.delete();
    }

	//隐藏应用图标

	public static void HideApkIcon(Context con){

		con.getPackageManager().setComponentEnabledSetting(new ComponentName(con,con.getClass().getName()+"-alias"),PackageManager.COMPONENT_ENABLED_STATE_DISABLED,1);

	}

//显示图标

	public static void ShowApkIcon(Context con){

		con.getPackageManager().setComponentEnabledSetting(new ComponentName(con,con.getClass().getName()+"-alias"),PackageManager.COMPONENT_ENABLED_STATE_ENABLED,1);

	}

	/**
	 * 获取应用程序名称
	 */
	public static String getAppName(Context context)
	{
		try
		{
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
				context.getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return context.getResources().getString(labelRes);
		} catch (NameNotFoundException e)
		{
			return "未知";

		}

	}

	/**
	 * [获取应用程序版本名称信息]
	 * 
	 * @param context
	 * @return 当前应用的版本名称
	 */
	public static String getVersionName(Context context)
	{
		try
		{
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
				context.getPackageName(), 0);
			return packageInfo.versionName;

		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * [获取应用程序版本号信息]
	 * 
	 * @param context
	 * @return 当前应用的版本号
	 */
	public static int getVersionCode(Context context)
	{
		try
		{
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
				context.getPackageName(), 0);
			return packageInfo.versionCode;

		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return 1;
	}
	
//获取系统应用列表

	public static String GetSystemApks(Context con){
		StringBuffer mStringBuffer=new StringBuffer();
		PackageManager pm=con.getPackageManager();
		List<PackageInfo> pkginfo=pm.getInstalledPackages(0);
		for(int i=0;i<pkginfo.size();i++){
			PackageInfo PackageInfo=pkginfo.get(i);
			if ((PackageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0){

				mStringBuffer.append(","+PackageInfo.applicationInfo.packageName);
			}
	}
	
		return mStringBuffer.toString().replaceFirst(",","");
	}

	//根据intent获取包名

	public static String GetPkgName(Context con,Intent intent){

		StringBuffer mStringBuffer=new StringBuffer();

		List<ResolveInfo> mResolveInfo=con.getPackageManager().queryIntentActivities(intent,0);

		for(int i=0;i<mResolveInfo.size();i++){

			String str=mResolveInfo.get(i).activityInfo.packageName;

			mStringBuffer.append(","+str);

		}

		return mStringBuffer.toString().replaceFirst(",","");

	}

	//获取启动器包名

	public static String GetLauncherPkgName(Context con){

		Intent intent=new Intent(Intent.ACTION_MAIN);

		intent.addCategory(Intent.CATEGORY_HOME);

		return GetPkgName(con,intent);
	}

	//获取电话包名

	public static String GetPhonePkgName(Context con){

		Intent intent=new Intent(Intent.ACTION_DIAL);

		return GetPkgName(con,intent);

	}

	//获取短信包名

	public static String GetSmsPkgName(Context con){

		Uri uri = Uri.parse("smsto:");  

		Intent intent = new Intent(Intent.ACTION_SENDTO, uri);

		return GetPkgName(con,intent);

	}

	//获取相机包名

	public static String GetCameraPkgName(Context con){

		Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		return GetPkgName(con,intent);

	}
	
	//获取输入法包名
	
	public static String[] GetInputPkgName(Context con){

		InputMethodManager imm = (InputMethodManager)con. getSystemService(Context.INPUT_METHOD_SERVICE);
		List<InputMethodInfo> methodList = imm.getInputMethodList();

		String[] pkgnames=new String[methodList.size()];
		
		for(int i=0;i<methodList.size();i++){

		   pkgnames[i]=methodList.get(i).getPackageName();

		}
		
		return pkgnames;

	}

}


