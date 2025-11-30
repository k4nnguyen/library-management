package library.Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Loan implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String loanId;
    private final Reader reader;
    private final Book book;
    private final LocalDate borrowDate;
    private final LocalDate expireDate;
    private LocalDate returnDate;
    private boolean returned;

    public Loan(String loanId, Reader reader, Book book, LocalDate borrowDate, LocalDate expireDate) {
        // Kiểm tra điều kiện
        if (loanId == null || loanId.trim().isEmpty()) {
            throw new IllegalArgumentException("Loan ID không được null hoặc rỗng");
        }
        if (reader == null) {
            throw new IllegalArgumentException("Reader không được null");
        }
        if (book == null) {
            throw new IllegalArgumentException("Book không được null");
        }
        if (borrowDate == null) {
            throw new IllegalArgumentException("Ngày mượn không được null");
        }
        if (expireDate == null) {
            throw new IllegalArgumentException("Ngày hết hạn không được null");
        }
        if (expireDate.isBefore(borrowDate)) {
            throw new IllegalArgumentException("Ngày hết hạn phải sau ngày mượn");
        }
        // Set
        this.loanId = loanId;
        this.reader = reader;
        this.book = book;
        this.borrowDate = borrowDate;
        this.expireDate = expireDate;
        this.returned = false;
    }

    // Getter
    public String getLoanId() {
        return this.loanId;
    }

    public Reader getReader() {
        return this.reader;
    }

    public Book getBook() {
        return this.book;
    }

    public LocalDate getBorrowDate() {
        return this.borrowDate;
    }

    public LocalDate getExpireDate() {
        return this.expireDate;
    }

    public boolean isReturned() {
        return this.returned;
    }

    public LocalDate getReturnDate() {
        return this.returnDate;
    }

    public String getStatusString() {
        if (this.returned)
            return "Đã trả";
        if (isOverdue())
            return "Quá hạn";
        return "Đang mượn";
    }

    // Check qua han
    public boolean isOverdue() {
        if (this.returned == true)
            return false;
        return LocalDate.now().isAfter(this.expireDate);
    }

    public long getDaysOverdue() {
        if (!isOverdue())
            return 0;
        return ChronoUnit.DAYS.between(this.expireDate, LocalDate.now());
    }

    public void markAsReturned() {
        if (this.returned) {
            throw new IllegalStateException("Sách đã được trả rồi");
        }
        this.returnDate = LocalDate.now();
        this.returned = true;
    }

    public void displayInformation() {
        System.out.println("Độc giả: " + reader.getName() + " (" + reader.getUserID() + ")");
        System.out.println("Sách: " + book.getBookName());
        System.out.println("Ngày mượn: " + borrowDate);
        System.out.println("Ngày hết hạn: " + expireDate);
        System.out.println("Trạng thái: " + (returned ? "Đã trả" : "Đang mượn"));

        if (returned && returnDate != null) {
            System.out.println("Ngày trả: " + returnDate);
        }

        if (isOverdue()) {
            System.out.println("Quá hạn: " + getDaysOverdue() + " ngày");
        }
        System.out.println("=============================");
    }
}
