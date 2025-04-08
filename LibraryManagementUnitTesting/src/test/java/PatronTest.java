import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class PatronTest {
    private Patron patron;
    private Book book;

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

    @Test
    @DisplayName("TC-PATRON-003: Patron hasCheckedOutBook returns correct value")
    void testHasCheckedOutBook() {
        patron = new Patron("Camila");
        book = new Book("Math", "Euclid");

        assertFalse(patron.hasCheckedOutBook(book), "Should be false if not checked out");
        patron.checkOutBook(book);
        assertTrue(patron.hasCheckedOutBook(book), "Should be true after checking out");
    }

}
