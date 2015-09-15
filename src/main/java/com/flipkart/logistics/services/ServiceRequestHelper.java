package com.flipkart.logistics.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.logistics.models.Merchant;
import com.flipkart.logistics.models.ServiceRequest;
import com.flipkart.logistics.utils.HibernateUtil;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.Service;

import java.io.IOException;

/**
 * Created by vishal.bhandari on 25/08/15.
 */
public class ServiceRequestHelper {

    public Long addServiceRequest(ServiceRequest serviceRequest)
    {
        Long serviceRequestId=0L;

        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        Transaction txn = null;
        try{
            txn = session.beginTransaction();
            serviceRequestId = (Long)session.save(serviceRequest);
            txn.commit();
        }catch (HibernateException e) {
            if (txn!=null) txn.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return serviceRequestId;
    }

    public ServiceRequest getServiceRequestById(Long ServiceRequestId)    {

        Session session = HibernateUtil.getInstance().getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(ServiceRequest.class);
        criteria.add(Restrictions.eq("id", ServiceRequestId));
        ServiceRequest serviceRequest = (ServiceRequest) criteria.uniqueResult();
        session.close();
        return serviceRequest;
    }

    public ServiceRequest parseJsonRequest(String body) throws IOException {

        ServiceRequest serviceRequest = new ServiceRequest();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode bodyNode = mapper.readTree(body);
        JsonNode statusNode = bodyNode.findValue("merchant_ref_id");
        if(new OnboardingMerchantHelper().getMerchantById(statusNode.asText()) == null )
            return null;

        serviceRequest.setMerchantReferenceId(statusNode.asText());
        statusNode = bodyNode.findValue("category");
        serviceRequest.setCategory(mapper.writeValueAsString(statusNode));
        statusNode = bodyNode.findValue("request");
        serviceRequest.setRequest(mapper.writeValueAsString(statusNode));

        return serviceRequest;
    }
}
