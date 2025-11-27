package library.Model;

import java.util.*;

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
        Book book = new Book(nextBookId++, name, genre,author,length,year,quantity,true);
        books.add(book);
    }

    // Remove Book
    public void removeBook(int bookId)
    {
        Book x = findBookById(bookId);
        if(x != null)
            books.remove(bookId);
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
    }

    // Update book quantity
    public void updateBookQuantity(int bookId,int quantity)
    {
        Book x = findBookById(bookId);
        if(x != null)
            x.setQuantity(quantity);
    }

    // Show all book
    public void showBooksInformation()
    {
        for(Book x: books)
        {
            x.getInformation();
            System.out.println("================================");
        }
    }
    public List<Book> getBooks(){
        return books;
    }
}
