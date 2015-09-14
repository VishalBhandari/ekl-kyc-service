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
        ArrayList<ShipmentItemJsonModel> shipmentItemList = new ArrayList<ShipmentItemJsonModel>();
        ArrayList<ShipmentAttributeJsonModel> shipmentAttributeList = new ArrayList<ShipmentAttributeJsonModel>();

        for(Request request:requestList)
         {

             ShipmentAttributeJsonModel shipmentAttribute1 = new ShipmentAttributeJsonModel("priority_value", "NON-PRIORITY");
             ShipmentAttributeJsonModel shipmentAttribute2 = new ShipmentAttributeJsonModel("retry_count",String.valueOf(request.getRetryCount()));
             shipmentAttributeList.add(shipmentAttribute1);
             shipmentAttributeList.add(shipmentAttribute2);


             for(Document doc : request.getDocument()) {
                 ShipmentItemJsonModel shipmentItem = new ShipmentItemJsonModel(doc.getDocName(),doc.getDocType());
                 shipmentItemList.add(shipmentItem);
             }

             ShipmentJsonModel shipment = new ShipmentJsonModel();

            shipment.setShipmentItems(shipmentItemList);
            shipment.setShipmentAttributes(shipmentAttributeList);

            shipment.setShipmentType("DOC");
            shipment.setPickupType("KYC");
            shipment.setShipmenttype("Incoming");

             newShipmentId = new AttributeHelper().getNewShipmentId("shipmentIDLatestValue");

             shipment.setShipmentId(newShipmentId);
            shipment.setOrderId(newShipmentId);
            shipment.setExternalTrackingId(request.getRequestReferenceId());

            shipment.setDeliveryCustomerAddress1(request.getMerchant().getAddress1());
            shipment.setDeliveryCustomerAddress2(request.getMerchant().getAddress2());
            shipment.setDeliveryCustomerCity(request.getMerchant().getCity());
            shipment.setDeliveryCustomerCountry(request.getMerchant().getCountry());
            shipment.setDeliveryCustomerEmail(request.getMerchant().getEmail());
            shipment.setDeliveryCustomerName(request.getMerchant().getName());
            shipment.setDeliveryCustomerPhone(request.getMerchant().getPhone());
            shipment.setDeliveryCustomerPincode(request.getMerchant().getPinCode());
            shipment.setDeliveryCustomerState(request.getMerchant().getState());

            shipment.setShippingCustomerAddress1(request.getCustomer().getAddress1());
            shipment.setShippingCustomerCity(request.getCustomer().getCity());
            shipment.setShippingCustomerCountry(request.getCustomer().getCountry());
            shipment.setShippingCustomerName(request.getCustomer().getName());
            shipment.setShippingCustomerPincode(request.getCustomer().getPinCode());
            shipment.setShippingCustomerState(request.getCustomer().getState());
            shipment.setShippingCustomerEmail(request.getCustomer().getEmail());

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
