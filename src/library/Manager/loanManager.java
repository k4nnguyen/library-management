package library.Manager;

<<<<<<< HEAD
import library.Service.ILoanService;
import library.Model.Loan;
import library.Model.Reader;
import library.Model.Book;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
=======
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import library.Model.Book;
import library.Model.Loan;
import library.Model.Reader;
import library.Service.ILoanService;
>>>>>>> 78199628859bf9b17831ddf00263fcf110c026bb

public class loanManager implements ILoanService{
    private final List<Loan> loans;
    
    public loanManager(){
        this.loans = new ArrayList<>();
    }
<<<<<<< HEAD
=======

    // Construct with existing loans (e.g., loaded from persistence)
    public loanManager(List<Loan> initialLoans) {
        this.loans = new ArrayList<>();
        if (initialLoans != null) this.loans.addAll(initialLoans);
    }
>>>>>>> 78199628859bf9b17831ddf00263fcf110c026bb
    
    // ========== Loan Operations ==========
    
    @Override
    public Loan createLoan(Reader reader, Book book, 
                          LocalDate borrowDate, LocalDate expireDate) {
        // Validation
        if(!book.canBorrow()) {
            throw new IllegalStateException("Sách không có sẵn để mượn");
        }
        if(!reader.isCardValid()) {
            throw new IllegalStateException("Thẻ độc giả không hợp lệ");
        }
        
        // Tạo loan
        Loan loan = new Loan(reader, book, borrowDate, expireDate);
        
        // Cập nhật trạng thái
        book.borrow();
        reader.addLoanToHistory(loan);
        loans.add(loan);
        
        return loan;
    }
    
    @Override
    public void returnLoan(Loan loan) {
        if(loan == null) {
            throw new IllegalArgumentException("Loan không được null");
        }
        if(loan.isReturned()) {
            throw new IllegalStateException("Sách đã được trả rồi");
        }
        
        loan.markAsReturned();
        loan.getBook().returnItem();
    }
    
    // ========== Query Operations ==========
    
    @Override
    public List<Loan> getAllLoans() {
        return new ArrayList<>(loans);  // Defensive copy
    }
    
    @Override
    public List<Loan> getLoansByReader(Reader reader) {
        if(reader == null) {
            return new ArrayList<>();
        }
        return loans.stream()
            .filter(l -> l.getReader().equals(reader))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Loan> getLoansByBook(Book book) {
        if(book == null) {
            return new ArrayList<>();
        }
        return loans.stream()
            .filter(l -> l.getBook().equals(book))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Loan> getActiveLoans() {
        return loans.stream()
            .filter(l -> !l.isReturned())
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Loan> getOverdueLoans() {
        return loans.stream()
            .filter(Loan::isOverdue)
            .collect(Collectors.toList());
    }
    
    // ========== Statistics ==========
    
    @Override
    public int getTotalLoans() {
        return loans.size();
    }
    
    @Override
    public int getActiveLoansCount() {
        return (int) loans.stream()
            .filter(l -> !l.isReturned())
            .count();
    }
    
    @Override
    public int getOverdueLoansCount() {
        return (int) loans.stream()
            .filter(Loan::isOverdue)
            .count();
    }
    
    @Override
    public double getOverdueRate() {
        long total = getTotalLoans();
        if(total == 0) return 0.0;
        
        long overdue = getOverdueLoansCount();
        return (overdue * 100.0) / total;
    }
}