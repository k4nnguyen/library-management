package library.Model;
public class Book implements Borrowable, Displayable{
    private String bookName,genre,author;
    private int bookLength,publishYear,bookQuantity;
    private final int bookID;
    private Boolean available;
    //  ==================== Constructor ====================
    public Book(){
        this.bookName = "";
        this.bookID = 0;
        this.genre = "";
        this.bookLength = 0;
        this.publishYear = 0;
        this.bookQuantity = 0;
        this.available = false; 
    }
    public Book(int id, String bookName, String genre,
    String author, int bookLength, int publishYear,
    int bookQuantity)
    {
        this.bookID = id;
        setBookName(bookName);
        setGenre(genre);
        setAuthor(author);
        setBookLength(bookLength);
        setPublishYear(publishYear);
        setQuantity(bookQuantity);
    }
    //  ==================== Setter and Getter ====================
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
        if(bookQuantity >= 0)
        {
            this.bookQuantity = bookQuantity;
            this.available = (bookQuantity > 0);
        }
        else
            throw new IllegalArgumentException("Số lượng sách không được là số âm");
    }
    public final void setAvailable(Boolean available)
    {
        if(available == null)
            throw new IllegalArgumentException("Cần phải set thành true hoặc false");
        else
            this.available = available;
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
    public int getQuantity() {
        return bookQuantity;
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

    @Override
    public boolean isAvailable() {
        return this.available != null && this.available;
    }
    
    @Override
    public boolean canBorrow() {
        return isAvailable() && this.bookQuantity > 0;
    }
    
    @Override
    public boolean borrow() {
        if(canBorrow()) {
            setQuantity(this.bookQuantity - 1);
            return true;
        }
        return false;
    }
    
    @Override
    public void returnItem() {
        setQuantity(this.bookQuantity + 1);
    }
    
    @Override
    public String getItemName() {
        return this.bookName;
    }

    @Override
    public int getItemID() {
        return this.bookID;
    }

    @Override
    public void displayInformation() {
        String status = isAvailable() ? "Có sẵn" : "Không có sẵn";
        System.out.println("=== THÔNG TIN SÁCH ===");
        System.out.println("ID: " + String.format("%02d", this.bookID));
        System.out.println("Tên sách: " + this.bookName);
        System.out.println("Thể loại: " + this.genre);
        System.out.println("Tác giả: " + this.author);
        System.out.println("Năm xuất bản: " + this.publishYear);
        System.out.println("Số trang: " + this.bookLength);
        System.out.println("Số lượng: " + this.bookQuantity);
        System.out.println("Trạng thái: " + status);
        System.out.println("======================");
    }
    
    @Override
    public String getDisplayString() {
        return String.format("[%02d] %s - %s (%d cuốn)", 
            bookID, bookName, author, bookQuantity);
    }
    
}
//
