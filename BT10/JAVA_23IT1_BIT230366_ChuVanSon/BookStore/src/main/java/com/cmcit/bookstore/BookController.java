package com.cmcit.bookstore;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookController {
    private static final String DB_URL = "jdbc:sqlserver://CHUSON;databaseName=BookStore;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASS = "123456789";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    // 1. List books
    public List<Book> list(int sortType) { // Thay đổi tên phương thức thành "list"
        List<Book> books = new ArrayList<>();
        String query;

        switch (sortType) {
            case 1:
                query = "SELECT * FROM Book ORDER BY author";
                break;
            case 2:
                query = "SELECT * FROM Book ORDER BY publish";
                break;
            default:
                query = "SELECT * FROM Book ORDER BY title";
        }

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                books.add(new Book(
                    rs.getInt("id"), rs.getString("title"), rs.getString("author"),
                    rs.getString("publish"), rs.getString("isbn"), rs.getInt("categoryId")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books; // Trả về danh sách sách
    }

    // 2. Create a new book
    public void create(Book book) { // Thay đổi tên phương thức thành "create"
        String query = "INSERT INTO Book (title, author, publish, isbn, categoryId) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getPublish());
            pstmt.setString(4, book.getIsbn());
            pstmt.setInt(5, book.getCategoryId());
            pstmt.executeUpdate();

            System.out.println("Book created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. Update a book by id
    public void update(int id, Book book) { // Đã đúng theo yêu cầu
        String query = "UPDATE Book SET title = ?, author = ?, publish = ?, isbn = ?, categoryId = ? WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getPublish());
            pstmt.setString(4, book.getIsbn());
            pstmt.setInt(5, book.getCategoryId());
            pstmt.setInt(6, id);
            pstmt.executeUpdate();

            System.out.println("Book updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. Delete a book by id
    public void delete(int id) { // Thay đổi tên phương thức thành "delete"
        String query = "DELETE FROM Book WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Book deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 5. Find book by title or author
    public void find(String search) { // Thay đổi tên phương thức thành "find"
        String query = "SELECT * FROM Book WHERE title LIKE ? OR author LIKE ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "%" + search + "%");
            pstmt.setString(2, "%" + search + "%");

            ResultSet rs = pstmt.executeQuery();
            boolean found = false; // Biến kiểm tra có sách nào tìm thấy không
            while (rs.next()) {
                found = true;
                System.out.println("Book found: " + rs.getString("title") +
                        ", Author: " + rs.getString("author") +
                        ", Publish: " + rs.getString("publish") +
                        ", ISBN: " + rs.getString("isbn") +
                        ", Category ID: " + rs.getInt("categoryId"));
            }
            if (!found) {
                System.out.println("Not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 6. Find book by category
    public void findByCategory(String categoryName) { // Đã đúng theo yêu cầu
        String query = "SELECT b.* FROM Book b JOIN Category c ON b.categoryId = c.id WHERE c.name LIKE ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, "%" + categoryName + "%");

            ResultSet rs = pstmt.executeQuery();
            boolean found = false; // Biến kiểm tra có sách nào tìm thấy không
            while (rs.next()) {
                found = true;
                System.out.println("Book found: " + rs.getString("title") +
                        ", Author: " + rs.getString("author") +
                        ", Publish: " + rs.getString("publish") +
                        ", ISBN: " + rs.getString("isbn") +
                        ", Category ID: " + rs.getInt("categoryId"));
            }
            if (!found) {
                System.out.println("Not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
