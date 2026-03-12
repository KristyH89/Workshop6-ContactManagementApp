package org.contactManagementApp;

import java.util.List;
import java.util.Scanner;

/**
 * Menu class handles user interaction for the Contact Management App.
 * It calls ContactDAO methods to perform all operations.
 */
public class Menu {

    private final ContactDAO dao = new ContactDAO();
    private final Scanner scanner = new Scanner(System.in);
    private final String DEFAULT_FILE = "contacts.txt";

    /**
     * Start the menu loop.
     */
    public void start() {
        loadContactsOnStartup();

        boolean running = true;

        while (running) {
            displayMenu();

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                choice = -1;
            }

            switch (choice) {
                case 1 -> addContact();
                case 2 -> searchByName();
                case 3 -> searchByMobile();
                case 4 -> displayAllContacts();
                case 5 -> sortContacts();
                case 6 -> deleteContact();
                case 7 -> updateContact();
                case 8 -> exportContacts();
                case 9 -> importContacts();
                case 0 -> {
                    saveContactsOnExit();
                    running = false;
                    System.out.println("Exiting...Goodbye and have a nice day!");
                }
                default -> System.out.println("Invalid option, please try again.");
            }

            System.out.println();
        }
    }

    private void displayMenu() {
        System.out.println("""
                === Contact Management ===
                1. Add Contact
                2. Search by Name
                3. Search by Mobile
                4. Display All Contacts
                5. Sort Contacts
                6. Delete Contact
                7. Update Contact
                8. Export Contacts
                9. Import Contacts
                0. Exit
                """);
        System.out.print("Choose an option: ");
    }

    private void loadContactsOnStartup() {
        int count = dao.loadFromFile(DEFAULT_FILE);
        if (count > 0) {
            System.out.println("Loaded " + count + " contact(s) from " + DEFAULT_FILE + "\n");
        } else if (count == 0) {
            System.out.println("No existing contacts found. Starting fresh.\n");
        } else {
            System.out.println("Failed to load contacts. Starting fresh.\n");
        }
    }

    private void saveContactsOnExit() {
        if (dao.getContactCount() > 0) {
            System.out.println("\nSaving contacts...");
            if (dao.saveToFile(DEFAULT_FILE)) {
                System.out.println("Contacts saved successfully.");
            } else {
                System.out.println("Failed to save contacts.");
            }
        }
    }

    private void addContact() {
        System.out.print("Enter name: ");
        String name = sanitizeInput(scanner.nextLine().trim(), "name");

        System.out.print("Enter mobile: ");
        String mobile = sanitizeInput(scanner.nextLine().trim(), "mobile");

        if (dao.addContact(name, mobile)) {
            System.out.println("Contact added successfully!");
        } else {
            System.out.println("Duplicate contact, not added.");
        }
    }

    private void searchByName() {
        System.out.print("Enter name to search: ");
        String name = scanner.nextLine().trim();

        List<String> results = dao.searchByName(name);

        if (results.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            System.out.println("Found " + results.size() + " contact(s):");
            for (String c : results) {
                System.out.println(ContactDAO.formatContact(c));
            }
        }
    }

    private void searchByMobile() {
        System.out.print("Enter mobile to search: ");
        String mobile = scanner.nextLine().trim();

        String result = dao.searchByMobile(mobile);
        if (result != null) {
            System.out.println(ContactDAO.formatContact(result));
        } else {
            System.out.println("No contact found with that mobile number.");
        }
    }

    private void displayAllContacts() {
        List<String> all = dao.getAllContacts();
        if (all.isEmpty()) {
            System.out.println("No contacts available.");
        } else {
            System.out.println("All contacts:");
            for (String c : all) {
                System.out.println(ContactDAO.formatContact(c));
            }
        }
    }

    private void sortContacts() {
        dao.sortContacts();
        System.out.println("Contacts sorted alphabetically!");
        displayAllContacts();
    }

    private void deleteContact() {
        System.out.println("Delete by: 1. Name  2. Mobile  3. Exact");
        System.out.print("Choose option: ");
        int option = Integer.parseInt(scanner.nextLine().trim());

        switch (option) {
            case 1 -> deleteByName();
            case 2 -> deleteByMobile();
            case 3 -> deleteExact();
            default -> System.out.println("Invalid option.");
        }
    }

    private void deleteByName() {
        System.out.print("Enter name to delete: ");
        String name = scanner.nextLine().trim();

        int deleted = dao.deleteByName(name);
        System.out.println("Deleted " + deleted + " contact(s).");
    }

    private void deleteByMobile() {
        System.out.print("Enter mobile to delete: ");
        String mobile = scanner.nextLine().trim();

        if (dao.deleteByMobile(mobile)) {
            System.out.println("Contact deleted successfully.");
        } else {
            System.out.println("No contact found with that mobile.");
        }
    }

    private void deleteExact() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter mobile: ");
        String mobile = scanner.nextLine().trim();

        if (dao.deleteContact(name, mobile)) {
            System.out.println("Contact deleted successfully.");
        } else {
            System.out.println("No exact contact found.");
        }
    }

    private void updateContact() {
        System.out.print("Enter current name: ");
        String oldName = scanner.nextLine().trim();
        System.out.print("Enter current mobile: ");
        String oldMobile = scanner.nextLine().trim();

        System.out.print("Enter new name (leave empty to keep same): ");
        String newName = scanner.nextLine().trim();
        if (newName.isEmpty()) newName = oldName;

        System.out.print("Enter new mobile (leave empty to keep same): ");
        String newMobile = scanner.nextLine().trim();
        if (newMobile.isEmpty()) newMobile = oldMobile;

        if (dao.updateContact(oldName, oldMobile, newName, newMobile)) {
            System.out.println("Contact updated successfully!");
        } else {
            System.out.println("Failed to update contact. Duplicate or not found.");
        }
    }

    private void exportContacts() {
        System.out.print("Enter filename to export (leave empty for default " + DEFAULT_FILE + "): ");
        String filename = scanner.nextLine().trim();
        if (filename.isEmpty()) filename = DEFAULT_FILE;

        if (dao.saveToFile(filename)) {
            System.out.println("Contacts exported successfully to " + filename);
        } else {
            System.out.println("Failed to export contacts.");
        }
    }

    private void importContacts() {
        System.out.print("Enter filename to import (leave empty for default " + DEFAULT_FILE + "): ");
        String filename = scanner.nextLine().trim();
        if (filename.isEmpty()) filename = DEFAULT_FILE;

        int count = dao.loadFromFile(filename);
        if (count >= 0) {
            System.out.println("Imported " + count + " contact(s) from " + filename);
        } else {
            System.out.println("Failed to import contacts.");
        }
    }

    /**
     * Remove '|' character from input (reserved delimiter).
     */
    private String sanitizeInput(String input, String fieldName) {
        if (input.contains("|")) {
            System.out.println("Warning: '|' is not allowed in " + fieldName + ". Removed automatically.");
            return input.replace("|", "");
        }
        return input;
    }
}