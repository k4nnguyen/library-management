package library.Model;
// ======================= AN =============================
public class Book {
    private int bookShelfId;
    private String bookName,genre,author;
    private int bookLength,publishYear,bookQuantity;
    private int bookID;
    // Constructor
    public Book(){
        this.bookName = "";
        this.genre = "";
        this.bookLength = 0;
        this.publishYear = 0;
        this.bookQuantity = 0;
    }
    public Book(int id, String bookName, String genre,
    String author, int bookLength, int publishYear,
    int bookQuantity, Boolean available)
    {
        setBookName(bookName);
        setGenre(genre);
        setAuthor(author);
        setBookLength(bookLength);
        setPublishYear(publishYear);
        setQuantity(bookQuantity);
    }
    // Set and Get
    public final void setBookName(String bookName)
    {
        if(!bookName.equals(""))
            this.bookName = bookName;
        else
            throw new IllegalArgumentException("Tên sách không được để trống!");
    }
    public final void setGenre(String genre)
    {
        if(!genre.equals(""))
            this.genre = genre;
        else
            throw new IllegalArgumentException("Thể loại không được để trống!");
    }
    public final void setAuthor(String author)
    {
        if(!author.equals(""))
            this.author = author;
        else
            throw new IllegalArgumentException("Tên tác giả không được để trống!");
    }
    public final void setBookLength(int bookLength)
    {
        if(bookLength > 0)
            this.bookLength = bookLength;
        else
            throw new IllegalArgumentException("Độ dài của sách phải là số dương");
    }
    public final void setPublishYear(int publishYear)
    {
        if(publishYear > 0 && publishYear <= 2025)
            this.publishYear = publishYear;
        else
            throw new IllegalArgumentException("Năm xuất bản cần phải trong phạm vi 0 - 2025");
    }
    public final void setQuantity(int bookQuantity)
    {
        if(bookQuantity > 0)
            this.bookQuantity = bookQuantity;
        else
            throw new IllegalArgumentException("Số lượng sách cần là số dương");
    }

    public String getBookName() {
        return bookName;
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
    public int getQuantity() {
        return bookQuantity;
    }

    public void getInformation(){
        String s = "ID: " + String.format("%02d",this.bookID) + "\nTên sách: " + this.bookName + "\nThể loại: " + this.genre + "\nTác giả: " + this.author + "\nNăm xuất bản: " + this.publishYear +  "\nSố lượng trang: " + this.bookLength;
        System.out.println(s);
    }

}
//