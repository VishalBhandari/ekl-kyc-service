package com.flipkart.logistics.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.logistics.models.*;
import com.flipkart.logistics.services.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by vishal.bhandari on 24/08/15.
 */
@Path("/flipkart/request")
public class RequestController {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create-request")

    public Response createServiceRequest(String body) throws IOException {

        ServiceRequest sr = new ServiceRequest();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode bodyNode = mapper.readTree(body);

        JsonNode statusNode = bodyNode.findValue("merchant_ref_id");
        sr.setMerchantRefId(statusNode.longValue());

        statusNode = bodyNode.findValue("customer_json");
         sr.setCustomerJson(mapper.writeValueAsString(statusNode));
        statusNode = bodyNode.findValue("service");
         sr.setService(mapper.writeValueAsString(statusNode));
        statusNode = bodyNode.findValue("category");
         sr.setCategory(mapper.writeValueAsString(statusNode));
        statusNode = bodyNode.findValue("eta");
         sr.setEta(mapper.writeValueAsString(statusNode));
        statusNode = bodyNode.findValue("doc_json");
         sr.setDocJson(mapper.writeValueAsString(statusNode));

        new ServiceRequestHelper().addServiceRequesttoDb(sr);
        createRequest(body,sr);
        return Response.status(Response.Status.OK).build();


    }

    void createRequest(String body,ServiceRequest sr) throws IOException {
        Request req = new Request();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode bodyNode = mapper.readTree(body);

        JsonNode statusNode = bodyNode.findValue("service");

        String service_name = statusNode.textValue();
        Service service = new ServiceHelper().getServiceByName(service_name);
        statusNode = bodyNode.findValue("merchant_ref_id");
        long merchantRefId = statusNode.asLong();

        Merchant merchant = new OnboardingMerchantHelper().getMerchantById(merchantRefId);

        statusNode = bodyNode.findValue("category");
        String category_name = statusNode.textValue();
        Category category = new CategoryHelper().getCategoryByName(category_name);

        statusNode = bodyNode.findValue("eta");
        String eta = statusNode.textValue();

        Customer customer = new Customer();
        JsonNode bodyNodeTemp = bodyNode.findValue("customer_json");
        statusNode = bodyNodeTemp.findValue("name");
        customer.setName(statusNode.textValue());
        statusNode = bodyNodeTemp.findValue("phone");
        customer.setPhone(statusNode.textValue());
        statusNode = bodyNodeTemp.findValue("email");
        customer.setEmail(statusNode.textValue());
        statusNode = bodyNodeTemp.findValue("address1");
        customer.setAddress1(statusNode.textValue());
        statusNode = bodyNodeTemp.findValue("address2");
        customer.setAddress2(statusNode.textValue());
        statusNode = bodyNodeTemp.findValue("city");
        customer.setCity(statusNode.textValue());
        statusNode = bodyNodeTemp.findValue("state");
        customer.setState(statusNode.textValue());
        statusNode = bodyNodeTemp.findValue("country");
        customer.setCountry(statusNode.textValue());
        statusNode = bodyNodeTemp.findValue("pincode");
        customer.setPinCode(statusNode.textValue());

        statusNode = bodyNode.findValue("doc_json");
        Iterator<JsonNode> iterator1 = statusNode.elements();
        Document doc;
        HashSet<Document> documents = new HashSet<Document>();

        while (iterator1.hasNext()) {
            bodyNodeTemp = iterator1.next();
            statusNode = bodyNodeTemp.findValue("doc_type");
            String doc_type = statusNode.textValue();

            statusNode = bodyNodeTemp.findValue("document");
            Iterator<JsonNode> iterator2 = statusNode.elements();

            while(iterator2.hasNext())
            {
                statusNode = iterator2.next();
                String document = statusNode.textValue();
                 doc = new Document(doc_type,document);
                documents.add(doc);
            }

        }


        req.setDocument(documents);
         req.setCategory(category);
        req.setService(service);
        req.setEta(eta);
        req.setSr(sr);
        req.setMerchant(merchant);
        req.setStatus("PENDING");
        req.setCustomer(customer);
        new RequestHelper().addRequesttoDb(req);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getServiceRequest/serviceRequestId/{serviceRequestId}")
    public Response getServiceRequest( @PathParam("serviceRequestId") Long serviceRequestId) throws JsonProcessingException {

        ServiceRequest serviceRequest = new ServiceRequestHelper().getServiceRequestById(serviceRequestId);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(serviceRequest);
        return Response.status(Response.Status.OK).entity(jsonString).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getRequest/requestId/{requestId}")
    public Response getRequest( @PathParam("requestId") Long requestId) throws JsonProcessingException {

        Request request = new RequestHelper().getRequestById(requestId);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";

        try {
            jsonString = mapper.writeValueAsString(request);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(jsonString);
        return Response.status(Response.Status.OK).entity(jsonString).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getDocument/requestId/{requestId}")
    public Response getDocument( @PathParam("requestId") Long requestId) throws JsonProcessingException {

        Set<Document> documentSet  = new DocumentHelper().getDocumentListById(requestId);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";

        try {
            jsonString = mapper.writeValueAsString(documentSet);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(jsonString);
        return Response.status(Response.Status.OK).entity(jsonString).build();
    }
}