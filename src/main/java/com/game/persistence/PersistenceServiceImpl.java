package com.game.persistence;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class PersistenceServiceImpl implements IPersistenceService {

    @Resource
    private SessionFactory sf;

    @Override
    public void save(Object entity) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Object entity) {
        Session session = sf.openSession();
        Transaction transaction = session.beginTransaction();
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
        transaction.commit();
        session.close();
        return list;
    }
}
