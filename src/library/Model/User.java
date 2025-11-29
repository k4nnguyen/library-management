package library.Model;
//================== MinhNQ =========================
import java.io.Serializable;
import java.util.regex.Pattern;

public abstract class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private String username;
    private String password; 

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PHONE_REGEX = "\\d{10}";

    public User(String name, String phoneNumber, String email, String address, 
                String username, String password) {
        
        setName(name);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setAddress(address);
        setUsername(username);
        setPassword(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên không được để trống!");
        }
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || !phoneNumber.matches(PHONE_REGEX)) {
            throw new IllegalArgumentException("Số điện thoại phải là 10 chữ số");
        }
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !Pattern.matches(EMAIL_REGEX, email)) {
            throw new IllegalArgumentException("Email không đúng định dạng");
        }
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = (address == null) ? "" : address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username không được để trống!");
        }
        this.username = username;
    }

    public void setPassword(String password) {
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password phải có ít nhất 6 ký tự");
        }
        this.password = password; 
    }
    
    public boolean checkPassword(String passwordAttempt) {
        if (passwordAttempt == null) {
            return false;
        }
        return this.password.equals(passwordAttempt);
    }

    public abstract void displayInformation();
    public abstract String getUserID();
}