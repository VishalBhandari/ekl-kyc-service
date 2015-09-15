package com.flipkart.logistics.services;

import com.flipkart.logistics.models.Category;
import com.flipkart.logistics.models.Service;
import com.flipkart.logistics.utils.HibernateUtil;
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

    public Service getServiceByName(String name)    {

        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Service.class);
        criteria.add(Restrictions.eq(Service.SERVICE_TYPE, name));
        Service service = (Service) criteria.uniqueResult();
        session.close();
        return service;
    }
}
