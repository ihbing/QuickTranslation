package com.mhook.MrSFastTranslation.Utils;

import android.content.*;
import android.os.*;

public class BroadcastUtils
{

	//注册广播

	public static void Receiver(Context con,  String action,  BroadcastReceiver broadcastreceiver){

		IntentFilter IntentFilter1=new IntentFilter();

		IntentFilter1.addAction(action);

		IntentFilter1.setPriority(Integer.MAX_VALUE);

		con.registerReceiver(broadcastreceiver,IntentFilter1);

	}

	//发送广播
	
	public static void SendBroadcast(Context con,String action,String data){

		Bundle Bundle1=new Bundle();
		
		Bundle1.putString("data",data);
		
		SendBroadcast(con,action,Bundle1);
		
	}

	public static void SendBroadcast(Context con,String action,Bundle bundle){

		Intent Intent1=new Intent();

		Intent1.setAction(action);
		
		Intent1.putExtra("bundle",bundle);

		con.sendBroadcast(Intent1);

	}
	
	}
	
