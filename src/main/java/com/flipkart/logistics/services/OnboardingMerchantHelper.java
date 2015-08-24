package com.flipkart.logistics.services;

import com.flipkart.logistics.models.Merchant;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

/**
 * Created by vishal.bhandari on 20/08/15.
 */
public class OnboardingMerchantHelper {

    private SessionFactory factory;

    public Merchant getMerchantByName(String name)    {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Session session = factory.openSession();
        Criteria c = session.createCriteria(Merchant.class);
        c.add(Restrictions.eq(Merchant.NAME, name));
        return (Merchant) c.uniqueResult();
    }

    public Long addMerchanttoDb(Merchant m)
    {
        Long merchant_id=0L;
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            merchant_id = (Long)session.save(m);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return merchant_id;
    }
}
