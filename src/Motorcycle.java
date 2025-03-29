import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Motorcycle extends Vehicle implements Maintainable, OwnerHistory {
    private boolean hasPassengerHandles;

    public Motorcycle(int wheels, String manufacturer, FuelType fuelType, Instant year,
                      Owner owner, RegistrationInfo registrationInfo, boolean hasPassengerHandles) {

        // Validate wheels count before calling super constructor
        if (wheels != 2 && wheels != 3) {
            throw new IllegalArgumentException("Motorcycle must have 2 or 3 wheels.");
        }

        super(wheels, manufacturer, fuelType, year, owner, registrationInfo);
        this.hasPassengerHandles = hasPassengerHandles;
    }

    public boolean isHasPassengerHandles() {
        return hasPassengerHandles;
    }

    public void setHasPassengerHandles(boolean hasPassengerHandles) {
        this.hasPassengerHandles = hasPassengerHandles;
    }

    @Override
    public void addMaintenance(String desc) {
        maintenanceHistory.add(LocalDate.now() + ": " + desc);
    }

    @Override
    public List<String> getMaintenanceHistory() {
        return maintenanceHistory;
    }

    @Override
    public void printInfo() {
        System.out.println(this);
    }

    @Override
    public void getHistory(Owner owner) {
        System.out.println("Ownership history for motorcycle owned by: " + owner.getName());
    }

    @Override
    public boolean validYear(Instant year) {
        int inputYear = year.atZone(java.time.ZoneId.systemDefault()).getYear();
        int currentYear = LocalDate.now().getYear();
        return inputYear >= 1886 && inputYear <= currentYear;
    }

    @Override
    public boolean validName(String manufacturer) {
        return manufacturer != null && manufacturer.matches("^[A-Za-z\\s\\-]+$");
    }

    @Override
    public String toString() {
        return "Motorcycle{" +
                "manufacturer='" + getManufacturer() + '\'' +
                ", wheels=" + getWheels() +
                ", fuelType=" + getFuelType() +
                ", year=" + getYear() +
                ", hasPassengerHandles=" + hasPassengerHandles +
                '}';
    }

    @Override
    public List<Owner> getOwnerHistory() {
        return List.of();
    }
}