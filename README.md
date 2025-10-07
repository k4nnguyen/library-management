#  Hệ Thống Quản Lý Thư Viện - Java Desktop Application

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)

##  Giới Thiệu

**Hệ Thống Quản Lý Thư Viện** là một ứng dụng desktop được phát triển bằng **Java Core** thuần túy, không sử dụng bất kỳ framework nào. Ứng dụng được xây dựng hoàn toàn dựa trên các thư viện chuẩn của Java, nhằm tự động hóa toàn bộ quy trình quản lý của một thư viện hiện đại, là giải pháp toàn diện thay thế cho phương pháp quản lý thủ công truyền thống, giúp nâng cao hiệu quả hoạt động và chất lượng phục vụ.

## Mục Tiêu Dự Án

- **Tự động hóa** các nghiệp vụ thư viện truyền thống
- **Tối ưu hóa** quy trình mượn/trả sách
- **Giảm thiểu** sai sót trong quản lý
- **Cung cấp** công cụ thống kê, báo cáo chuyên nghiệp

## Chức Năng Hệ Thống Chi Tiết

###  1. Quản Lý Thông Tin Độc Giả (Class: User)



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

