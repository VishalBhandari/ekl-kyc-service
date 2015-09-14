package com.flipkart.logistics.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.logistics.models.*;
import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
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

    public Request getRequestById(String requestReferenceId)    {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Session session = factory.openSession();
        Criteria c = session.createCriteria(Request.class);
        c.add(Restrictions.eq("requestReferenceId", requestReferenceId));
        Request request = (Request)c.uniqueResult();
        session.close();
        if(request == null || request.getActive() == 0)
            return null;
        else
            return request;
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
            query.setParameter("status" , "SUBMITTED" );
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

    public String createAllRequest(String body, ServiceRequest sr) throws IOException {

        String result="";
        Iterator<JsonNode> iterator1, iterator2, iterator3;
        JsonNode bodyNodeTemp, bodyNode, statusNode1, statusNode;

        Request req = new Request();

        ObjectMapper mapper = new ObjectMapper();
        bodyNode = mapper.readTree(body);

        statusNode = bodyNode.findValue("merchant_ref_id");
        String merchantRefId = statusNode.textValue();
        Merchant merchant = new OnboardingMerchantHelper().getMerchantById(merchantRefId);

        statusNode = bodyNode.findValue("category");
        String category_name = statusNode.textValue();
        Category category = new CategoryHelper().getCategoryByName(category_name);


        statusNode = bodyNode.findValue("request");
        iterator1 = statusNode.elements();

        while (iterator1.hasNext()) {
            statusNode = iterator1.next();
            Customer customer = new Customer();
            bodyNodeTemp = statusNode.findValue("customer");

            statusNode1 = bodyNodeTemp.findValue("name");
            customer.setName(statusNode1.textValue());
            statusNode1 = bodyNodeTemp.findValue("phone");
            customer.setPhone(statusNode1.textValue());
            statusNode1 = bodyNodeTemp.findValue("email");
            customer.setEmail(statusNode1.textValue());
            statusNode1 = bodyNodeTemp.findValue("address1");
            customer.setAddress1(statusNode1.textValue());
            statusNode1 = bodyNodeTemp.findValue("address2");
            customer.setAddress2(statusNode1.textValue());
            statusNode1 = bodyNodeTemp.findValue("city");
            customer.setCity(statusNode1.textValue());
            statusNode1 = bodyNodeTemp.findValue("state");
            customer.setState(statusNode1.textValue());
            statusNode1 = bodyNodeTemp.findValue("country");
            customer.setCountry(statusNode1.textValue());
            statusNode1 = bodyNodeTemp.findValue("pincode");
            customer.setPinCode(statusNode1.textValue());

            statusNode1 = statusNode.findValue("service");
            String service_name = statusNode1.textValue();
            Service service = new ServiceHelper().getServiceByName(service_name);

            statusNode1 = statusNode.findValue("expectedBy");
            String expectedBy = statusNode1.textValue();

            statusNode1 = statusNode.findValue("retry_count");
            Long retryCount = statusNode1.asLong();

            statusNode1 = statusNode.findValue("documents");
            iterator2 = statusNode1.elements();
            Document doc;
            HashSet<Document> documents = new HashSet<Document>();

            while (iterator2.hasNext()) {
                bodyNodeTemp = iterator2.next();
                statusNode1 = bodyNodeTemp.findValue("doc_type");
                String doc_type = statusNode1.textValue();

                statusNode1 = bodyNodeTemp.findValue("document");
                iterator3 = statusNode1.elements();

                while (iterator3.hasNext()) {
                    bodyNodeTemp = iterator3.next();
                    String document = bodyNodeTemp.textValue();
                    doc = new Document(doc_type, document);
                    documents.add(doc);
                }

            }
            req.setDocument(documents);
            req.setCategory(category);
            req.setService(service);
            req.setExpectedBy(expectedBy);
            req.setSr(sr);
            req.setMerchant(merchant);
            req.setStatus("PENDING");
            req.setCustomer(customer);
            req.setRetryCount(retryCount);
            req.setActive(1);
            addRequesttoDb(req);
            setRequestReferenceId(req);
            result = result + req.getRequestReferenceId() + "\t" + req.getCustomer().getName() + "\n";

        }
        return result;
    }

    private void setRequestReferenceId(Request request) {

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

            String requestReferenceId = "REQ" + String.format("%08d",request.getId());
            request.setRequestReferenceId(requestReferenceId);
            session.saveOrUpdate(request);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public boolean  deleteRequestById(String requestReferenceId) {

        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Request request = getRequestById(requestReferenceId);
        Session session = factory.openSession();

        if(request==null || request.getActive()==0)
            return false;

        Transaction tx = null;

        try{
            tx = session.beginTransaction();
            request.setActive(0);
            request.setStatus("CANCELLED");
            session.saveOrUpdate(request);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return true;
    }

    public void updateRequest(Request request) {
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
            session.saveOrUpdate(request);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

}
