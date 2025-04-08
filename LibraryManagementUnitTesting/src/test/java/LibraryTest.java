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

    @Test
    @DisplayName("TC-LIB-008: Remove a book from library")
    void testRemoveBook() {
        book = new Book("Sapiens", "Yuval Harari");
        library.addBook(book);
        library.removeBook(book);
        assertFalse(library.listAvailableBooks().contains(book), "Book should be removed from library");
    }

    @Test
    @DisplayName("TC-LIB-009: Return a book that wasn't checked out by the patron")
    void testReturnBookWrongPatron() {
        book = new Book("Mystery", "Author");
        Patron otherPatron = new Patron("Other");
        library.addBook(book);
        library.checkOutBook(otherPatron, book, 5);
        assertFalse(library.returnBook(patron, book), "Should not return book checked out by another patron");
    }

    @Test
    @DisplayName("TC-LIB-010: Calculate fine for book not overdue")
    void testCalculateFineNoOverdue() {
        book = new Book("Fresh", "Author");
        library.addBook(book);
        library.checkOutBook(patron, book, 5);
        double fine = library.calculateFine(patron, book);
        assertEquals(0.0, fine, "There should be no fine if book is not overdue");
    }

    @Test
    @DisplayName("TC-LIB-011: Try to check out a non-existent book")
    void testCheckoutNonexistentBook() {
        book = new Book("Invisible", "Ghost");
        assertFalse(library.checkOutBook(patron, book, 3), "Should not checkout a book that is not in library");
    }

}
 