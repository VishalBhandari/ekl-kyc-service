package com.flipkart.logistics.models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by vishal.bhandari on 28/08/15.
 */
public class ShipmentJsonModel {

    private String shipmentType;

    public String getShipmenttype() {
        return shipment_type;
    }

    public void setShipmenttype(String shipment_type) {
        this.shipment_type = shipment_type;
    }

    private String shipment_type;
    private String pickupType;
    private String shipmentId;
    private String orderId;
    private String externalTrackingId;

    private String deliveryCustomerCity;
    private String deliveryCustomerCountry;
    private String deliveryCustomerPincode;
    private String deliveryCustomerAddress1;
    private String deliveryCustomerAddress2;
    private String deliveryCustomerEmail;
    private String deliveryCustomerPhone;
    private String deliveryCustomerState;
    private String deliveryCustomerName;

    private String shippingCustomerState;
    private String shippingCustomerName;
    private String shippingCustomerCountry;
    private String shippingCustomerCity;
    private String shippingCustomerAddress1;
    private String shippingCustomerPincode;
    private String shippingCustomerEmail;


    private String originLocationName;


    private ArrayList<ShipmentItemJsonModel> shipmentItems;
    private ArrayList<ShipmentAttributeJsonModel> shipmentAttributes;



    private String merchantName;


    public ArrayList<ShipmentAttributeJsonModel> getShipmentAttributes() {
        return shipmentAttributes;
    }
    public void setShipmentAttributes(
            ArrayList<ShipmentAttributeJsonModel> shipmentAttributes) {
        this.shipmentAttributes = shipmentAttributes;
    }
    public String getMerchantName() {
        return merchantName;
    }
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }



    public ArrayList<ShipmentItemJsonModel> getShipmentItems() {
        return shipmentItems;
    }
    public void setShipmentItems(ArrayList<ShipmentItemJsonModel> shipmentItems) {
        this.shipmentItems = shipmentItems;
    }
    public String getShippingCustomerPincode() {
        return shippingCustomerPincode;
    }
    public void setShippingCustomerPincode(String shippingCustomerPincode) {
        this.shippingCustomerPincode = shippingCustomerPincode;
    }
    public String getShippingCustomerAddress1() {
        return shippingCustomerAddress1;
    }
    public void setShippingCustomerAddress1(String shippingCustomerAddress1) {
        this.shippingCustomerAddress1 = shippingCustomerAddress1;
    }
    public String getOriginLocationName() {
        return originLocationName;
    }
    public void setOriginLocationName(String originLocationName) {
        this.originLocationName = originLocationName;
    }

    public Timestamp getPickupDateTime() {
        return pickupDateTime;
    }
    public void setPickupDateTime(Timestamp pickupDateTime) {
        this.pickupDateTime = pickupDateTime;
    }
    public String getPickupType() {
        return pickupType;
    }
    public void setPickupType(String pickupType) {
        this.pickupType = pickupType;
    }
    private Timestamp pickupDateTime;



    public String getShipmentType() {
        return shipmentType;
    }
    public void setShipmentType(String shipmentType) {
        this.shipmentType = shipmentType;
    }
    public String getShipmentId() {
        return shipmentId;
    }
    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getExternalTrackingId() {
        return externalTrackingId;
    }
    public void setExternalTrackingId(String externalTrackingId) {
        this.externalTrackingId = externalTrackingId;
    }
    public String getDeliveryCustomerCity() {
        return deliveryCustomerCity;
    }
    public void setDeliveryCustomerCity(String deliveryCustomerCity) {
        this.deliveryCustomerCity = deliveryCustomerCity;
    }
    public String getDeliveryCustomerCountry() {
        return deliveryCustomerCountry;
    }
    public void setDeliveryCustomerCountry(String deliveryCustomerCountry) {
        this.deliveryCustomerCountry = deliveryCustomerCountry;
    }
    public String getDeliveryCustomerPincode() {
        return deliveryCustomerPincode;
    }
    public void setDeliveryCustomerPincode(String deliveryCustomerPincode) {
        this.deliveryCustomerPincode = deliveryCustomerPincode;
    }
    public String getDeliveryCustomerAddress1() {
        return deliveryCustomerAddress1;
    }
    public void setDeliveryCustomerAddress1(String deliveryCustomerAddress1) {
        this.deliveryCustomerAddress1 = deliveryCustomerAddress1;
    }
    public String getDeliveryCustomerName() {
        return deliveryCustomerName;
    }
    public void setDeliveryCustomerName(String deliveryCustomerName) {
        this.deliveryCustomerName = deliveryCustomerName;
    }
    public String getShippingCustomerState() {
        return shippingCustomerState;
    }
    public void setShippingCustomerState(String shippingCustomerState) {
        this.shippingCustomerState = shippingCustomerState;
    }
    public String getShippingCustomerName() {
        return shippingCustomerName;
    }
    public void setShippingCustomerName(String shippingCustomerName) {
        this.shippingCustomerName = shippingCustomerName;
    }
    public String getShippingCustomerCountry() {
        return shippingCustomerCountry;
    }
    public void setShippingCustomerCountry(String shippingCustomerCountry) {
        this.shippingCustomerCountry = shippingCustomerCountry;
    }
    public String getShippingCustomerCity() {
        return shippingCustomerCity;
    }
    public void setShippingCustomerCity(String shippingCustomerCity) {
        this.shippingCustomerCity = shippingCustomerCity;
    }

    public String getDeliveryCustomerAddress2() {
        return deliveryCustomerAddress2;
    }
    public void setDeliveryCustomerAddress2(String deliveryCustomerAddress2) {
        this.deliveryCustomerAddress2 = deliveryCustomerAddress2;
    }
    public String getDeliveryCustomerEmail() {
        return deliveryCustomerEmail;
    }
    public void setDeliveryCustomerEmail(String deliveryCustomerEmail) {
        this.deliveryCustomerEmail = deliveryCustomerEmail;
    }
    public String getDeliveryCustomerPhone() {
        return deliveryCustomerPhone;
    }
    public void setDeliveryCustomerPhone(String deliveryCustomerPhone) {
        this.deliveryCustomerPhone = deliveryCustomerPhone;
    }
    public String getDeliveryCustomerState() {
        return deliveryCustomerState;
    }
    public void setDeliveryCustomerState(String deliveryCustomerState) {
        this.deliveryCustomerState = deliveryCustomerState;
    }


    public void setShippingCustomerEmail(String shippingCustomerEmail) {
        this.shippingCustomerEmail = shippingCustomerEmail;
    }

    public String getShippingCustomerEmail() {
        return shippingCustomerEmail;
    }
}
