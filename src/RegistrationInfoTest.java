import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class RegistrationInfoTest {

    @Test
    public void testValidRegistrationInfo() {
        LocalDate purchase = LocalDate.of(2022, 1, 1);
        LocalDate expiry = LocalDate.now().plusYears(2);
        LocalDate maintenance = LocalDate.of(2023, 6, 1);

        RegistrationInfo info = new RegistrationInfo(purchase, expiry, maintenance);

        assertEquals(purchase, info.getPurchaseDate());
        assertEquals(expiry, info.getLicenseExpiryDate());
        assertEquals(maintenance, info.getLastMaintenanceDate());
        assertTrue(info.isLicenseValid());
        assertTrue(info.daysSinceLastMaintenance() >= 0);
    }

    @Test
    public void testInvalidExpiryBeforePurchase() {
        LocalDate purchase = LocalDate.of(2023, 1, 1);
        LocalDate expiry = LocalDate.of(2022, 12, 31);
        LocalDate maintenance = LocalDate.of(2023, 6, 1);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new RegistrationInfo(purchase, expiry, maintenance);
        });
        assertTrue(exception.getMessage().contains("Expiry date must be after purchase date."));
    }

    @Test
    public void testInvalidFutureMaintenanceDate() {
        LocalDate purchase = LocalDate.of(2023, 1, 1);
        LocalDate expiry = LocalDate.of(2025, 1, 1);
        LocalDate maintenance = LocalDate.now().plusDays(10); // תאריך עתידי

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new RegistrationInfo(purchase, expiry, maintenance);
        });
        assertTrue(exception.getMessage().contains("Maintenance date cannot be in the future."));
    }

    @Test
    public void testLicenseValidityExpired() {
        LocalDate purchase = LocalDate.of(2020, 1, 1);
        LocalDate expiry = LocalDate.now().minusDays(1);
        LocalDate maintenance = LocalDate.of(2023, 6, 1);

        RegistrationInfo info = new RegistrationInfo(purchase, expiry, maintenance);

        assertFalse(info.isLicenseValid());
    }

    @Test
    public void testDaysSinceLastMaintenance() {
        LocalDate maintenance = LocalDate.now().minusDays(30);
        RegistrationInfo info = new RegistrationInfo(
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2025, 1, 1),
                maintenance
        );
        assertEquals(30, info.daysSinceLastMaintenance());
    }
}