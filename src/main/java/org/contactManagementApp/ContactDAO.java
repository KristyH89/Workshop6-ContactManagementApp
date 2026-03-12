package org.contactManagementApp;

import java.util.HashMap;
import java.util.Map;

/**
 * ContactDAO class acts as a storage manager for all contacts.
 * It provides methods to add, search, and display contacts.
 * Internally, it uses a HashMap where:
 *   - key = name
 *   - value = mobile number
 */

public class ContactDAO {

    // HashMap to store contacts: name -> mobile
    private Map<String, String> contacts = new HashMap<>();

    /**
     * Adds a new contact to the contact list.
     * @param name the contact's name
     * @param mobile the contact's mobile number
     * @return true if the contact was added successfully, false if the name already exists
     */

    public boolean addContact(String name, String mobile) {
        if (contacts.containsKey(name)) {  // Check if the name already exists
            return false;                  // Prevent duplicate names
        }
        contacts.put(name, mobile);        // Add the new contact
        return true;
    }

    /**
     * Searches for a contact by name.
     * @param name the contact's name
     * @return the mobile number if found, null otherwise
     */
    public String searchByName(String name) {
        return contacts.get(name);        // HashMap allows fast lookup by key
    }
    /**
     * Searches for a contact by mobile number.
     * @param mobile the contact's mobile number
     * @return the name associated with the mobile number, or null if not found
     */

    public String searchByMobile(String mobile){
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            if (entry.getValue().equals(mobile)) {  // Compare each number
                return entry.getKey();              // Return the associated name
            }
        }
        return null;        // Not found
    }
//Displays all contacts in a readable format.
    public void displayAllContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
            return;
        }
// Loop through all contacts and print them
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            System.out.println(entry.getKey() + " (" + entry.getValue() + ")");
        }
    }
}