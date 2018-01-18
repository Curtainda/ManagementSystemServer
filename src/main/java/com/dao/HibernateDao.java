package com.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface HibernateDao {

    public Object Query(Class c, String id ,String idtype);

    public List Query(String hql);

    public List Queryforsql(String sql);

    public void save(Object bean);

    public void update(Object bean);

    public void delete(Class c, String id ,String idtype);

    public void delete(Class c, String[] ids,String idtype);
}
