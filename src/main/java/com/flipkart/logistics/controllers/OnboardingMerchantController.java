package com.flipkart.logistics.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.logistics.models.Category;
import com.flipkart.logistics.models.Merchant;
import com.flipkart.logistics.models.MerchantCategoryMapping;
import com.flipkart.logistics.services.CategoryService;
import com.flipkart.logistics.services.MerchantCategoryMappingService;
import com.flipkart.logistics.services.OnboardingMerchantService;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
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

        Object obj= new OnboardingMerchantService().getMerchantByName(value);
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
            md.setId(new OnboardingMerchantService().addMerchanttoDb(md));
            statusNode = bodyNode.findValue("category");
            Iterator<JsonNode> iterator = statusNode.elements();
            while (iterator.hasNext()) {
                statusNode = iterator.next();
                String category = statusNode.textValue();
                CategoryService cs = new CategoryService();
                Category c = cs.getCategoryByName(category);
                MerchantCategoryMapping mcm = new MerchantCategoryMapping();
                mcm.setId(0L);
                mcm.setMerchantId(md.getId());
                mcm.setCategory(c);
                new MerchantCategoryMappingService().addMerchantCategoryMappingtoDb(mcm);
                System.out.println("djdjhd" +mcm.getId());
            }
            System.out.println(md.getId());

        }
        return Response.status(Response.Status.OK).build();
    }
}
