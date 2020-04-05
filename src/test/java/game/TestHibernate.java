package game;


import com.game.account.entity.AccountEntity;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestHibernate {

    @Resource
    private SessionFactory factory;

    @Test
    public void fun(){

        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(AccountEntity.class);
        List list = criteria.list();
        for(Object obj : list){
            AccountEntity entity = (AccountEntity) obj;
            System.out.println(entity);
        }
        transaction.commit();
        session.close();

    }

}
