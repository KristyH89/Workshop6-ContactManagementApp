package org.contactManagementApp;

import java.util.HashMap;
import java.util.Map;

/**
 * ContactDAO manages all contacts using a HashMap.
 * Key = contact name, Value = mobile number.
 * Provides basic CRUD operations: add, search, delete, update.
 */
public class ContactDAO {

    // HashMap to store contacts: name -> mobile
    private final Map<String, String> contacts = new HashMap<>();

    /**
     * Add a new contact.
     * Rejects duplicate names.
     * @param name contact's name
     * @param mobile contact's mobile number
     * @return true if added, false if name already exists
     */
    public boolean addContact(String name, String mobile) {
        if (contacts.containsKey(name)) {
            return false; // duplicate name
        }
        contacts.put(name, mobile);
        return true;
    }

    /**
     * Search for a contact by name.
     * @param name contact name
     * @return mobile number if found, null otherwise
     */
    public String searchByName(String name) {
        return contacts.get(name);
    }

    /**
     * Search for a contact by mobile number.
     * @param mobile mobile number
     * @return contact name if found, null otherwise
     */
    public String searchByMobile(String mobile) {
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            if (entry.getValue().equals(mobile)) return entry.getKey();
        }
        return null;
    }

    /**
     * Display all contacts in a readable format.
     */
    public void displayAllContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts available.");
            return;
        }
        int i = 1;
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            System.out.println(i + ". " + entry.getKey() + " (" + entry.getValue() + ")");
            i++;
        }
    }

    /**
     * Delete a contact by name.
     * @param name contact name
     * @return true if deleted, false if not found
     */
    public boolean deleteByName(String name) {
        return contacts.remove(name) != null;
    }

    /**
     * Delete a contact by mobile number.
     * @param mobile mobile number
     * @return true if deleted, false if not found
     */
    public boolean deleteByMobile(String mobile) {
        String keyToRemove = null;
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            if (entry.getValue().equals(mobile)) {
                keyToRemove = entry.getKey();
                break;
            }
        }
        if (keyToRemove != null) {
            contacts.remove(keyToRemove);
            return true;
        }
        return false;
    }

    /**
     * Update an existing contact.
     * Prevents creating duplicate names.
     * @param oldName current contact name
     * @param newName new contact name
     * @param newMobile new mobile number
     * @return true if updated successfully, false otherwise
     */
    public boolean updateContact(String oldName, String newName, String newMobile) {
        if (!contacts.containsKey(oldName)) return false; // old contact not found
        if (!oldName.equals(newName) && contacts.containsKey(newName)) return false; // duplicate new name

        contacts.remove(oldName);
        contacts.put(newName, newMobile);
        return true;
    }

    /**
     * Get the number of contacts.
     * @return contact count
     */
    public int count() {
        return contacts.size();
    }
}