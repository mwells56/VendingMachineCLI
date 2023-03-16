package com.techelevator.items;

public class Gum extends VendingMachineItem {
    public Gum (String slotNumber, String itemName, String price) {
        super(slotNumber, itemName, price);
    }

    @Override
    public String getItemMessage() {
        return "Chew Chew, Pop!";
    }
}
