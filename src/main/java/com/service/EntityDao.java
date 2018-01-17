package com.service;


import java.util.List;

public interface EntityDao {

    public Object Query(Class c, String id ,String idtype);

    public List Query(String hql);

    public List Queryforsql(String sql);

    public void save(Object bean);

    public void update(Class c, String id ,String idtype);

    public void update(Class c, String[] ids,String idtype);

    public void delete(Object bean);

    public void delete(Class c, String id ,String idtype);

    public void delete(Class c, String[] ids,String idtype);
}