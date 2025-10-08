# Hệ Thống Quản Lý Thư Viện - Java Desktop Application

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)

## Giới Thiệu

**Hệ Thống Quản Lý Thư Viện** là một ứng dụng desktop được phát triển bằng **Java Core** thuần túy, không sử dụng bất kỳ framework nào. Ứng dụng được xây dựng nhằm tự động hóa toàn bộ quy trình quản lý của một thư viện hiện đại, là giải pháp toàn diện thay thế cho phương pháp quản lý thủ công truyền thống, giúp nâng cao hiệu quả hoạt động. Với các tính năng giúp cho việc
quản lý thư viện dễ dàng hơn như: Thêm/Xóa/Chỉnh sửa thông tin người dùng, sách. Chức năng tìm kiếm người dùng để hỗ trợ
quản lý việc kiểm tra thông tin người dùng và sách, cũng như kiểm tra sách đã được mượn hay chưa. Ngoài ra còn tạo thống kê
số lượng sách theo thể loại cũng như tạo báo cáo khi quá hạn mượn sách!

## Mục Tiêu Dự Án

- **Tự động hóa** các nghiệp vụ thư viện truyền thống
- **Tối ưu hóa** quy trình mượn/trả sách
- **Giảm thiểu** sai sót trong quản lý
- **Cung cấp** công cụ thống kê, báo cáo chuyên nghiệp

## Chức Năng Hệ Thống Chi Tiết

### 1. Quản Lý Thông Tin Độc Giả (Class: User)

#### Chức năng:

- **Thêm độc giả mới**: Validate thông tin trước khi lưu
- **Chỉnh sửa thông tin**: Cập nhật thông tin cá nhân
- **Xóa độc giả**: Kiểm tra ràng buộc trước khi xóa
- **Tìm kiếm**: Theo ID, tên, số điện thoại
- **Quản lý thẻ**: Kích hoạt/khóa thẻ độc giả

### 2. Quản Lý Kho Sách (Class: Book)

#### Chức năng:

- **Thêm sách mới**: Kiểm tra trùng mã sách
- **Chỉnh sửa thông tin sách**: Cập nhật thông tin
- **Xóa sách**: Chỉ xóa khi không có phiếu mượn liên quan
- **Tìm kiếm sách**: Đa điều kiện (tên, tác giả, thể loại)
- **Quản lý số lượng**: Tự động cập nhật khi mượn/trả

### 3. Quản Lý Mượn/Trả Sách (Class: PhieuMuon)

#### Quy Trình Mượn Sách:

1. **Xác thực độc giả**:
2. **Kiểm tra sách khả dụng**:
3. **Tạo phiếu mượn**: Tự động tính
4. **Cập nhật số lượng sách**:

#### Quy Trình Trả Sách:

1. **Xác định phiếu mượn**: Tìm theo
2. **Kiểm tra hạn trả**:
3. **Ghi nhận trả**:
4. **Cập nhật kho sách**: Tăng số lượng sách có sẵn

### 5. Hệ Thống Báo Cáo & Thống Kê

- **Thống kê sách**: Số lượng theo thể loại, tình trạng
- **Thống kê độc giả**: Phân tích theo tình trạng thẻ
- **Báo cáo mượn/trả**: Thống kê lượt mượn, tỷ lệ trả đúng hạn
- **Báo cáo quá hạn**: Danh sách phiếu mượn quá hạn
