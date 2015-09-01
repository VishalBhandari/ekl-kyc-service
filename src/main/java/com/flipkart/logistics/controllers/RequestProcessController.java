package com.flipkart.logistics.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.flipkart.logistics.client.HttpClientHelper;
import com.flipkart.logistics.models.*;
import com.flipkart.logistics.services.RequestHelper;
import org.apache.http.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.params.HttpParams;
import org.eclipse.jetty.util.ajax.JSON;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by vishal.bhandari on 28/08/15.
 */

@Path("/flipkart/processrequest")
public class RequestProcessController {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/process-request")

    public Response processRequest() throws IOException {

        Request request = new RequestHelper().getPendingRequest();
        if (request != null) {

            ShipmentAttributeJsonModel shipmentAttribute = new ShipmentAttributeJsonModel("priority_value", "NON-PRIORITY");
            ShipmentItemJsonModel shipmentItem = new ShipmentItemJsonModel("passport", "id_proof");

            ShipmentJsonModel shipment = new ShipmentJsonModel();

            ArrayList<ShipmentItemJsonModel> shipmentItemList = new ArrayList<ShipmentItemJsonModel>();
            ArrayList<ShipmentAttributeJsonModel> shipmentAttributeList = new ArrayList<ShipmentAttributeJsonModel>();


            shipmentItemList.add(shipmentItem);
            shipment.setShipmentItems(shipmentItemList);

            shipmentAttributeList.add(shipmentAttribute);
            shipment.setShipmentAttributes(shipmentAttributeList);

            shipment.setShipmentType("RVP");
            shipment.setPickupType("PICKUP_ONLY");
            shipment.setShipmenttype("Incoming");
            shipment.setShipmentId("DOC0000005");
            shipment.setOrderId("DOC0000005");
            shipment.setExternalTrackingId("DOC0000005");
            shipment.setDeliveryCustomerAddress1("18, Middle street, ABC City");
            shipment.setDeliveryCustomerAddress2("18, Middle street, ABC City");
            shipment.setDeliveryCustomerCity("Bangalore");
            shipment.setDeliveryCustomerCountry("INDIA");
            shipment.setDeliveryCustomerEmail("ekl.dev@ekl.com");
            shipment.setDeliveryCustomerName("Anshul G");
            shipment.setDeliveryCustomerPhone("9535528878");
            shipment.setDeliveryCustomerPincode("282001");
            shipment.setDeliveryCustomerState("Karnataka");

            shipment.setShippingCustomerAddress1("FKL facility, Chennai");
            shipment.setShippingCustomerCity("Chennai");
            shipment.setShippingCustomerCountry("Ind");
            shipment.setShippingCustomerName("Flipkart Chennai");
            shipment.setShippingCustomerPincode("342001");
            shipment.setShippingCustomerState("TamilNadu");
            shipment.setShippingCustomerEmail("test@gmail.com");
            shipment.setOriginLocationName("fkl-bangalore");


            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(shipment);


            HttpClientHelper client = new HttpClientHelper();
            HttpResponse response = null;
            try {
                response = client.postRequest("http://flo-fkl-app2.stage.ch.flipkart.com:27012/fsd-external-apis/shipments/create-shipment", jsonString);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
        return Response.status(Response.Status.OK).build();
    }

}
