// ✅ Truck.java – כולל ולידציות, מימוש מלא של ממשקים וטיפול בחריגות בטוחה

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public class Truck extends Vehicle implements Loadable, Maintainable, OwnerHistory {
    private double maxLoadCapacity;
    private boolean hasTrailer;
    private double currentLoad;
    private double weight;

    public Truck(int wheels, String manufacturer, FuelType fuelType, Instant year,
                 Owner owner, RegistrationInfo registrationInfo,
                 double maxLoadCapacity, boolean hasTrailer, double currentLoad) {

        super(wheels, manufacturer, fuelType, year, owner, registrationInfo);
        if (wheels < 4) {
            throw new IllegalArgumentException("A truck must have at least 4 wheels.");
        }
        if (manufacturer == null || !manufacturer.matches("^[A-Za-z\\s]+$")) {
            throw new IllegalArgumentException("Manufacturer name must contain only letters and spaces.");
        }

        if (maxLoadCapacity <= 0) {
            throw new IllegalArgumentException("Max load capacity must be greater than 0.");
        }

        if (currentLoad < 0 || currentLoad > maxLoadCapacity) {
            throw new IllegalArgumentException("Current load must be between 0 and max load capacity.");
        }

        this.maxLoadCapacity = maxLoadCapacity;
        this.hasTrailer = hasTrailer;
        this.currentLoad = currentLoad;
    }

    public double getMaxLoadCapacity() {
        return maxLoadCapacity;
    }

    public boolean hasTrailer() {
        return hasTrailer;
    }

    public double getCurrentLoad() {
        return currentLoad;
    }

    @Override
    public void unload() {
        if (currentLoad == 0) {
            throw new IllegalArgumentException("Cannot unload, truck is already empty.");
        }
        currentLoad = 0;
    }


    @Override
    public double getMaxLoad() {
        return maxLoadCapacity;
    }

    @Override
    public boolean canLoad(double weight) {
        return (currentLoad + weight) <= maxLoadCapacity;
    }

    @Override
    public void load(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Load amount must be positive.");
        }

        double totalWeightAfterLoad = weight + currentLoad + amount;
        if (totalWeightAfterLoad > maxLoadCapacity) {
            throw new IllegalArgumentException(STR."Cannot load: total weight (\{totalWeightAfterLoad}) exceeds maximum capacity (\{maxLoadCapacity}).");
        }

        currentLoad += amount;
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
    public boolean validYear(Instant year) {
        int inputYear = year.atZone(java.time.ZoneId.systemDefault()).getYear();
        int currentYear = LocalDate.now().getYear();
        return inputYear >= 1886 && inputYear <= currentYear;
    }

    @Override
    public boolean validName(String manufacturer) {
        return manufacturer != null && manufacturer.matches("^[A-Za-z\\s]+$");
    }

    @Override
    public void getHistory(Owner owner) {
        System.out.println("Ownership history for truck owned by: " + owner.getName());
    }

    @Override
    public String toString() {
        return STR."Truck{manufacturer='\{getManufacturer()}\{'\''}, wheels=\{getWheels()}, fuelType=\{getFuelType()}, year=\{getYear()}, maxLoadCapacity=\{maxLoadCapacity}, hasTrailer=\{hasTrailer}, currentLoad=\{currentLoad}\{'}'}";
    }

    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be positive.");
        }
        this.weight = weight;
    }

    @Override
    public List<Owner> getOwnerHistory() {
        return List.of();
    }
}