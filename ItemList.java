
/**
 * The ItemList class represents a doubly linked list of ItemInfoNode objects,
 * which store ItemInfo objects.
 * It contains references to the head, tail, and cursor nodes in the list.
 * This class is used to construct and manage a list of items with their
 * information.
 * 
 * The class provides methods to insert, move, check out, and remove items in
 * the list.
 * It also includes methods to print item information by location, RFID tag, and
 * all items in the list.
 * 
 * The class handles cases where the list is empty and checks for incorrect
 * inputs in the methods.
 * 
 * @author Shiv Kanani
 *         SBU ID: 115171965
 *         Homework #2 for CSE 214, Summer 2023
 **/
public class ItemList {

    private ItemInfoNode head; // Reference to the head node in the list
    private ItemInfoNode tail; // Reference to the tail node in the list
    private ItemInfoNode cursor; // Reference to the cursor node in the list

    /**
     * Constructs an empty ItemList object with default values for head, tail, and
     * cursor (all set to null).
     */
    public ItemList() {
        head = null;
        tail = null;
        cursor = null;
    }

    /**
     * Removes all purchased items from the list and prints their information.
     * The items with the current location "out" are considered purchased.
     * Analysis of Complexity:
     * Best Case: O(1) if all items are already in their original locations, so no
     * movement is required.
     * Worst Case: O(n) if we need to traverse the entire list to find and move the
     * items that need to be cleaned.
     * 
     */
    public void cleanStore() {
        ItemInfoNode findItem = head;
        System.out.println(String.format("%49s%18s", "Original", "Current"));
        System.out.println(String.format("%-22s%-19s%-19s%-21s%-10s",
                "Item Name", "RFID", "Location", "Location", "Price"));
        System.out.println(String.format("%-20s%-20s%-20s%-20s%-10s",
                "---------", "---------", "---------", "---------", "------"));

        while (findItem != null) {
            if (!findItem.getInfo().getCurrentLocation().equals("out") &&
                    !findItem.getInfo().getOriginalLocation().equals(findItem.getInfo().getCurrentLocation())) {
                System.out.println(String.format("%-20s%-22s%-19s%-20s%-5.2f",
                        findItem.getInfo().getItemName(),
                        findItem.getInfo().getRfidTagNumber(),
                        findItem.getInfo().getOriginalLocation(),
                        findItem.getInfo().getCurrentLocation(),
                        findItem.getInfo().getItemPrice()));

                // Change the current location to the original location
                findItem.getInfo().setCurrentLocation(findItem.getInfo().getOriginalLocation());
            }
            findItem = findItem.getNext();
        }
    }

    /**
     * Inserts an item into the list in a sorted manner based on the RFID tag
     * number.
     * Analysis of Complexity:
     * Best Case: O(1) if the list is empty, as the new node is directly inserted as
     * the head.
     * Worst Case: O(n) if the item's RFID tag number is greater than all existing
     * items' RFID tags,
     * and we need to traverse the entire list to find the correct position for
     * insertion.
     * 
     * @param name         the name of the item
     * @param rfidTag      the RFID tag number of the item
     * @param price        the price of the item
     * @param initPosition the initial position of the item
     * @throws Exception if the RFID tag number or location format is incorrect
     *                   during the item insertion
     */
    public void insertInfo(String name, String rfidTag, double price, String initPosition) throws Exception {
        ItemInfo newInfo = new ItemInfo(name, price, rfidTag, initPosition);
        ItemInfoNode newNode = new ItemInfoNode(newInfo);

        if (head == null) { // If the list is empty
            head = newNode;
            tail = newNode;
            cursor = newNode;
        } else {
            ItemInfoNode nodePtr = head;
            ItemInfoNode prevNode = null;

            while (nodePtr != null && rfidTag.compareToIgnoreCase(nodePtr.getInfo().getRfidTagNumber()) >= 0) {
                prevNode = nodePtr; // For duplicate rfidTags
                nodePtr = nodePtr.getNext();
            }

            if (prevNode != null) { // Insert after the same RFID tag
                newNode.setNext(prevNode.getNext());
                newNode.setPrev(prevNode);
                if (prevNode.getNext() != null) {
                    prevNode.getNext().setPrev(newNode);
                } else {
                    tail = newNode;
                }
                prevNode.setNext(newNode);
            } else { // If no duplicate RFID tag, insert at the beginning
                newNode.setNext(head);
                head.setPrev(newNode);
                head = newNode;
            }

            cursor = newNode; // Move the cursor to the newly inserted node.
        }
    }

    /**
     * Prints all the items' information that are located in the given location.
     * Analysis of Complexity:
     * Best Case: O(1) if there are no items at the specified location, so nothing
     * needs to be printed.
     * Worst Case: O(n) if we need to traverse the entire list to find items with
     * the specified location.
     * 
     * @param location the location to search for items
     */
    public void printByLocation(String location) {
        System.out.println(String.format("%49s%18s", "Original", "Current"));
        System.out.println(String.format("%-22s%-19s%-19s%-21s%-10s",
                "Item Name", "RFID", "Location", "Location", "Price"));
        System.out.println(String.format("%-20s%-20s%-20s%-20s%-10s",
                "---------", "---------", "---------", "---------", "------"));

        ItemInfoNode node = head;

        while (node != null) {
            if (node.getInfo().getCurrentLocation().equalsIgnoreCase(location)) {
                System.out.println(String.format("%-20s%-22s%-19s%-20s%-5.2f",
                        node.getInfo().getItemName(),
                        node.getInfo().getRfidTagNumber(),
                        node.getInfo().getOriginalLocation(),
                        node.getInfo().getCurrentLocation(),
                        node.getInfo().getItemPrice()));
            }
            node = node.getNext();
        }
    }

    /**
     * Moves an item from a source location to a destination location in the list.
     * Analysis of Complexity:
     * Best Case: O(1) if the item is already at the destination location or if it
     * is not found in the list.
     * Worst Case: O(n) if the item is found, and we need to traverse the entire
     * list to find the item to be moved.
     *
     * @param rfidTag the RFID tag number of the item to move
     * @param source  the source location of the item
     * @param dest    the destination location of the item
     * @return true if the item is successfully moved, false otherwise
     * @throws Exception if the destination location format is incorrect or it is "out" of the inventory.
     */
    public boolean moveItem(String rfidTag, String source, String dest) throws Exception {
        if (!ItemInfo.checkLocationFormat(dest))
            throw new IllegalLocationException("The destination location has an invalid format!");

        if (source.equals("out"))
            throw new IllegalLocationException("The source location is 'out'. We no longer have the item!");

        ItemInfoNode findItem = head;
        boolean itemFound = false;
        while (findItem != null) {
            if (findItem.getInfo().getRfidTagNumber().equals(rfidTag)) {
                cursor = findItem;
                if (!cursor.getInfo().getCurrentLocation().equals(source)) {
                    findItem = findItem.getNext();
                } else {
                    itemFound = true;
                    break;
                }
            }
            findItem = findItem.getNext();
        }

        if (itemFound && cursor.getInfo().getCurrentLocation().equals(source)) {
            cursor.getInfo().setCurrentLocation(dest);
            return itemFound;
        } else {
            return false;
        }
    }

    /**
     * Checks out all the items in a cart with the given cart number.
     * It sets the current location of the items to "out" and prints their
     * information.
     * Analysis of Complexity:
     * Best Case: O(1) if there are no items in the cart, so nothing needs to be
     * checked out.
     * Worst Case: O(n) if all items in the list are in the specified cart, and we
     * need to traverse the entire list
     * to find and check out the items in the cart.
     *
     * @param cartNumber the cart number to check out items from
     * @return the total price of the items checked out from the cart
     */
    public double checkOut(String cartNumber) {
        ItemInfoNode findItem = head;
        double cartPrice = 0;

        System.out.println(String.format("%49s%18s", "Original", "Current"));
        System.out.println(String.format("%-22s%-19s%-19s%-21s%-10s",
                "Item Name", "RFID", "Location", "Location", "Price"));
        System.out.println(String.format("%-20s%-20s%-20s%-20s%-10s",
                "---------", "---------", "---------", "---------", "------"));

        for (int i = 0; i < listLength(); i++) {
            if (findItem.getInfo().getCurrentLocation().equals(cartNumber)) {
                cartPrice = findItem.getInfo().getItemPrice();
                System.out.println(String.format("%-20s%-22s%-19s%-20s%-5.2f",
                        findItem.getInfo().getItemName(),
                        findItem.getInfo().getRfidTagNumber(),
                        findItem.getInfo().getOriginalLocation(),
                        findItem.getInfo().getCurrentLocation(),
                        findItem.getInfo().getItemPrice()));
                findItem.getInfo().setCurrentLocation("out");
            }
            findItem = findItem.getNext();
        }

        return cartPrice;
    }

    /**
     * Removes all purchased items from the list and prints their information.
     * The items with the current location "out" are considered purchased.
     * Analysis of Complexity:
     * Best Case: O(1) if there are no purchased items, so no removal is required.
     * Worst Case: O(n) if all items in the list are purchased, and we need to
     * traverse the entire list
     * to find and remove all purchased items.
     * 
     */
    public void removeAllPurchased() {
        ItemInfoNode removeNode = head;

        System.out.println(String.format("%49s%18s", "Original", "Current"));
        System.out.println(String.format("%-22s%-19s%-19s%-21s%-10s",
                "Item Name", "RFID", "Location", "Location", "Price"));
        System.out.println(String.format("%-20s%-20s%-20s%-20s%-10s",
                "---------", "---------", "---------", "---------", "------"));
        while (removeNode != null) {
            if (removeNode.getInfo().getCurrentLocation().equals("out")) {
                cursor = removeNode;
                System.out.println(String.format("%-20s%-22s%-19s%-20s%-5.2f",
                        removeNode.getInfo().getItemName(),
                        removeNode.getInfo().getRfidTagNumber(),
                        removeNode.getInfo().getOriginalLocation(),
                        removeNode.getInfo().getCurrentLocation(),
                        removeNode.getInfo().getItemPrice()));

                removeAllPurchasedHelper(cursor); // Removes the specific node
            }
            removeNode = removeNode.getNext();

        }
    }

    /**
     * Helper method to remove a specific node from the list.
     * It updates the previous and next nodes accordingly.
     *
     * @param cursorToRemoveNode the node to be removed from the list
     */
    public void removeAllPurchasedHelper(ItemInfoNode cursorToRemoveNode) {
        ItemInfoNode prevNode = cursorToRemoveNode.getPrev();
        ItemInfoNode nextNode = cursorToRemoveNode.getNext();

        if (prevNode != null)
            prevNode.setNext(nextNode);

        if (nextNode != null)
            nextNode.setPrev(prevNode);

        if (cursorToRemoveNode == head)
            head = nextNode;

        if (cursorToRemoveNode == tail)
            tail = prevNode;

        cursor = nextNode;
    }

    /**
     * Returns the number of items in the list.
     *
     * @return the number of items in the list
     */
    public int listLength() {
        ItemInfoNode nodePtr = head;
        int counter = 0;
        while (nodePtr != null) {
            counter++;
            nodePtr = nodePtr.getNext();
        }
        return counter;
    }

    /**
     * Prints information for all the items in the list.
     * Analysis of Complexity:
     * Best Case: O(1) if the list is empty, so nothing needs to be printed.
     * Worst Case: O(n) if we need to traverse the entire list to print information
     * for all items.
     */
    public void printAll() {
        System.out.println(String.format("%49s%18s", "Original", "Current"));
        System.out.println(String.format("%-22s%-19s%-19s%-21s%-10s",
                "Item Name", "RFID", "Location", "Location", "Price"));
        System.out.println(String.format("%-20s%-20s%-20s%-20s%-10s",
                "---------", "---------", "---------", "---------", "------"));

        ItemInfoNode node = head;

        while (node != null) { // iterates over every item in the list to print each item.
            if (node != null) { // if node is not null, it prints the item data.
                System.out.println(String.format("%-20s%-22s%-19s%-20s%-5.2f",
                        node.getInfo().getItemName(),
                        node.getInfo().getRfidTagNumber(),
                        node.getInfo().getOriginalLocation(),
                        node.getInfo().getCurrentLocation(),
                        node.getInfo().getItemPrice()));
            }
            node = node.getNext();
        }
    }

    /**
     * Prints information for the item with the given RFID tag number.
     *
     * @param rfidTagPrint the RFID tag number of the item to print
     */
    public void printByRFID(String rfidTagPrint) {
        System.out.println(String.format("%49s%18s", "Original", "Current"));
        System.out.println(String.format("%-22s%-19s%-19s%-21s%-10s",
                "Item Name", "RFID", "Location", "Location", "Price"));
        System.out.println(String.format("%-20s%-20s%-20s%-20s%-10s",
                "---------", "---------", "---------", "---------", "------"));

        ItemInfoNode printRfidNode = head;
        boolean found = false;

        while (printRfidNode != null) {
            if (printRfidNode.getInfo().getRfidTagNumber().equalsIgnoreCase(rfidTagPrint)) {
                System.out.println(String.format("%-20s%-22s%-19s%-20s%-5.2f",
                        printRfidNode.getInfo().getItemName(),
                        printRfidNode.getInfo().getRfidTagNumber(),
                        printRfidNode.getInfo().getOriginalLocation(),
                        printRfidNode.getInfo().getCurrentLocation(),
                        printRfidNode.getInfo().getItemPrice()));
                found = true;
            }
            printRfidNode = printRfidNode.getNext();
        }

        if (!found) {
            System.out.println("Item with RFID " + rfidTagPrint + " not found.");
        }
    }

    /**
     * Gets the reference to the head node in the list.
     *
     * @return The reference to the head node in the list
     */
    public ItemInfoNode getHead() {
        return head;
    }
}
