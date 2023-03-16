package com.techelevator;

import com.techelevator.view.MainMenu;
import com.techelevator.view.PurchasingMenu;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String MAIN_MENU_SECRET_OPTION = "*Sales Report";

	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";

	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT, MAIN_MENU_SECRET_OPTION };
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};

	private MainMenu mainMenu;
	private PurchasingMenu purchasingMenu;

	public VendingMachineCLI(MainMenu mainMenu, PurchasingMenu purchasingMenu) {
		this.mainMenu = mainMenu;
		this.purchasingMenu = purchasingMenu;
	}

	public void run() {
		boolean running = true;
		while (running) {
			String choice = (String) mainMenu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			// A switch statement could also be used here.  Your choice.
			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				mainMenu.displayVendingMachineItems();
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
				boolean purchasing = true;
				while (purchasing) {
					System.out.println("Current Money Provided: $" + purchasingMenu.getUserBalance());
					String purchaseMenuChoice = (String) mainMenu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
					if (purchaseMenuChoice.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
						purchasingMenu.feedMoney();
					} else if (purchaseMenuChoice.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
						purchasingMenu.selectProduct();
					} else if (purchaseMenuChoice.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
						purchasingMenu.finishTransaction();
						purchasing = false;
					}
				}
			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				running = false;
			} else if (choice.equals(MAIN_MENU_SECRET_OPTION)) {
				mainMenu.generateSalesReport();
			}
		}
	}

	public static void main(String[] args) {
		MainMenu mainMenu = new MainMenu(System.in, System.out);
		PurchasingMenu purchasingMenu = new PurchasingMenu(mainMenu, System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(mainMenu, purchasingMenu);
		cli.run();
	}
}
