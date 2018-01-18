package com.SocketIO;

import com.SocketIO.ListenServer.LoginServer;
import com.SocketIO.ListenServer.MenuServer;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.util.Tool;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Map;
@Component("SocketIo_Server")
public class SocketIo_Server implements ServletContextListener,   ApplicationListener<ContextRefreshedEvent> {
    private static SocketIOServer server = null;
    private static String SocketIo_url = null;
    private static String SocketIo_port = null;

     static {//初始化 server
        SocketIo_url = Tool.NodeforFile("SocketIo.properties","SocketIo_url");
        SocketIo_port = Tool.NodeforFile("SocketIo.properties","SocketIo_port");
        Configuration config = new Configuration();
        config.setHostname(SocketIo_url); //服务器主机ip，这里配置本机
        //端口，任意
        config.setPort(Integer.parseInt(SocketIo_port));
        config.setMaxFramePayloadLength(1024 * 1024);
        config.setMaxHttpContentLength(1024 * 1024);
        server = new SocketIOServer(config);
        ListenServer();//监听方法
    }

    public static void StartServer() {
        server.start();
    }

    public static void StopServer() {
        server.stop();
    }

    public static void ListenServer() {
        //监听事件，Login为事件名称，自定义
        server.addEventListener("Login", String.class, new DataListener<String>() {
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
                //客户端推送Login事件时，onData接受数据，这里是string类型的json数据，还可以为Byte[],object其他类型
                String sa = client.getRemoteAddress().toString();
                String clientIp = sa.substring(1, sa.indexOf(":"));//获取客户端连接的ip
                Map params = client.getHandshakeData().getUrlParams();//获取客户端url参数
                System.out.println(clientIp + "：客户端接收：----------->" + data);
                String res = LoginServer.login(data);
                System.out.println(clientIp + "：服务端返回：----------->" + res);
                server.getBroadcastOperations().sendEvent("listen"+"Login", res);//监听反馈的消息
            }
        });

        //监听事件，Login为事件名称，自定义
        server.addEventListener("Menu", String.class, new DataListener<String>() {
            public void onData(SocketIOClient client, String data, AckRequest ackRequest) {
                //客户端推送Login事件时，onData接受数据，这里是string类型的json数据，还可以为Byte[],object其他类型
                String sa = client.getRemoteAddress().toString();
                String clientIp = sa.substring(1, sa.indexOf(":"));//获取客户端连接的ip
                Map params = client.getHandshakeData().getUrlParams();//获取客户端url参数
                System.out.println(clientIp + "：客户端接收：----------->" + data);
                String res = MenuServer.Menu(data);
                System.out.println(clientIp + "：服务端返回：----------->" + res);
                server.getBroadcastOperations().sendEvent("listen"+"Menu", res);//监听反馈的消息
            }
        });

        /**
         * 监听其他事件
         */
        //添加客户端连接事件
        server.addConnectListener(new ConnectListener() {
            public void onConnect(SocketIOClient client) {
                String sa = client.getRemoteAddress().toString();
                String clientIp = sa.substring(1, sa.indexOf(":"));//获取客户端连接的ip
                System.out.println(clientIp + ": 已建立链接");
            }
        });
        //添加客户端断开连接事件
        server.addDisconnectListener(new DisconnectListener() {
            public void onDisconnect(SocketIOClient client) {
                String sa = client.getRemoteAddress().toString();
                String clientIp = sa.substring(1, sa.indexOf(":"));//获取客户端连接的ip
                System.out.println(clientIp + ": 已断开链接");
            }
        });
    }


public static void main(String[] arg){
 //   StartServer();
 //   StopServer();
}

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        StartServer();
        System.out.println("------ StartServer ------");
    }


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("tomcate启动了..............");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        StopServer();
        System.out.println("------ StopServer ------");
        System.out.println("tomcat关闭了..........");
    }
}