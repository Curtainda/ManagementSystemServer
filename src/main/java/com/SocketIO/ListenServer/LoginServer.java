package com.SocketIO.ListenServer;

import com.service.EntityDao;
import com.util.Tool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class LoginServer {
    private static EntityDao dao;
    private static String UUID;
    @Autowired
    public void setDatastore(EntityDao dao) {//spring 给静态类注入的方法
        LoginServer.dao = dao;
    }

    public static String login(String logininfo){
        String res = "";
        Map<String, String> map = new HashMap<String, String>();
        try {
            UUID = Tool.DocJson(logininfo,"uuid");
            String username = Tool.DocJson(logininfo,"username");
            String password = Tool.DocJson(logininfo,"password");
            String sql = "select * from user t where t.username = '"+username+"' and t.password = '"+password+"'";
            List list = dao.Queryforsql(sql);
            if(list.size() >0 ){
                String listjson = JSONArray.fromObject(list).toString();
                map.put("Code", "0");
                map.put("Message",listjson);
            }else{
                map.put("Code", "-1");
                map.put("Message", "账号或密码错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            map.put("Code", "-1");
            map.put("Message", e.getMessage());
        }
        map.put("uuid",UUID);
        JSONObject json = JSONObject.fromObject(map);
        res = json.toString();
        return  res;
    }






}
