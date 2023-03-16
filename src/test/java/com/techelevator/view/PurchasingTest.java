package com.techelevator.view;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

import com.techelevator.items.VendingMachineItem;
import org.junit.*;

public class PurchasingTest {
    private ByteArrayOutputStream output;

    @Before
    public void setup() {
        output = new ByteArrayOutputStream();
    }

    @Test
    public void wholeNumberReturnsBalancePlusWholeNumber() {
        BigDecimal expected = new BigDecimal("5.00");

        String userInput = "5.00\n";
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));

        MainMenu mainMenu = new MainMenu (System.in, System.out);
        PurchasingMenu purchasingMenu = new PurchasingMenu(mainMenu, System.in, System.out);

        BigDecimal result = purchasingMenu.feedMoney();

        Assert.assertEquals(expected, result);
    }

    @Test
    public void userEntersMoneyWithDollarSignStillReturnsWholeNumber() {
        BigDecimal expected = new BigDecimal("5.00");

        String userInput = "$5.00\n";
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));

        MainMenu mainMenu = new MainMenu (System.in, System.out);
        PurchasingMenu purchasingMenu = new PurchasingMenu(mainMenu, System.in, System.out);

        BigDecimal result = purchasingMenu.feedMoney();

        Assert.assertEquals(expected, result);
    }

    @Test
    public void userEntersCoinsDoesNotAccept() {
        BigDecimal expected = new BigDecimal("0.00");

        String userInput = "5.25\n";
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));

        MainMenu mainMenu = new MainMenu (System.in, System.out);
        PurchasingMenu purchasingMenu = new PurchasingMenu(mainMenu, System.in, System.out);

        BigDecimal result = purchasingMenu.feedMoney();

        Assert.assertEquals(expected, result);
    }

    @Test
    public void userEnterNegativeDollarDoesNotAccept() {
        BigDecimal expected = new BigDecimal("0.00");

        String userInput = "-5.00\n";
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));

        MainMenu mainMenu = new MainMenu (System.in, System.out);
        PurchasingMenu purchasingMenu = new PurchasingMenu(mainMenu, System.in, System.out);

        BigDecimal result = purchasingMenu.feedMoney();

        Assert.assertEquals(expected, result);
    }

    @Test
    public void itemIsDispensedPrintsItemMessage() {
        String expected = "Here is your item:\r\nPotato Crisps\nCost: $3.05\nBalance Remaining: $1.95\r\nCrunch Crunch, It's Yummy!\r\n";
        String userInput = "A1\n";
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));
        MainMenu mainMenu = new MainMenu (System.in, System.out);
        PurchasingMenu purchasingMenu = new PurchasingMenu(mainMenu, System.in, System.out);
        purchasingMenu.setUserBalance(BigDecimal.valueOf(5.00));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        purchasingMenu.selectProduct();
        String[] resultInTwoParts = output.toString().split(">>> ");
        String result = resultInTwoParts[1];
        Assert.assertEquals(expected, result);
    }

    @Test
    public void insufficientFundsPrintsInsufficientFundsMessage() {
        String expected = "You have not inserted enough money to purchase this item.\r\n";
        String userInput = "A1\n";
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));
        MainMenu mainMenu = new MainMenu (System.in, System.out);
        PurchasingMenu purchasingMenu = new PurchasingMenu(mainMenu, System.in, System.out);
        purchasingMenu.setUserBalance(BigDecimal.valueOf(1.00));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        purchasingMenu.selectProduct();
        String[] resultInTwoParts = output.toString().split(">>> ");
        String result = resultInTwoParts[1];
        Assert.assertEquals(expected, result);
    }

    @Test
    public void soldOutItemGetsSoldOutMessage() {
        String expected = "This product is SOLD OUT.\r\n";
        String userInput = "A1\n";

        System.setIn(new ByteArrayInputStream(userInput.getBytes()));

        MainMenu mainMenu = new MainMenu (System.in, System.out);
        PurchasingMenu purchasingMenu = new PurchasingMenu(mainMenu, System.in, System.out);

        purchasingMenu.setUserBalance(BigDecimal.valueOf(5.00));
        VendingMachineItem testItem = mainMenu.getInventory().get(0);
        testItem.setQuantityRemaining(0);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        purchasingMenu.selectProduct();

        String[] resultInTwoParts = output.toString().split(">>> ");
        String result = resultInTwoParts[1];

        Assert.assertEquals(expected, result);
    }

    @Test
    public void nonExistentItemGetsDoesntExistMessage() {
        String expected = "Invalid selection.\r\n";
        String userInput = "F10\n";

        System.setIn(new ByteArrayInputStream(userInput.getBytes()));
        MainMenu mainMenu = new MainMenu (System.in, System.out);
        PurchasingMenu purchasingMenu = new PurchasingMenu(mainMenu, System.in, System.out);
        purchasingMenu.setUserBalance(BigDecimal.valueOf(1.00));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        purchasingMenu.selectProduct();
        String[] resultInTwoParts = output.toString().split(">>> ");
        String result = resultInTwoParts[1];
        Assert.assertEquals(expected, result);
    }

    @Test
    public void changeShouldBeCorrectlyDistributed() {
        String expected = "Your change is:\n6 quarter(s)\n1 dime(s)\n1 nickel(s)\r\n";

        MainMenu mainMenu = new MainMenu (System.in, System.out);
        PurchasingMenu purchasingMenu = new PurchasingMenu(mainMenu, System.in, System.out);
        purchasingMenu.setUserBalance(BigDecimal.valueOf(1.65));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        purchasingMenu.finishTransaction();
        String result = output.toString();
        Assert.assertEquals(expected, result);
    }

    @Test
    public void FinishTransactionShouldResetUserBalanceToZero() {
        BigDecimal expected = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

        MainMenu mainMenu = new MainMenu (System.in, System.out);
        PurchasingMenu purchasingMenu = new PurchasingMenu(mainMenu, System.in, System.out);
        purchasingMenu.setUserBalance(BigDecimal.valueOf(1.65));

        purchasingMenu.finishTransaction();
        BigDecimal result = purchasingMenu.getUserBalance();
        Assert.assertEquals(expected, result);
    }
}
