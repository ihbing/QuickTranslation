package com.mhook.MrSFastTranslation;
import android.app.*;
import android.os.*;
import android.widget.*;
import android.content.*;
import android.widget.CompoundButton.*;
import android.widget.AdapterView.*;
import android.view.*;
import java.util.*;
import android.content.pm.*;
import com.mhook.MrSFastTranslation.Utils.SPUtils;
import com.mhook.MrSFastTranslation.Utils.Utils;
import com.mhook.MrSFastTranslation.Utils.*;
import android.text.*;
import android.widget.SearchView.*;
import android.graphics.*;
import android.widget.SeekBar.*;
import java.io.*;
import android.view.inputmethod.*;

public class SettingActivity extends Activity
{
	public static Activity mActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		
		mActivity=this;
		
		if((System.currentTimeMillis()-AppUtils.GetLastUpdateTime(mActivity))<20000){
			
			AppUtils.ClearSelfDataAll(mActivity);
			
		}
		new SPUtils().put(mActivity,"ok","");
		
		setTheme( (boolean)new SPUtils().get(mActivity,"lighttheme",false)?android.R.style.Theme_Holo:android.R.style.Theme_Holo_Light);
		
		setContentView(R.layout.mainsetting);

		initID();
	}
	
	//初始化设置
	
	TextView modeDescription,apkDescription,checken;
	
	public void initID(){
		
		apkDescription=(TextView)findViewById(R.id.mainsettingTextView_apkDescription);
		
		checken=(TextView)findViewById(R.id.mainsetting_checken);
		
		final TextView TextView1=(TextView)findViewById(R.id.mainsettingTextView9),
		
		TextView2=(TextView)findViewById(R.id.mainsettingTextView10);
		
		final Switch mSwitch1=(Switch)findViewById(R.id.mainsetting_hideicon),
		
		mSwitch2=(Switch)findViewById(R.id.mainsettingSwitch2),
		
		mSwitch3=(Switch)findViewById(R.id.mainsettingSwitch3),
		
		mSwitch4=(Switch)findViewById(R.id.mainsettingSwitch4),
		
		mSwitch5=(Switch)findViewById(R.id.mainsettingSwitch5),
		
		mSwitch6=(Switch)findViewById(R.id.mainsettingSwitch6);
		
		final EditText mEditText2=(EditText)findViewById(R.id.mainsettingEditText2),
		
		mEditText3=(EditText)findViewById(R.id.mainsettingEditText3);
		
		final Button btn1=(Button)findViewById(R.id.mainsettingButton1),
		
		btn2=(Button)findViewById(R.id.mainsettingButton2),
		
		btn4=(Button)findViewById(R.id.mainsettingButton4),
		
		btn5=(Button)findViewById(R.id.mainsettingButton5),
		
		btn7=(Button)findViewById(R.id.mainsettingButton7);
		
		checken.setText((isat39()?"":"您需要在xposed框架里激活本模块！")+"");
		
		checken.setTextColor(isat39()?Color.BLACK:Color.RED);
		
		checken.setVisibility(isat39()?View.GONE:View.VISIBLE);
		
		apkDescription.setText(AllResources.apkIntroduction);
		
		final LinearLayout LinearLayout1=(LinearLayout)findViewById(R.id.mainsettingLinearLayout1),
		
		LinearLayout2=(LinearLayout)findViewById(R.id.mainsettingLinearLayout2);
		
		SeekBar SeekBar1=(SeekBar)findViewById(R.id.mainsettingSeekBar1);
		
		//初始化

		if((boolean)new SPUtils().get(mActivity,AllResources.ShareIsFirstRun,true)){

			SPUtils.clear(mActivity);

			new SPUtils().put(mActivity,AllResources.ShareIsFirstRun,false);

			new SPUtils().put(mActivity,AllResources.ShareWhiteCharacter,AllResources.WhiteCharacter);

			T.ShowToast(mActivity,"初始化完成");

		}
		
		if(!isat39()){
			
			mSwitch4.setEnabled(false);
			
			mSwitch5.setEnabled(false);
			
			mSwitch6.setEnabled(false);
			
			LinearLayout1.setEnabled(false);
			
			LinearLayout2.setEnabled(false);
			
			btn7.setEnabled(false);

		}
		
		OnCheckedChangeListener mOnCheckedChangeListener1=new OnCheckedChangeListener(){
			
			boolean iscancel=false;

			@Override
			public void onCheckedChanged(CompoundButton p1, final boolean p2)
			{
				
				if(p1==mSwitch1){
					
					new SPUtils().put(mActivity,"is_hide_icon",p2);
					
					if(p2){
						
						AppUtils.HideApkIcon(mActivity);
						
					}else{
						
						AppUtils.ShowApkIcon(mActivity);
						
					}

					T.ShowToast(mActivity,"图标已"+(p2?"隐藏":"显示"));
					
				}

				if(p1==mSwitch2){
					
					T.ShowToast(mActivity,"调试模式已"+(p2?"开启":"关闭"));

					new SPUtils().put(mActivity,"isbug",p2);
					
//					if(iscancel){
//
//						iscancel=false;
//
//						return;
//
//					}
//
//					DialogInterface.OnClickListener mOnClickListener1=new DialogInterface.OnClickListener(){
//
//						@Override
//						public void onClick(DialogInterface p3, int p4)
//						{
//
//							T.ShowToast(mActivity,"调试模式已"+(p2?"开启":"关闭"));
//
//							new SPUtils().put(mActivity,"isbug",p2);
//
//							Utils.execAdb("kill "+AppUtils.GetPid(SettingActivity.this,"com.android.systemui"));
//
//							// TODO: Implement this method
//						}
//					};
//
//					DialogInterface.OnCancelListener mOnCancelListener1=new DialogInterface.OnCancelListener(){
//
//						@Override
//						public void onCancel(DialogInterface p3)
//						{
//
//							iscancel=true;
//
//							mSwitch2.setChecked(new SPUtils().get(mActivity,"isbug",false));
//
//							// TODO: Implement this method
//						}
//					};
//
//					new AlertDialog.Builder(mActivity).setTitle("提示").setMessage((p2?"开启":"关闭")+"调试模式需要重启UI.").setPositiveButton(
//
//						"重启UI(需root)",mOnClickListener1).setOnCancelListener(mOnCancelListener1).create().show();
//
				}
				
				if(p1==mSwitch3){
					
					T.ShowToast(mActivity,"重启应用后生效");
					
					new SPUtils().put(mActivity,"lighttheme",p2);
					
				}
				
				if(p1==mSwitch4){
					
					
					
					new SPUtils().put(mActivity,AllResources.ShareXposedEnable,p2);
					
					T.ShowToast(mActivity,"模块已"+(p2?"启用":"关闭"));
					
//					if(iscancel){
//						
//						iscancel=false;
//						
//						return;
//						
//					}
//					
//					DialogInterface.OnClickListener mOnClickListener1=new DialogInterface.OnClickListener(){
//
//						@Override
//						public void onClick(DialogInterface p3, int p4)
//						{
//							
//							T.ShowToast(mActivity,"模块已"+(p2?"启动":"关闭"));
//							
//							new SPUtils().put(mActivity,"xposedenable",p2);
//							
//							Utils.execAdb("kill "+AppUtils.GetPid(SettingActivity.this,"com.android.systemui"));
//							
//							// TODO: Implement this method
//						}
//					};
//					
//					DialogInterface.OnCancelListener mOnCancelListener1=new DialogInterface.OnCancelListener(){
//
//						@Override
//						public void onCancel(DialogInterface p3)
//						{
//							
//							iscancel=true;
//							
//							mSwitch4.setChecked(new SPUtils().get(mActivity,"xposedenable",true));
//							
//							// TODO: Implement this method
//						}
//					};
//					
//						new AlertDialog.Builder(mActivity).setTitle("提示").setMessage((p2?"开启":"关闭")+"模块需要重启UI.").setPositiveButton(
//						
//						"重启UI(需root)",mOnClickListener1).setOnCancelListener(mOnCancelListener1).create().show();
//					
					}
				
				if(p1==mSwitch5){
					
					LinearLayout1.setVisibility(p2?View.VISIBLE:View.GONE);
					
					TextView1.setVisibility(p2?View.VISIBLE:View.GONE);
					
					btn7.setVisibility((mSwitch5.isChecked()|mSwitch6.isChecked())?View.VISIBLE:View.GONE);
					
					new SPUtils().put(mActivity,AllResources.ShareOpenWhiteRules,p2);
					
				}
				
				if(p1==mSwitch6){

					LinearLayout2.setVisibility(p2?View.VISIBLE:View.GONE);

					TextView2.setVisibility(p2?View.VISIBLE:View.GONE);

					btn7.setVisibility((mSwitch5.isChecked()|mSwitch6.isChecked())?View.VISIBLE:View.GONE);

					new SPUtils().put(mActivity,AllResources.ShareOpenBlackRules,p2);

				}
				
				
				// TODO: Implement this method
			}
		};
		
		//模块
		
		mSwitch4.setChecked(new SPUtils().get(mActivity,AllResources.ShareXposedEnable,true));
		
		mSwitch4.setOnCheckedChangeListener(mOnCheckedChangeListener1);
		
		//隐藏图标
		
		mSwitch1.setChecked(new SPUtils().get(mActivity,"is_hide_icon",false));
		
		mSwitch1.setOnCheckedChangeListener(mOnCheckedChangeListener1);
		
		//主题
		
		mSwitch3.setChecked(new SPUtils().get(mActivity,"lighttheme",false));
		
		mSwitch3.setOnCheckedChangeListener(mOnCheckedChangeListener1);
		
		
		//其他设置
		
		OnSeekBarChangeListener OnSeekBarChangeListener1=new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar p1, int p2, boolean p3)
			{
				
				T.ShowToast(mActivity,"字符\"你好,美丽的世界！\"显示时间:"+String.format("%.2f","你好,美丽的世界！".length()*((p2+10)/100f)));
				
				new SPUtils().put(mActivity,AllResources.ShareZoom,""+String.format("%.2f",((p2+10)/100f)));
				
				
				// TODO: Implement this method
			}

			@Override
			public void onStartTrackingTouch(SeekBar p1)
			{
				// TODO: Implement this method
			}

			@Override
			public void onStopTrackingTouch(SeekBar p1)
			{
				// TODO: Implement this method
			}
		};
		
		//显示比例
		
		SeekBar1.setProgress((int) (Float.parseFloat( new SPUtils().get(mActivity,AllResources.ShareZoom,"0.3").toString())*100));
		
		SeekBar1.setMax(90);
		
		SeekBar1.setOnSeekBarChangeListener(OnSeekBarChangeListener1);
		
		//开启白名单字符
		
		mSwitch5.setChecked(new SPUtils().get(mActivity,AllResources.ShareOpenWhiteRules,true));
		
		mSwitch5.setOnCheckedChangeListener(mOnCheckedChangeListener1);
		
		//开启黑名单字符
		
		mSwitch6.setChecked(new SPUtils().get(mActivity,AllResources.ShareOpenBlackRules,true));

		mSwitch6.setOnCheckedChangeListener(mOnCheckedChangeListener1);
		
		
		//初始化名单
			
		LinearLayout1.setVisibility(mSwitch5.isChecked()?View.VISIBLE:View.GONE);

		TextView1.setVisibility(mSwitch5.isChecked()?View.VISIBLE:View.GONE);
			
		LinearLayout2.setVisibility(mSwitch6.isChecked()?View.VISIBLE:View.GONE);

		TextView2.setVisibility(mSwitch6.isChecked()?View.VISIBLE:View.GONE);
		
		btn7.setVisibility((mSwitch5.isChecked()|mSwitch6.isChecked())?View.VISIBLE:View.GONE);
		
		//过滤规则
		
		mEditText2.setText(new SPUtils().get(mActivity,AllResources.ShareWhiteCharacter,AllResources.WhiteCharacter).toString());
		
		mEditText3.setText(new SPUtils().get(mActivity,AllResources.ShareBlackCharacter,AllResources.BlackCharacter).toString());

		OnClickListener mOnClickListener1=new OnClickListener(){

			@Override
			public void onClick(View p1)
			{

				if(p1==btn1){

					IntentUtils.OpenAlipay(mActivity,AllResources.alipayurl);

				}

				if(p1==btn2){

					IntentUtils.OpenMarket(mActivity,AllResources.pkgname);

				}

				if(p1==btn7){
					
					new SPUtils().put(mActivity,AllResources.ShareWhiteCharacter,mEditText2.getText().toString());
					
					new SPUtils().put(mActivity,AllResources.ShareBlackCharacter,mEditText3.getText().toString());
					
					if(mEditText2.getText().toString().equals("")){
						
						mEditText2.setText(AllResources.WhiteCharacter);
						
					}
					
					if(mEditText3.getText().toString().equals("")){

						mEditText3.setText(AllResources.BlackCharacter);

					}
					
					T.ShowToast(mActivity,"配置已保存！");
					
				}
				
				if(p1==btn4){
					
					IntentUtils.OpenQQGroup(mActivity,AllResources.QQgroup);
					
				}
				
				if(p1==btn5){
					
					if(mSwitch2.isChecked()){
						
						 String logstrs="";
						
						if(ShellUtils.checkRootPermission()){

							List<String> execlist=new ArrayList<String>();

							execlist.add("logcat -d -s fanyi -v long");

							ShellUtils.CommandResult CommandResult=null;

							CommandResult=  ShellUtils.execCommand(execlist,true,true);

							if(CommandResult.result==0){

								//System.out.println(CommandResult.result+"\n"+CommandResult.errorMsg+"\n"+CommandResult.successMsg.replaceAll("D/fanyi","\nfanyi"));

								logstrs=CommandResult.errorMsg+"\n"+ CommandResult.successMsg.replaceAll("[A-Z]/fanyi","\nfanyi");
								
							}

						}else{

							T.ShowToast(mActivity,"无root权限！！");

						}
						
						final String logstr=logstrs;
						
							DialogInterface.OnClickListener mOnClickListener2=new DialogInterface.OnClickListener(){

								@Override
								public void onClick(DialogInterface p1, int p2)
								{
									
									AppUtils.SetPrimaryClip(mActivity,logstr);
									
									T.ShowToast(mActivity,"已复制");
									
									// TODO: Implement this method
								}
							};
							
							DialogInterface.OnClickListener mOnClickListener3=new DialogInterface.OnClickListener(){

								@Override
								public void onClick(DialogInterface p1, int p2)
								{
									
									ShellUtils.CommandResult  CommandResult2= ShellUtils.execCommand("logcat -c",true);
									
									if(CommandResult2.result==0){
										
										T.ShowToast(mActivity,"日志清除完毕");
										
									}
									
								}
							};
							
							ScrollView ScrollView1=new ScrollView(mActivity);
							
						    ScrollView1.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
							
							ScrollView1.setBackgroundColor(Color.WHITE);
							
							TextView TextView3=new TextView(mActivity);
							
							TextView3.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
							
							TextView3.setText(logstr);
							
							TextView3.setTextSize(10);
							
							TextView3.setTextColor(Color.RED);
							
							TextView3.setBackgroundColor(Color.WHITE);
							
							TextView3.setPadding(5,5,5,5);
							
							ScrollView1.addView(TextView3);
							
						AlertUtils.DiyViewAlertWithTwoBtn(mActivity,"运行日志",getResources().getDrawable(R.drawable.icon),ScrollView1,"复制",mOnClickListener2,"清除",mOnClickListener3);
							
						}else{
						
						T.ShowToast(mActivity,"调试模式未开启");
						
					}
					
					}
					
				}

		};
		
		//开发者
		
		btn4.setOnClickListener(mOnClickListener1);
		
		btn5.setOnClickListener(mOnClickListener1);
		
		mSwitch2.setChecked(new SPUtils().get(mActivity,"isbug",false));
		
		mSwitch2.setOnCheckedChangeListener(mOnCheckedChangeListener1);
		
		//捐赠和评分
		
		//捐赠
		
		btn1.setOnClickListener(mOnClickListener1);
		
		//评分
		
		btn2.setOnClickListener(mOnClickListener1);
		
		//保存配置
		
		btn7.setOnClickListener(mOnClickListener1);
		
		//存储输入法包名
		
		String[] inputpkgnames=AppUtils.GetInputPkgName(mActivity);
		
		StringBuilder builder=new StringBuilder();
		
		for(String inputpkgname:inputpkgnames){
			
			builder.append(","+inputpkgname);
			
		}
		
		new SPUtils().put(mActivity,AllResources.ShareInputPkgName,builder.toString().replaceFirst(",",""));
		
	}
	
	//检查激活状态
	public boolean isat39(){
		return false;
	}
	
}
