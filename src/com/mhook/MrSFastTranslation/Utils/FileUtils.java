package com.mhook.MrSFastTranslation.Utils;
import java.io.*;

public class FileUtils
{
	
	public static String ReadFile(String filepath){
		
		return ReadFile(filepath,"utf-8");
		
	}
	
	//读取文件
	
	public static String ReadFile(String filepath,String encoding){
		
		File file=new File(filepath);
		
		InputStreamReader reader = null;

		StringWriter writer = new StringWriter();

		try {

			if (encoding == null || "".equals(encoding.trim())) {

				reader = new InputStreamReader(new FileInputStream(file), encoding);

			} else {

				reader = new InputStreamReader(new FileInputStream(file));

			}

//将输入流写入输出流

			int DEFAULT_BUFFER_SIZE = 1024;//这个是由于有错误，又补上去的，但是应该不是不是这样改的

			char[] buffer = new char[DEFAULT_BUFFER_SIZE];

			int n = 0;

			while (-1 != (n = reader.read(buffer))) {

				writer.write(buffer, 0, n);

			}

		} catch (Exception e) {

			e.printStackTrace();

			return "";

		} finally {

			if (reader != null)

				try {

					reader.close();

				} catch (IOException e) {

					e.printStackTrace();

				}

		}

//返回转换结果

		if (writer != null)

			return writer.toString();

		else return "";
		
	}
	
	//不覆盖写入
	
	public static boolean WriteFileNoCover(String filepath,String str){
		
		return WriteFile(filepath,str,false);
		
		}
		
		//覆盖写入
		
	public static boolean WriteFileCover(String filepath,String str){

		return WriteFile(filepath,str,true);

	}
	
	
	//写入文件
	
	static boolean WriteFile(String filepath,String str,boolean iscover){
		
		boolean flag = true;

		BufferedReader bufferedReader = null;

		BufferedWriter bufferedWriter = null;

		try {

			File distFile = new File(filepath);

			if (!distFile.getParentFile().exists()) distFile.getParentFile().mkdirs();

			bufferedReader = new BufferedReader(new StringReader(str));

			bufferedWriter = new BufferedWriter(new FileWriter(distFile,!iscover));

			char buf[] = new char[1024]; //字符缓冲区

			int len;

			while ((len = bufferedReader.read(buf)) != -1) {

				bufferedWriter.write(buf, 0, len);

			}

			bufferedWriter.flush();

			bufferedReader.close();

			bufferedWriter.close();

		} catch (IOException e) {

			e.printStackTrace();

			flag = false;

			return flag;

		} finally {

			if (bufferedReader != null) {

				try {

					bufferedReader.close();

				} catch (IOException e) {

					e.printStackTrace();

				}

			}

		}

		return flag;

	
		
	}
}
