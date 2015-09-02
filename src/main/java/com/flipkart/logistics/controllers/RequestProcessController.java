package com.flipkart.logistics.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.flipkart.logistics.client.HttpClientHelper;
import com.flipkart.logistics.models.*;
import com.flipkart.logistics.services.AttributeHelper;
import com.flipkart.logistics.services.RequestHelper;
import org.apache.http.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.LineFormatter;
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
import java.util.List;
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

        List<Request> requestList = new RequestHelper().getPendingRequest();
        String newShipmentId;

        for(Request request:requestList)
         {

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

             newShipmentId = new AttributeHelper().getNewShipmentId("shipmentIDLatestValue");


             shipment.setShipmentId(newShipmentId);
            shipment.setOrderId(newShipmentId);
            shipment.setExternalTrackingId(newShipmentId);

            shipment.setDeliveryCustomerAddress1(request.getCustomer().getAddress1());
            shipment.setDeliveryCustomerAddress2(request.getCustomer().getAddress2());
            shipment.setDeliveryCustomerCity(request.getCustomer().getCity());
            shipment.setDeliveryCustomerCountry(request.getCustomer().getCountry());
            shipment.setDeliveryCustomerEmail(request.getCustomer().getEmail());
            shipment.setDeliveryCustomerName(request.getCustomer().getName());
            shipment.setDeliveryCustomerPhone(request.getCustomer().getPhone());
            shipment.setDeliveryCustomerPincode(request.getCustomer().getPinCode());
            shipment.setDeliveryCustomerState(request.getCustomer().getState());

            shipment.setShippingCustomerAddress1(request.getMerchant().getAddress1());
            shipment.setShippingCustomerCity(request.getMerchant().getCity());
            shipment.setShippingCustomerCountry(request.getMerchant().getCountry());
            shipment.setShippingCustomerName(request.getMerchant().getName());
            shipment.setShippingCustomerPincode(request.getMerchant().getPinCode());
            shipment.setShippingCustomerState(request.getMerchant().getState());
            shipment.setShippingCustomerEmail(request.getMerchant().getEmail());

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

             if(response.getStatusLine().getStatusCode() == 201)
             {
                 //request.setStatus("WIP");
                 new RequestHelper().setStatusAsProcessed(request);
             }

        }
        return Response.status(Response.Status.OK).build();
    }

}
