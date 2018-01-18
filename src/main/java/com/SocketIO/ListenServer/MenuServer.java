package com.SocketIO.ListenServer;

import com.entity.MenuEntity;
import com.service.EntityDao;
import com.util.Tool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class MenuServer {
    private static EntityDao dao;
    private static String UUID;
    @Autowired
    public void setDatastore(EntityDao dao) {//spring 给静态类注入的方法
        MenuServer.dao = dao;
    }

    public static String Menu (String msg){
        String res = "";
        String function = Tool.DocJson(msg,"function");
        if("query".equals(function)){
            res = QueryMenu(msg);//查询菜单
        }else if ("add".equals(function)){
            res = AddMenu(msg);//添加菜单
        }else if ("edt".equals(function)){
            res = EdtMenu(msg);//修改菜单
        }else if ("del".equals(function)){
            res = DelMenu(msg);//删除单个
        }else if ("batch_del".equals(function)){
            res = BatchDelMenu(msg);//批量删除
        }else {
            Map<String, String> map = new HashMap<String, String>();
            UUID = Tool.DocJson(msg,"uuid");
            map.put("uuid",UUID);
            map.put("Code", "-1");
            map.put("Message", "无此方法");
            JSONObject json = JSONObject.fromObject(map);
            res = json.toString();
        }
        return res;
    }

    public static String QueryMenu(String msg){
        String res = "";
        Map<String, String> map = new HashMap<String, String>();
        try {
            UUID = Tool.DocJson(msg,"uuid");
            String menu_name = Tool.DocJson(msg,"menu_name");
            String isdisabledSelect = Tool.DocJson(msg,"isdisabledSelect");
            String SQL = "SELECT * from menu t " ;
            if(!"".equals(menu_name)){
                SQL += "where t.menu_name like '%"+menu_name+"%'";
            }
            if(!"".equals(isdisabledSelect)){
                SQL += " and t.state = '"+isdisabledSelect+"' ";
            }
            List list = dao.Queryforsql(SQL);
            if(list != null  && list.size() >0){
                String listjson = JSONArray.fromObject(list).toString();
                map.put("Code", "0");
                map.put("Message",listjson);
            }else{
                map.put("Code", "-1");
                map.put("Message", "无数据");
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

    public static String AddMenu(String msg){
        String res = "";
        Map<String, String> map = new HashMap<String, String>();
        try {
            UUID = Tool.DocJson(msg,"uuid");
            String menu_id = Tool.DocJson(msg,"menu_id");
            String menu_name = Tool.DocJson(msg,"menu_name");
            String remarks = Tool.DocJson(msg,"remarks");
            MenuEntity menu = new MenuEntity();
            menu.setId(BigInteger.valueOf(0));
            menu.setMenuId(menu_id);
            menu.setMenuName(menu_name);
            menu.setRemarks(remarks);
            dao.save(menu);
            map.put("Code", "0");
            map.put("Message","添加成功");
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

    public static String EdtMenu(String msg){
        String res = "";
        Map<String, String> map = new HashMap<String, String>();
        try {
            UUID = Tool.DocJson(msg,"uuid");
            String menu_id = Tool.DocJson(msg,"menu_id");
            String menu_name = Tool.DocJson(msg,"menu_name");
            // String state = "";
            String remarks = Tool.DocJson(msg,"remarks");
            MenuEntity menu = new MenuEntity();
            dao.save(menu);
            map.put("Code", "0");
            map.put("Message","添加成功");
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

    public static String DelMenu(String msg){
        String res = "";
        Map<String, String> map = new HashMap<String, String>();
        try {
            UUID = Tool.DocJson(msg,"uuid");
            String id = Tool.DocJson(msg,"id");
            dao.delete(MenuEntity.class,id,"bigint");
            map.put("Code", "0");
            map.put("Message","删除成功");
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

    public static String BatchDelMenu(String msg){
        String res = "";
        Map<String, String> map = new HashMap<String, String>();
        try {
            UUID = Tool.DocJson(msg,"uuid");
            String ids = Tool.DocJson(msg,"ids");
            String[] Ids = ids.split(",");
            dao.delete(MenuEntity.class,Ids,"bigint");
            map.put("Code", "0");
            map.put("Message","删除成功");
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
