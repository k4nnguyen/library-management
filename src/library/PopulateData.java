package library;

import library.Manager.BookManager;
import library.Manager.DataManager;
import library.Manager.UserManager;
import library.Model.Book;
import library.Model.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PopulateData {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("Bắt đầu tạo dữ liệu mẫu...");
        System.out.println("========================================\n");

        // Create books
        List<Book> books = createBooks();
        DataManager.saveBooks(books);
        System.out.println("✓ Đã tạo " + books.size() + " quyển sách");

        // Create readers
        List<Reader> readers = createReaders();
        DataManager.saveReaders(readers);
        System.out.println("✓ Đã tạo " + readers.size() + " độc giả");

        // Initialize empty loans list
        DataManager.saveLoans(new ArrayList<>());
        System.out.println("✓ Đã khởi tạo danh sách mượn sách");

        System.out.println("\n========================================");
        System.out.println("Hoàn thành! Dữ liệu đã được lưu vào thư mục data/");
        System.out.println("========================================");
    }

    private static List<Book> createBooks() {
        List<Book> books = new ArrayList<>();

        // Văn học Việt Nam
        books.add(new Book(1, "Số Đỏ", "Văn học Việt Nam", "Vũ Trọng Phụng", 250, 1936, 5));
        books.add(new Book(2, "Chí Phèo", "Văn học Việt Nam", "Nam Cao", 180, 1941, 8));
        books.add(new Book(3, "Tắt Đèn", "Văn học Việt Nam", "Ngô Tất Tố", 320, 1939, 3));
        books.add(new Book(4, "Vợ Nhặt", "Văn học Việt Nam", "Kim Lân", 150, 1962, 12));

        // Văn học nước ngoài
        books.add(new Book(5, "Nhà Giả Kim", "Văn học nước ngoài", "Paulo Coelho", 227, 1988, 15));
        books.add(new Book(6, "Đắc Nhân Tâm", "Tâm lý - Kỹ năng sống", "Dale Carnegie", 320, 1936, 20));
        books.add(new Book(7, "Những Người Khốn Khổ", "Văn học nước ngoài", "Victor Hugo", 1488, 1862, 4));
        books.add(new Book(8, "Chiến Tranh Và Hòa Bình", "Văn học nước ngoài", "Leo Tolstoy", 1225, 1869, 2));
        books.add(new Book(9, "Tội Ác Và Hình Phạt", "Văn học nước ngoài", "Fyodor Dostoevsky", 671, 1866, 6));
        books.add(new Book(10, "Anna Karenina", "Văn học nước ngoài", "Leo Tolstoy", 864, 1877, 7));

        // Khoa học - Công nghệ
        books.add(new Book(11, "Lược Sử Thời Gian", "Khoa học", "Stephen Hawking", 256, 1988, 10));
        books.add(new Book(12, "Sapiens: Lược Sử Loài Người", "Khoa học", "Yuval Noah Harari", 498, 2011, 18));
        books.add(new Book(13, "Clean Code", "Công nghệ thông tin", "Robert C. Martin", 464, 2008, 9));
        books.add(new Book(14, "Design Patterns", "Công nghệ thông tin", "Gang of Four", 395, 1994, 5));
        books.add(new Book(15, "The Pragmatic Programmer", "Công nghệ thông tin", "Andy Hunt & Dave Thomas", 352, 1999,
                11));

        // Kinh tế - Kinh doanh
        books.add(new Book(16, "Думай и богатей", "Kinh tế", "Napoleon Hill", 320, 1937, 14));
        books.add(new Book(17, "Bí Mật Tư Duy Triệu Phú", "Kinh tế", "T. Harv Eker", 224, 2005, 16));
        books.add(new Book(18, "Nghệ Thuật Bán Hàng", "Kinh doanh", "Brian Tracy", 208, 1995, 8));
        books.add(new Book(19, "The Lean Startup", "Kinh doanh", "Eric Ries", 336, 2011, 6));

        // Tiểu thuyết trinh thám
        books.add(new Book(20, "Sherlock Holmes", "Trinh thám", "Arthur Conan Doyle", 307, 1892, 13));
        books.add(new Book(21, "Murder on the Orient Express", "Trinh thám", "Agatha Christie", 256, 1934, 9));
        books.add(new Book(22, "The Da Vinci Code", "Trinh thám", "Dan Brown", 689, 2003, 11));

        // Văn học thiếu nhi
        books.add(new Book(23, "Dế Mèn Phiêu Lưu Ký", "Thiếu nhi", "Tô Hoài", 180, 1941, 25));
        books.add(new Book(24, "Harry Potter và Hòn Đá Phù Thủy", "Thiếu nhi", "J.K. Rowling", 332, 1997, 21));
        books.add(new Book(25, "Nhà Giả Kim (Young Edition)", "Thiếu nhi", "Paulo Coelho", 240, 2014, 17));

        // Lịch sử
        books.add(new Book(26, "Việt Nam Sử Lược", "Lịch sử", "Trần Trọng Kim", 450, 1920, 7));
        books.add(new Book(27, "Homo Deus", "Lịch sử", "Yuval Noah Harari", 450, 2015, 10));
        books.add(new Book(28, "21 Bài Học Cho Thế Kỷ 21", "Lịch sử", "Yuval Noah Harari", 374, 2018, 12));

        // Tâm lý - Triết học
        books.add(new Book(29, "Tuổi Trẻ Đáng Giá Bao Nhiêu", "Tâm lý", "Rosie Nguyễn", 208, 2018, 19));
        books.add(new Book(30, "Đời Ngắn Đừng Ngủ Dài", "Tâm lý", "Robin Sharma", 256, 2006, 22));

        return books;
    }

    private static List<Reader> createReaders() {
        List<Reader> readers = new ArrayList<>();

        readers.add(new Reader(
                1,
                "Nguyễn Văn An",
                "0901234567",
                "123 Đường Lê Lợi, Q1, TPHCM",
                "nvan",
                "123456",
                LocalDate.of(1995, 3, 15),
                "Nam"));
        readers.get(0).setEmail("nguyenvanan@gmail.com");

        readers.add(new Reader(
                2,
                "Trần Thị Bình",
                "0912345678",
                "456 Đường Nguyễn Huệ, Q1, TPHCM",
                "ttbinh",
                "123456",
                LocalDate.of(1998, 7, 22),
                "Nữ"));
        readers.get(1).setEmail("tranthibinh@gmail.com");

        readers.add(new Reader(
                3,
                "Lê Minh Cường",
                "0923456789",
                "789 Đường Pasteur, Q3, TPHCM",
                "lmcuong",
                "123456",
                LocalDate.of(1992, 11, 8),
                "Nam"));
        readers.get(2).setEmail("leminhcuong@gmail.com");

        readers.add(new Reader(
                4,
                "Phạm Thu Dung",
                "0934567890",
                "321 Đường Hai Bà Trưng, Q3, TPHCM",
                "ptdung",
                "123456",
                LocalDate.of(2000, 5, 30),
                "Nữ"));
        readers.get(3).setEmail("phamthudung@gmail.com");

        readers.add(new Reader(
                5,
                "Hoàng Văn Đức",
                "0945678901",
                "654 Đường Trần Hưng Đạo, Q5, TPHCM",
                "hvduc",
                "123456",
                LocalDate.of(1990, 12, 17),
                "Nam"));
        readers.get(4).setEmail("hoangvanduc@gmail.com");

        readers.add(new Reader(
                6,
                "Võ Thị Hoa",
                "0956789012",
                "987 Đường Lý Thường Kiệt, Q10, TPHCM",
                "vthoa",
                "123456",
                LocalDate.of(1996, 2, 28),
                "Nữ"));
        readers.get(5).setEmail("vothihoa@gmail.com");

        readers.add(new Reader(
                7,
                "Đặng Quốc Huy",
                "0967890123",
                "147 Đường Võ Thị Sáu, Q3, TPHCM",
                "dqhuy",
                "123456",
                LocalDate.of(1993, 9, 5),
                "Nam"));
        readers.get(6).setEmail("dangquochuy@gmail.com");

        readers.add(new Reader(
                8,
                "Bùi Thị Kim",
                "0978901234",
                "258 Đường Điện Biên Phủ, Bình Thạnh, TPHCM",
                "btkim",
                "123456",
                LocalDate.of(1999, 6, 14),
                "Nữ"));
        readers.get(7).setEmail("buithikim@gmail.com");

        readers.add(new Reader(
                9,
                "Ngô Minh Khoa",
                "0989012345",
                "369 Đường Cách Mạng Tháng 8, Q10, TPHCM",
                "nmkhoa",
                "123456",
                LocalDate.of(1994, 4, 19),
                "Nam"));
        readers.get(8).setEmail("ngominhkhoa@gmail.com");

        readers.add(new Reader(
                10,
                "Trương Thanh Loan",
                "0990123456",
                "741 Đường Hoàng Văn Thụ, Tân Bình, TPHCM",
                "ttloan",
                "123456",
                LocalDate.of(1997, 10, 25),
                "Nữ"));
        readers.get(9).setEmail("truongthanhloan@gmail.com");

        return readers;
    }
}
