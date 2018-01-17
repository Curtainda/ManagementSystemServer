package com.SocketIO;


import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DAOTest {
    ApplicationContext context = null;
    SessionFactory sessionFactory = null;

    @Before
    public void init() {
        context = new ClassPathXmlApplicationContext( new String[] {"applicationContext.xml"});
    }
    //增加
    @Test
    public void insert() {

    }
    //修改
    @Test
    public void update() {

    }
    //查找
    @Test
    public void getById() {
       // UserEntity user = (UserEntity)context.getBean("UserEntity");
//        HibernateDao Hibernate = (HibernateDao)context.getBean("HibernateDaoImpl");

      //  userService.Query(user);
    }
    //删除
    @Test
    public void delete() {

    }
}
