package com.mhook.MrSFastTranslation;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.*;
import android.os.*;
import android.graphics.*;
import android.content.SharedPreferences.Editor;
import android.view.View.*;
import android.text.*;
import android.content.*;
import android.util.*;
import android.view.*;
import android.app.*;
import java.lang.reflect.*;
import android.content.pm.*;
import de.robv.android.xposed.*;
import android.widget.AdapterView.*;
import android.graphics.drawable.*;
import com.mhook.MrSFastTranslation.Utils.*;
import java.util.regex.*;
import java.math.*;

public class kqwToast implements OnTouchListener
{

    // 上下文
    private Context mContext;

    private static WindowManager mWindowManager;

    private static View mKqwToast;

    private WindowManager.LayoutParams mParams;

	/*
     * 构造方法
     *
     * @param context
     */
	 
    public kqwToast(Context context,int x,int y) {
		
		Utils.printf("进入悬浮窗处理阶段");
		
		closeKqwToast();
		
		if(null==context){
			
			Utils.printf("错误:上下文未知");
			
			return;
			
		}
		
		mContext = context;
		
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

        mParams = new WindowManager.LayoutParams();
        // 设置显示位置
        mParams.width = LayoutParams.WRAP_CONTENT;
        mParams.height = LayoutParams.WRAP_CONTENT;
        mParams.y =y;
		mParams.x=x;
        mParams.gravity = Gravity.TOP;
        mParams.flags =WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		mParams.format=PixelFormat.RGBA_8888;
		mParams.type=WindowManager.LayoutParams.TYPE_TOAST;
		//mParams.alpha=0.6f;
    }

    /**
     * 显示交易信息
     */
	 
    public boolean showKqwToast(String text) {
		if(mWindowManager==null){
			
			Utils.printf("错误:窗口管理器未知");
			
			return false;
			
			}
			
		if(null==mContext){

			Utils.printf("错误:上下文未知");

			return false;

		}
		
		strtext=text;
		
        mKqwToast = getView(mContext);
		
		if(null ==mKqwToast){
			
			Utils.printf("获取悬浮窗布局失败！");
			
			return false;
			
		}

		XmlUtils mXmlUtils1=new XmlUtils(AllResources.pkgname,AllResources.sharename);
		
		String zoom=mXmlUtils1.GetString(AllResources.ShareZoom,"0.3").toString();
		
		float showtime= ((float)(text.length())*(Float.parseFloat("".equals(  zoom)?"0.3":zoom)));
			
		BigDecimal BigDecimal1=new BigDecimal(showtime);
		
		showtime=BigDecimal1.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
		
		setDelay(showtime);
		
		mTextView.setText(text+AllResources.Tail+" 显示:"+(showtime)+"s");

		if(text.length()>100){

			mTextView.setTextSize(12);

		}
		
		mTextView.setOnTouchListener(this);
		
		if(mContext!=null){
			
        mWindowManager.addView(mKqwToast, mParams);
		
		return true;
		
		}
		
		return false;
		
    }
String strtext;
    
	
	/*
	设置窗口可移动
	*/
	
	int lastX, lastY;
	int paramX, paramY;
	int dx = 0,dy = 0;
	boolean isBemoved=false;
	long downtime=0,uptime=0;
	@Override
	public boolean onTouch(View p1, MotionEvent event)
	{
		switch (event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				isBemoved=false;
				downtime=System.currentTimeMillis();
				lastX = (int) event.getRawX();
				lastY = (int) event.getRawY();
				paramX = mParams.x;
				paramY = mParams.y;
			
				break;
			case MotionEvent.ACTION_MOVE:
				
			
				dx = (int) event.getRawX() - lastX;
				dy = (int) event.getRawY() - lastY;
				if(Math.abs(dy)>=40||Math.abs(dx)>=40){
					isBemoved=true;
				
				mParams.x = paramX + dx;
				mParams.y = paramY + dy;
		
				// 更新悬浮窗位置
				mWindowManager.updateViewLayout(mKqwToast, mParams);
				}
				break;
case MotionEvent.ACTION_UP:
	uptime=System.currentTimeMillis();
	if( uptime-downtime<300||  isBemoved){
		if(!isBemoved){
			
			Utils.printf("点击取消");
			
			closeKqwToast();
			
			break;
			
		}
		
		Utils.printf("移动中...x="+paramX+dx+"y="+paramY+dy);
		
		SaveTheCoordinates(mContext,paramX + dx,paramY + dy);
		
		isBemoved=false;
		
		break;
		
		}
		
		        Utils.show(mContext,"已复制");
		
				Utils.printf("长按复制完毕");
				
				android.content.  ClipboardManager clip=( android.content.  ClipboardManager)mContext.getSystemService(Context.CLIPBOARD_SERVICE);
					
				clip.setPrimaryClip(ClipData.newPlainText("fanyi",strtext));
					
				closeKqwToast();
	
	break;
	
		}
		
		return true;


		// TODO: Implement this method

	}
	
	
    /**
     * 关闭提示信息
     */
	 
    public static void closeKqwToast() {
		if(mHandler!=null){
			mHandler.removeCallbacks(rb_off);
			
		}
        try {
            if (null != mWindowManager && mKqwToast != null) {
                mWindowManager.removeView(mKqwToast);
				
            }
        } catch (Exception e) {
        }
    }
	
	/*
	设置显示时间
	*/
	
	static Handler mHandler=new Handler();
	static Runnable rb_off=new Runnable(){

		@Override
		public void run()
		{closeKqwToast();
			// TODO: Implement this method
		}
	};
	public synchronized void setDelay(float time){
		
		Utils.printf("显示时间:"+time);
		
		mHandler.postDelayed(rb_off, (int)(time*1000));
		
		
	}
	/*
	设置布局
	*/
	//翻译语言选择列表
	public void createFyList(TextView mlv){
		
		
		
		
		
	}
	
	TextView mTextView;
	public LinearLayout getView(final Context con){
		
		if(null==con){

			Utils.printf("错误:上下文未知,不能获取悬浮窗布局");

			return null;

		}
		
		LinearLayout mLinearLayout = new LinearLayout(con);


		mLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
										  LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

mLinearLayout.setGravity(Gravity.CENTER);
		mLinearLayout.setOrientation(LinearLayout.VERTICAL);
mLinearLayout.setBackgroundColor(Color.TRANSPARENT);
		mTextView =layUtils.getFyView(mContext);
		mLinearLayout.addView(mTextView);
		if(mContext instanceof Service){
			if(mContext.getPackageName().equals("com.mhook.MrSFastTranslation")){
				
				Utils.printf("显示转日韩按钮:否");
				
				return mLinearLayout;
			}
		}
		
		Utils.printf("显示转日韩按钮:是");
		
		if(null==mContext){

			Utils.printf("错误:上下文未知,不能获取布局");

			return null;

		}
		
mLinearLayout.addView(layUtils.addlinearLay(mContext));

		Utils.printf("全部窗口创建成功");
		
		return mLinearLayout;
		
	}
	
	
	/*
	 存储窗口坐标信息
	 */
	public void SaveTheCoordinates(Context context,int x,int y){
		
		if(null==context){

			Utils.printf("错误:上下文未知,不能保存坐标信息");

			return ;

		}
		
		SharedPreferences sp=context.getSharedPreferences(AllResources.sharename,Context.MODE_WORLD_READABLE);
		sp.edit().putInt("x",x).commit();
		sp.edit().putInt("y",y).commit();



	}
	
	
}
