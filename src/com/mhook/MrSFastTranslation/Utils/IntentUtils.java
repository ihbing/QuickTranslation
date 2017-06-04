package com.mhook.MrSFastTranslation.Utils;

import android.content.*;
import android.net.*;
import android.content.pm.*;
import java.util.*;

public class IntentUtils
{
	
	public static void OpenQQPerson(Context con,String qqpersonnum){

		OpenQQ(con,qqpersonnum,0);

	}
	
	public static void OpenQQGroup(Context con,String qqgroupnum){
		
		OpenQQ(con,qqgroupnum,1);
		
	}
	
	public static void OpenQQ(Context con,String qqnum,int type){
		
		OpenIntent(con,Intent.ACTION_VIEW,0,"mqqapi://card/show_pslcard?src_type=internal&version=1&uin="+qqnum+"&card_type="+(type==0?"person":"group")+"&source=external","");
		
	}
	
	public static void OpenUrl(Context con,String url){

		OpenIntent(con,Intent.ACTION_VIEW,0,url,"");


	}

	public static void OpenShare(Context con,String msg){

		Intent sendIntent = new Intent();

		sendIntent.setAction(Intent.ACTION_SEND);

		sendIntent.putExtra(Intent.EXTRA_TEXT,msg);

		sendIntent.setType("text/plain");

		con.startActivity(Intent.createChooser(sendIntent, "分享"));  

	}

	public static void OpenMarket(Context con,String pkgname){

		OpenIntent(con,Intent.ACTION_VIEW,0,"market://details?id="+pkgname,"");

	}

	public static void OpenAlipay(Context con,String path){

		OpenIntent(con,Intent.ACTION_VIEW,Intent.FLAG_ACTIVITY_NEW_TASK,"alipayqr://platformapi/startapp?saId=10000007&clientVersion=3.7.0.0718&qrcode="+path,"");

	}

	public static void OpenBrowser(Context con,String path){

		OpenIntent(con,Intent.ACTION_VIEW,0,path,"");

	}

	public static void OpenVideoPlayer(Context con,String videopath){

		OpenIntent(con,Intent.ACTION_VIEW,0,videopath,"video/*");

	}


	public static void OpenIntent(Context con,String action,int flags,String path,String type){

		Intent intent = new Intent(action);

		if(flags!=0){

			intent.setFlags(flags);

		}

		if(type.equals("")){

			intent.setData(Uri.parse(path));

		}else{

			intent.setDataAndType(Uri.parse(path), type);

		}

		try{

			con.startActivity(intent);

		}catch(Exception e){

			T.ShowToast(con,"不存在可供跳转的应用！");

		}

	}
	
}

