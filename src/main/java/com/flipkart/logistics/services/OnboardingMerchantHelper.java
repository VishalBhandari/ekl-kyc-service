package com.flipkart.logistics.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.logistics.models.*;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

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
        c.add(Restrictions.and(Restrictions.eq(Merchant.NAME, name), Restrictions.eq("active", 1)));
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

    public Merchant getMerchantById(String merchantReferenceId)    {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Session session = factory.openSession();
        Criteria c = session.createCriteria(Merchant.class);
        c.add(Restrictions.eq("merchantReferenceId", merchantReferenceId));
        Merchant merchant = (Merchant) c.uniqueResult();
        session.close();
        if(merchant==null || merchant.getactive() == 0 )
        return null;
        else
            return merchant;
    }

    public boolean deleteMerchantById(String merchantReferenceId)    {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Merchant merchant = getMerchantById(merchantReferenceId);
        Session session = factory.openSession();

        if(merchant==null || merchant.getactive()==0)
            return false;

        Transaction tx = null;

        try{
            tx = session.beginTransaction();
            merchant.setactive(0);
            session.saveOrUpdate(merchant);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return true;

    }

    public Merchant parseJsonMerchant(String body) throws IOException {

        HashSet<Category> categories = new HashSet<Category>();
        HashSet<Service> services = new HashSet<Service>();
        Merchant merchant = new Merchant();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode bodyNode = mapper.readTree(body);
        JsonNode statusNode = bodyNode.findValue("name");
        merchant.setName(statusNode.textValue());
        statusNode = bodyNode.findValue("phone");
        merchant.setPhone(statusNode.textValue());
        statusNode = bodyNode.findValue("email");
        merchant.setEmail(statusNode.textValue());
        statusNode = bodyNode.findValue("address1");
        merchant.setAddress1(statusNode.textValue());
        statusNode = bodyNode.findValue("address2");
        merchant.setAddress2(statusNode.textValue());
        statusNode = bodyNode.findValue("city");
        merchant.setCity(statusNode.textValue());
        statusNode = bodyNode.findValue("state");
        merchant.setState(statusNode.textValue());
        statusNode = bodyNode.findValue("country");
        merchant.setCountry(statusNode.textValue());
        statusNode = bodyNode.findValue("pincode");
        merchant.setPinCode(statusNode.textValue());
        statusNode = bodyNode.findValue("category");
        Iterator<JsonNode> iterator = statusNode.elements();

        while (iterator.hasNext()) {
            statusNode = iterator.next();
            String categoryName = statusNode.textValue();
            Category category = new CategoryHelper().getCategoryByName(categoryName);
            if(category==null) {
                category = new Category(categoryName);
            }
            categories.add(category);
            merchant.setCategory(categories);
        }

        statusNode = bodyNode.findValue("service");
        iterator = statusNode.elements();

        while (iterator.hasNext()) {
            statusNode = iterator.next();
            String serviceName = statusNode.textValue();
            Service service = new ServiceHelper().getServiceByName(serviceName);
            if(service==null) {
                service = new Service(serviceName);
            }

            services.add(service);
            merchant.setService(services);
        }
        merchant.setCreateDateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).toString());
        merchant.setactive(1);
        return merchant;
    }

    public void setMerchantReferenceId(Merchant merchant) {

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

            String merchantReferenceId = "MER" + String.format("%08d",merchant.getId());

            // String hql = "update Merchant set merchant_reference_id =:newvalue where id =:merchantId";

            // Query query = session.createQuery(hql);
            // query.setParameter("newvalue" , MerchantReferenceId );
            // query.setParameter("merchantId", merchant.getId());
            // query.executeUpdate();

            merchant.setMerchantReferenceId(merchantReferenceId);
            session.saveOrUpdate(merchant);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }



    }

    public void updateMerchant(Merchant merchant) {

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
            session.saveOrUpdate(merchant);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

}
