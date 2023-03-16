package com.techelevator.view;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LogWritingTest {

    private final String DEST_FILE_PATH = "Log.txt";
    private final Path destFilePath = Paths.get(DEST_FILE_PATH);
    private final File destFile = new File (DEST_FILE_PATH);

    @Before
    public void setUp() throws Exception {
        if (destFile.exists()) {
            destFile.delete();
        }
    }

    @After
    public void breakDown() throws Exception {
        if (destFile.exists()) {
            destFile.delete();
        }
    }

    @Test
    public void checkLogForFeedMoney() throws IOException {
        String expected = "FEED MONEY: $5.00 $5.00\n";

        String userInput = "5.00\n";
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));
        MainMenu mainMenu = new MainMenu(System.in, System.out);
        PurchasingMenu purchasingMenu = new PurchasingMenu(mainMenu, System.in, System.out);
        purchasingMenu.feedMoney();

        Assert.assertTrue(Files.exists(destFilePath));
        String[] resultInTwoParts = Files.readString(destFilePath).split("M ");
        String result = resultInTwoParts[1];

        Assert.assertEquals(expected, result);
    }

    @Test public void checkLogForSelectProduct() throws IOException {
        String expected = "Cola C1 $1.25 $3.75\n";

        String userInput = "C1\n";
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));

        MainMenu mainMenu = new MainMenu(System.in, System.out);
        PurchasingMenu purchasingMenu = new PurchasingMenu(mainMenu, System.in, System.out);
        purchasingMenu.setUserBalance(BigDecimal.valueOf(5.00));
        purchasingMenu.selectProduct();

        Assert.assertTrue(Files.exists(destFilePath));
        String[] resultInTwoParts = Files.readString(destFilePath).split("M ");
        String result = resultInTwoParts[1];

        Assert.assertEquals(expected, result);
    }

    @Test public void checkLogForFinishTransaction() throws IOException{
        String expected = "GIVE CHANGE: $5.00 $0.00\n";

        MainMenu mainMenu = new MainMenu(System.in, System.out);
        PurchasingMenu purchasingMenu = new PurchasingMenu(mainMenu, System.in, System.out);
        purchasingMenu.setUserBalance(BigDecimal.valueOf(5.00));
        purchasingMenu.finishTransaction();

        Assert.assertTrue(Files.exists(destFilePath));
        String[] resultInTwoParts = Files.readString(destFilePath).split("M ");
        String result = resultInTwoParts[1];

        Assert.assertEquals(expected, result);
    }
}
