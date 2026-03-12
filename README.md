![Lexicon Logo](https://lexicongruppen.se/media/wi5hphtd/lexicon-logo.svg)

# Workshop: Contact Management App

## Objective

Your task is to develop a console-based Java application that manages contacts stored in the following format:
`name|mobile`

You will implement operations such as adding, searching, listing contacts, and more.

**You must choose one implementation approach**:

- [ ] Option 1: Use a `String[]` Array
- [ ] Option 2: Use a Java Collection (`ArrayList<String>, HashSet<String>, or HashMap<String, String>`)

---

## Instructions

1. Set up a new Maven project.
2. Name the project appropriately, e.g., `ContactManagementApp`.
3. Push the project to a GitHub repository.
4. Share the repository link with your instructor.
5. Implement the workshop tasks.

---

## Choose One Implementation Approach

### Option 1: String Array

- Store contacts in a String[]
- Manually resize the array when full
- Shift elements when deleting
- Use loops for searching, updating, and checking duplicates

### Option 2: Collections

You may use one of the following collections:

- ArrayList<String>
- HashSet<String>
- HashMap<String, String>

---

## Application Requirements

### Menu Display

Your program must display a menu like:

=== Contact Management ===

**1**. Add Contact  
**2**. Search by Name  
**3**. Display All Contacts  
**0**. Exit  
Choose an option:  


The menu must repeat until Exit is selected.

---

### Contact Format

Every contact must always be stored as:
`name|mobile`

---

### Add a New Contact

- Ask for name
- Ask for a mobile number
- Combine into: name|mobile
- Reject duplicates
- Save the new contact

---

### Searching

Search by Name:

- Print all matching contacts

Search by Mobile Number:

- Print the matching contact

---

### List All Contacts

Display contacts in a readable format, for example:

1. Mehrdad Javan (0712345678)
2. Elnaz Azizi (0798765432)
3. Simon Elbrink (0789456123)

---

### Code Structure

Your program should:

- Create a class for Contacts named ContactDAO or similar
- Use methods for each operation
- Use clear naming
- Keep logic organized
- Include helpful comments

---

## Example Flow (Conceptual)

=== Contact Management ===

**1**. Add Contact  
**2**. Search by Name  
**3**. Display All Contacts  
**0**. Exit  

Choose an option: 1  
Enter name: Mehrdad Javan  
Enter mobile: 0712345678  
Contact added successfully!

---

## Optional Features

If you finish the core functionality, you can extend the application with one or more of these features:

- **Sorting**

  - Sort contacts alphabetically by name.

- **Delete Contacts**

  - Allow deletion by name and/or mobile number.
  - Display the matching contact(s) before deletion.
  - Ask the user for confirmation to prevent accidental deletions.

- **Update Contacts**

  - Enable updating of a contact’s name, mobile number, or both.
  - Reuse existing validation rules (e.g., avoid creating duplicates).
  - Show the original contact and the updated result for clarity.

- **Unit Testing**

  - Write unit tests for each data access operation.

- **File I/O (Export / Import)**
  - **Export:** Save contacts to a file in one of the following formats:
    - `name|mobile` (plain text)
    - CSV
    - Excel (`.xlsx`)
  - **Import:** Load existing contacts from a file when the application starts.

---

Good luck!
