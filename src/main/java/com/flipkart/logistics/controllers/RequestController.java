package com.flipkart.logistics.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.logistics.models.ServiceRequest;
import com.flipkart.logistics.services.ServiceRequestHelper;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by vishal.bhandari on 24/08/15.
 */
@Path("/flipkart/request")
public class RequestController {

    @PUT
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
        statusNode = bodyNode.findValue("service_json");
         sr.setServiceJson(mapper.writeValueAsString(statusNode));
        statusNode = bodyNode.findValue("category_json");
         sr.setCategoryJson(mapper.writeValueAsString(statusNode));
        statusNode = bodyNode.findValue("eta_json");
         sr.setEtaJson(mapper.writeValueAsString(statusNode));
        statusNode = bodyNode.findValue("doc_json");
         sr.setDocJson(mapper.writeValueAsString(statusNode));

        new ServiceRequestHelper().addServiceRequesttoDb(sr);
        return Response.status(Response.Status.OK).build();


    }

}