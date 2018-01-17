package com.dao.impl;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.dao.HibernateDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class HibernateDaoImpl implements HibernateDao {
    /**
     * Autowired 自动装配 相当于get() set()
     */
    @Autowired
    protected SessionFactory sessionFactory;

    /**
     * gerCurrentSession 会自动关闭session，使用的是当前的session事务
     * 一般查询使用
     * @return
     */
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    /**
     * openSession 需要手动关闭session 意思是打开一个新的session
     * 一般更新 删除使用
     * @return
     */
    private Session getNewSession() {
        return sessionFactory.openSession();
    }

    /**
     * 根据 id 查询信息
     * @param id
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Object Query(Class c, String id ,String idtype) {
        Object obj = null;
        Session session = getSession();
        if ("int".equals(idtype)) {//判断主键是int型还是strng
            obj = session.get(c, Integer.valueOf(id).intValue());
        } else {
            obj = session.get(c, id);
        }
        session.flush();
        session.clear();
        session.close();
        return obj;
    }
    @SuppressWarnings("rawtypes")
    public List Query(String hql) {
        List list = null;
        Session session = null;
        session = getNewSession();
        list = session.createQuery(hql).list();
        session.flush();
        session.clear();
        session.close();
        return list;
    }
    public List Queryforsql(String sql){
        List list = null;
        Session session = null;
        session = getNewSession();
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        list =query.list();
        //list = session.createSQLQuery(sql).list();
        if ((list != null) && (list.size() > 0)) {
            ArrayList<Map<String, Object>> resultlist = new ArrayList<Map<String, Object>>();
            for(int i=0;i<list.size();i++){
                Map<String, Object> map = new HashMap<String, Object>();
                map = (Map)list.get(i);
                for (String key : map.keySet()) {
                    if(map.get(key) == null){
                        map.put(key, "");
                    }
                }
                resultlist.add(map);
            }
            return resultlist;
        }
        session.flush();
        session.clear();
        session.close();
        return list;
    }
    @SuppressWarnings({ "rawtypes" })
    public void save(Object bean) {
        Transaction transaction = null;
        Session session = null;
        session  = getNewSession();
        transaction = session.beginTransaction();
        session.save(bean);//保存
        transaction.commit();//提交
        session.flush();
        session.clear();
        session.close();
    }
    @SuppressWarnings({ "rawtypes" })
    public void update(Class c, String id ,String idtype) {
        Transaction transaction = null;
        Session session = null;
        session  = getNewSession();
        transaction = session.beginTransaction();
        Object obj = null;
        if ("int".equals(idtype)) {//判断主键是int型还是strng
            obj = session.get(c, Integer.valueOf(id).intValue());
        } else {
            obj = session.get(c, id);
        }
        session.update(obj);//修改
        transaction.commit();//提交
        session.flush();
        session.clear();
        session.close();
    }
    @SuppressWarnings({ "rawtypes" })
    public void update(Class c, String[] ids,String idtype) {
        Transaction transaction = null;
        Session session = null;
        session  = getNewSession();
        transaction = session.beginTransaction();
        for(String id : ids){
            Object obj = null;
            if ("int".equals(idtype)) {//判断主键是int型还是strng
                obj = session.get(c, Integer.valueOf(id).intValue());
            } else {
                obj = session.get(c, id);
            }
            session.update(obj);//批量修改
        }
        transaction.commit();//提交
        session.flush();
        session.clear();
        session.close();
    }
    @SuppressWarnings({ "rawtypes" })
    public void delete(Object bean) {
        Transaction transaction = null;
        Session session = null;
        session  = getNewSession();
        transaction = session.beginTransaction();
        session.delete(bean);//删除
        transaction.commit();//提交
        session.flush();
        session.clear();
        session.close();
    }
    @SuppressWarnings({ "rawtypes" })
    public void delete(Class c, String id ,String idtype) {
        Transaction transaction = null;
        Session session = null;
        session  = getNewSession();
        transaction = session.beginTransaction();
        Object obj = null;
        if ("int".equals(idtype)) {//判断主键是int型还是strng
            obj = session.get(c, Integer.valueOf(id).intValue());
        } else {
            obj = session.get(c, id);
        }
        session.delete(obj);//删除
        transaction.commit();//提交
        session.flush();
        session.clear();
        session.close();
    }
    @SuppressWarnings({ "rawtypes" })
    public void delete(Class c, String[] ids,String idtype) {
        Transaction transaction = null;
        Session session = null;
        session  = getNewSession();
        transaction = session.beginTransaction();
        for(String id : ids){
            Object obj = null;
            if ("int".equals(idtype)) {//判断主键是int型还是strng
                obj = session.get(c, Integer.valueOf(id).intValue());
            } else {
                obj = session.get(c, id);
            }
            session.delete(obj);//删除
        }
        transaction.commit();//提交
        session.flush();
        session.clear();
        session.close();
    }
}
