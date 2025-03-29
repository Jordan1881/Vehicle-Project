import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public abstract class Vehicle implements OwnerHistory {
    protected int wheels;
    protected String manufacturer;
    protected FuelType fuelType;
    protected Instant year;
    protected Owner owner;
    protected RegistrationInfo registrationInfo;
    protected List<String> maintenanceHistory = new ArrayList<>();

    public Vehicle(int wheels, String manufacturer, FuelType fuelType, Instant year, Owner owner, RegistrationInfo registrationInfo) {
        if (this instanceof Motorcycle) {
            if (wheels != 2 && wheels != 3) {
                throw new IllegalArgumentException("Motorcycle must have 2 or 3 wheels.");
            }
        } else if (wheels < 4) {
            throw new IllegalArgumentException("Number of wheels must be at least 4 for non-motorcycles.");
        }
        if (manufacturer == null || !manufacturer.matches("[A-Za-z\\s\\-]+")) {
            throw new IllegalArgumentException("Manufacturer name must contain only letters, spaces, and hyphens.");
        }

        if (manufacturer.length() < 2 || manufacturer.length() > 50) {
            throw new IllegalArgumentException("Manufacturer name must be between 2 and 50 characters.");
        }
        int inputYear = year.atZone(ZoneId.systemDefault()).getYear();
        int currentYear = LocalDate.now().getYear();
        if (inputYear < 1886 || inputYear > currentYear) {
            throw new IllegalArgumentException("Manufacturing year must be between 1886 and current year.");
        }
        if (owner == null) {
            throw new IllegalArgumentException("Owner cannot be null.");
        }
        if (registrationInfo == null) {
            throw new IllegalArgumentException("Registration info cannot be null.");
        }
        if (fuelType == null) {
            throw new IllegalArgumentException("Fuel type cannot be null.");
        }

        this.wheels = wheels;
        this.manufacturer = manufacturer;
        this.fuelType = fuelType;
        this.year = year;
        this.owner = owner;
        this.registrationInfo = registrationInfo;
    }

    public int getWheels() {
        return wheels;
    }

    public void setWheels(int wheels) {
        if (wheels <= 0) {
            throw new IllegalArgumentException("Number of wheels must be positive.");
        }
        this.wheels = wheels;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        if (manufacturer == null || !manufacturer.matches("[A-Za-z\\s\\-]+")) {
            throw new IllegalArgumentException("Manufacturer name must contain only letters, spaces, and hyphens.");
        }
        this.manufacturer = manufacturer;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public Instant getYear() {
        return year;
    }

    public void setYear(Instant year) {
        int inputYear = year.atZone(ZoneId.systemDefault()).getYear();
        int currentYear = LocalDate.now().getYear();
        if (inputYear < 1886 || inputYear > currentYear) {
            throw new IllegalArgumentException("Manufacturing year must be between 1886 and current year.");
        }
        this.year = year;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public RegistrationInfo getRegistrationInfo() {
        return registrationInfo;
    }

    public void setRegistrationInfo(RegistrationInfo registrationInfo) {
        this.registrationInfo = registrationInfo;
    }

    public List<String> getMaintenanceHistory() {
        return maintenanceHistory;
    }

    public void addMaintenance(String description) {
        maintenanceHistory.add(LocalDate.now() + ": " + description);
    }

    public void printInfo() {
        System.out.println("---- Vehicle Information ----");
        System.out.println("Manufacturer: " + getManufacturer());
        System.out.println("Fuel Type: " + getFuelType());
        System.out.println("Year: " + getYear());
        System.out.println("Owner: " + owner.getName());
        System.out.println("Registration Info: " + getRegistrationInfo());
    }

    public abstract boolean validYear(Instant year);

    public abstract boolean validName(String manufacturer);

    @Override
    public void getHistory(Owner owner) {
        System.out.println(STR."Ownership history of: \{owner} on a Vehicle of type \{this.getClass().getSimpleName()}");
    }

    @Override
    public List<Owner> getOwnerHistory() {
        return List.of();
    }
}