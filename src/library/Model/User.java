package library.Model;
//================== MinhNQ =========================
import java.util.UUID;
import java.util.regex.Pattern;

public abstract class User {
    private String name;
    private String phoneNumber;
    private String email;
    private String address;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PHONE_REGEX = "\\d{10}";

    public User(String name, String phoneNumber, String email, String address) {

        setName(name);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setAddress(address);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Ten khong duoc de trong.");
        }
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches(PHONE_REGEX)) {
            throw new IllegalArgumentException("So dien thoai phai la 10 chu so.");
        }
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !Pattern.matches(EMAIL_REGEX, email)) {
            throw new IllegalArgumentException("Email khong dung dinh dang.");
        }
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = (address == null) ? "" : address;
    }

    public abstract void displayInformation();
}