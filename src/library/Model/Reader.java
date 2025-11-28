package library.Model;
//================== MinhNQ =========================
import java.util.ArrayList;
import java.util.List;

public class Reader extends User {
    private String id;
    private boolean isCardValid;
    private List<Loan> loanHistory;

    public Reader(int idNumber, String name, String phoneNumber, String email, String address, 
                  String username, String password) {
        
        super(name, phoneNumber, email, address, username, password);
        this.id= "R"+ String.format("%03d", idNumber);
        this.isCardValid = true;
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
            throw new IllegalArgumentException("Giao dich muon sach khong duoc de trong.");
        }
        this.loanHistory.add(loan);
    }
    @Override
    public String getUserID() {
        return this.id;
    }
    
    @Override
    public void displayInformation() {
        System.out.println("--- Reader Information ---");
        System.out.println("User ID: " + getUserID());
        System.out.println("Name: " + getName());
        System.out.println("Phone: " + getPhoneNumber());
        System.out.println("Email: " + getEmail());
        System.out.println("Address: " + getAddress());
        System.out.println("Username: " + getUsername());
        System.out.println("Card Status: " + (this.isCardValid ? "Active" : "Locked"));
        System.out.println("Total Loans: " + this.loanHistory.size());
        System.out.println("--------------------------");
    }
}
