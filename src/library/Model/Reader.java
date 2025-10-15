package library.Model;

import java.util.UUID;

public class Reader 
{
    private String userID;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private boolean isCardValid;

    public Reader(String name, String phoneNumber, String email, String address) 
    {
        this.userID = "USR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;

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
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
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