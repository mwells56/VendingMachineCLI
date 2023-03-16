package com.techelevator.items;

import java.math.BigDecimal;

public abstract class VendingMachineItem {
    String slotNumber;
    String itemName;
    BigDecimal price;
    int quantityRemaining = 5;
    int quantitySold = 0;

    public VendingMachineItem() {

    }

    public VendingMachineItem(String slotNumber, String itemName, String price) {
        this.slotNumber = slotNumber;
        this.itemName = itemName;
        this.price = new BigDecimal(price);
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantityRemaining() {
        return quantityRemaining;
    }

    public void setQuantityRemaining(int quantityRemaining) {
        this.quantityRemaining = quantityRemaining;
    }

    public String getItemMessage() {
       return "";
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }
}
