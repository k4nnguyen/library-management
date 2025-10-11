package library.Model;

import java.time.LocalDate;

public class Loan {
    Reader reader;
    Book book;
    LocalDate borrowDate;
    LocalDate expireDate;

    public void setBook(Book book) {
        this.book = book;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Loan(Book book, LocalDate borrowDate, LocalDate expireDate, Reader reader) {
        this.book = book;
        this.borrowDate = borrowDate;
        this.expireDate = expireDate;
        this.reader = reader;
    }
}
