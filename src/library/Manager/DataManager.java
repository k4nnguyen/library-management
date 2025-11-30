package library.Manager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import library.Model.Book;
import library.Model.Loan;
import library.Model.Reader;

public class DataManager {
	private static final String BOOK_FILE = "data/books.dat";
	private static final String READER_FILE = "data/readers.dat";
	private static final String LOAN_FILE = "data/loans.dat";

	public static List<Book> loadBooks() {
		return loadList(BOOK_FILE);
	}

	public static List<Reader> loadReaders() {
		return loadList(READER_FILE);
	}

	public static List<Loan> loadLoans() {
		return loadList(LOAN_FILE);
	}

	@SuppressWarnings("unchecked")
	private static <T> List<T> loadList(String filename) {
		File f = new File(filename);
		if (!f.exists()) return new ArrayList<>();
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
			return (List<T>) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public static <T> void saveList(String filename, List<T> list) {
		File parent = new File(new File(filename).getParent());
		if (parent != null && !parent.exists()) parent.mkdirs();
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
			oos.writeObject(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveBooks(List<Book> books) { saveList(BOOK_FILE, books); }
	public static void saveReaders(List<Reader> readers) { saveList(READER_FILE, readers); }
	public static void saveLoans(List<Loan> loans) { saveList(LOAN_FILE, loans); }

	// Auto-increment helpers
	public static int nextBookId(List<Book> books) {
		return books.stream().mapToInt(Book::getBookID).max().orElse(0) + 1;
	}
	public static int nextReaderNumber(List<Reader> readers) {
		int max = readers.stream()
			.map(r -> r.getUserID())
			.map(id -> id.replaceAll("[^0-9]", ""))
			.filter(s -> !s.isEmpty())
			.mapToInt(Integer::parseInt)
			.max().orElse(0);
		// If no numeric IDs found, fallback to size+1
		return (max == 0) ? readers.size() + 1 : max + 1;
	}

}
