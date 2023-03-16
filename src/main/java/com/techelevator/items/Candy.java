package com.techelevator.items;

public class Candy extends VendingMachineItem {
    public Candy (String slotNumber, String itemName, String price) {
        super(slotNumber, itemName, price);
    }

    @Override
    public String getItemMessage() {
        return "Munch Munch, Mmm Mmm Good!";
    }
}
