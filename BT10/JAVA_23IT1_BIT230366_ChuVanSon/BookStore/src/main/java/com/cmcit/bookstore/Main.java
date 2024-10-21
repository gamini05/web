package com.cmcit.bookstore;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BookController controller = new BookController();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("================ BOOK STORE ========================");
            System.out.println("1. List all books");
            System.out.println("2. Create a new book");
            System.out.println("3. Edit a book");
            System.out.println("4. Find book");
            System.out.println("5. Delete a book");
            System.out.println("6. Find book by category");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
                continue; // Restart the loop
            }

            switch (choice) {
                case 1 -> {
                    System.out.println("List of all books:");
                    controller.list(0).forEach(book -> 
                        System.out.println(book.getTitle()));
                }
                case 2 -> {
                    // Collect book data and call create()
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter publish date (yyyy-mm-dd): ");
                    String publish = scanner.nextLine();
                    System.out.print("Enter ISBN: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Enter category ID: ");
                    int categoryId = scanner.nextInt();
                    scanner.nextLine(); // Clear buffer

                    Book newBook = new Book(0, title, author, publish, isbn, categoryId);
                    controller.create(newBook);
                    System.out.println("Book created successfully!");
                }
                case 3 -> {
                    // Edit book functionality
                    System.out.print("Enter book ID to edit: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Clear buffer

                    System.out.print("Enter new title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter new author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter new publish date (yyyy-mm-dd): ");
                    String publish = scanner.nextLine();
                    System.out.print("Enter new ISBN: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Enter new category ID: ");
                    int categoryId = scanner.nextInt();

                    Book updatedBook = new Book(id, title, author, publish, isbn, categoryId);
                    controller.update(id, updatedBook);
                    System.out.println("Book updated successfully!");
                }
                case 4 -> {
                    System.out.print("Enter title or author to search: ");
                    String search = scanner.nextLine();
                    controller.find(search);
                }
                case 5 -> {
                    System.out.print("Enter book ID to delete: ");
                    int id = scanner.nextInt();
                    controller.delete(id);
                    System.out.println("Book deleted successfully!");
                }
                case 6 -> {
                    System.out.print("Enter category name to search: ");
                    String categorySearch = scanner.nextLine();
                    controller.findByCategory(categorySearch);
                }
                case 7 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice! Please enter a number between 1 and 7.");
            }
        }
    }
}
