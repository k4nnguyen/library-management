package library.Model;
// ======================= LAM ==============================
public class Librarian extends User {

    private boolean workingStatus;
    private String id,startDate;

    public Librarian(int idNumber, String name, String phoneNumber, String email, String address, 
                    String username, String password,String startDate, 
                    int numberOfLibrarian) {
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
        if (startDate != null && !startDate.trim().isEmpty()) {
            this.startDate = startDate;
        }
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