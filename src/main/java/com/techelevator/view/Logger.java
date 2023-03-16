package com.techelevator.view;

import com.techelevator.items.VendingMachineItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    public Logger() {

    }

    public void logAction(String actionType, BigDecimal transactionAmount, BigDecimal userBalance) {
        LocalDate date = LocalDate.now();

        String pattern = "hh:mm:ss a";
        LocalTime now = LocalTime.now();
        String time = now.format(DateTimeFormatter.ofPattern(pattern));

        File logFile = new File("Log.txt");
        boolean append = logFile.exists();
        try(PrintWriter logWriter = new PrintWriter(new FileOutputStream(logFile, append))) {
            logWriter.print(date + " " + time + " ");
            switch(actionType){
                case("feedMoney"):
                    logWriter.print("FEED MONEY: ");
                    break;
                case("finishTransaction"):
                    logWriter.print("GIVE CHANGE: ");
                    break;
            }
            logWriter.print("$" + transactionAmount.setScale(2, RoundingMode.HALF_UP) + " $" + userBalance + "\n");
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist");;
        }
    }

    public void logAction(VendingMachineItem item, BigDecimal transactionAmount, BigDecimal userBalance) {
        LocalDate date = LocalDate.now();

        String pattern = "hh:mm:ss a";
        LocalTime now = LocalTime.now();
        String time = now.format(DateTimeFormatter.ofPattern(pattern));

        File logFile = new File("Log.txt");
        boolean append = logFile.exists();
        try(PrintWriter logWriter = new PrintWriter(new FileOutputStream(logFile, append))) {
            logWriter.print(date + " " + time + " ");
            logWriter.print(item.getItemName()+ " " + item.getSlotNumber() + " ");
            logWriter.print("$" + transactionAmount.setScale(2, RoundingMode.HALF_UP) + " $" + userBalance + "\n");
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist");;
        }
    }
}
