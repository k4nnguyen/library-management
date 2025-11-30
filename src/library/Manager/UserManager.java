package library.Manager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import library.Model.Reader;
import library.Model.User;
import library.Service.IUserService;

public class UserManager implements IUserService {

	private final List<Reader> readers;

	public UserManager() {
		this.readers = DataManager.loadReaders();
	}

	@Override
	public Reader createReader(String name, String phoneNumber, String address, String username, String password,
			LocalDate dob, String gender, String email) {
		// email is required and must be valid
		if (email == null || email.trim().isEmpty()) {
			throw new IllegalArgumentException("Email không được để trống");
		}
		final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
		if (!EMAIL_PATTERN.matcher(email).matches()) {
			throw new IllegalArgumentException("Email không đúng định dạng");
		}

		int idNum = DataManager.nextReaderNumber(readers);
		Reader r = new Reader(idNum, name, phoneNumber, address, username, password, dob, gender);
		r.setEmail(email);
		readers.add(r);
		DataManager.saveReaders(readers);
		return r;
	}

	@Override
	public void removeUser(String userId) {
		// remove from readers if present
		Reader target = readers.stream().filter(r -> r.getUserID().equals(userId)).findFirst().orElse(null);
		if (target != null) {
			readers.remove(target);
			DataManager.saveReaders(readers);
		}
	}

	@Override
	public void updateUser(User user) {
		if (user instanceof Reader) {
			Reader r = (Reader) user;
			for (int i = 0; i < readers.size(); i++) {
				if (readers.get(i).getUserID().equals(r.getUserID())) {
					readers.set(i, r);
					DataManager.saveReaders(readers);
					return;
				}
			}
			// if not found, add
			readers.add(r);
			DataManager.saveReaders(readers);
		}
	}

	@Override
	public User findUserById(String userId) {
		for (Reader r : readers)
			if (r.getUserID().equals(userId))
				return r;
		return null;
	}

	@Override
	public User findUserByUsername(String username) {
		for (Reader r : readers)
			if (r.getUsername() != null && r.getUsername().equals(username))
				return r;
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		List<User> out = new ArrayList<>();
		out.addAll(readers);
		return out;
	}

	@Override
	public List<Reader> getAllReaders() {
		return new ArrayList<>(readers);
	}

	@Override
	public List<User> searchUsers(String query) {
		String q = (query == null) ? "" : query.toLowerCase();
		List<User> out = new ArrayList<>();
		for (Reader u : readers) {
			if ((u.getName() != null && u.getName().toLowerCase().contains(q)) ||
					(u.getUsername() != null && u.getUsername().toLowerCase().contains(q)) ||
					(u.getPhoneNumber() != null && u.getPhoneNumber().contains(q))) {
				out.add(u);
			}
		}
		return out;
	}

	@Override
	public boolean authenticate(String username, String password) {
		User u = findUserByUsername(username);
		return u != null && u.checkPassword(password);
	}

	@Override
	public int getTotalUsers() {
		return readers.size();
	}

}
