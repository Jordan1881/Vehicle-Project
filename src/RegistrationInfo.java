// RegistrationInfo.java

import java.time.LocalDate;

public class RegistrationInfo {
    private LocalDate purchaseDate;
    private LocalDate licenseExpiryDate;
    private LocalDate lastMaintenanceDate;

    public RegistrationInfo(LocalDate purchaseDate, LocalDate licenseExpiryDate, LocalDate lastMaintenanceDate) {
        if (licenseExpiryDate.isBefore(purchaseDate)) {
            throw new IllegalArgumentException("Expiry date must be after purchase date.");
        }

        if (lastMaintenanceDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Maintenance date cannot be in the future.");
        }

        this.purchaseDate = purchaseDate;
        this.licenseExpiryDate = licenseExpiryDate;
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public LocalDate getLicenseExpiryDate() {
        return licenseExpiryDate;
    }

    public LocalDate getLastMaintenanceDate() {
        return lastMaintenanceDate;
    }

    public long daysSinceLastMaintenance() {
        return LocalDate.now().toEpochDay() - lastMaintenanceDate.toEpochDay();
    }

    public boolean isLicenseValid() {
        return licenseExpiryDate.isAfter(LocalDate.now());
    }

    @Override
    public String toString() {
        return "Purchase Date: " + purchaseDate +
                ", License Expiry: " + licenseExpiryDate +
                ", Last Maintenance: " + lastMaintenanceDate;
    }
}
