package com.example.mall.dto.request;

import java.util.List;

public class CreateOrderRequest {
    private List<Long> cartItemIds;
    private Long addressId;
    private String remark;
    
    // Getters and Setters
    public List<Long> getCartItemIds() {
        return cartItemIds;
    }
    
    public void setCartItemIds(List<Long> cartItemIds) {
        this.cartItemIds = cartItemIds;
    }
    
    public Long getAddressId() {
        return addressId;
    }
    
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
}