package library.Model;
//================== MinhNQ =========================
import java.util.UUID;
import java.util.regex.Pattern;

public class Reader 
{
    private String userID;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private boolean isCardValid;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PHONE_REGEX = "\\d{10}";

    public Reader(String name, String phoneNumber, String email, String address) 
    {
        this.userID = "USR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        setName(name);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setAddress(address);

        this.isCardValid = true;
    }

    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public boolean isCardValid() {
        return isCardValid;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên độc giả không được để trống.");
        }
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches(PHONE_REGEX)) {
            throw new IllegalArgumentException("Số điện thoại phải là 10 chữ số.");
        }
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        if (email == null || !Pattern.matches(EMAIL_REGEX, email)) {
            throw new IllegalArgumentException("Email không đúng định dạng.");
        }
        this.email = email;
    }

    public void setAddress(String address) {
        if (address == null) {
            this.address = ""; // Gán giá trị mặc định là chuỗi rỗng
        } else {
            this.address = address;
        }
    }

    public void lockCard() {
        this.isCardValid = false;
    }

    public void activateCard() {
        this.isCardValid = true;
    }

    public void displayInformation() 
    {
        System.out.println("--- Reader Information ---");
        System.out.println("User ID: " + this.userID);
        System.out.println("Name: " + this.name);
        System.out.println("Phone: " + this.phoneNumber);
        System.out.println("Email: " + this.email);
        System.out.println("Address: " + this.address);
        System.out.println("Card Status: " + (this.isCardValid ? "Active" : "Locked"));
        System.out.println("--------------------------");
    }
}