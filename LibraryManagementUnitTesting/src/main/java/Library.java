import java.time.LocalDate;  
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;  
import java.util.HashMap;  
import java.util.List; 
  
public class Library {  
    private List<Book> books;  
    private List<Patron> patrons;  
    private HashMap<Patron, Book> checkedOutBooks;  
  
    public Library() {  
        books = new ArrayList<>();  
        patrons = new ArrayList<>();  
        checkedOutBooks = new HashMap<>();  
    }  
  
    public boolean addBook(Book book) {
        for (Book b : books) {
            if (b.equals(book)) {
                return false;
            }
        }
        books.add(book);
        return true;
    }
  
    public void removeBook(Book book) {  
        books.remove(book);  
    }  
  
    public void addPatron(Patron patron) {  
        patrons.add(patron);  
    }  
  
    public boolean checkOutBook(Patron patron, Book book, int daysToDue) {  
        if (books.contains(book) && !book.isCheckedOut()) {  
            book.checkOut(daysToDue);  
            checkedOutBooks.put(patron, book);  
            patron.checkOutBook(book);
            return true;  
        }  
        return false;  
    }  
    
  
    public boolean returnBook(Patron patron, Book book) {  
        if (checkedOutBooks.get(patron) == book) {
            book.returnBook();  
            checkedOutBooks.remove(patron);  
            patron.returnBook(book);  
            return true;  
        }
        return false;  
    }
  
    public double calculateFine(Patron patron, Book book) {  
        if (checkedOutBooks.get(patron) == book && book.isCheckedOut()) {  
            LocalDate today = LocalDate.now();  
            long daysOverdue = ChronoUnit.DAYS.between(book.getDueDate(), today);  
            if (daysOverdue > 0) {  
                return daysOverdue * 0.50;  
            }  
        }  
        return 0.0;  
    }    
  
    public List<Book> listAvailableBooks() {  
        List<Book> availableBooks = new ArrayList<>();  
        for (Book book : books) {  
            if (!book.isCheckedOut()) {  
                availableBooks.add(book);  
            }  
        }  
        return availableBooks;  
    }  
  
    public List<Patron> listPatrons() {  
        return patrons;  
    }  
}  
