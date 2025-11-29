package library.Model;

import java.util.ArrayList;
import java.util.List;

public class bookManager {
    private final List<Book> books;
    private int nextBookId;
    public bookManager(){
        this.books = new ArrayList<>();
        this.nextBookId = 1;
    }

    // Find book By id
    public Book findBookById(int bookId)
    {
        for(Book x: books)
        {
            if(x.getBookID() == bookId)
                return x;
        }
        return null;
    }
    // Add new Book
    public void addBook(String name, String genre, String author,
    int length, int year, int quantity){
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên sách không được để trống!");
        }
        if (genre == null || genre.trim().isEmpty()) {
            throw new IllegalArgumentException("Thể loại không được để trống!");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Tác giả không được để trống");
        }
        if (length <= 0) {
            throw new IllegalArgumentException("Độ dài sách phải lớn hơn 0");
        }
        if (year <= 0 || year > 2025) {
            throw new IllegalArgumentException("Năm xuất bản không hợp lệ!");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Số lượng sách phải lớn hơn 0");
        }
        
        Book book = new Book(nextBookId++, name, genre,author,length,year,quantity);
        books.add(book);
    }

    // Remove Book
    public void removeBook(int bookId)
    {
        Book x = findBookById(bookId);
        if(x != null)
            books.remove(x);
        else
            throw new IllegalArgumentException("Không tìm thấy sách với ID: " + bookId);
    }

    // Set Available
    public void setBookAvailable(int bookId, boolean available)
    {
        Book x = findBookById(bookId);
        if(x != null)
        {
            if(available) x.setAvailable();
            else x.setUnavailable();
        }
        else {
            throw new IllegalArgumentException("Không tìm thấy sách với ID: " + bookId);
        }
    }

    // Update book quantity
    public void updateBookQuantity(int bookId,int quantity)
    {
        if (quantity < 0) {
            throw new IllegalArgumentException("Số lượng sách không thể âm");
        }
        
        Book x = findBookById(bookId);
        if(x != null)
            x.setQuantity(quantity);
        else
            throw new IllegalArgumentException("Không tìm thấy sách với ID: " + bookId);
    }

    // Show all book
    public void showBooksInformation()
    {
        for(Book x: books)
        {
            x.displayInformation();
            System.out.println("================================");
        }
    }
    public List<Book> getBooks(){
        return new ArrayList<>(books);
    }
}