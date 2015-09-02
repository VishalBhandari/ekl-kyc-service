package com.flipkart.logistics.services;

import com.flipkart.logistics.models.Attribute;
import com.flipkart.logistics.models.Category;
import com.flipkart.logistics.models.Request;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

/**
 * Created by vishal.bhandari on 01/09/15.
 */
public class AttributeHelper {

    private SessionFactory factory = null;

    public String getNewShipmentId(String key)
    {
        String newShipmentID="";
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
            Criteria c = session.createCriteria(Attribute.class);
            c.add(Restrictions.eq("key", key));
            Attribute attribute = (Attribute) c.uniqueResult();
             newShipmentID = "DOC" + String.format("%08d",attribute.getValue()+1);
            long temp = attribute.getValue()+1;
            String hql = "update Attribute set value =:newvalue where id =:attributeId";
            Query query = session.createQuery(hql);
            query.setParameter("newvalue" , temp );
            query.setParameter("attributeId", attribute.getId());
            query.executeUpdate();
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }

        return newShipmentID;
    }
}

