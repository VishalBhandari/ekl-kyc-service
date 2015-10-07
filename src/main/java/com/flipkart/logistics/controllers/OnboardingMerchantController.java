package com.flipkart.logistics.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.logistics.models.Category;
import com.flipkart.logistics.models.Merchant;
import com.flipkart.logistics.models.Service;
import com.flipkart.logistics.services.CategoryHelper;
import com.flipkart.logistics.services.OnboardingMerchantHelper;
import com.flipkart.logistics.services.ServiceHelper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;


/**
 * Created by vishal.bhandari on 18/08/15.
 */
@Path("/flipkart/merchant")
@Produces(MediaType.APPLICATION_JSON)
public class OnboardingMerchantController {

    @POST
    @Path("/createMerchant")
    public Response createMerchant(String body) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode bodyNode = mapper.readTree(body);
        JsonNode statusNode = bodyNode.findValue("name");
        String value = statusNode.textValue();
        Merchant merchant= new OnboardingMerchantHelper().getMerchantByName(value);
        if (merchant == null || merchant.getactive()==0) /* new merchant */
        {
            merchant = new OnboardingMerchantHelper().parseJsonMerchant(body);
            new OnboardingMerchantHelper().addMerchant(merchant);
            /* set merchant reference id */
            new OnboardingMerchantHelper().setMerchantReferenceId(merchant);
            return Response.status(Response.Status.OK).entity("Merchant is onboarded " +
                    merchant.getMerchantReferenceId()).build();
        }
        else
        {
            return Response.status(Response.Status.CONFLICT).entity("Merchant was already onboarded " +
                    merchant.getMerchantReferenceId()).build();
        }

    }

    @GET
    @Path("/getMerchant/merchantId/{merchantId}")

    public Response getMerchant( @PathParam("merchantId") String merchantReferenceId) throws JsonProcessingException {

        if (merchantReferenceId == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("MerchantId is null").build();
        }
        Merchant merchant = new OnboardingMerchantHelper().getMerchantById(merchantReferenceId);
        if (merchant == null || merchant.getactive() == 0)
            return Response.status(Response.Status.CONFLICT).entity("The merchantId is not present").build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(merchant);
        return Response.status(Response.Status.OK).entity(jsonString).build();
    }

    @POST
    @Path("/deleteMerchant/merchantId/{merchantId}")

    public Response deleteMerchant( @PathParam("merchantId") String merchantReferenceId) throws JsonProcessingException {

        boolean status = new OnboardingMerchantHelper().deleteMerchantById(merchantReferenceId);
        if(status == true)
        return Response.status(Response.Status.OK).entity("Merchant " +merchantReferenceId+" is deleted"  ).build();
        else
            return Response.status(Response.Status.OK).entity("Merchant is not present").build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)


    public Response updateMerchant(String body,@PathParam("merchantId") String merchantReferenceId) throws IOException
    {

        JsonNode bodyNode,statusNode;
        Merchant merchant= new OnboardingMerchantHelper().getMerchantById(merchantReferenceId);
        if(merchant == null || merchant.getactive()==0)
        {
            return Response.status(Response.Status.OK).entity("Merchant is not present").build();
        }
        ObjectMapper mapper = new ObjectMapper();
        bodyNode = mapper.readTree(body);

        statusNode = bodyNode.findValue("name");
        if(statusNode!=null)
            merchant.setName(statusNode.textValue());

        statusNode = bodyNode.findValue("phone");
        if(statusNode!=null)
            merchant.setPhone(statusNode.textValue());

        statusNode = bodyNode.findValue("email");
        if(statusNode!=null)
            merchant.setEmail(statusNode.textValue());

        statusNode = bodyNode.findValue("address1");
        if(statusNode!=null)
            merchant.setAddress1(statusNode.textValue());

        statusNode = bodyNode.findValue("address2");
        if(statusNode!=null)
            merchant.setAddress2(statusNode.textValue());

        statusNode = bodyNode.findValue("city");
        if(statusNode!=null)
            merchant.setCity(statusNode.textValue());

        statusNode = bodyNode.findValue("state");
        if(statusNode!=null)
            merchant.setState(statusNode.textValue());

        statusNode = bodyNode.findValue("country");
        if(statusNode!=null)
            merchant.setCountry(statusNode.textValue());

        statusNode = bodyNode.findValue("pincode");
        if(statusNode!=null)
            merchant.setPinCode(statusNode.textValue());

        statusNode = bodyNode.findValue("category");
        if(statusNode!=null) {
            Iterator<JsonNode> iterator = statusNode.elements();
            HashSet<Category> categories = new HashSet<Category>();

            while (iterator.hasNext()) {
                statusNode = iterator.next();
                String categoryName = statusNode.textValue();
                Category category = new CategoryHelper().getCategoryByName(categoryName);
                if (category == null) {
                    category = new Category(categoryName);
                }
                categories.add(category);
                merchant.setCategory(categories);
            }
        }

        statusNode = bodyNode.findValue("service");
        if(statusNode!=null) {
            Iterator<JsonNode> iterator = statusNode.elements();
            HashSet<Service> services = new HashSet<Service>();
            while (iterator.hasNext()) {
                statusNode = iterator.next();
                String serviceName = statusNode.textValue();
                Service service = new ServiceHelper().getServiceByName(serviceName);
                if (service == null) {
                    service = new Service(serviceName);
                }
                services.add(service);
                merchant.setService(services);
            }
        }
        new OnboardingMerchantHelper().updateMerchant(merchant);

        return Response.status(Response.Status.OK).entity("Merchant is updated " +
                    merchant.getMerchantReferenceId()).build();
    }

}
