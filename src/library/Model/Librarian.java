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
            throw new IllegalArgumentException("Start date khong duoc de trong");
        }
        try{
            LocalDate.parse(startDate);
            this.startDate = startDate;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid start date format. Please use YYYY-MM-DD.");
        }
    }

    public String getUserID() {
        return this.id;
    }

    @Override
    public void displayInformation() {
        System.out.println("Librarian ID: " + getUserID());
        System.out.println("Name: " + getName());
        System.out.println("Phone: " + getPhoneNumber());
        System.out.println("Email: " + getEmail());
        System.out.println("Address: " + getAddress());
        System.out.println("Working Status: " + (this.workingStatus ? "Working" : "Not Working"));
        System.out.println("Start Date: " + this.startDate);
    }
}