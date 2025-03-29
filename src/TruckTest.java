import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class TruckTest {

    @Test
    public void testInvalidCurrentLoadExceedsMax() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Truck truck = new Truck(
                    6,                      // wheels
                    "Volvo",               // manufacturer
                    FuelType.DIESEL,       // fuel type
                    Instant.now(),         // year
                    new Owner("Avi Cohen", "123456789", "0521234567"),
                    new RegistrationInfo(
                            java.time.LocalDate.now().minusYears(1),
                            java.time.LocalDate.now().plusYears(1),
                            java.time.LocalDate.now().minusMonths(6)
                    ),
                    1000.0,     // maxLoadCapacity
                    true,       // hasTrailer
                    1200.0      // currentLoad → שגוי, כי יותר מהמקסימום!
            );
        });

        assertTrue(exception.getMessage().contains("Current load must be between 0 and max load capacity"));
    }

    @Test
    public void testValidTruck() {
        Truck truck = new Truck(
                6,                      // wheels
                "Volvo",               // manufacturer
                FuelType.DIESEL,       // fuel type
                Instant.now(),         // year
                new Owner("Avi Cohen", "123456789", "0521234567"),
                new RegistrationInfo(
                        java.time.LocalDate.now().minusYears(1),
                        java.time.LocalDate.now().plusYears(1),
                        java.time.LocalDate.now().minusMonths(6)
                ),
                1000.0,     // maxLoadCapacity
                true,       // hasTrailer
                500.0       // currentLoad
        );

        assertEquals(1000.0, truck.getMaxLoadCapacity());
        assertTrue(truck.hasTrailer());
        assertEquals(500.0, truck.getCurrentLoad());
    }
    @Test
    public void testInvalidTruckWithLessThanFourWheels() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Truck truck = new Truck(
                    2,                      // wheels → שגוי, כי פחות מ-4
                    "Volvo",               // manufacturer
                    FuelType.DIESEL,       // fuel type
                    Instant.now(),         // year
                    new Owner("Avi Cohen", "123456789", "0521234567"),
                    new RegistrationInfo(
                            java.time.LocalDate.now().minusYears(1),
                            java.time.LocalDate.now().plusYears(1),
                            java.time.LocalDate.now().minusMonths(6)
                    ),
                    1000.0,     // maxLoadCapacity
                    true,       // hasTrailer
                    500.0       // currentLoad
            );
        });

        assertTrue(exception.getMessage().contains("A truck must have at least 4 wheels."));
    }
    @Test
    public void testInvalidTruckWithInvalidManufacturer() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Truck truck = new Truck(
                    6,                      // wheels
                    "Volvo123",            // manufacturer → שגוי, כי מכיל מספרים
                    FuelType.DIESEL,       // fuel type
                    Instant.now(),         // year
                    new Owner("Avi Cohen", "123456789", "0521234567"),
                    new RegistrationInfo(
                            java.time.LocalDate.now().minusYears(1),
                            java.time.LocalDate.now().plusYears(1),
                            java.time.LocalDate.now().minusMonths(6)
                    ),
                    1000.0,     // maxLoadCapacity
                    true,       // hasTrailer
                    500.0       // currentLoad
            );
        });

        assertTrue(exception.getMessage().contains("Manufacturer name must contain only letters and spaces."));
    }

    @Test
    public void testInvalidTruckWithNegativeMaxLoadCapacity() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Truck truck = new Truck(
                    6,                      // wheels
                    "Volvo",               // manufacturer
                    FuelType.DIESEL,       // fuel type
                    Instant.now(),         // year
                    new Owner("Avi Cohen", "123456789", "0521234567"),
                    new RegistrationInfo(
                            java.time.LocalDate.now().minusYears(1),
                            java.time.LocalDate.now().plusYears(1),
                            java.time.LocalDate.now().minusMonths(6)
                    ),
                    -1000.0,     // maxLoadCapacity → שגוי, כי פחות מ-0
                    true,       // hasTrailer
                    500.0       // currentLoad
            );
        });

        assertTrue(exception.getMessage().contains("Max load capacity must be greater than 0."));
    }
    @Test
    public void testAddMaintenanceForTruck() {
        Owner owner = new Owner("David", "987654321", "0526543210");
        RegistrationInfo regInfo = new RegistrationInfo(
                LocalDate.now().minusYears(1),
                LocalDate.now().plusYears(1),
                LocalDate.now().minusDays(60)
        );

        Truck truck = new Truck(
                6,
                "Mercedes",
                FuelType.DIESEL,
                Instant.now(),
                owner,
                regInfo,
                10000.0,
                true,
                5000.0
        );

        truck.addMaintenance("Changed brake pads");
        truck.addMaintenance("Engine oil replaced");

        assertEquals(2, truck.getMaintenanceHistory().size());
        assertTrue(truck.getMaintenanceHistory().get(0).contains("Changed brake pads"));
        assertTrue(truck.getMaintenanceHistory().get(1).contains("Engine oil replaced"));
    }
    @Test
    public void testGetHistory_Truck() {
        Owner owner = new Owner("Eli Dahan", "987654321", "0544444444");

        RegistrationInfo regInfo = new RegistrationInfo(
                LocalDate.of(2020, 8, 1),
                LocalDate.of(2025, 8, 1),
                LocalDate.of(2023, 9, 1)
        );

        Truck truck = new Truck(
                6,
                "Volvo",
                FuelType.DIESEL,
                LocalDate.of(2020, 1, 1)
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant(),
                owner,
                regInfo,
                5000.0,
                true,
                300.0
        );

        assertDoesNotThrow(() -> truck.getHistory(owner));
    }
    @Test
    public void testValidLoad() {
        Truck truck = new Truck(
                6,
                "Volvo",
                FuelType.DIESEL,
                Instant.now(),
                new Owner("Test Owner", "123456789", "0521111111"),
                new RegistrationInfo(LocalDate.now().minusYears(1), LocalDate.now().plusYears(1), LocalDate.now().minusMonths(2)),
                15000, // max load
                true,
                5000   // current load
        );

        assertDoesNotThrow(() -> truck.load(3000));
        assertEquals(8000, truck.getCurrentLoad());
    }

    @Test
    void testOverLoadThrowsException() {
        Truck truck = new Truck(
                6,
                "Volvo",
                FuelType.DIESEL,
                Instant.now(),
                new Owner("Test User", "123456789", "0501234567"),
                new RegistrationInfo(LocalDate.now().minusYears(1), LocalDate.now().plusYears(1), LocalDate.now().minusMonths(3)),
                10000, // maxLoadCapacity
                true,
                2000  // truck weight
        );

        truck.load(8000); // current load = 8000, + weight = 2000 → total = 10000

        // Now even 1kg extra should throw an exception:
        assertThrows(IllegalArgumentException.class, () -> truck.load(1));
    }
}
