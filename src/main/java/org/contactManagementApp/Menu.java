package org.contactManagementApp;

import java.util.Scanner;

/**
 * Menu handles user interaction.
 * It displays the menu, gets input, and calls ContactDAO methods.
 */
public class Menu {

    private final ContactDAO dao = new ContactDAO(); // Contact storage
    private final Scanner scanner = new Scanner(System.in); // User input

    /**
     * Start the menu loop until the user exits.
     */
    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("""
                === Contact Management ===
                1. Add Contact
                2. Search by Name
                3. Search by Mobile
                4. Display All Contacts
                5. Delete by Name
                6. Delete by Mobile
                7. Update Contact
                0. Exit
                """);
            System.out.print("Choose an option: ");
            int choice;
            try { choice = Integer.parseInt(scanner.nextLine()); }
            catch (Exception e) { choice = -1; }

            switch (choice) {
                case 1 -> addContact();          // Add a new contact
                case 2 -> searchByName();        // Search by name
                case 3 -> searchByMobile();      // Search by mobile
                case 4 -> dao.displayAllContacts(); // Show all contacts
                case 5 -> deleteByName();        // Delete by name
                case 6 -> deleteByMobile();      // Delete by mobile
                case 7 -> updateContact();       // Update contact
                case 0 -> {
                    running = false;
                    System.out.println("Exiting...Goodbye!");
                }
                default -> System.out.println("Invalid option.");
            }
            System.out.println();
        }
    }

    // Methods with English comments

    private void addContact() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter mobile: ");
        String mobile = scanner.nextLine();
        if (dao.addContact(name, mobile)) System.out.println("Contact added!");
        else System.out.println("Duplicate name, contact not added.");
    }

    private void searchByName() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        String mobile = dao.searchByName(name);
        if (mobile != null) System.out.println(name + " (" + mobile + ")");
        else System.out.println("Contact not found.");
    }

    private void searchByMobile() {
        System.out.print("Enter mobile: ");
        String mobile = scanner.nextLine();
        String name = dao.searchByMobile(mobile);
        if (name != null) System.out.println(name + " (" + mobile + ")");
        else System.out.println("Contact not found.");
    }

    private void deleteByName() {
        System.out.print("Enter name to delete: ");
        String name = scanner.nextLine();
        if (dao.deleteByName(name)) System.out.println("Deleted contact.");
        else System.out.println("No contact found.");
    }

    private void deleteByMobile() {
        System.out.print("Enter mobile to delete: ");
        String mobile = scanner.nextLine();
        if (dao.deleteByMobile(mobile)) System.out.println("Deleted contact.");
        else System.out.println("No contact found.");
    }

    private void updateContact() {
        System.out.print("Enter existing name: ");
        String oldName = scanner.nextLine();
        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();
        System.out.print("Enter new mobile: ");
        String newMobile = scanner.nextLine();
        if (dao.updateContact(oldName, newName, newMobile)) System.out.println("Contact updated!");
        else System.out.println("Update failed (duplicate or not found).");
    }
}