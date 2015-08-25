package com.flipkart.logistics.services;

import com.flipkart.logistics.models.Merchant;
import com.flipkart.logistics.models.ServiceRequest;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Created by vishal.bhandari on 25/08/15.
 */
public class ServiceRequestHelper {

    private SessionFactory factory;

    public Long addServiceRequesttoDb(ServiceRequest sr)
    {
        Long servicerequest_id=0L;
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
            servicerequest_id = (Long)session.save(sr);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return servicerequest_id;
    }
}
