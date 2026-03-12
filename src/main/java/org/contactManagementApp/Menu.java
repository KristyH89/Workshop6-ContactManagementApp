package org.contactManagementApp;

import java.util.Scanner;

/**
 * Menu class handles user interaction.
 * It displays the menu, takes input, and calls ContactDAO methods.
 */

public class Menu {

    private ContactDAO dao = new ContactDAO(); // Our contact storage
    private Scanner scanner = new Scanner(System.in); // For reading user input

    public void start() {

        int option;

        do {
            System.out.println("""
            === Contact Management ===
            1. Add Contact
            2. Search by Name
            3. Search by Mobile
            4. Display All Contacts
            0. Exit
            """);

            System.out.print("Choose an option: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    addContact();                 // Add a new contact
                    break;
                case 2:
                    searchContactByName();        // Search by name
                    break;
                case 3:
                    searchContactByMobile();      // Search by mobile number
                    break;
                case 4:
                    dao.displayAllContacts();     // Show all contacts
                    break;
                case 0:
                    System.out.println("Exiting... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }

        } while (option != 0);          // Repeat until user exits
    }

    /**
     * Adds a new contact.
     * Checks for duplicate names before adding.
     */
    private void addContact() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter mobile: ");
        String mobile = scanner.nextLine();

        // Use ContactDAO to add the contact
        if (dao.addContact(name, mobile)) {
            System.out.println("Contact added!");
        } else {
            System.out.println("Contact already exists!");
        }
    }
// Searches a contact by name and prints the result.

    private void searchContactByName() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        String mobile = dao.searchByName(name);

        if (mobile != null) {
            System.out.println(name + " (" + mobile + ")");
        } else {
            System.out.println("Contact not found.");
        }
    }
// Searches a contact by mobile number and prints the result.
    private void searchContactByMobile() {
        System.out.print("Enter mobile: ");
        String mobile = scanner.nextLine();

        String name = dao.searchByMobile(mobile);

        if (name != null) {
            System.out.println(name + " (" + mobile + ")");
        } else {
            System.out.println("Contact not found.");
        }
    }
}