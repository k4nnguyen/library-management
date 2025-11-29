package library.Model;

public interface Borrowable {
    boolean isAvailable();
    boolean canBorrow();
    boolean borrow();
    void returnItem(); 
    String getItemName();
    int getItemID(); 
}
