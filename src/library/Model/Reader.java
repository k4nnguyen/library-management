package library.Model;
//================== MinhNQ =========================
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Reader extends User implements Displayable, Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private boolean isCardValid;
    private List<Loan> loanHistory;

    public Reader(int idNumber, String name, String phoneNumber, String email, String address, 
                  String username, String password) {
        
        super(name, phoneNumber, email, address, username, password);
        this.id= "R"+ String.format("%03d", idNumber);
        activateCard();
        this.loanHistory = new ArrayList<>();
    }

    public boolean isCardValid() {
        return isCardValid;
    }

    public void lockCard() {
        this.isCardValid = false;
    }

    public void activateCard() {
        this.isCardValid = true;
    }

    public List<Loan> getLoanHistory() {
        return loanHistory;
    }

    public void addLoanToHistory(Loan loan) {
        if (loan == null) {
            throw new IllegalArgumentException("Giao dịch mượn sách không được để trống.");
        }
        this.loanHistory.add(loan);
    }
    @Override
    public String getUserID() {
        return this.id;
    }
    
    @Override
    public void displayInformation() {
        System.out.println("Mã độc giả: " + getUserID());
        System.out.println("Tên: " + getName());
        System.out.println("Số điện thoại: " + getPhoneNumber());
        System.out.println("Email: " + getEmail());
        System.out.println("Địa chỉ: " + getAddress());
        System.out.println("Tên đăng nhập: " + getUsername());
        System.out.println("Trạng thái thẻ: " + (this.isCardValid ? "Đang hoạt động" : "Đã khóa"));
        System.out.println("Tổng số lần mượn: " + this.loanHistory.size());
    }

    @Override
    public String getDisplayString() {
        return String.format("%s (%s) - %d loans", getName(), getUserID(), loanHistory.size());
    }
}
