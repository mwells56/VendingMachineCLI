package com.techelevator.view;

import com.techelevator.items.VendingMachineItem;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class PurchasingMenu extends VendingMenu {
    private BigDecimal userBalance = BigDecimal.valueOf(0.00);
    MainMenu mainMenu;
    Logger logger = new Logger();

    public PurchasingMenu (MainMenu mainMenu, InputStream input, OutputStream output) {
        super(input, output);
        this.mainMenu = mainMenu;
    }

    public BigDecimal getUserBalance() {
        return userBalance.setScale(2, RoundingMode.HALF_UP);
    }

    public void selectProduct() {
        System.out.println("Please select an item number from the following options: ");
        mainMenu.displayVendingMachineItems();
        System.out.print(">>> ");
        String userSelection = in.nextLine();
        boolean itemExists = false;
        for (VendingMachineItem item : mainMenu.getInventory()) {
            if (item.getSlotNumber().equalsIgnoreCase(userSelection) ||
                    item.getItemName().equalsIgnoreCase(userSelection)) {
                itemExists = true;
                if (item.getQuantityRemaining() > 0 && getUserBalance().compareTo(item.getPrice()) >= 0) {
                    item.setQuantityRemaining(item.getQuantityRemaining() - 1);
                    item.setQuantitySold(item.getQuantitySold() + 1);
                    userBalance = getUserBalance().subtract(item.getPrice());
                    logger.logAction(item, item.getPrice(), getUserBalance());
                    System.out.println("Here is your item:");
                    System.out.println(item.getItemName() + "\nCost: $" + item.getPrice() + "\nBalance Remaining: $" + getUserBalance());
                    System.out.println(item.getItemMessage());
                } else if (item.getQuantityRemaining() <= 0) {
                    System.out.println("This product is SOLD OUT.");
                } else if (getUserBalance().compareTo(item.getPrice()) < 0) {
                    System.out.println("You have not inserted enough money to purchase this item.");
                }
            }
        }
        if (!itemExists) {
            System.out.println("Invalid selection.");
        }
    }

    public BigDecimal feedMoney() {
        try {
            System.out.println("Please insert your bills.");
            String userInput = in.nextLine().replace("$", "");
            BigDecimal moneyInserted = BigDecimal.valueOf(Double.parseDouble(userInput));
            if (moneyInserted.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0 && (moneyInserted.compareTo(BigDecimal.ZERO) == 1)) {
                userBalance = userBalance.add(moneyInserted);
                logger.logAction("feedMoney", moneyInserted, getUserBalance());
            } else {
                System.out.println("Machine only accepts whole bills.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter bill amount as a number.");
        }

        return getUserBalance();
    }

    public void finishTransaction() {
        BigDecimal[] quartersAndRemainder = userBalance.divideAndRemainder(BigDecimal.valueOf(0.25));
        BigDecimal[] dimesAndRemainder = quartersAndRemainder[1].divideAndRemainder(BigDecimal.valueOf(0.10));
        BigDecimal nickels= dimesAndRemainder[1].divide(BigDecimal.valueOf(0.05));

        BigDecimal changeToGive = userBalance;

        userBalance.setScale(2, RoundingMode.HALF_UP);

        userBalance = BigDecimal.ZERO;

        logger.logAction("finishTransaction",  changeToGive, getUserBalance());

        System.out.println("Your change is:\n" + quartersAndRemainder[0].setScale(0, RoundingMode.HALF_UP)
                + " quarter(s)\n" + dimesAndRemainder[0].setScale(0, RoundingMode.HALF_UP) + " dime(s)\n"
                + nickels.setScale(0, RoundingMode.HALF_UP) + " nickel(s)");
    }

    public void setUserBalance(BigDecimal userBalance) {
        this.userBalance = userBalance;
    }
}
