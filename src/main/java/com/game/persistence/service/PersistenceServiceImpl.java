package com.game.persistence.service;

import com.game.persistence.AbstractEntity;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author kolo
 */
@Component
public class PersistenceServiceImpl implements IPersistenceService {

    @Resource
    private SessionFactory sf;

    @Override
    public void save(AbstractEntity entity) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        entity.convert();
        session.save(entity);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(AbstractEntity entity) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        entity.convert();
        session.update(entity);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Object> getAll(Class<?> clz) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(clz);
        List list = criteria.list();
        parseAll(list);
        transaction.commit();
        session.close();
        return list;
    }

    /**
     * 解析所有的重写了Convert方法的实体
     *
     */
    private void parseAll(List list){
        for(Object obj : list){
            AbstractEntity entity = (AbstractEntity) obj;
            entity.parse();
        }
    }
}
