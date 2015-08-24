package com.flipkart.logistics.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.logistics.models.Category;
import com.flipkart.logistics.models.Merchant;
import com.flipkart.logistics.models.Service;
import com.flipkart.logistics.services.CategoryHelper;
import com.flipkart.logistics.services.OnboardingMerchantHelper;
import com.flipkart.logistics.services.ServiceHelper;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;


/**
 * Created by vishal.bhandari on 18/08/15.
 */
@Path("/flipkart/onboarding")
public class OnboardingMerchantController {

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/create-merchant")

    public Response createMerchant(String body) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode bodyNode = mapper.readTree(body);

        JsonNode statusNode = bodyNode.findValue("name");
        String value = statusNode.textValue();
        HashSet<Category> categories = new HashSet<Category>();
        HashSet<Service> services = new HashSet<Service>();

        Object obj= new OnboardingMerchantHelper().getMerchantByName(value);
        if (obj == null) {

            Merchant md = new Merchant();
            md.setName(value);
            statusNode = bodyNode.findValue("phone");
            md.setPhone(statusNode.textValue());
            statusNode = bodyNode.findValue("email");
            md.setEmail(statusNode.textValue());
            statusNode = bodyNode.findValue("address1");
            md.setAddress1(statusNode.textValue());
            statusNode = bodyNode.findValue("address2");
            md.setAddress2(statusNode.textValue());
            statusNode = bodyNode.findValue("city");
            md.setCity(statusNode.textValue());
            statusNode = bodyNode.findValue("state");
            md.setState(statusNode.textValue());
            statusNode = bodyNode.findValue("country");
            md.setCountry(statusNode.textValue());
            statusNode = bodyNode.findValue("pincode");
            md.setPinCode(statusNode.textValue());
            statusNode = bodyNode.findValue("category");
            Iterator<JsonNode> iterator = statusNode.elements();
            while (iterator.hasNext()) {
                statusNode = iterator.next();
                String category_name = statusNode.textValue();
                Category c = new CategoryHelper().getCategoryByName(category_name);
                if(c!=null)
                {
                    categories.add(c);
                }
                md.setCategory(categories);
            }

            statusNode = bodyNode.findValue("service");
            iterator = statusNode.elements();
            while (iterator.hasNext()) {
                statusNode = iterator.next();
                String service_name = statusNode.textValue();
                Service c = new ServiceHelper().getServiceByName(service_name);
                if(c!=null)
                {
                    services.add(c);
                }
                md.setService(services);
            }

            new OnboardingMerchantHelper().addMerchanttoDb(md);
        }
        return Response.status(Response.Status.OK).build();
    }
}
