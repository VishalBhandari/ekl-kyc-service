package com.flipkart.logistics.services;

import com.flipkart.logistics.models.Category;
import com.flipkart.logistics.models.Service;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

/**
 * Created by vishal.bhandari on 22/08/15.
 */
public class ServiceHelper {
    private SessionFactory factory = null;
    public Service getServiceByName(String name)    {
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
        Criteria c = session.createCriteria(Service.class);
        c.add(Restrictions.eq(Service.SERVICE_TYPE, name));
        return (Service) c.uniqueResult();
    }
}
