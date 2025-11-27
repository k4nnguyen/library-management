package library.Model;
//================== MinhNQ =========================
import java.util.ArrayList;
import java.util.List;

public class Reader extends User {
    private int readerId;
    public Reader(int readerId,String name, String phoneNumber, String email, String address) {
        super(name, phoneNumber, email, address);
        this.readerId = readerId;
    }
    @Override
    public void displayInformation() {
        System.out.println("--- Reader Information ---");
        System.out.println("Name: " + getName());
        System.out.println("Phone: " + getPhoneNumber());
        System.out.println("Email: " + getEmail());
        System.out.println("Address: " + getAddress());
        System.out.println("--------------------------");
    }
}