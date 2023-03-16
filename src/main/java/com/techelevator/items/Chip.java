package com.techelevator.items;

public class Chip extends VendingMachineItem {
    public Chip (String slotNumber, String itemName, String price) {
        super(slotNumber, itemName, price);
    }

    @Override
    public String getItemMessage() {
        return "Crunch Crunch, It's Yummy!";
    }
}
