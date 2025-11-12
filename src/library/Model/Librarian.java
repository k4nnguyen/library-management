package library.Model;
// ======================= LAM =============================
public class Librarian extends User {

    private boolean workingStatus;
    private String startDate;
    private int numberOfLibrarian;

    public Librarian(String name, String phoneNumber, String email, String address, 
                    String startDate, int numberOfLibrarian) {
        super(name, phoneNumber, email, address);
        this.workingStatus = true;
        this.startDate = startDate;
        this.numberOfLibrarian = numberOfLibrarian;
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
        this.startDate = startDate;
    }

    public int getNumberOfLibrarian() {
        return numberOfLibrarian;
    }

    public void setNumberOfLibrarian(int numberOfLibrarian) {
        this.numberOfLibrarian = numberOfLibrarian;
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
        System.out.println("Number of Librarians: " + this.numberOfLibrarian);
    }
}