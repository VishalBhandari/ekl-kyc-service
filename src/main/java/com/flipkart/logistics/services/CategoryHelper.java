package com.flipkart.logistics.services;

import com.flipkart.logistics.models.Category;
import com.flipkart.logistics.utils.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

/**
 * Created by vishal.bhandari on 21/08/15.
 */
public class CategoryHelper {

    public Category getCategoryByName(String name)    {

        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Category.class);
        criteria.add(Restrictions.eq(Category.CATEGORY_TYPE, name));
        Category category = (Category) criteria.uniqueResult();
        session.close();
        return category;
    }
}
