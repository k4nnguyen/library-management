
package library.Model;
public class Librarian {
    
    private static int nextID = 1;
    private String librarianID;
    private String librarianName;
    private String email;
    private String phoneNumber;
    private boolean workingStatus;
    private String startDate;
    private int numberOfLibrarian;

    
    public Librarian(String librarianName, String email, String phoneNumber,
                     boolean workingStatus, String startDate, int numberOfLibrarian) {
        this.librarianID = "LIB" + String.format("%03d", nextID++);
        this.librarianName = librarianName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.workingStatus = workingStatus;
        this.startDate = startDate;
        this.numberOfLibrarian = numberOfLibrarian;
    }

    public String getLibrarianID() {
        return librarianID;
    }

    public String getLibrarianName() {
        return librarianName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getStartDate() {
        return startDate;
    }

    public int getNumberOfLibrarian() {
        return numberOfLibrarian;
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
}
