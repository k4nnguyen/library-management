Dựa vào cấu trúc thư mục trong ảnh, tôi sẽ sửa lại README.md cho khớp với dự án thực tế:

# Hệ Thống Quản Lý Thư Viện - Java Desktop Application

## Giới Thiệu

**Hệ Thống Quản Lý Thư Viện** là một ứng dụng desktop được phát triển bằng **Java Core** thuần túy, không sử dụng bất kỳ framework nào. Ứng dụng được xây dựng nhằm tự động hóa toàn bộ quy trình quản lý của một thư viện hiện đại.

## Cấu Trúc Dự Án

```
src/library/
├── GUI/
│   ├── BookDialog.java
│   ├── BookPanel.java
│   ├── LoanDialog.java
│   ├── LoanPanel.java
│   ├── LoginFrame.java
│   ├── MainFrame.java
│   ├── ReaderDialog.java
│   ├── ReaderPanel.java
│   └── StatsPanel.java
├── Manager/
│   ├── BookManager.java
│   ├── DataManager.java
│   ├── LoanManager.java
│   └── UserManager.java
├── Model/
│   ├── Book.java
│   ├── Borrowable.java
│   ├── Displayable.java
│   ├── Librarian.java
│   ├── Loan.java
│   ├── Reader.java
│   └── User.java
└── Service/
    ├── IBookService.java
    ├── ILoanService.java
    ├── IUserService.java
    └── Main.java
```

## Chức Năng Hệ Thống

### 1. Quản Lý Người Dùng (UserManager)
- Đăng ký, chỉnh sửa, xóa độc giả
- Quản lý thông tin thủ thư
- Phân quyền truy cập

### 2. Quản Lý Sách (BookManager)
- Thêm, sửa, xóa thông tin sách
- Tìm kiếm và lọc sách
- Quản lý số lượng tồn kho

### 3. Quản Lý Mượn/Trả (LoanManager)
- Tạo phiếu mượn sách
- Xử lý trả sách và tính phí quá hạn
- Theo dõi tình trạng mượn/trả

### 4. Thống Kê & Báo Cáo (StatsPanel)
- Thống kê sách theo thể loại
- Báo cáo mượn/trả
- Danh sách quá hạn

## Công Nghệ Sử Dụng
- **Ngôn ngữ**: Java Core
- **GUI**: Java Swing và AWT
- **Lưu trữ**: File-based 
