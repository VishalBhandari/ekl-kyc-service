package com.flipkart.logistics.services;

import com.flipkart.logistics.models.Document;
import com.flipkart.logistics.models.Request;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Set;

/**
 * Created by vishal.bhandari on 27/08/15.
 */
public class DocumentHelper {

    private SessionFactory factory;
    public Set<Document> getDocumentListById(String requestReferenceId)    {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Session session = factory.openSession();
        Criteria c = session.createCriteria(Request.class);
        c.add(Restrictions.eq("requestReferenceId", requestReferenceId));
        Request req = (Request)c.uniqueResult();
        if(req == null || req.getActive() == 0)
            return null;
        return req.getDocument();
    }
}
