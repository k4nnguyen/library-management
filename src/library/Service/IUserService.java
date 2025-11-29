package library.Service;

import library.Model.User;
import library.Model.Reader;
import java.time.LocalDate;
import java.util.List;

public interface IUserService {
	// Create a reader 
	Reader createReader(String name, String phoneNumber, String address, String username, String password, LocalDate dob, String gender);

	// Remove by user id (e.g. R001)
	void removeUser(String userId);

	// Update user information
	void updateUser(User user);

	// Lookup
	User findUserById(String userId);
	User findUserByUsername(String username);

	// List / search
	List<User> getAllUsers();
	List<Reader> getAllReaders();
	List<User> searchUsers(String query);

	// Authentication
	boolean authenticate(String username, String password);

	// Statistics
	int getTotalUsers();
}
