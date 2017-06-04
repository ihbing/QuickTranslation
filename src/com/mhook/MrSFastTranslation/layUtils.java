package com.mhook.MrSFastTranslation;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.widget.TableLayout.*;
import android.graphics.*;
import android.graphics.drawable.*;
import com.mhook.MrSFastTranslation.Utils.*;

public class layUtils
{
	//翻译窗口布局
	public static TextView getFyView(Context con){
		TextView mTextView=new TextView(con);
		mTextView.setTextSize(15);
		mTextView.setPadding(50,40,50,40);
		mTextView.setTextColor(Color.BLACK);
		GradientDrawable adImageBackground = new GradientDrawable();
		adImageBackground.setShape(GradientDrawable.RECTANGLE);
		adImageBackground.setColor(Color.argb(219,238,238,238));
		float[] radios={30,30,30,30,30,30,30,30};
		adImageBackground.setCornerRadii(radios);
		mTextView.setBackground(adImageBackground);
		mTextView.setLayoutParams(new LinearLayout.LayoutParams(
									  LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
									  Utils.printf("创建翻译窗口成功");
									  
									  return mTextView;
		
	}
	//列表左边布局
public static View getLeftView(final Context con){
	TextView mTextView=new TextView(con);
	mTextView.setLayoutParams(new LinearLayout.LayoutParams(
								  LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	mTextView.setText("转韩语");
	mTextView.setTextSize(10);
	mTextView.setTextColor(Color.BLACK);
	mTextView.setPadding(30,10,30,10);
	mTextView.setGravity(Gravity.CENTER_HORIZONTAL);
	GradientDrawable adImageBackground = new GradientDrawable();
    adImageBackground.setShape(GradientDrawable.RECTANGLE);
    adImageBackground.setColor(Color.argb(219,238,238,238));
	float[] radios={30,30,0,0,0,0,30,30};
	adImageBackground.setCornerRadii(radios);
	mTextView.setBackground(adImageBackground);

	mTextView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View p1)
			{android.content.ClipboardManager clip=(android.content.ClipboardManager)con.getSystemService(Context.CLIPBOARD_SERVICE);
			
			clip.setPrimaryClip(ClipData.newPlainText("ko",clip.getPrimaryClip().getItemAt(0).getText()));
			
				// TODO: Implement this method
			}
		});
	mTextView.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View p1, MotionEvent p2)
			{GradientDrawable adImageBackground = new GradientDrawable();
				adImageBackground.setShape(GradientDrawable.RECTANGLE);
				float[] radios={30,30,0,0,0,0,30,30};
				adImageBackground.setCornerRadii(radios);
				switch(p2.getAction()){
					case p2.ACTION_DOWN:
						adImageBackground.setColor(Color.argb(219,200,200,200));
                        p1.setBackground(adImageBackground);
						break;
					case p2.ACTION_UP:

						adImageBackground.setColor(Color.argb(219,238,238,238));
                        p1.setBackground(adImageBackground);
						break;
					default :
						break;
				}
				// TODO: Implement this method
				return false;
			}
		});
		
		Utils.printf("转韩语窗口创建成功");
		
	return mTextView;
	
}
	public static View getRightView(final Context con){
		TextView mTextView=new TextView(con);
		mTextView.setLayoutParams(new LinearLayout.LayoutParams(
									  LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		mTextView.setText("转日语");
		mTextView.setTextSize(10);
		mTextView.setTextColor(Color.BLACK);
		mTextView.setPadding(30,10,30,10);
		mTextView.setGravity(Gravity.CENTER_HORIZONTAL);
		GradientDrawable adImageBackground = new GradientDrawable();
		adImageBackground.setShape(GradientDrawable.RECTANGLE);
		adImageBackground.setColor(Color.argb(219,238,238,238));
		float[] radios={0,0,30,30,30,30,0,0};
		adImageBackground.setCornerRadii(radios);
		mTextView.setBackground(adImageBackground);
		mTextView.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{android.content.ClipboardManager clip=(android.content.ClipboardManager)con.getSystemService(Context.CLIPBOARD_SERVICE);
					
				clip.setPrimaryClip(ClipData.newPlainText("ja",clip.getPrimaryClip().getItemAt(0).getText()));
				
					// TODO: Implement this method
				}
			});
		mTextView.setOnTouchListener(new OnTouchListener(){

				@Override
				public boolean onTouch(View p1, MotionEvent p2)
				{GradientDrawable adImageBackground = new GradientDrawable();
					adImageBackground.setShape(GradientDrawable.RECTANGLE);
					float[] radios={0,0,30,30,30,30,0,0};
					adImageBackground.setCornerRadii(radios);
					switch(p2.getAction()){
						case p2.ACTION_DOWN:
							adImageBackground.setColor(Color.argb(219,200,200,200));
							p1.setBackground(adImageBackground);
							break;
						case p2.ACTION_UP:
							
							adImageBackground.setColor(Color.argb(219,238,238,238));
							p1.setBackground(adImageBackground);
							break;
						default :
							break;
					}
					// TODO: Implement this method
					return false;
				}
			});
			
		Utils.printf("转日语窗口创建成功");
			
		return mTextView;

	}
//设置背景布局
	public static View addlinearLay(Context con){
		LinearLayout mLl=new LinearLayout(con);
		mLl.setLayoutParams(new LinearLayout.LayoutParams(
								LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		mLl.setGravity(Gravity.CENTER);
		mLl.setOrientation(LinearLayout.HORIZONTAL);
		mLl.setBackgroundColor(Color.TRANSPARENT);
		mLl.addView(getLeftView(con));
		mLl.addView(getRightView(con));
		
		return mLl;
	}
}
