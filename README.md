#  Hệ Thống Quản Lý Thư Viện - Java Core Desktop Application

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)

##  Giới Thiệu

**Hệ Thống Quản Lý Thư Viện** là một ứng dụng desktop được phát triển bằng **Java Core** thuần túy, không sử dụng bất kỳ framework nào. Ứng dụng được xây dựng hoàn toàn dựa trên các thư viện chuẩn của Java, nhằm tự động hóa toàn bộ quy trình quản lý của một thư viện hiện đại.

## Mục Tiêu Dự Án

- **Tự động hóa** các nghiệp vụ thư viện truyền thống
- **Triển khai thuần Java** không phụ thuộc framework
- **Tối ưu hóa** quy trình mượn/trả sách
- **Giảm thiểu** sai sót trong quản lý
- **Cung cấp** công cụ thống kê, báo cáo chuyên nghiệp

##  Yêu Cầu Hệ Thống

### Phần Cứng Tối Thiểu
- **Bộ vi xử lý:** Intel Core i3 hoặc tương đương
- **Bộ nhớ RAM:** Tối thiểu 2GB (Khuyến nghị 4GB)
- **Ổ đĩa cứng:** Tối thiểu 200MB trống
- **Màn hình:** Độ phân giải 1024x768 trở lên

### Phần Mềm Bắt Buộc
- **Hệ điều hành:** Windows 7/8/10/11, macOS 10.12+, Linux
- **Java Runtime Environment:** Phiên bản 8 trở lên
- **Không yêu cầu** cơ sở dữ liệu bên ngoài (sử dụng file-based storage)

## Kiến Trúc Hệ Thống Thuần Java

### Công Nghệ Sử Dụng
- **Ngôn ngữ lập trình:** Java Core (JDK 8+)
- **Kiến trúc:** MVC (Model-View-Controller) thủ công
- **Xử lý sự kiện:** Java Event Listener
- **Quản lý dependency:** Không sử dụng, pure Java


## Chức Năng Hệ Thống Chi Tiết

###  2. Quản Lý Thông Tin Độc Giả (Class: User)



#### Chức năng:
- **Thêm độc giả mới**: Validate thông tin trước khi lưu
- **Chỉnh sửa thông tin**: Cập nhật thông tin cá nhân
- **Xóa độc giả**: Kiểm tra ràng buộc trước khi xóa
- **Tìm kiếm**: Theo ID, tên, số điện thoại
- **Quản lý thẻ**: Kích hoạt/khóa thẻ độc giả

### 3. Quản Lý Kho Sách (Class: Book)


#### Chức năng:
- **Thêm sách mới**: Kiểm tra trùng mã sách
- **Chỉnh sửa thông tin sách**: Cập nhật thông tin
- **Xóa sách**: Chỉ xóa khi không có phiếu mượn liên quan
- **Tìm kiếm sách**: Đa điều kiện (tên, tác giả, thể loại)
- **Quản lý số lượng**: Tự động cập nhật khi mượn/trả

### 4. Quản Lý Mượn/Trả Sách (Class: PhieuMuon)



#### Quy Trình Mượn Sách:
1. **Xác thực độc giả**: `user.checkValidCard()`
2. **Kiểm tra sách khả dụng**: `book.checkValid()`
3. **Tạo phiếu mượn**: Tự động tính `tinhToanNgayHenTra()`
4. **Cập nhật số lượng sách**: `book.update()`

#### Quy Trình Trả Sách:
1. **Xác định phiếu mượn**: Tìm theo `maPhieuMuon`
2. **Kiểm tra hạn trả**: `kiemTraQuaHan()`
3. **Ghi nhận trả**: `danhDauDaTra()`
4. **Cập nhật kho sách**: Tăng số lượng sách có sẵn

### 5. Hệ Thống Báo Cáo & Thống Kê
- **Thống kê sách**: Số lượng theo thể loại, tình trạng
- **Thống kê độc giả**: Phân tích theo tình trạng thẻ
- **Báo cáo mượn/trả**: Thống kê lượt mượn, tỷ lệ trả đúng hạn
- **Báo cáo quá hạn**: Danh sách phiếu mượn quá hạn



## Hướng Dẫn Cài Đặt & Chạy Ứng Dụng

### Bước 1: Kiểm Tra Môi Trường Java
```bash
# Kiểm tra phiên bản Java
java -version

# Nếu chưa cài đặt, tải từ:
# https://www.oracle.com/java/technologies/javase-downloads.html
```

### Bước 2: Tải Và Biên Dịch Mã Nguồn
```bash
# Clone hoặc tải source code
# Giải nén vào thư mục LibraryManagementSystem

# Di chuyển vào thư mục src
cd LibraryManagementSystem/src

# Biên dịch toàn bộ source code
javac -d ../bin *.java

# Hoặc biên dịch từng file (nếu cần)
javac Main.java
javac Book.java
javac User.java
javac PhieuMuon.java
javac ThuThu.java
javac QuanLyThuVien.java
```

### Bước 3: Chạy Ứng Dụng
```bash
# Chạy từ thư mục bin
java -cp ../bin Main

# Hoặc chạy trực tiếp từ src
java Main
```

### Bước 4: Đăng Nhập Lần Đầu
- **Tên đăng nhập:** `admin`
- **Mật khẩu:** `admin123`
- Sau đó có thể đổi mật khẩu trong phần quản lý

## Hướng Dẫn Sử Dụng Chi Tiết


### Quy Trình Thêm Sách Mới
1. Từ menu chọn **"Quản lý sách" → "Thêm sách mới"**
2. Nhập đầy đủ thông tin:
   - Mã sách (duy nhất)
   - Tên sách
   - Tác giả
   - Thể loại
   - Số lượng
   - Trạng thái
3. Click **"Lưu"** để thêm vào hệ thống

### Quy Trình Tạo Phiếu Mượn
1. Chọn **"Phiếu mượn" → "Tạo phiếu mượn"**
2. Chọn độc giả từ danh sách (chỉ hiển thị thẻ hợp lệ)
3. Chọn sách từ kho (chỉ hiển thị sách khả dụng)
4. Hệ thống tự tính ngày hẹn trả
5. Xác nhận tạo phiếu


## Xử Lý Lỗi Thường Gặp

### Lỗi Biên Dịch
```bash
# Lỗi: package does not exist
# Giải pháp: Đảm bảo tất cả file .java cùng thư mục hoặc set classpath

# Lỗi: incompatible types
# Giải pháp: Kiểm tra kiểu dữ liệu trong ép kiểu
```

### Lỗi Runtime
```java
// Lỗi NullPointerException
// Giải pháp: Kiểm tra khởi tạo đối tượng trước khi sử dụng

// Lỗi FileNotFoundException  
// Giải pháp: Tạo thư mục data/ trước khi chạy
```

## Tính Năng Đặc Biệt Của Phiên Bản Thuần Java

### Ưu Điểm
- **Nhẹ**: Không cần cài đặt thêm framework
- **Đơn giản**: Dễ hiểu, dễ bảo trì
- **Tương thích**: Chạy trên mọi máy có JRE
- **Hiệu suất**: Tối ưu cho ứng dụng nhỏ

### Hạn Chế
- **GUI cơ bản**: So với framework hiện đại
- **Tự implement**: Cần tự xử lý nhiều chức năng
- **Bảo trì**: Quản lý dependency thủ công
