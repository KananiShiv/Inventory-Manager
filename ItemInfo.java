
/**
 * The ItemInfo class is used to represent an item with its itemName, itemPrice, rfidTagNumber, originalLocation, and currentLocation.
 * The methods in this class are used to set and retrieve the properties of an item.
 * 
 * @author Shiv Kanani
 * SBU ID: 115171965
 * Homework #2 for CSE 214, Summer 2023
 **/
public class ItemInfo {

    private String itemName; // The name of the item
    private double itemPrice; // The price of the item
    private String rfidTagNumber; // The RFID tag number of the item
    private String originalLocation; // The original location of the item
    private String currentLocation; // The current location of the item

    /**
     * Constructs an empty ItemInfo object.
     */
    public ItemInfo() {
    }

    /**
     * Constructs an ItemInfo object with the specified itemName, itemPrice, rfidTagNumber, and originalLocation.
     *
     * @param itemName         the name of the item
     * @param itemPrice        the price of the item
     * @param rfidTagNumber    the RFID tag number of the item
     * @param originalLocation the original location of the item
     * @throws IllegalRFidTagNumberException if the RFID tag number is incorrect
     * @throws IllegalLocationException     if the location format is incorrect
     */
    public ItemInfo(String itemName, double itemPrice, String rfidTagNumber, String originalLocation)
            throws Exception {
        this.itemName = itemName;
        this.itemPrice = itemPrice;

        // Check if the RFID tag number format is correct
        boolean rfidFormat = checkRfidFormat(rfidTagNumber);
        if (!rfidFormat)
            throw new IllegalRFidTagNumberException("Incorrect rfidTagNumber! Please try again");
        else
            this.rfidTagNumber = rfidTagNumber;

        // Check if the original location format is correct
        boolean locationFormat = checkLocationFormat(originalLocation);
        if (!locationFormat)
            throw new IllegalLocationException("Incorrect Location! Please try again!");
        else {
            this.originalLocation = originalLocation;
            this.currentLocation = originalLocation;
        }
    }

    // Getters and setters for the properties of the item

    /**
     * Gets the name of the item.
     *
     * @return The name of the item
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Sets the name of the item.
     *
     * @param itemName The name of the item
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Gets the price of the item.
     *
     * @return The price of the item
     */
    public double getItemPrice() {
        return itemPrice;
    }

    /**
     * Sets the price of the item.
     *
     * @param itemPrice The price of the item
     */
    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    /**
     * Gets the RFID tag number of the item.
     *
     * @return The RFID tag number of the item
     */
    public String getRfidTagNumber() {
        return rfidTagNumber;
    }

    /**
     * Sets the RFID tag number of the item.
     *
     * @param rfidTagNumber The RFID tag number of the item
     */
    public void setRfidTagNumber(String rfidTagNumber) {
        this.rfidTagNumber = rfidTagNumber;
    }

    /**
     * Gets the original location of the item.
     *
     * @return The original location of the item
     */
    public String getOriginalLocation() {
        return originalLocation;
    }

    /**
     * Sets the original location of the item.
     *
     * @param originalLocation The original location of the item
     */
    public void setOriginalLocation(String originalLocation) {
        this.originalLocation = originalLocation;
    }

    /**
     * Gets the current location of the item.
     *
     * @return The current location of the item
     */
    public String getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Sets the current location of the item.
     *
     * @param currentLocation The current location of the item
     */
    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    /**
     * Checks if the given RFID tag number format is valid.
     *
     * @param rfidString the RFID tag number to be checked
     * @return true if the format is valid, false otherwise
     */
    public static boolean checkRfidFormat(String rfidString) {
        if (rfidString.length() != 9) {
            return false;
        }

        try {
            Integer.parseInt(rfidString);
            return false;
        } catch (NumberFormatException e) {
        }

        for (char ch : rfidString.toCharArray()) {
            if (!((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'F') || (ch >= 'a' && ch <= 'f'))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the given location format is valid.
     *
     * @param location the location to be checked
     * @return true if the format is valid, false otherwise
     */
    public static boolean checkLocationFormat(String location) {
        if (location.matches("(?i)s\\d{5}")) {
            return true;
        }

        if (location.matches("(?i)c\\d{3}")) {
            return true;
        }

        if (location.equalsIgnoreCase("out")) {
            return true;
        }

        return false;
    }

}
