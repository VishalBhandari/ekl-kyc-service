package com.flipkart.logistics.services;

import com.flipkart.logistics.models.Document;
import com.flipkart.logistics.models.Request;
import com.flipkart.logistics.utils.HibernateUtil;
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


    public Set<Document> getDocumentListById(String requestReferenceId)    {

        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Request.class);
        criteria.add(Restrictions.eq("requestReferenceId", requestReferenceId));
        Request request = (Request)criteria.uniqueResult();
        session.close();
        if(request == null || request.getActive() == 0)
            return null;
        return request.getDocument();
    }
}
