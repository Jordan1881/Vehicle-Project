import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OwnerTest {

    @Test
    public void testValidOwner() {
        Owner owner = new Owner("David Levi", "123456789", "0521234567");
        assertEquals("David Levi", owner.getName());
        assertEquals("123456789", owner.getId());
        assertEquals("0521234567", owner.getPhone());
    }

    @Test
    public void testInvalidNameWithNumbers() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Owner("David123", "123456789", "0521234567");
        });
        assertTrue(exception.getMessage().contains("Name must contain only letters and spaces"));
    }

    @Test
    public void testInvalidIdWithLetters() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Owner("David Levi", "ABC123", "0521234567");
        });
        assertTrue(exception.getMessage().contains("ID must contain only digits"));
    }

    @Test
    public void testInvalidPhoneFormat() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Owner("David Levi", "123456789", "phone123");
        });
        assertTrue(exception.getMessage().contains("Phone must be digits and start with 05"));
    }
}