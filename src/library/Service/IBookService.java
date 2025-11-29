package library.Service;

import library.Model.Book;
import java.util.List;
import java.util.Map;
public interface IBookService {
    
    // ========== CRUD  ==========
    void addBook(String name, String genre, String author, 
                 int length, int year, int quantity);
    void removeBook(int bookId);
    void updateBook(int bookId, String name, String genre, String author,
        int length, int year, int quantity);
    
    // ========== Search Operations ==========
    
    Book findBookById(int bookId);
    List<Book> searchByName(String name);
    List<Book> searchByAuthor(String author);
    List<Book> searchByGenre(String genre);
    List<Book> getAllBooks();
    
    // ========== Update Operations ==========
    void updateBookQuantity(int bookId, int quantity);
    void setBookAvailable(int bookId, boolean available);
    
    // ========== Statistics ==========
    int getTotalBooks();
    int getAvailableBooks();
    Map<String, Integer> getBookCountByGenre();
}