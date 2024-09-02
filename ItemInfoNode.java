
/**
 * The ItemInfoNode class represents a node in a doubly linked list, which stores an ItemInfo object.
 * It contains references to the next and previous nodes in the list.
 * This class is used to construct a doubly linked list of ItemInfo objects.
 * 
 * An ItemInfoNode object holds an ItemInfo object and has pointers to its next and previous nodes in the list.
 * The methods in this class are used to set and retrieve the properties of a node in the list.
 *
 * @author Shiv Kanani
 * SBU ID: 115171965
 * Homework #2 for CSE 214, Summer 2023
 **/

public class ItemInfoNode {

    private ItemInfo info; // The ItemInfo object stored in the node
    private ItemInfoNode next; // Reference to the next node in the list
    private ItemInfoNode prev; // Reference to the previous node in the list

    /**
     * Constructs an empty ItemInfoNode object with default values for info, next, and prev.
     * The info object is initialized with a new empty ItemInfo object.
     */
    public ItemInfoNode() {
        info = new ItemInfo();
        next = null;
        prev = null;
    }

    /**
     * Constructs an ItemInfoNode object with the given ItemInfo object and default values for next and prev.
     *
     * @param data The ItemInfo object to be stored in the node
     */
    public ItemInfoNode(ItemInfo data) {
        info = data;
        next = null;
        prev = null;
    }

    /**
     * Gets the ItemInfo object stored in the node.
     *
     * @return The ItemInfo object stored in the node
     */
    public ItemInfo getInfo() {
        return info;
    }

    /**
     * Sets the ItemInfo object to be stored in the node.
     *
     * @param info The ItemInfo object to be stored in the node
     */
    public void setInfo(ItemInfo info) {
        this.info = info;
    }

    /**
     * Gets the reference to the next node in the list.
     *
     * @return The reference to the next node in the list
     */
    public ItemInfoNode getNext() {
        return next;
    }

    /**
     * Sets the reference to the next node in the list.
     *
     * @param next The reference to the next node in the list
     */
    public void setNext(ItemInfoNode next) {
        this.next = next;
    }

    /**
     * Gets the reference to the previous node in the list.
     *
     * @return The reference to the previous node in the list
     */
    public ItemInfoNode getPrev() {
        return prev;
    }

    /**
     * Sets the reference to the previous node in the list.
     *
     * @param prev The reference to the previous node in the list
     */
    public void setPrev(ItemInfoNode prev) {
        this.prev = prev;
    }
}
