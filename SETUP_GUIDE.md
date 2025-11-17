1. Mở **SQL Server Management Studio (SSMS)**
2. Kết nối với server (Windows Authentication)
3. Mở và chạy file **`database/Library.sql`**
   - File này sẽ tạo database `library_management`
   - Tạo các bảng: `books`, `users`, `loan`
   - Thêm dữ liệu mẫu
   - Thêm user và role

**SAU ĐÓ PHẢI RESTART SQL SERVER!**

Cách restart:

- Mở `services.msc` (Win + R)
- Tìm "SQL Server (SQLEXPRESS)"
- Right-click → Restart

```powershell
# Từ thư mục gốc project
cd "path\to\library-management"
javac -cp "lib\*;." testConnection
java -cp "lib\*;." testConnection
```
