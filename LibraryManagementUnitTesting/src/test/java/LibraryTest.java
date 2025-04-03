import java.time.LocalDate;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;


class LibraryTest {
    private Library library;
    private Patron patron;
    private Book book;

    @BeforeEach
    void setUp() {
        library = new Library();
        patron = new Patron("Miguel Pantera");
    }

    @Test
    @DisplayName("TC-LIB-001: Add a duplicate book")
    void testAddDuplicateBook() {
        book = new Book("Harry Potter", "JK Rowling");
        assertTrue(library.addBook(book), "Book should be added to the library");
        assertFalse(library.addBook(book), "Duplicate book should not be added to the library");
    }


    @Test
    @DisplayName("TC-LIB-002: Checkout an unavailable book")
    void testCheckoutUnavailableBook() {
        book = new Book("Algebra", "Socrates");
        library.addBook(book);
        library.checkOutBook(patron, book, 3);
        assertFalse(library.checkOutBook(patron, book, 3), "Book already checked out");
    }

    @Test
    @DisplayName("TC-LIB-003: Return a book not checked out")
    void testReturnBookNotCheckedOut() {
        book = new Book("Bajo la misma estrella", "Green");
        library.addBook(book);
        assertFalse(library.returnBook(patron, book), "Should not allow returning a non-checked-out book");
    }

    @Test
    @DisplayName("TC-LIB-004: Calculate fine for overdue book")
    void testCalculateFineOverdueBook() {
        book = new Book("Bajo la misma estrella", "Green");
        library.addBook(book);
        library.checkOutBook(patron, book, 7);
        book.setDueDate(LocalDate.now().minusDays(10));
        double fine = library.calculateFine(patron, book);  
        assertEquals(5.00, fine, "Fine calculation is incorrect");
    }

    @Test
    @DisplayName("TC-LIB-005: List available books")
    void testListAvailableBooks() {
        Book book1 = new Book("Book1", "Author 1");
        Book book2 = new Book("Book2", "Author 2");
        Book book3 = new Book("Book3", "Author 3");
        Book book4 = new Book("Book4", "Author 4");
        Book book5 = new Book("Book5", "Author 5");

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);
        library.addBook(book5);

        library.checkOutBook(patron, book4, 7);
        library.checkOutBook(patron, book5, 7);

        List<Book> availableBooks = library.listAvailableBooks();

        assertEquals(3, availableBooks.size(), "Available books count is incorrect");

        List<String> availableBookTitles = new ArrayList<>();
        for (Book availableBook : availableBooks) {
            availableBookTitles.add(availableBook.getTitle());
        }

        assertTrue(availableBookTitles.contains("Book1"));
        assertTrue(availableBookTitles.contains("Book2"));
        assertTrue(availableBookTitles.contains("Book3"));
    }


    @Test
    @DisplayName("TC-LIB-006: List patrons")
    void testListPatrons() {
        Patron patron1 = new Patron("Juan Mario");
        Patron patron2 = new Patron("Juana Maria");
        library.addPatron(patron1);
        library.addPatron(patron2);
        List<Patron> patrons = library.listPatrons();
        assertTrue(patrons.contains(patron1) && patrons.contains(patron2), "Patron listing incorrect");
    }

    @Test  
    @DisplayName("TC-LIB-007: Add book")
    public void testAddBook() {  
        library = new Library();  
        book = new Book("1984", "George Orwell");  
        library.addBook(book);  
        assertTrue(library.listAvailableBooks().contains(book));  
    }  
}

class BookTest {
    private Book book;
    @Test
    @DisplayName("TC-BOOK-001: Create a valid book")
    void testBookCreated() {
        book = new Book("Turtles all the way down", "Green");
        assertNotNull(book, "Book should be created");
    }
}

class PatronTest {
    private Patron patron;
    private Book book;
    private Library library;
    @Test
    @DisplayName("TC-PATRON-001: Create a valid patron")
    void testRegisterPatron() {
        patron = new Patron("Camila Mendoza");
        assertNotNull(patron, "Patron should be created");
    }

    @Test
    @DisplayName("TC-PATRON-002: Patron checks out and returns a book")
    void testCheckOutAndReturnBook() {
        patron = new Patron("Miguel");
        book = new Book("Algebra", "Author");
        assertTrue(patron.getCheckedOutBooks().isEmpty(), "Patron should have no books initially");
        patron.checkOutBook(book);
        assertTrue(patron.getCheckedOutBooks().contains(book), "Patron should have checked out the book");
        patron.returnBook(book);
        assertFalse(patron.getCheckedOutBooks().contains(book), "Patron should have returned the book");
    }
}
 