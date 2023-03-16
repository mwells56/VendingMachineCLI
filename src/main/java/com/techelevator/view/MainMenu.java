package com.techelevator.view;

import com.techelevator.items.*;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu extends VendingMenu {
    private List<VendingMachineItem> inventory = setInventory();

    public MainMenu (InputStream input, OutputStream output) {
        super(input, output);
    }

    public List<VendingMachineItem> setInventory() {
        File vendingMachineInventory = new File("C:\\Users\\Student\\workspace\\module-1-capstone-team-7\\vendingmachine.csv");
        List<VendingMachineItem> inventory = new ArrayList<>();
        if (vendingMachineInventory.exists()) {
            try (Scanner vendingMachineInventoryContents = new Scanner (vendingMachineInventory)) {
                while (vendingMachineInventoryContents.hasNextLine()) {
                    String itemString = vendingMachineInventoryContents.nextLine();
                    String[] itemArray = itemString.split("\\|");
                    switch (itemArray[3]) {
                        case ("Chip"):
                            Chip chip = new Chip(itemArray[0], itemArray[1], itemArray[2]);
                            inventory.add(chip);
                            break;
                        case ("Candy"):
                            Candy candy = new Candy (itemArray[0], itemArray[1], itemArray[2]);
                            inventory.add(candy);
                            break;
                        case ("Drink"):
                            Drink drink = new Drink (itemArray[0], itemArray[1], itemArray[2]);
                            inventory.add(drink);
                            break;
                        case ("Gum"):
                            Gum gum = new Gum (itemArray[0], itemArray[1], itemArray[2]);
                            inventory.add(gum);
                            break;
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Inventory file does not exist.");
            }
        }

        return inventory;
    }

    public List<VendingMachineItem> getInventory() {
        return inventory;
    }

    public void displayVendingMachineItems() {
        for (VendingMachineItem item : inventory) {
            String quantityRemaining = Integer.toString(item.getQuantityRemaining()) + " left";

            if (item.getQuantityRemaining() < 1) {
                quantityRemaining = "SOLD OUT";
            }

            System.out.println(item.getSlotNumber() + ". " + item.getItemName() + " | $" + item.getPrice() + " | "
                    + quantityRemaining);
        }
    }

    public void generateSalesReport() {
        LocalDate date = LocalDate.now();
        String pattern = "hh-mm-ss-a";
        LocalTime now = LocalTime.now();
        String time = now.format(DateTimeFormatter.ofPattern(pattern));
        File salesReport = new File("SalesReport_"+date+"_"+time+".txt");
        try(PrintWriter salesReportWriter = new PrintWriter(salesReport)) {
            BigDecimal totalSales = BigDecimal.ZERO;
            for (VendingMachineItem item : inventory) {
                int quantitySold = item.getQuantitySold();
                salesReportWriter.println(item.getItemName() + "|" + quantitySold);
                totalSales = totalSales.add(item.getPrice().multiply(BigDecimal.valueOf(quantitySold)));
            }
            salesReportWriter.println("\n**TOTAL SALES** $" + totalSales.setScale(2));
        } catch (FileNotFoundException e) {
            System.out.println("File not found...");
        }
    }
}
