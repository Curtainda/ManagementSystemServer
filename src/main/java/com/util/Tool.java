package com.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;


public class Tool {
	/**
	 * 解析json字符串和json数组字符串
	 */
	public  static String DocJson(String... Sum) throws JSONException{
			/*用法： 1.参数是可变的但是传参的个数只能是2或者3
			       2.第一个参数是json字符串。第二个参数是节点的名称。第三个参数（可传可不穿）获取json数组的第几个参数
			*/
			String res = "";
			String strdoc = "";	
			String docname = "";
			int num = -1;
			if(Sum.length !=3&&Sum.length !=2){
				return "-1";//长度不正确
			}
			for(int i = 0; i < Sum.length; i++){
				strdoc=Sum[0];//
				docname = Sum[1];
				if(Sum.length >2){
					num = Integer.parseInt(Sum[2]);
				}			
			}		
			char[] strChar = strdoc.substring(0, 1).toCharArray();
			char firstChar = strChar[0];		   
			if(firstChar == '{'){//json数据 
				  JSONObject jsonObject = JSONObject.fromObject(strdoc);
				  res = jsonObject.optString(docname);
			 }else if(firstChar == '['){ //json数组
			      JSONArray jsonArr = JSONArray.fromObject(strdoc);
			     if(num != -1){
			    	 if((num-1) <= jsonArr.size()){
			    		 res = jsonArr.getJSONObject(num-1).optString(docname); 
			    	 }else{
			    		 return "-2";//获取的值超过了数组长度的值
			    	 }		        
			     }else{
			         for (int i = 0; i < jsonArr.size(); i++) {			    		
						 if(i == (jsonArr.size()-1)){
						    res += jsonArr.getJSONObject(i).optString(docname);
						 }else{
						    res += jsonArr.getJSONObject(i).optString(docname)+",";
						 }
					 } 
			     }		        	 
			 }else{ 
			    return "-3";//不是json文本字符串
			 } 
			 return res;
		 }
	/** 
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
	/**
	 * socket调用
     * readline 报文
	 * ipAddress  地址
	 * ipPort  端口
	 * @return
	 */
    public static String Socket(String readline ,String ipAddress,int ipPort) {
		String ServerBW = "";
		try {
			Socket socket = new Socket(ipAddress, ipPort);
			PrintWriter os = new PrintWriter(socket.getOutputStream());
			BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String str1 = socket.getLocalSocketAddress().toString();
			String str2 = socket.getRemoteSocketAddress().toString();
			os.println(readline);
			os.flush();
			System.out.println("客户端(" + str1 + "):" + readline);
			ServerBW = is.readLine();
			System.out.println("服务器(" + str2 + "):" + ServerBW);
			os.close();
			is.close();
			socket.close();
		} catch (Exception e) {
			System.out.println("Error:" + e);
		}
		return ServerBW;
	}
    /**
     * 世界唯一ID 36位
     */
    public static String uuid() {
        UUID uuid = UUID.randomUUID();
       // System.out.println(uuid.toString());
        return uuid.toString();
    }
	/**
	 * 获取电脑的mac地址
	 */
    public static String getMACAddress(InetAddress ia) throws Exception {
		byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < mac.length; i++) {
			if (i != 0) {
				sb.append("-");
			}
			String s = Integer.toHexString(mac[i] & 0xFF);
			sb.append(s.length() == 1 ? 0 + s : s);
		}
		return sb.toString().toUpperCase();
	}
    /**
     * 获取现在日期
     * @return
     */
    public static String nowdata (){
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");		
		String nowtime =df.format(new Date());
    	return nowtime;
    }
    /**
     * 获取现在时间
     * @return
     */
    public static String nowtime (){
    	SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");		
		String nowtime =df.format(new Date());
    	return nowtime;
    }
    /**
     * 获取现在时刻精确到毫秒
     * @return
     */
    public static String getnow (){
    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");		
		String nowtime =df.format(new Date());
    	return nowtime;
    }
    /**
     * 获取配置文件对应节点的值
     * @param file
     * @param node
     * @return
     */
    public static String NodeforFile(String file,String node) {
        InputStream is = null;
        String nodetext = "";
        try {
          is = Tool.class.getClassLoader().getResourceAsStream(file);
          Properties p = new Properties();
          p.load(is);
          nodetext = p.getProperty(node);
        } catch (IOException e) {
          e.printStackTrace();
        }
    	return nodetext;
     }    
    /**
     * 获取配置文件对应节点的值(绝对路径)
     * @param file
     * @param node
     * @return
     */
    public static String NodeforABSpathFile(String file,String node) {
        InputStream is = null;
        String nodetext = "";
        try {
          is = new FileInputStream(new File(file));
          Properties p = new Properties();
          p.load(is);
          nodetext = p.getProperty(node);
        } catch (IOException e) {
          e.printStackTrace();
        }
    	return nodetext;
     }    
    /**
     * 根据出生日期计算年龄
     * @param birth
     * @return
     */
	public static int getAge(String  birth) {
		SimpleDateFormat a = new SimpleDateFormat("yyymmdd");
		Date birthDate = null;
		try {
			birthDate = a.parse(birth);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (birthDate == null)
			throw new RuntimeException("出生日期不能为null");

		int age = 0;

		Date now = new Date();

		SimpleDateFormat format_y = new SimpleDateFormat("yyyy");
		SimpleDateFormat format_M = new SimpleDateFormat("MM");

		String birth_year = format_y.format(birthDate);
		String this_year = format_y.format(now);

		String birth_month = format_M.format(birthDate);
		String this_month = format_M.format(now);

		// 初步，估算
		age = Integer.parseInt(this_year) - Integer.parseInt(birth_year);

		// 如果未到出生月份，则age - 1
		if (this_month.compareTo(birth_month) < 0)
			age -= 1;
		if (age < 0)
			age = 0;
		return age;
	}
	/**
	 * 这一步是对ArrayList与sql进行处理，输出完整的sql语句    
	 * @param sqlTemplate
	 * @param parameterValues
	 * @return
	 */
    public static String getQueryString(String sqlTemplate, Object   parameterValues) { 
        int len = sqlTemplate.length();    
        StringBuffer t = new StringBuffer(len * 2);
        if (parameterValues != null) {    
            int i = 0, limit = 0, base = 0;
            while ((limit = sqlTemplate.indexOf('?', limit)) != -1) {    
                t.append(sqlTemplate.substring(base, limit)); 
                if (parameterValues instanceof String[]) {
                	String[] param = (String[])parameterValues;
                	 t.append("'"+param[i]+"'");
                }
                if (parameterValues instanceof ArrayList) {
                	ArrayList param = (ArrayList)parameterValues;
                	 t.append("'"+param.get(i)+"'");
                }
                //t.append(parameterValues.get(i));
                i++;    
                limit++;    
                base = limit;    
            }    
            if (base < len) {    
                t.append(sqlTemplate.substring(base));    
            }    
        }    
        //System.out.println(t.toString());
        return t.toString();    
    }    

}