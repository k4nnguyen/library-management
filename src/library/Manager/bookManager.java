package library.Manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import library.Model.Book;
import library.Model.Loan;
import library.Service.IBookService;

public class bookManager implements IBookService{
    private final List<Book> books;
    private int nextBookId;
    // optional reference to loanManager to enforce invariants
    private loanManager loanMgr;
    
    public bookManager(){
        this.books = new ArrayList<>();
        this.nextBookId = 1;
    }

    // Construct with existing books (e.g., loaded from persistence)
    public bookManager(List<Book> initialBooks){
        this.books = new ArrayList<>();
        if(initialBooks != null) this.books.addAll(initialBooks);
        // compute next id based on max existing id
        this.nextBookId = this.books.stream().mapToInt(Book::getBookID).max().orElse(0) + 1;
    }

    // Add new Book
    @Override
    public void addBook(String name, String genre, String author,
    int length, int year, int quantity){
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên sách không được để trống!");
        }
        if (genre == null || genre.trim().isEmpty()) {
            throw new IllegalArgumentException("Thể loại không được để trống!");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Tác giả không được để trống!");
        }
        if (length <= 0) {
            throw new IllegalArgumentException("Độ dài sách phải lớn hơn 0");
        }
        if (year <= 0 || year > 2025) {
            throw new IllegalArgumentException("Năm xuất bản không hợp lệ");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Số lượng sách phải lớn hơn 0");
        }
        
        Book book = new Book(nextBookId++, name, genre,author,length,year,quantity);
        books.add(book);
    }

    // Remove Book
    @Override
    public void removeBook(int bookId)
    {
        Book x = findBookById(bookId);
        if(x != null)
            books.remove(x);
        else
            throw new IllegalArgumentException("Không tìm thấy sách với ID: " + bookId);
    }

    // Update Book
    @Override
    public void updateBook(int bookId, String name, String genre, String author,
                          int length, int year, int quantity) {
        Book book = findBookById(bookId);
        if(book == null) {
            throw new IllegalArgumentException("Không tìm thấy sách với ID: " + bookId);
        }
        // Validate quantity against active borrows if loan manager is available
        int activeBorrows = 0;
        if (loanMgr != null) {
            List<Loan> loansForBook = loanMgr.getLoansByBook(book);
            activeBorrows = (int) loansForBook.stream().filter(l -> !l.isReturned()).count();
        }
        if (quantity < activeBorrows) {
            throw new IllegalArgumentException("Số lượng mới không thể nhỏ hơn số sách đang được mượn: " + activeBorrows);
        }

        book.setBookName(name);
        book.setGenre(genre);
        book.setAuthor(author);
        book.setBookLength(length);
        book.setPublishYear(year);
        book.setQuantity(quantity);
    }
    
    // Set Available
    @Override
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

    // Update Book Quantity
    @Override
    public void updateBookQuantity(int bookId,int quantity)
    {
        if (quantity < 0) {
            throw new IllegalArgumentException("Số lượng sách không thể âm");
        }
        Book x = findBookById(bookId);
        if(x != null) {
            int activeBorrows = 0;
            if (loanMgr != null) {
                List<Loan> loansForBook = loanMgr.getLoansByBook(x);
                activeBorrows = (int) loansForBook.stream().filter(l -> !l.isReturned()).count();
            }
            if (quantity < activeBorrows) {
                throw new IllegalArgumentException("Số lượng mới không thể nhỏ hơn số sách đang được mượn: " + activeBorrows);
            }
            x.setQuantity(quantity);
        } else
            throw new IllegalArgumentException("Không tìm thấy sách với ID: " + bookId);
    }

    // Optional setter to inject loanManager for enforcing invariants
    public void setLoanManager(loanManager lm) {
        this.loanMgr = lm;
    }
    
    // ======= Find Book function =======
    // Find book By id
    @Override
    public Book findBookById(int bookId)
    {
        for(Book x: books)
        {
            if(x.getBookID() == bookId)
                return x;
        }
        return null;
    }
    
   
    @Override
    public List<Book> searchByName(String name) {
        if(name == null || name.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String searchTerm = name.toLowerCase().trim();
        return books.stream()
            .filter(b -> b.getBookName().toLowerCase().contains(searchTerm))
            .collect(Collectors.toList());
    }
    
   
    @Override
    public List<Book> searchByAuthor(String author) {
        if(author == null || author.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String searchTerm = author.toLowerCase().trim();
        return books.stream()
            .filter(b -> b.getAuthor().toLowerCase().contains(searchTerm))
            .collect(Collectors.toList());
    }
    
    
    @Override
    public List<Book> searchByGenre(String genre) {
        if(genre == null || genre.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return books.stream()
            .filter(b -> b.getGenre().equalsIgnoreCase(genre.trim()))
            .collect(Collectors.toList());
    }
    
    // ======= Get Information =======
    
    @Override
    public int getTotalBooks() {
        return books.stream()
            .mapToInt(Book::getQuantity)
            .sum();
    }

    @Override
    public List<Book> getAllBooks() {
        return books; 
    }
    
    @Override
    public int getAvailableBooks() {
        return books.stream()
            .filter(Book::isAvailable)
            .mapToInt(Book::getQuantity)
            .sum();
    }
    
    @Override
    public Map<String, Integer> getBookCountByGenre() {
        return books.stream()
            .collect(Collectors.groupingBy(
                Book::getGenre,
                Collectors.summingInt(Book::getQuantity)
            ));
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