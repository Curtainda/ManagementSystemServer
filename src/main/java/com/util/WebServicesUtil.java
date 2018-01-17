package com.util;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.w3c.dom.Document;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.Dispatch;
import java.net.URL;

public class WebServicesUtil {
	
	
	public static void main(String[] args) {
		//String msg = "{\"M_name\":\"register\",\"msg\":[{\"LOGINID\":\"13888888888\",\"PASSWORD\":\"123456\",\"EMAIL\":\"123456@163.com\",\"PHONE\":\"13888888888\",\"GENDER\":\"M\",\"IDCARD\":\"410423XXXXXXXXXXXX\",\"RNAME\":\"张三\",\"FLAG\":\"0\"}]}";
		//String res = Tool.DocJson(msg,"M_name");
		//System.out.print(res);
		//System.out.print(Tool.stampToDate("658684800000"));
		
	}
	
	/**
	 * msg 传入的参数
	 * endpoint webservice的地址
	 * OperationName  方法名称
	 * Parameter   参数名称
	 * 例子:pubws 生成的webwervices
	 */
	public static String invokeRemoteFuc(String msg,String endpoint,String OperationName,String Parameter)  {
		
		String ret = null;
		try {
		org.apache.axis.client.Service service = new org.apache.axis.client.Service(); // 创建一个服务(service)调用(call)
        org.apache.axis.client.Call call = (org.apache.axis.client.Call) service.createCall();// 通过service创建call对象
        call.setTargetEndpointAddress(new URL(endpoint));// 设置service所在URL
        call.setOperationName(OperationName);// 方法名
        call.addParameter(Parameter, org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);//接口的参数
        call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型
        ret = (String) call.invoke(new Object[] {msg});
		} catch (Exception e) {
			e.printStackTrace();
			ret = "[{\"RESULTCODE\":\"-1\",\"ERRORMSG\":\""+e.getMessage()+"\"}]";
		}
		return ret;
	}

	/**
	 * msg 传入的参数
	 * endpoint webservice的地址
	 * targetNamespace    命名空间
	 * OperationName  方法名称
	 * Parameter   参数名称
	 * 例子:郏县中医院 webservice
	 */
	public static String invokeRemoteFuc2(String msg,String endpoint,String targetNamespace,String OperationName,String Parameter)  {

		String res = null;
		try{
	        org.apache.axis.client.Service service = new org.apache.axis.client.Service(); // 创建一个服务(service)调用(call)
	        org.apache.axis.client.Call call = (org.apache.axis.client.Call) service.createCall();// 通过service创建call对象
	        call.setTargetEndpointAddress(new URL(endpoint));  // 设置service所在URL
	        call.setOperationName(new QName(targetNamespace, OperationName));
	        call.setUseSOAPAction(true);
	        call.addParameter(new QName(targetNamespace,Parameter), org.apache.axis.encoding.XMLType.XSD_STRING,javax.xml.rpc.ParameterMode.IN);//接口的参数
	        //call.setReturnClass(org.w3c.dom.Element.class);
	        call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);//设置返回类型 
	        //String path = targetNamespace + method;
	        //call.setSOAPActionURI(path);
	        Object ret = call.invoke(new Object[] {msg});
	        res = ret.toString();
	   }catch(Exception e){
	    e.printStackTrace();
	      res = "[{\"RESULTCODE\":\"-1\",\"ERRORMSG\":\""+e.getMessage()+"\"}]";
	   }
		return res;  
	}
	
	
	/**
	 * msg 传入的参数
	 * endpoint webservice的地址
	 * targetNamespace    命名空间
	 * OperationName  方法名称
	 * Parameter   参数名称
	 * 例子:新农合 调用  soap 开发的webservice
	 */
	 public static String invokeRemoteFuc3(String wsdlUrl ,String wsdlns,String msg2) throws Exception{
		  wsdlUrl = "http://127.0.0.1:8035/pubWsWeb/ws/HnSelfService?wsdl";
		  wsdlns = "http://service.sinosoft.com/";
		    //1、创建服务(Service)  
	        URL url = new URL(wsdlUrl);  
	        QName sname = new QName(wsdlns,"HnSelfServiceImplService");  
	        javax.xml.ws.Service service = javax.xml.ws.Service.create(url,sname);  
	                      
	        //2、创建Dispatch  
	        Dispatch<SOAPMessage> dispatch = service.createDispatch(new QName(wsdlns,"HnSelfServiceImplPort"),SOAPMessage.class,javax.xml.ws.Service.Mode.MESSAGE);  
	                      
	        //3、创建SOAPMessage  
	        SOAPMessage msg = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL).createMessage();  
	        SOAPEnvelope envelope = msg.getSOAPPart().getEnvelope();  
	        SOAPBody body = envelope.getBody();  
	                      
	        //4、创建QName来指定消息中传递数据  
	        QName ename = new QName(wsdlns,"Web","axis");//<nn:add xmlns="xx"/>  
	        SOAPBodyElement ele = body.addBodyElement(ename);  

	        // 传递参数  
	        ele.addChildElement("Msg").setValue(msg2);    
	        msg.writeTo(System.out);
	        //System.out.println("\n invoking.....");  
	        //5、通过Dispatch传递消息,会返回响应消息  
	        SOAPMessage response = dispatch.invoke(msg);  
	        //response.writeTo(System.out);  
	        System.out.println();  
	                      
	        //6、响应消息处理,将响应的消息转换为dom对象  
	        Document doc = response.getSOAPPart().getEnvelope().getBody().extractContentAsDocument();
	        String str = doc.getElementsByTagName("return").item(0).getTextContent();
	       //System.out.println(str);  
		 return str;
	 }

    /**
     * axis2 调用.net开发的websevice 未设置sopaAction
     * msg 传入的参数
     * endpoint webservice的地址
     * targetNamespace    命名空间
     * OperationName  方法名称
     * Parameter   参数名称
     *
     */
	 public static String invokeRemoteFuc4(String msg,String endpoint,String targetNamespace,String OperationName,String Parameter){
         String ret = null;
         try {
         ServiceClient sc = new ServiceClient();
         Options opts = new Options();
         String url = endpoint;
         EndpointReference end = new EndpointReference(url);
         opts.setTo(end);
         opts.setAction(targetNamespace + OperationName);
         sc.setOptions(opts);
         OMFactory fac = OMAbstractFactory.getOMFactory();
         OMNamespace omNs = fac.createOMNamespace(targetNamespace, "");
         OMElement method = fac.createOMElement(OperationName,omNs);
         OMElement value = fac.createOMElement(Parameter,omNs);
         value.setText(msg);
         method.addChild(value);
         OMElement res = sc.sendReceive(method);
         ret = res.getFirstElement().getText();
         // System.out.println(res.getFirstElement().getText());
         return ret;
         } catch (Exception e) {
             e.printStackTrace();
             ret = "[{\"RESULTCODE\":\"-1\",\"ERRORMSG\":\""+e.getMessage()+"\"}]";
         }
         return ret;
     }
	
	
}