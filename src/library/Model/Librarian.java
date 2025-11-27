package library.Model;
// ======================= LAM ==============================
public class Librarian extends User {
    private int librarianId;
    private String startDate;

    public Librarian(int librarianId,String name, String phoneNumber, String email, String address,
                    String startDate) {
        super(name, phoneNumber, email, address);
        this.startDate = startDate;
        this.librarianId = librarianId;
    }



    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }


    @Override
    public void displayInformation() {

        System.out.println("Name: " + getName());
        System.out.println("Phone: " + getPhoneNumber());
        System.out.println("Email: " + getEmail());
        System.out.println("Address: " + getAddress());
        System.out.println("Start Date: " + this.startDate);
    }
}