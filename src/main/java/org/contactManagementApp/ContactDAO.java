package org.contactManagementApp;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ContactDAO class handles storing and managing contacts.
 * Each contact is stored as a string in the format "name|mobile".
 */
public class ContactDAO {

    private final List<String> contacts;

    public ContactDAO() {
        contacts = new ArrayList<>();
    }

    /**
     * Normalize a mobile number by removing non-digit characters,
     * except a leading '+'.
     */
    private String normalizeMobile(String mobile) {
        if (mobile == null || mobile.isEmpty()) return mobile;

        boolean hasPlus = mobile.startsWith("+");
        String normalized = mobile.replaceAll("[^0-9]", "");
        if (hasPlus && !normalized.isEmpty()) {
            normalized = "+" + normalized;
        }
        return normalized;
    }

    /**
     * Add a new contact.
     * @return true if added, false if duplicate
     */
    public boolean addContact(String name, String mobile) {
        String normalizedMobile = normalizeMobile(mobile);
        String contact = name + "|" + normalizedMobile;

        if (isDuplicate(name, normalizedMobile)) return false;

        contacts.add(contact);
        return true;
    }

    /**
     * Check for exact duplicate (name + mobile).
     */
    private boolean isDuplicate(String name, String mobile) {
        for (String c : contacts) {
            String[] parts = c.split("\\|");
            if (parts.length == 2 && parts[0].equalsIgnoreCase(name)
                    && parts[1].equals(mobile)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Search contacts by name (case-insensitive, partial match).
     */
    public List<String> searchByName(String name) {
        List<String> results = new ArrayList<>();
        for (String c : contacts) {
            String[] parts = c.split("\\|");
            if (parts.length == 2 && parts[0].toLowerCase().contains(name.toLowerCase())) {
                results.add(c);
            }
        }
        return results;
    }

    /**
     * Search a contact by mobile number.
     * @return "name|mobile" string or null
     */
    public String searchByMobile(String mobile) {
        String normalizedMobile = normalizeMobile(mobile);
        for (String c : contacts) {
            String[] parts = c.split("\\|");
            if (parts.length == 2 && parts[1].equals(normalizedMobile)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Get all contacts as a list.
     */
    public List<String> getAllContacts() {
        return new ArrayList<>(contacts);
    }

    /**
     * Delete a contact by exact match of name + mobile.
     */
    public boolean deleteContact(String name, String mobile) {
        String normalizedMobile = normalizeMobile(mobile);
        return contacts.remove(name + "|" + normalizedMobile);
    }

    /**
     * Delete all contacts matching a given name (case-insensitive).
     * @return number of deleted contacts
     */
    public int deleteByName(String name) {
        List<String> toDelete = new ArrayList<>();
        for (String c : contacts) {
            String[] parts = c.split("\\|");
            if (parts.length == 2 && parts[0].equalsIgnoreCase(name)) {
                toDelete.add(c);
            }
        }
        contacts.removeAll(toDelete);
        return toDelete.size();
    }

    /**
     * Delete a contact by mobile number.
     */
    public boolean deleteByMobile(String mobile) {
        String normalizedMobile = normalizeMobile(mobile);
        for (String c : contacts) {
            String[] parts = c.split("\\|");
            if (parts.length == 2 && parts[1].equals(normalizedMobile)) {
                contacts.remove(c);
                return true;
            }
        }
        return false;
    }

    /**
     * Update a contact (name and/or mobile).
     * @return true if successful, false if old contact not found or duplicate
     */
    public boolean updateContact(String oldName, String oldMobile, String newName, String newMobile) {
        String normalizedOldMobile = normalizeMobile(oldMobile);
        String normalizedNewMobile = normalizeMobile(newMobile);
        String oldContact = oldName + "|" + normalizedOldMobile;

        if (!contacts.contains(oldContact)) return false;

        if (!oldContact.equals(newName + "|" + normalizedNewMobile)) {
            if (isDuplicate(newName, normalizedNewMobile)) return false;
        }

        int index = contacts.indexOf(oldContact);
        contacts.set(index, newName + "|" + normalizedNewMobile);
        return true;
    }

    /**
     * Sort contacts alphabetically by name.
     */
    public void sortContacts() {
        Collections.sort(contacts, (c1, c2) -> {
            String name1 = c1.split("\\|")[0];
            String name2 = c2.split("\\|")[0];
            return name1.compareToIgnoreCase(name2);
        });
    }

    /**
     * Format a contact for display: "Name (Mobile)".
     */
    public static String formatContact(String contact) {
        String[] parts = contact.split("\\|");
        if (parts.length == 2) {
            return parts[0] + " (" + parts[1] + ")";
        }
        return contact;
    }

    /**
     * Save contacts to a file in "name|mobile" format.
     */
    public boolean saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String c : contacts) {
                writer.write(c);
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.err.println("Error saving contacts: " + e.getMessage());
            return false;
        }
    }

    /**
     * Load contacts from a file (replaces existing list).
     * @return number of loaded contacts, -1 if error
     */
    public int loadFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) return -1;

        List<String> loaded = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && line.contains("|")) {
                    String[] parts = line.split("\\|");
                    if (parts.length == 2) loaded.add(line);
                }
            }
            contacts.clear();
            contacts.addAll(loaded);
            return loaded.size();
        } catch (IOException e) {
            System.err.println("Error loading contacts: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Get total number of contacts.
     */
    public int getContactCount() {
        return contacts.size();
    }
}