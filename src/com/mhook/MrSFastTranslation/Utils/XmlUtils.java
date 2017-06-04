package com.mhook.MrSFastTranslation.Utils;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import com.mhook.MrSFastTranslation.*;

public class XmlUtils
{

	private static String filepath="/sdcard/config.xml";

	public XmlUtils(){

		filepath="/data/data/"+AllResources.pkgname+"/shared_prefs/"+AllResources.sharename+".xml";

	}

	public XmlUtils(String pkgname,String sharename){


		filepath="/data/data/"+pkgname+"/shared_prefs/"+sharename+".xml";

	}

	public XmlUtils(String xmlpath){
		
		filepath=xmlpath;

	}

	public static long GetLong(String key,long defaultvalue){

		return (long)Get(key,defaultvalue);

	}

	public static float GetFloat(String key,float defaultvalue){

		return (float)Get(key,defaultvalue);

	}

	public static boolean GetBoolean(String key,boolean defaultvalue){

		return (boolean)Get(key,defaultvalue);

	}

	public static int GetInt(String key,int defaultvalue){

		return (int)Get(key,defaultvalue);

	}

	public static String GetString(String key,String defaultvalue){

		return (String)Get(key,defaultvalue);

	}

	//读取文件

	private static Object Get(String key,Object defaultvalue){

	    Document  doc = null;

		try{

			File  xmlFile = new File(filepath);

			if(!xmlFile.exists())return defaultvalue;

			DocumentBuilderFactory  builderFactory =  DocumentBuilderFactory.newInstance();

			DocumentBuilder   builder = builderFactory.newDocumentBuilder();

			doc = builder.parse(xmlFile);

		}catch(Exception e){

		}

		if(null==doc) return defaultvalue;

		String tag="";

		if(defaultvalue instanceof Integer)tag="int";

		if(defaultvalue instanceof Boolean)tag="boolean";

		if(defaultvalue instanceof String)tag="string";

		if(defaultvalue instanceof Long)tag="long";

		if(defaultvalue instanceof Float)tag="float";

		NodeList NodeList1=doc.getElementsByTagName(tag);

		for(int i=0;i<NodeList1.getLength();i++){

			Element  ele = (Element)NodeList1.item(i);

			if(ele.getAttribute("name").equals(key)){

				String value=ele.getAttribute("value");

				if(null==value|"".equals(value)){

					value= ele.getFirstChild().getNodeValue();

				}

				if(defaultvalue instanceof Integer)return Integer.parseInt(value);

				if(defaultvalue instanceof Boolean)return Boolean.parseBoolean(value);

				if(defaultvalue instanceof String)return value;

				if(defaultvalue instanceof Long)return Long.parseLong(value);

				if(defaultvalue instanceof Float)return Float.parseFloat(value);

			}

		}

		return defaultvalue;

	}

	public static void PutFloat(String key,float value){

		Put(key,value);

	}


	public static void PutBoolean(String key,boolean value){

		Put(key,value);

	}


	public static void PutLong(String key,long value){

		Put(key,value);

	}


	public static void PutString(String key,String value){

		Put(key,value);

	}


	public static void PutInt(String key,int value){

		Put(key,value);

	}

	public static void Put(String key,Object value) {

		Document  doc = null;

		Element root =null;

		try{

			File  xmlFile = new File(filepath);

			DocumentBuilderFactory  builderFactory =  DocumentBuilderFactory.newInstance();

			DocumentBuilder   builder = builderFactory.newDocumentBuilder();

			if(xmlFile.exists()){

				doc = builder.parse(xmlFile);

				root=(Element)doc.getElementsByTagName("map").item(0);

				if(null==root){

					root = doc.createElement("map");

					doc.appendChild(root); // 将根元素添加到文档上

				}

			}else{

				doc=builder.newDocument();

				root = doc.createElement("map");

				doc.appendChild(root); // 将根元素添加到文档上

			}

			String tag="";

			if(value instanceof Integer)tag="int";

			if(value instanceof Boolean)tag="boolean";

			if(value instanceof String)tag="string";

			if(value instanceof Long)tag="long";

			if(value instanceof Float)tag="float";

			NodeList NodeList1=root.getElementsByTagName(tag);

			Element stu=null;

			for(int i=0;i<NodeList1.getLength();i++){

				if(!((Element)NodeList1.item(i)).getAttribute("name").equals(key))continue;

				stu=(Element)NodeList1.item(i);

				break;

			}

			if(null==stu){

				stu = doc.createElement(tag);

				stu.setAttribute("name",key);

				if("string".equals(tag)){

					stu.appendChild(doc.createTextNode(String.valueOf(value)));

				}else{

					stu.setAttribute("value",String.valueOf(value));

				}

				root.appendChild(stu);// 添加属性

			}else{

				if("string".equals(tag)){

					stu.appendChild(doc.createTextNode(String.valueOf(value)));

				}else{

					stu.setAttribute("value",String.valueOf(value));

				}

			}
			Source source = new DOMSource(doc);
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			OutputStreamWriter write = new OutputStreamWriter(outStream);
			Result result = new StreamResult(write);

			Transformer xformer = TransformerFactory.newInstance()
				.newTransformer();
			xformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");

			xformer.transform(source, result);

			WriteFile(filepath,outStream.toString(),true);


		} catch (TransformerConfigurationException e) {
			e.printStackTrace();

		} catch (TransformerException e) {
			e.printStackTrace();

		}
		catch (IOException e)
		{}

		catch (SAXException e)
		{}

		catch (ParserConfigurationException e)
		{}


    }

	private static boolean WriteFile(String filepath,String str,boolean iscover){

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
