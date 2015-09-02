package com.flipkart.logistics.services;

import com.flipkart.logistics.models.Attribute;
import com.flipkart.logistics.models.Request;
import com.flipkart.logistics.models.ServiceRequest;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by vishal.bhandari on 26/08/15.
 */
public class RequestHelper {

    private SessionFactory factory;

    public Long addRequesttoDb(Request req)
    {
        Long request_id=0L;
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
            request_id = (Long)session.save(req);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return request_id;
    }

    public Request getRequestById(Long RequestId)    {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Session session = factory.openSession();
        Criteria c = session.createCriteria(Request.class);
        c.add(Restrictions.eq("id", RequestId));
        return (Request) c.uniqueResult();
    }

    public List<Request> getPendingRequest()
    {
        try
        {
            factory = new Configuration().configure().buildSessionFactory();
        }catch (Throwable ex)
        {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Session session = factory.openSession();
        Criteria c = session.createCriteria(Request.class);
        c.add(Restrictions.eq("status", "PENDING"));
        return (List<Request>) c.list();

    }

    public void setStatusAsProcessed(Request request)
    {
        try {
            //factory = new Configuration().configure().buildSessionFactory();

            Configuration configuration = new Configuration().configure();
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().
                    applySettings(configuration.getProperties());
            factory = configuration.buildSessionFactory(builder.build());

        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        if (factory == null) {
            // TODO: Throw error message
        }

        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            String hql = "update Request set status =:status" + " where id =:requestId";
            Query query = session.createQuery(hql);
            query.setParameter("status" , "PROCESSED" );
            query.setParameter("requestId", request.getId());
            query.executeUpdate();
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
}
