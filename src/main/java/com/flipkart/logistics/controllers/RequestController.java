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
    @Path("/createServiceRequest")

    public Response createServiceRequest(String body) throws IOException {

        if(body == null)
        {
            return Response.status(Response.Status.BAD_REQUEST).entity("The json string is null").build();
        }

        ServiceRequest serviceRequest = new ServiceRequestHelper().parseJsonRequest(body);
        if(serviceRequest == null)
            return Response.status(Response.Status.OK).entity("the merchant is not onboarded").build();

        new ServiceRequestHelper().addServiceRequesttoDb(serviceRequest);
        String result = new RequestHelper().createAllRequest(body,serviceRequest);
        return Response.status(Response.Status.OK).entity(result).build();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getServiceRequest/serviceRequestId/{serviceRequestId}")
    public Response getServiceRequest( @PathParam("serviceRequestId") Long serviceRequestId) throws JsonProcessingException {

        ServiceRequest serviceRequest = new ServiceRequestHelper().getServiceRequestById(serviceRequestId);

        if(serviceRequest == null)
            return Response.status(Response.Status.OK).entity("ServiceRequestId is invalid").build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(serviceRequest);
        return Response.status(Response.Status.OK).entity(jsonString).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getRequest/requestId/{requestId}")
    public Response getRequest( @PathParam("requestId") String requestReferenceId) throws JsonProcessingException {

        Request request = new RequestHelper().getRequestById(requestReferenceId);

        if(request == null || request.getActive() == 0)
            return Response.status(Response.Status.OK).entity("RequestId is invalid").build();
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
    public Response getDocument( @PathParam("requestId") String requestReferenceId) throws JsonProcessingException {

        Set<Document> documentSet  = new DocumentHelper().getDocumentListById(requestReferenceId);
        if(documentSet == null)
            return Response.status(Response.Status.OK).entity("RequestId is invalid").build();

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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/deleteRequest/requestId/{requestId}")
    public Response deleteMerchant( @PathParam("requestId") String requestReferenceId) throws JsonProcessingException {

        boolean status = new RequestHelper().deleteRequestById(requestReferenceId);
        if(status == true)
            return Response.status(Response.Status.OK).entity("RequestId " +requestReferenceId+" is deleted"  ).build();
        else
            return Response.status(Response.Status.OK).entity("RequestId is not present").build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/updateRequest/requestId/{requestId}")

    public Response updateRequest(String body,@PathParam("requestId") String requestReferenceId) throws IOException {

        JsonNode bodyNode,statusNode,statusNode1,bodyNodeTemp;
        Request request = new RequestHelper().getRequestById(requestReferenceId);
        if(request== null || request.getActive()==0)
            return Response.status(Response.Status.OK).entity("RequestId is not present").build();

        ObjectMapper mapper = new ObjectMapper();
        bodyNode = mapper.readTree(body);

        statusNode = bodyNode.findValue("category");
        if(statusNode!=null) {
            Category category = new CategoryHelper().getCategoryByName(statusNode.textValue());
            request.setCategory(category);
        }

        statusNode = bodyNode.findValue("retry_count");
        if(statusNode!=null)
            request.setRetryCount(statusNode.asLong());

        statusNode = bodyNode.findValue("status");
        if(statusNode!=null)
            request.setStatus(statusNode.textValue());

        statusNode =bodyNode.findValue("customer");
        if(statusNode!=null)
        {
            Customer customer = new Customer();

            statusNode1 = statusNode.findValue("name");
            if(statusNode1!=null)
            customer.setName(statusNode1.textValue());

            statusNode1 = statusNode.findValue("phone");
            if(statusNode1!=null)
            customer.setPhone(statusNode1.textValue());

            statusNode1 = statusNode.findValue("email");
            if(statusNode1!=null)
            customer.setEmail(statusNode1.textValue());

            statusNode1 = statusNode.findValue("address1");
            if(statusNode1!=null)
            customer.setAddress1(statusNode1.textValue());

            statusNode1 = statusNode.findValue("address2");
            if(statusNode1!=null)
            customer.setAddress2(statusNode1.textValue());

            statusNode1 = statusNode.findValue("city");
            if(statusNode1!=null)
            customer.setCity(statusNode1.textValue());

            statusNode1 = statusNode.findValue("state");
            if(statusNode1!=null)
            customer.setState(statusNode1.textValue());

            statusNode1 = statusNode.findValue("country");
            if(statusNode1!=null)
            customer.setCountry(statusNode1.textValue());

            statusNode1 = statusNode.findValue("pincode");
            if(statusNode1!=null)
            customer.setPinCode(statusNode1.textValue());

            request.setCustomer(customer);
        }

        statusNode = bodyNode.findValue("service");
        if(statusNode!=null){
            Service service = new ServiceHelper().getServiceByName(statusNode.textValue());
            request.setService(service);
        }

        statusNode = bodyNode.findValue("expectedBy");
        if(statusNode!=null)
            request.setExpectedBy(statusNode.textValue());


        statusNode = bodyNode.findValue("documents");
        if(statusNode!=null)
        {
            Iterator<JsonNode> iterator1, iterator2;

            iterator1 = statusNode.elements();
            Document doc;
            HashSet<Document> documents = new HashSet<Document>();

            while (iterator1.hasNext()) {
                bodyNodeTemp = iterator1.next();
                statusNode1 = bodyNodeTemp.findValue("doc_type");
                String doc_type = statusNode1.textValue();

                statusNode1 = bodyNodeTemp.findValue("document");
                iterator2 = statusNode1.elements();

                while (iterator2.hasNext()) {
                    bodyNodeTemp = iterator2.next();
                    String document = bodyNodeTemp.textValue();
                    doc = new Document(doc_type, document);
                    documents.add(doc);
                }

            }
            request.setDocument(documents);
        }
        new RequestHelper().updateRequest(request);

        return Response.status(Response.Status.OK).entity("RequestId is updated " +
                request.getRequestReferenceId()).build();
    }
}