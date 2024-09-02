import java.util.Scanner;

/**
 * The DepartmentStore class is the main class for running the Department Store
 * Menu program.
 * It's a menu-driven program that performs various operations on the item list,
 * such as
 * inserting items, moving items, printing items by location or RFID tag,
 * checking out items from
 * a cart, and updating the inventory system. The class utilizes the ItemList
 * class to manage the
 * list of items in the store. The main method prompts the user for input and
 * calls corresponding
 * methods from the ItemList class to perform the operations. The menu keeps
 * running until the user
 * chooses to exit the program by selecting "Q".
 * 
 * Note: The class handles various exceptions and ensures that user inputs are
 * valid before performing
 * the operations on the item list.
 * 
 * @author Shiv Kanani
 *         SBU ID: 115171965
 *         Homework #2 for CSE 214, Summer 2023
 **/
public class DepartmentStore {

    /**
     * The main method for running the Department Store Menu program.
     * Prompts the user to select a menu option and performs the corresponding
     * operation.
     * 
     * @param args command-line arguments (not used in this program)
     * @throws IllegalLocationException      if the Location format is incorrect
     * @throws IllegalRFidTagNumberException if the RFidTagNumber format is
     *                                       incorrect
     */
    public static void main(String[] args) throws Exception {

        Scanner input = new Scanner(System.in);
        boolean flag = true;
        ItemList itemList = new ItemList();

        System.out.println("Welcome to our Department Store Menu!");
        System.out.println("");

        try {
            while (flag) { // Will keep the menu keep running until the user chooses to quit using Case
                           // "Q".
                System.out.println("C - Clean store");
                System.out.println("I - Insert an item into the list");
                System.out.println("L - List by location");
                System.out.println("M - Move an item in the store");
                System.out.println("O - Checkout");
                System.out.println("P - Print all items in store");
                System.out.println("R - Print by RFID tag number");
                System.out.println("U - Update inventory system");
                System.out.println("Q - Exit the program");
                System.out.println("");
                System.out.println("Please select an option: ");

                String option = input.nextLine();

                switch (option.toUpperCase()) {

                    case "C":
                        System.out.println("The following item(s) have been moved back to their original locations: ");
                        System.out.println("");
                        itemList.cleanStore();
                        System.out.println("Select a menu option: ");
                        System.out.println("");
                        break;

                    case "I":
                        System.out.println("Enter the name: ");
                        String itemName = input.nextLine();
                        System.out.println("Enter the RFID: ");
                        String rfidTag = input.nextLine();
                        System.out.println("Enter the original location: ");
                        String originalLocation = input.nextLine();
                        System.out.println("Enter the price:");
                        double price = input.nextDouble();
                        input.nextLine();

                        if (!ItemInfo.checkLocationFormat(originalLocation))
                            throw new IllegalLocationException(
                                    "The destination location has incorrect format! Please try again!");
                        if (!ItemInfo.checkRfidFormat(rfidTag))
                            throw new IllegalRFidTagNumberException(
                                    "The RFID tag number has incorrect format! Please try again!");

                        else {
                            itemList.insertInfo(itemName, rfidTag, price, originalLocation);
                        }
                        System.out.println("The item has been successfully added to the list.");
                        System.out.println("");
                        System.out.println("Select a menu option: ");
                        System.out.println("");
                        continue;

                    case "L":
                        System.out.println("Enter the location: ");
                        String findCurrentLocation = input.nextLine();
                        if (!ItemInfo.checkLocationFormat(findCurrentLocation))
                            throw new IllegalLocationException(
                                    "The location has incorrect format! Please try again!");
                        else
                            itemList.printByLocation(findCurrentLocation);
                        System.out.println("Select a menu option: ");
                        System.out.println("");
                        continue;

                    case "M":
                        System.out.println("Enter the RFID: ");
                        String rfidTagNumber = input.nextLine();
                        System.out.println("Enter the original location: ");
                        String firstLocation = input.nextLine();
                        System.out.println("Enter the new location: ");
                        String newLocation = input.nextLine();

                        ItemInfoNode current = itemList.getHead();
                        boolean rfidTagFound = false;

                        while (current != null) {
                            ItemInfo item = current.getInfo();
                            if (item.getRfidTagNumber().equalsIgnoreCase(rfidTagNumber)) {
                                rfidTagFound = true;
                                break;
                            }
                            current = current.getNext();
                        }

                        if (!rfidTagFound) {
                            System.out.println("There is no item with the given RFID!");
                            System.out.println("Select a menu option: ");
                            System.out.println("");
                            continue;
                        }

                        if (firstLocation.equals("out") || newLocation.equals("out")) {
                            System.out.println("The item is no longer in the store!");
                            System.out.println("Select a menu option: ");
                            System.out.println("");
                            continue;
                        } else {
                            itemList.moveItem(rfidTagNumber, firstLocation, newLocation);
                            System.out.println("");
                            System.out.println("The item has been moved!");
                        }

                        System.out.println("Select a menu option: ");
                        System.out.println("");
                        continue;

                    case "O":
                        System.out.println("Enter the cart number: ");
                        String cartNumber = input.nextLine();
                        if (!cartNumber.matches("(?i)c\\d{3}"))
                            throw new IllegalLocationException(
                                    "The location should be a cart number in correct format! Please try again!");
                        double cartTotal = itemList.checkOut(cartNumber);
                        System.out
                                .println("The total cost for all merchandise in " + cartNumber + " was $" + cartTotal);
                        System.out.println("Select a menu option: ");
                        System.out.println("");
                        continue;

                    case "P":
                        itemList.printAll();
                        System.out.println("");
                        System.out.println("Select a menu option: ");
                        System.out.println("");
                        continue;

                    case "R":
                        System.out.println("Enter the RFID: ");
                        String rfidTagPrint = input.nextLine();

                        itemList.printByRFID(rfidTagPrint);
                        System.out.println("");
                        System.out.println("Select a menu option: ");
                        System.out.println("");
                        continue;

                    case "U":
                        System.out.println("The following item(s) have removed from the system: ");
                        System.out.println("");
                        itemList.removeAllPurchased();
                        System.out.println("Select a menu option: ");
                        System.out.println("");
                        continue;

                    case "Q": // Quit
                        System.out.println("Thank you for using our Department Store! ");
                        flag = false;
                        input.close();
                        break;
                    default:
                        if (!option.equalsIgnoreCase("Q")) {
                            System.out.println("Invalid option");
                        }
                        break;
                }
            }
        } catch (IllegalRFidTagNumberException | IllegalLocationException e) {
            System.out.println("Your RFID tag or location format is incorrect! Please try again!");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred. Please check your inputs are correct!");
        }
    }
}
