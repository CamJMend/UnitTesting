import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

class BookTest {
    private Book book;

    @Test
    @DisplayName("TC-BOOK-001: Create a valid book")
    void testBookCreated() {
        book = new Book("Turtles all the way down", "Green");
        assertNotNull(book, "Book should be created");
    }

    @Test
    @DisplayName("TC-BOOK-002: Check out a book sets due date correctly")
    void testCheckOutSetsDueDate() {
        book = new Book("1984", "George Orwell");
        book.checkOut(10);
        assertTrue(book.isCheckedOut(), "Book should be marked as checked out");
        assertEquals(LocalDate.now().plusDays(10), book.getDueDate(), "Due date should be 10 days from now");
    }

    @Test
    @DisplayName("TC-BOOK-003: Return a book resets due date")
    void testReturnResetsDueDate() {
        book = new Book("1984", "George Orwell");
        book.checkOut(5);
        book.returnBook();
        assertFalse(book.isCheckedOut(), "Book should not be checked out");
        assertNull(book.getDueDate(), "Due date should be null after returning");
    }

    @Test
    @DisplayName("TC-BOOK-004: Set due date throws error if book not checked out")
    void testSetDueDateInvalid() {
        book = new Book("1984", "George Orwell");
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            book.setDueDate(LocalDate.now().plusDays(5));
        });
        assertEquals("Cannot set due date for a book that is not checked out.", exception.getMessage());
    }

}
