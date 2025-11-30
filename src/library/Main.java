package library;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import library.Manager.*;
import library.Model.*;
import library.Service.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== HỆ THỐNG QUẢN LÝ THƯ VIỆN ===\n");

        // ========== Khởi tạo Services ==========
        // Sử dụng Interface type để loose coupling
        IBookService bookService = new bookManager();
        ILoanService loanService = new loanManager();

        // ========== Demo Book Operations ==========
        System.out.println("--- THÊM SÁCH ---");
        bookService.addBook("Harry Potter và Hòn đá Phù thủy", "Fantasy",
                "J.K. Rowling", 320, 1997, 5);
        bookService.addBook("Nhà giả kim", "Tiểu thuyết",
                "Paulo Coelho", 176, 1988, 3);
        bookService.addBook("Đắc nhân tâm", "Kỹ năng sống",
                "Dale Carnegie", 320, 1936, 10);
        System.out.println("Đã thêm 3 cuốn sách\n");

        // ========== Demo Search ==========
        System.out.println("--- TÌM KIẾM SÁCH ---");
        Book book1 = bookService.findBookById(1);
        System.out.println("Tìm theo ID: " + book1.getDisplayString());

        List<Book> harryPotterBooks = bookService.searchByName("Harry");
        System.out.println("Tìm theo tên 'Harry': " + harryPotterBooks.size() + " kết quả");

        List<Book> fantasyBooks = bookService.searchByGenre("Fantasy");
        System.out.println("Tìm theo thể loại 'Fantasy': " + fantasyBooks.size() + " kết quả\n");

        // ========== Demo Statistics ==========
        System.out.println("--- THỐNG KÊ SÁCH ---");
        System.out.println("Tổng số sách: " + bookService.getTotalBooks());
        System.out.println("Số sách có sẵn: " + bookService.getAvailableBooks());

        Map<String, Integer> countByGenre = bookService.getBookCountByGenre();
        System.out.println("\nSố lượng theo thể loại:");
        countByGenre.forEach((genre, count) -> System.out.println("  - " + genre + ": " + count + " cuốn"));
        System.out.println();

        // ========== Demo Reader ==========
        System.out.println("--- TẠO ĐỘC GIẢ ---");
        Reader reader1 = new Reader(1, "Nguyễn Văn A", "0123456789",
                "Hà Nội",
                "user1", "password123");
        reader1.displayInformation();
        System.out.println();

        // ========== Demo Loan Operations ==========
        System.out.println("--- MƯỢN SÁCH ---");
        try {
            Loan loan1 = loanService.createLoan(
                    reader1,
                    book1,
                    LocalDate.now(),
                    LocalDate.now().plusDays(14));
            System.out.println("Mượn sách thành công!");
            loan1.displayInformation();
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }

        // ========== Demo Loan Statistics ==========
        System.out.println("\n--- THỐNG KÊ PHIẾU MƯỢN ---");
        System.out.println("Tổng phiếu mượn: " + loanService.getTotalLoans());
        System.out.println("Phiếu đang active: " + loanService.getActiveLoansCount());
        System.out.println("Phiếu quá hạn: " + loanService.getOverdueLoansCount());
        System.out.println("Tỷ lệ quá hạn: " + String.format("%.2f", loanService.getOverdueRate()) + "%");

        // ========== Demo Return Book ==========
        System.out.println("\n--- TRẢ SÁCH ---");
        List<Loan> activeLoans = loanService.getActiveLoans();
        if (!activeLoans.isEmpty()) {
            Loan loanToReturn = activeLoans.get(0);
            loanService.returnLoan(loanToReturn);
            System.out.println("Đã trả sách thành công!");
            loanToReturn.displayInformation();
        }

        System.out.println("\n--- DEMO POLYMORPHISM ---");
        demonstrateBorrowable(book1);

        // ========== Demo Displayable ==========
        System.out.println("\n--- DEMO DISPLAYABLE ---");
        List<Displayable> displayables = List.of(book1, reader1);
        for (Displayable item : displayables) {
            System.out.println(item.getDisplayString());
        }

        System.out.println("\n=== KẾT THÚC DEMO ===");
    }

    private static void demonstrateBorrowable(Borrowable item) {
        System.out.println("Xử lý vật phẩm: " + item.getItemName());
        System.out.println("  - ID: " + item.getItemID());
        System.out.println("  - Có sẵn: " + (item.isAvailable() ? "Có" : "Không"));
        System.out.println("  - Có thể mượn: " + (item.canBorrow() ? "Có" : "Không"));
    }
}