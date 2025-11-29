package library.Service;

import library.Model.Loan;
import library.Model.Reader;
import library.Model.Book;
import java.time.LocalDate;
import java.util.List;
public interface ILoanService {
    
    // ========== Loan Operations ==========
    Loan createLoan(Reader reader, Book book, 
                   LocalDate borrowDate, LocalDate expireDate);
    void returnLoan(Loan loan);
    
    // ========== Query Operations ==========
    List<Loan> getAllLoans();
    List<Loan> getLoansByReader(Reader reader);
    List<Loan> getLoansByBook(Book book);
    List<Loan> getActiveLoans();
    List<Loan> getOverdueLoans();
    
    // ========== Statistics ==========
    int getTotalLoans();
    int getActiveLoansCount();
    int getOverdueLoansCount();
    double getOverdueRate();
}