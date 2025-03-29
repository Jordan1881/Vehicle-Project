import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class CarTest {

    private final Owner owner = new Owner("John Doe", "123456789", "0541234567");
    private final RegistrationInfo regInfo = new RegistrationInfo(
            LocalDate.now().minusYears(1),
            LocalDate.now().plusYears(1),
            LocalDate.now()
    );
    private final Instant year = Instant.now();

    @Test
    public void testValidCar() {
        Car car = new Car(4, "Toyota", FuelType.PETROL, year, owner, regInfo, 4, true, null);
        assertEquals("Toyota", car.getManufacturer());
        assertTrue(car.isConvertible());
        assertEquals(4, car.getDoors());
    }

    @Test
    public void testInvalidNumberOfDoors() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Car(4, "Mazda", FuelType.DIESEL, year, owner, regInfo, 1, false, null);
        });
        assertTrue(exception.getMessage().contains("Number of doors must be at least 2"));
    }
    @Test
    public void testAddMaintenanceToCar() {
        Owner dummyOwner = new Owner("Jane Smith", "987654321", "0521111111");
        RegistrationInfo dummyRegInfo = new RegistrationInfo(
                LocalDate.of(2021, 5, 10),
                LocalDate.of(2031, 5, 10),
                LocalDate.of(2023, 12, 1)
        );

        Car car = new Car(4, "Mazda", FuelType.HYBRID,
                Instant.parse("2021-05-10T00:00:00Z"),
                dummyOwner, dummyRegInfo, 4, false, new ArrayList<>());
        car.addMaintenance("Battery check");

        assertEquals(1, car.getMaintenanceHistory().size());
        assertTrue(car.getMaintenanceHistory().get(0).contains("Battery check"));
    }
    @Test
    public void testGetHistory_Car() {
        Owner owner = new Owner("Roni Cohen", "223344556", "0523333333");

        RegistrationInfo regInfo = new RegistrationInfo(
                LocalDate.of(2023, 2, 15),
                LocalDate.of(2027, 2, 15),
                LocalDate.of(2023, 6, 20)
        );

        Car car = new Car(
                4,
                "Mazda",
                FuelType.HYBRID,
                LocalDate.of(2022, 5, 10)
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant(),
                owner,
                regInfo,
                4,
                false,
                new ArrayList<>()
        );

        assertDoesNotThrow(() -> car.getHistory(owner));
    }
}