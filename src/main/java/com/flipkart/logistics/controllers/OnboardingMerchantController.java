package com.flipkart.logistics.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.logistics.models.MerchantDetails;
import com.flipkart.logistics.utils.Databaseutils;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

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
        statusNode = bodyNode.findValue("age");
        int age = statusNode.intValue();


        MerchantDetails md = new MerchantDetails();

        md.setName(value);
        md.setAge(age);
        new Databaseutils().addObjtoDb(md);

        return Response.status(Response.Status.OK).build();
    }
}
