package com.mhook.MrSFastTranslation;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.mhook.MrSFastTranslation.Utils.*;

/**
 * 获取HTML数据
 * 
 * @author David
 * 
 */
public class htmlyuan {

	public static String getHtml(String path,String encode)  {

		try{
			// 通过网络地址创建URL对象
			URL url = new URL(path);
			if(url==null){
				Utils.printf("未知的url");
			}
			// 根据URL
			// 打开连接，URL.openConnection函数会根据URL的类型，返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设定URL的请求类别，有POST、GET 两类
			conn.setRequestMethod("GET");
			//设置从主机读取数据超时（单位：毫秒）
			conn.setConnectTimeout(5000);
			//设置连接主机超时（单位：毫秒）
			conn.setReadTimeout(5000);
			// 通过打开的连接读取的输入流,获取html数据
			InputStream inStream = conn.getInputStream();
			// 得到html的二进制数据
			byte[] data = readInputStream(inStream);
			// 是用指定的字符集解码指定的字节数组构造一个新的字符串
			String html = new String(data, encode);
			return html;
		}catch(Exception e){
			Utils.printf("请求异常:"+e);
			return "";
		}

    }


    public static String getHtml(String path)  {
return getHtml(path,"UTF-8");

    }

    /**
     * 读取输入流，得到html的二进制数据
     * 
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

}

