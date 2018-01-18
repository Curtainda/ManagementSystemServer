package com.service.impl;


import com.dao.HibernateDao;
import com.service.EntityDao;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EntityDaoImpl implements EntityDao {

    @Autowired
    private HibernateDao Dao;


    @Transactional
    public Object Query(Class c, String id ,String idtype){
      return  Dao.Query( c,  id , idtype);
    }
    @Transactional
    public List Query(String hql){
        return Dao.Query(hql);
    }
    @Transactional
    public List Queryforsql(String sql){
        return  Dao.Queryforsql(sql);
    }
    @Transactional
    public void save(Object bean){
       Dao.save(bean);
    }
    @Transactional
    public void update(Object bean){
        Dao.update(bean);
    }
    @Transactional
    public void delete(Class c, String id ,String idtype){
        Dao.delete(c,  id , idtype);
    }
    @Transactional
    public void delete(Class c, String[] ids,String idtype){
        Dao.delete(c,  ids , idtype);
    }

}
