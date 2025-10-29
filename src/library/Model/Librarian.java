package library.Model;
// ======================= LAM =============================
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
        setLibrarianName(librarianName);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setWorkingStatus(workingStatus);
        setStartDate(startDate);
        setNumberOfLibrarian(numberOfLibrarian);
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

    public void setLibrarianName(String librarianName) {
        if (librarianName == null || librarianName.trim().isEmpty()) {
            throw new IllegalArgumentException("Librarian name cannot be empty");
        }
        this.librarianName = librarianName.trim();
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email.trim();
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }
        if (!isValidPhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
        this.phoneNumber = phoneNumber.trim();
    }

    public void setWorkingStatus(boolean workingStatus) {
        this.workingStatus = workingStatus;
    }

    public void setStartDate(String startDate) {
        if (startDate == null || startDate.trim().isEmpty()) {
            throw new IllegalArgumentException("Start date cannot be empty");
        }
        this.startDate = startDate.trim();
    }

    public void setNumberOfLibrarian(int numberOfLibrarian) {
        if (numberOfLibrarian < 0) {
            throw new IllegalArgumentException("Number of librarians cannot be negative");
        }
        this.numberOfLibrarian = numberOfLibrarian;
    }

    public void setWorking() {
        this.workingStatus = true;
    }

    public void setNotWorking() {
        this.workingStatus = false;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^[+]?[0-9]{10,15}$";
        return phoneNumber.replaceAll("\\s", "").matches(phoneRegex);
    }

    @Override
    public String toString() {
        return String.format("Librarian{ID: %s, Name: %s, Email: %s, Phone: %s, Status: %s, Start Date: %s}",
                librarianID, librarianName, email, phoneNumber, 
                workingStatus ? "Working" : "Not Working", startDate);
    }

    public String getDetails() {
        return String.format(
            "Librarian Details:\n" +
            "Librarian ID: %s\n" +
            "Name: %s\n" +
            "Email: %s\n" +
            "Phone Number: %s\n" +
            "Status: %s\n" +
            "Start Date: %s\n" +
            "Number of Librarians: %d",
            librarianID, librarianName, email, phoneNumber,
            workingStatus ? "Working" : "Not Working",
            startDate, numberOfLibrarian
        );
    }
}