import com.entity.UserEntity;
import com.service.EntityDao;
import com.util.Tool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class test {
    @Autowired
    @Qualifier("entityDaoImpl")
    private EntityDao dao;


    @Test
    public void Query() {
        try {
//        UserEntity user   = (UserEntity) dao.Query(UserEntity.class,"15","int");
//        System.out.println("Id---->"+user.getId());
//        System.out.println("Username---->"+user.getUsername());
//        System.out.println("Password---->"+user.getPassword());
//        System.out.println("Email---->"+user.getEmail());
//        String sql = "from UserEntity ";
//        List  list = dao.Query(sql);
//        System.out.println("list--->"+list.toString());
            String logininfo = "[{\"username\":\"cs\",\"password\":\"1\",\"uuid\":\"2BF914B3\"}]";
            String username = Tool.DocJson(logininfo,"username");
            String password = Tool.DocJson(logininfo,"password");
            String sql = "select * from user t where t.username = '"+username+"' and t.password = '"+password+"'";
            List list = dao.Queryforsql(sql);
            System.out.println("list--->"+list.toString());
            String  res = list.toString();
            System.out.println(res);


//            LoginServerImpl aaa = new LoginServerImpl();
//           String logininfo = "[{\"username\":\"123213\",\"password\":\"12323\",\"uuid\":\"2BF914B3\"}]";
//           String res = aaa.login(logininfo);
//           System.out.println(res);
    }catch (Exception e){
        System.out.println("err--->"+e.getMessage());
    }
    }
    @Test
    public void save() {
        int i = -1;
        try {
        UserEntity user = new UserEntity();
//      user.setId(213123321);
        user.setUsername("xwh");
        user.setPassword("123");
        dao.save(user);
        i=0;
        }catch (Exception e){
            System.out.println("err--->"+e.getMessage());
        }
        System.out.println("-------->"+ i);
    }
    @Test
    public void update() {
        int i = -1;
        try {
            UserEntity user = new UserEntity();
            dao.update(user);
            i=0;
        }catch (Exception e){
            System.out.println("err--->"+e.getMessage());
        }
        System.out.println("-------->"+ i);
    }
    @Test
    public void delete() {
       int i = -1;
       try {
           String[] ids = {"213123323","213123324","213123325"};
           dao.delete(UserEntity.class,ids,"int");
           i= 0 ;
       }catch (Exception e){
         System.out.println("err--->"+e.getMessage());
       }
       System.out.println("-------->"+ i);
    }
}
