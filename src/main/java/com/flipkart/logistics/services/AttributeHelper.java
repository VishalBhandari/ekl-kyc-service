package com.flipkart.logistics.services;

import com.flipkart.logistics.models.Attribute;
import com.flipkart.logistics.models.Category;
import com.flipkart.logistics.models.Request;
import com.flipkart.logistics.utils.HibernateUtil;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

/**
 * Created by vishal.bhandari on 01/09/15.
 */
public class AttributeHelper {


    public String createNewShipmentId(String key)
    {
        String newShipmentID="";
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        Transaction txn = null;
        try{
            txn = session.beginTransaction();
            Criteria criteria = session.createCriteria(Attribute.class);
            criteria.add(Restrictions.eq("name", key));
            Attribute attribute = (Attribute) criteria.uniqueResult();
            newShipmentID = "DOC" + String.format("%08d",Long.parseLong(attribute.getValue())+1);
            long temp = Long.parseLong(attribute.getValue())+1;
            attribute.setValue(Long.toString(temp));
            session.update(attribute);
            txn.commit();
        }catch (HibernateException e) {
            if (txn!=null) txn.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }

        return newShipmentID;
    }
}

