package library.Manager;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import library.Model.Book;
import library.Model.Loan;
import library.Model.Reader;
import library.Service.ILoanService;

public class LoanManager implements ILoanService {
    private final List<Loan> loans;
    private int nextLoanNumber;
    // optional refs to other managers so we can persist related data
    private BookManager bookMgr;
    private UserManager userMgr;

    public LoanManager() {
        this.loans = DataManager.loadLoans();
        // Calculate next loan number based on existing loans
        this.nextLoanNumber = calculateNextLoanNumber();
    }

    // Construct with existing loans (e.g., loaded from persistence)
    public LoanManager(List<Loan> initialLoans) {
        this.loans = new ArrayList<>();
        if (initialLoans != null)
            this.loans.addAll(initialLoans);
        this.nextLoanNumber = calculateNextLoanNumber();
    }

    // Optional constructor to inject managers
    public LoanManager(BookManager bookMgr, UserManager userMgr) {
        this.loans = DataManager.loadLoans();
        this.nextLoanNumber = calculateNextLoanNumber();
        this.bookMgr = bookMgr;
        this.userMgr = userMgr;
    }

    public void setBookManager(BookManager bm) {
        this.bookMgr = bm;
    }

    public void setUserManager(UserManager um) {
        this.userMgr = um;
    }

    private int calculateNextLoanNumber() {
        if (loans.isEmpty())
            return 1;
        return loans.stream()
                .map(Loan::getLoanId)
                .filter(id -> id != null && id.startsWith("LN"))
                .map(id -> id.substring(2)) // Remove "LN" prefix
                .mapToInt(numStr -> {
                    try {
                        return Integer.parseInt(numStr);
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .max()
                .orElse(0) + 1;
    }

    private String generateLoanId() {
        return "LN" + String.format("%03d", nextLoanNumber++);
    }

    // ========== Loan Operations ==========

    @Override
    public Loan createLoan(Reader reader, Book book,
            LocalDate borrowDate, LocalDate expireDate) {
        try {
            // Validation
            if (!book.canBorrow()) {
                throw new IllegalStateException("Sách không có sẵn để mượn");
            }
            if (!reader.isCardValid()) {
                throw new IllegalStateException("Thẻ độc giả không hợp lệ");
            }

            // Generate loan ID and create loan
            String loanId = generateLoanId();
            Loan loan = new Loan(loanId, reader, book, borrowDate, expireDate);

            // Cập nhật trạng thái
            book.borrow();
            reader.addLoanToHistory(loan);
            loans.add(loan);

            // Save to persistence
            DataManager.saveLoans(loans);
            // also persist books/readers if managers available
            if (bookMgr != null) {
                DataManager.saveBooks(bookMgr.getBooks());
            }
            if (userMgr != null) {
                DataManager.saveReaders(userMgr.getAllReaders());
            }

            return loan;
        } catch (IllegalStateException | IllegalArgumentException e) {
            throw e; // Re-throw validation errors
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo phiếu mượn: " + e.getMessage(), e);
        }
    }

    @Override
    public void returnLoan(Loan loan) {
        try {
            if (loan == null) {
                throw new IllegalArgumentException("Loan không được null");
            }
            if (loan.isReturned()) {
                throw new IllegalStateException("Sách đã được trả rồi");
            }

            loan.markAsReturned();
            loan.getBook().returnItem();

            // Save to persistence
            DataManager.saveLoans(loans);
            // also persist books/readers if managers available
            if (bookMgr != null) {
                DataManager.saveBooks(bookMgr.getBooks());
            }
            if (userMgr != null) {
                DataManager.saveReaders(userMgr.getAllReaders());
            }
        } catch (IllegalStateException | IllegalArgumentException e) {
            throw e; // Re-throw validation errors
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi trả sách: " + e.getMessage(), e);
        }
    }

    // ========== Query Operations ==========

    @Override
    public List<Loan> getAllLoans() {
        return new ArrayList<>(loans); // Defensive copy
    }

    @Override
    public List<Loan> getLoansByReader(Reader reader) {
        if (reader == null) {
            return new ArrayList<>();
        }
        return loans.stream()
                .filter(l -> l.getReader().equals(reader))
                .collect(Collectors.toList());
    }

    @Override
    public List<Loan> getLoansByBook(Book book) {
        if (book == null) {
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

    // ========== New Search Methods ==========

    public Loan findLoanById(String loanId) {
        if (loanId == null || loanId.trim().isEmpty()) {
            return null;
        }
        return loans.stream()
                .filter(l -> l.getLoanId().equalsIgnoreCase(loanId.trim()))
                .findFirst()
                .orElse(null);
    }

    public List<Loan> searchByReaderId(String readerId) {
        if (readerId == null || readerId.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String searchTerm = readerId.trim().toLowerCase();
        return loans.stream()
                .filter(l -> l.getReader().getUserID().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }

    public List<Loan> searchByBookName(String bookName) {
        if (bookName == null || bookName.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String searchTerm = bookName.trim().toLowerCase();
        return loans.stream()
                .filter(l -> l.getBook().getBookName().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }

    public List<Loan> searchByBookId(int bookId) {
        return loans.stream()
                .filter(l -> l.getBook().getBookID() == bookId)
                .collect(Collectors.toList());
    }

    public List<Loan> searchByBorrowDate(LocalDate date) {
        if (date == null) {
            return new ArrayList<>();
        }
        return loans.stream()
                .filter(l -> l.getBorrowDate().equals(date))
                .collect(Collectors.toList());
    }

    public List<Loan> searchByReturnDate(LocalDate date) {
        if (date == null) {
            return new ArrayList<>();
        }
        return loans.stream()
                .filter(l -> l.getReturnDate() != null && l.getReturnDate().equals(date))
                .collect(Collectors.toList());
    }

    public List<Loan> getReturnedLoans() {
        return loans.stream()
                .filter(Loan::isReturned)
                .collect(Collectors.toList());
    }

    public List<Loan> searchByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String statusLower = status.trim().toLowerCase();
        return loans.stream()
                .filter(l -> l.getStatusString().toLowerCase().contains(statusLower))
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
        if (total == 0)
            return 0.0;

        long overdue = getOverdueLoansCount();
        return (overdue * 100.0) / total;
    }
}