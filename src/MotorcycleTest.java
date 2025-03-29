import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class MotorcycleTest {

    private final Owner dummyOwner = new Owner("Test Rider", "123456789", "0521234567");
    private final RegistrationInfo dummyRegInfo = new RegistrationInfo(
            LocalDate.now().minusYears(1),
            LocalDate.now().plusYears(1),
            LocalDate.now().minusMonths(6)
    );

    @Test
    public void testValidMotorcycleWith2Wheels() {
        Motorcycle m = new Motorcycle(
                2,
                "Honda",
                FuelType.PETROL,
                Instant.now(),
                dummyOwner,
                dummyRegInfo,
                true
        );

        assertEquals(2, m.getWheels());
        assertEquals("Honda", m.getManufacturer());
        assertTrue(m.isHasPassengerHandles());
    }

    @Test
    public void testValidMotorcycleWith3Wheels() {
        Motorcycle m = new Motorcycle(
                3,
                "Can-Am",
                FuelType.ELECTRIC,
                Instant.now(),
                dummyOwner,
                dummyRegInfo,
                false
        );

        assertEquals(3, m.getWheels());
        assertEquals("Can-Am", m.getManufacturer());
        assertFalse(m.isHasPassengerHandles());
    }

    @Test
    public void testInvalidMotorcycleWithTooManyWheels() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Motorcycle(
                    4, // ❌ invalid
                    "Yamaha",
                    FuelType.DIESEL,
                    Instant.now(),
                    dummyOwner,
                    dummyRegInfo,
                    false
            );
        });

        assertTrue(exception.getMessage().contains("Motorcycle must have 2 or 3 wheels."));
    }

    @Test
    public void testInvalidManufacturerWithNumbers() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Motorcycle(
                    2,
                    "Suzuki123",
                    FuelType.HYBRID,
                    Instant.now(),
                    dummyOwner,
                    dummyRegInfo,
                    true
            );
        });

        assertTrue(exception.getMessage().contains("Manufacturer name must contain only letters, spaces, and hyphens."));    }

    @Test
    public void testInvalidWheelsZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Motorcycle(
                    0,
                    "Harley",
                    FuelType.PETROL,
                    Instant.now(),
                    dummyOwner,
                    dummyRegInfo,
                    false
            );
        });

        assertTrue(exception.getMessage().contains("Motorcycle must have 2 or 3 wheels."));
    }
    @Test
    void testAddMaintenanceMotorcycle() {
        Motorcycle bike = new Motorcycle(2, "Yamaha", FuelType.PETROL, Instant.now(),
                new Owner("John Doe", "123456789", "0501234567"),
                new RegistrationInfo(LocalDate.now().minusYears(1), LocalDate.now().plusYears(1), LocalDate.now().minusDays(30)),
                true);

        bike.addMaintenance("Oil change");
        bike.addMaintenance("Chain adjustment");

        List<String> history = bike.getMaintenanceHistory();
        assertEquals(2, ((List<?>) history).size());
        assertTrue(history.get(0).contains("Oil change"));
        assertTrue(history.get(1).contains("Chain adjustment"));
    }
    @Test
    public void testAddMaintenanceToMotorcycle() {
        Owner dummyOwner = new Owner("John Doe", "123456789", "050-0000000");
        RegistrationInfo dummyRegInfo = new RegistrationInfo(
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2030, 1, 1),
                LocalDate.of(2023, 1, 1)
        );

        Motorcycle moto = new Motorcycle(2, "Yamaha", FuelType.PETROL,
                Instant.parse("2020-01-01T00:00:00Z"),
                dummyOwner, dummyRegInfo, true);

        moto.addMaintenance("Changed oil");
        moto.addMaintenance("Replaced tire");

        assertEquals(2, moto.getMaintenanceHistory().size());
        assertTrue(moto.getMaintenanceHistory().get(0).contains("Changed oil"));
        assertTrue(moto.getMaintenanceHistory().get(1).contains("Replaced tire"));
    }
    @Test
    public void testGetHistory_Motorcycle() {
        Owner owner = new Owner("David Levi", "123456789", "0521111111");

        RegistrationInfo regInfo = new RegistrationInfo(
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2023, 5, 1)
        );

        Motorcycle motorcycle = new Motorcycle(
                2,
                "Yamaha",
                FuelType.PETROL,
                LocalDate.of(2021, 1, 1)
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant(),
                owner,
                regInfo,
                true
        );

        // Since getHistory prints to console, we just make sure it runs without throwing
        assertDoesNotThrow(() -> motorcycle.getHistory(owner));
    }
}