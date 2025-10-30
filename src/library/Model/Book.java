package library.Model;
// ======================= AN =============================
public class Book {
    static int cnt = 1;
    private String bookName;
    private int bookID;
    private String genre;
    private String author;
    private int bookLength;
    private int publishYear;
    private int numberOfBook;
    private Boolean available;

    public Book(){
        this.bookName = "";
        this.bookID = 0;
        this.genre = "";
        this.bookLength = 0;
        this.publishYear = 0;
        this.numberOfBook = 0;
        this.available = true;
    }
    public Book(String bookName, int bookID, String genre,
    String author, int bookLength, int publishYear,
    int numberOfBook, Boolean available)
    {
        setBookName(bookName);
        this.bookID = cnt++;
        setGenre(genre);
        setAuthor(author);
        setBookLength(bookLength);
        setPublishYear(publishYear);
        setNumberOfBook(numberOfBook);
        setAvailable(available);
    }
    public void setBookName(String bookName)
    {
        if(!bookName.equals(""))
            this.bookName = bookName;
        else
            throw new IllegalArgumentException("Tên sách không được để trống!");
    }
    public void setGenre(String genre)
    {
        if(!genre.equals(""))
            this.genre = genre;
        else
            throw new IllegalArgumentException("Thể loại không được để trống!");
    }
    public void setAuthor(String author)
    {
        if(!author.equals(""))
            this.author = author;
        else
            throw new IllegalArgumentException("Tên tác giả không được để trống!");
    }
    public void setBookLength(int bookLength)
    {
        if(bookLength > 0)
            this.bookLength = bookLength;
        else
            throw new IllegalArgumentException("Độ dài của sách phải là số dương");
    }
    public void setPublishYear(int publishYear)
    {
        if(publishYear > 0 && publishYear <= 2025)
            this.publishYear = publishYear;
        else
            throw new IllegalArgumentException("Năm xuất bản cần phải trong phạm vi 0 - 2025");
    }
    public void setNumberOfBook(int numberOfBook)
    {
        if(numberOfBook > 0)
            this.numberOfBook = numberOfBook;
        else
            throw new IllegalArgumentException("Số lượng sách cần là số dương");
    }
    public void setAvailable(Boolean available)
    {
        if(available == true || available == false)
            this.available = available;
        else
            throw new IllegalArgumentException("Cần phải set thành true hoặc false");
    }
    public String getBookName() {
        return bookName;
    }
    public int getBookID() {
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
    public int getPublishYear() {
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
    public void getInformation(){
        String s = "ID: " + this.bookID + "\nTên sách: " + this.bookName + "\nThể loại: " + this.genre + "\nTác giả: " + this.author + "\nNăm xuất bản: " + this.publishYear +  "\nSố lượng trang: " + this.bookLength;
        System.out.println(s);
    }

}
