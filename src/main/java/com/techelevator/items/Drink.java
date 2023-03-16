package com.techelevator.items;

public class Drink extends VendingMachineItem {
    public Drink (String slotNumber, String itemName, String price) {
        super(slotNumber, itemName, price);
    }

    @Override
    public String getItemMessage() {
        return "Glug Glug, Chug Chug!";
    }
}
