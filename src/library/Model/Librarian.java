package library.Model;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;

// ======================= LAM ==============================
public class Librarian extends User {

    private boolean workingStatus;
    private String id,startDate;

    public Librarian(int idNumber, String name, String phoneNumber, String email, String address, 
                    String username, String password,String startDate) {
        super(name, phoneNumber, email, address, username, password);
        this.id = "L" + String.format("%03d",idNumber);
        setWorking();
        setStartDate(startDate);
    }

    public boolean isWorking() {
        return workingStatus;
    }

    public void setWorking() {
        this.workingStatus = true;
    }

    public void setNotWorking() {
        this.workingStatus = false;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        if (startDate == null || startDate.trim().isEmpty()) {
            throw new IllegalArgumentException("Ngày bắt đầu không được để trống.");
        }
        try{
            LocalDate.parse(startDate);
            this.startDate = startDate;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Định dạng ngày không hợp lệ. Vui lòng sử dụng định dạng YYYY-MM-DD.");
        }
    }
    
    @Override
    public String getUserID() {
        return this.id;
    }

    @Override
    public void displayInformation() {
        System.out.println("Mã thủ thư: " + getUserID());
        System.out.println("Tên: " + getName());
        System.out.println("Số điện thoại: " + getPhoneNumber());
        System.out.println("Email: " + getEmail());
        System.out.println("Địa chỉ: " + getAddress());
        System.out.println("Trạng thái làm việc: " + (this.workingStatus ? "Đang làm việc" : "Không làm việc"));
        System.out.println("Ngày bắt đầu: " + this.startDate);
    }
}