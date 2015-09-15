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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Created by vishal.bhandari on 26/08/15.
 */
public class RequestHelper {


    public Long addRequest(Request request)
    {
        Long requestId=0L;

        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        Transaction txn = null;
        try{
            txn = session.beginTransaction();
            requestId = (Long)session.save(request);
            txn.commit();
        }catch (HibernateException e) {
            if (txn!=null) txn.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return requestId;
    }

    public Request getRequestById(String requestReferenceId)    {

        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Request.class);
        criteria.add(Restrictions.eq("requestReferenceId", requestReferenceId));
        Request request = (Request)criteria.uniqueResult();
        session.close();
        if(request == null || request.getActive() == 0)
            return null;
        else
            return request;
    }

    public List<Request> getPendingRequest()
    {

        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Request.class);
        criteria.add(Restrictions.eq("status", "PENDING"));
        List<Request> requestList = (List<Request>) criteria.list();
        session.close();
        return requestList;

    }

    public void updateStatus(Request request,String status)
    {

        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        Transaction txn = null;
        try{
            txn = session.beginTransaction();
            request.setStatus(status);
            session.saveOrUpdate(request);
            txn.commit();
        }catch (HibernateException e) {
            if (txn!=null) txn.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public String createAllRequest(String body, ServiceRequest sr) throws IOException {

        String result="";
        Iterator<JsonNode> iterator1, iterator2, iterator3;
        JsonNode bodyNodeTemp, bodyNode, statusNode1, statusNode;

        Request request = new Request();

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
            request.setDocument(documents);
            request.setCategory(category);
            request.setService(service);
            request.setExpectedBy(expectedBy);
            request.setSr(sr);
            request.setMerchant(merchant);
            request.setStatus("PENDING");
            request.setCustomer(customer);
            request.setRetryCount(retryCount);
            request.setActive(1);
            addRequest(request);
            setRequestReferenceId(request);
            result = result + request.getRequestReferenceId() + "\t" + request.getCustomer().getName() + "\n";

        }
        return result;
    }

    private void setRequestReferenceId(Request request) {

        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        Transaction txn = null;
        try{
            txn = session.beginTransaction();

            String requestReferenceId = "REQ" + String.format("%08d",request.getId());
            request.setRequestReferenceId(requestReferenceId);
            session.saveOrUpdate(request);
            txn.commit();
        }catch (HibernateException e) {
            if (txn!=null) txn.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public boolean  deleteRequestById(String requestReferenceId) {

        Request request = getRequestById(requestReferenceId);
        if(request==null || request.getActive()==0)
            return false;
        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        Transaction txn = null;

        try{
            txn = session.beginTransaction();
            request.setActive(0);
            request.setStatus("CANCELLED");
            session.saveOrUpdate(request);
            txn.commit();
        }catch (HibernateException e) {
            if (txn!=null) txn.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return true;
    }

    public void updateRequest(Request request) {

        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        Transaction txn = null;
        try{
            txn = session.beginTransaction();
            session.saveOrUpdate(request);
            txn.commit();
        }catch (HibernateException e) {
            if (txn!=null) txn.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

}
