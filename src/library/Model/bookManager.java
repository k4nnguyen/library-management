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
            throw new IllegalArgumentException("Ten sach khong duoc de trong");
        }
        if (genre == null || genre.trim().isEmpty()) {
            throw new IllegalArgumentException("The loai khong duoc de trong");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Tac gia khong duoc de trong");
        }
        if (length <= 0) {
            throw new IllegalArgumentException("Do dai sach phai lon hon 0");
        }
        if (year <= 0 || year > 2025) {
            throw new IllegalArgumentException("Nam xuat ban khong hop le");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("So luong sach phai lon hon 0");
        }
        
        Book book = new Book(nextBookId++, name, genre,author,length,year,quantity,true);
        books.add(book);
    }

    // Remove Book
    public void removeBook(int bookId)
    {
        Book x = findBookById(bookId);
        if(x != null)
            books.remove(x);
        else
            throw new IllegalArgumentException("Khong tim thay sach voi ID: " + bookId);
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
            throw new IllegalArgumentException("Khong tim thay sach voi ID: " + bookId);
        }
    }

    // Update book quantity
    public void updateBookQuantity(int bookId,int quantity)
    {
        if (quantity < 0) {
            throw new IllegalArgumentException("So luong sach khong the am");
        }
        
        Book x = findBookById(bookId);
        if(x != null)
            x.setQuantity(quantity);
        else
            throw new IllegalArgumentException("Khong tim thay sach voi ID: " + bookId);
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