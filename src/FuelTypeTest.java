import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FuelTypeTest {

    @Test
    void testEnumValuesExist() {
        assertNotNull(FuelType.valueOf("PETROL"));
        assertNotNull(FuelType.valueOf("DIESEL"));
        assertNotNull(FuelType.valueOf("ELECTRIC"));
        assertNotNull(FuelType.valueOf("HYBRID"));
    }

    @Test
    void testOrdinalAccess() {
        FuelType[] values = FuelType.values();
        assertEquals(FuelType.PETROL, values[0]);
        assertEquals(FuelType.DIESEL, values[1]);
        assertEquals(FuelType.ELECTRIC, values[2]);
        assertEquals(FuelType.HYBRID, values[3]);
    }

    @Test
    void testFromStringCaseInsensitive() {
        assertEquals(FuelType.PETROL, FuelType.valueOf("PETROL"));
        assertEquals(FuelType.DIESEL, FuelType.valueOf("DIESEL"));
        assertEquals(FuelType.ELECTRIC, FuelType.valueOf("ELECTRIC"));
        assertEquals(FuelType.HYBRID, FuelType.valueOf("HYBRID"));
    }

    @Test
    void testInvalidValueThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> FuelType.valueOf("GASOLINE"));
        assertThrows(IllegalArgumentException.class, () -> FuelType.valueOf("123"));
    }
    @Test
    void testFromStringIgnoreCaseValidValues() {
        assertEquals(FuelType.PETROL, FuelType.fromStringIgnoreCase("petrol"));
        assertEquals(FuelType.DIESEL, FuelType.fromStringIgnoreCase("Diesel"));
        assertEquals(FuelType.ELECTRIC, FuelType.fromStringIgnoreCase("ELECTRIC"));
        assertEquals(FuelType.HYBRID, FuelType.fromStringIgnoreCase("HyBrId"));
    }

    @Test
    void testFromStringIgnoreCaseInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> FuelType.fromStringIgnoreCase("gasoil"));
        assertThrows(IllegalArgumentException.class, () -> FuelType.fromStringIgnoreCase("jetfuel"));
    }
}