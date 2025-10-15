package library.Model;
// ======================= AN =============================
public class Book {
    private String bookName;
    private String bookID;
    private String genre;
    private String author;
    private int bookLength;
    private String publishYear;
    private int numberOfBook;
    private Boolean available;

    public Book(String bookName, String bookID, String genre,
    String author, int bookLength, String publishYear,
    int numberOfBook, Boolean available)
    {
        this.bookName = bookName;
        this.bookID = bookID;
        this.genre = genre;
        this.author = author;
        this.bookLength = bookLength;
        this.publishYear = publishYear;
        this.numberOfBook = numberOfBook;
        this.available = available;
    }
    public String getBookName() {
        return bookName;
    }
    public String getBookID() {
        return bookID;
    }
    public String getGenre() {
        return genre;
    }
    public String getAuthor() {
        return author;
    }
    public int getBookLength() {
        return bookLength;
    }
    public String getPublishYear() {
        return publishYear;
    }
    public int getNumberOfBook() {
        return numberOfBook;
    }
    public Boolean getAvailable() {
        return available;
    }
    public void setAvailable() {
        this.available = true;
    }
    public void setUnavailable() {
        this.available = false;
    }
    


}
