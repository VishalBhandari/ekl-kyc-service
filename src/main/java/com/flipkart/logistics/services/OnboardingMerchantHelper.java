package com.flipkart.logistics.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.logistics.models.*;
import com.flipkart.logistics.utils.HibernateUtil;
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

    public Merchant getMerchantByName(String name)    {

        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Merchant.class);
        criteria.add(Restrictions.and(Restrictions.eq(Merchant.NAME, name), Restrictions.eq("active", 1)));
        Merchant merchant = (Merchant) criteria.uniqueResult();
        session.close();
        return merchant;
    }

    public Long addMerchant(Merchant m)
    {
        Long merchantId=0L;

        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        Transaction txn = null;
        try{
            txn = session.beginTransaction();
            merchantId = (Long)session.save(m);
            txn.commit();
        }catch (HibernateException e) {
            if (txn!=null) txn.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return merchantId;
    }

    public Merchant getMerchantById(String merchantReferenceId)    {

        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Merchant.class);
        criteria.add(Restrictions.eq("merchantReferenceId", merchantReferenceId));
        Merchant merchant = (Merchant) criteria.uniqueResult();
        session.close();
        if(merchant==null || merchant.getactive() == 0 )
        return null;
        else
            return merchant;
    }

    public boolean deleteMerchantById(String merchantReferenceId)    {

        Merchant merchant = getMerchantById(merchantReferenceId);


        if(merchant==null || merchant.getactive()==0)
            return false;

        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        Transaction txn = null;

        try{
            txn = session.beginTransaction();
            merchant.setactive(0);
            session.saveOrUpdate(merchant);
            txn.commit();
        }catch (HibernateException e) {
            if (txn!=null) txn.rollback();
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

        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        Transaction txn = null;
        try{
            txn = session.beginTransaction();

            String merchantReferenceId = "MER" + String.format("%08d",merchant.getId());
            merchant.setMerchantReferenceId(merchantReferenceId);
            session.saveOrUpdate(merchant);
            txn.commit();
        }catch (HibernateException e) {
            if (txn!=null) txn.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }

    }

    public void updateMerchant(Merchant merchant) {

        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        Transaction txn = null;
        try{
            txn = session.beginTransaction();
            session.saveOrUpdate(merchant);
            txn.commit();
        }catch (HibernateException e) {
            if (txn!=null) txn.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

}
