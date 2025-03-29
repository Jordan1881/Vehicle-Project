

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Car extends Vehicle implements Maintainable, OwnerHistory {
    private int doors;
    private boolean convertible;
    private List<String> previousOwners;

    public Car(int wheels, String manufacturer, FuelType fuelType, Instant year,
               Owner owner, RegistrationInfo registrationInfo,
               int doors, boolean convertible, List<String> maintenanceHistory) {

        super(wheels, manufacturer, fuelType, year, owner, registrationInfo);

        if (doors < 2) {
            throw new IllegalArgumentException("Number of doors must be at least 2");
        }

        this.doors = doors;
        this.convertible = convertible;

        if (maintenanceHistory != null) {
            this.maintenanceHistory = maintenanceHistory;
        }

    }

    public int getDoors() {
        if (doors < 2) {
            throw new IllegalArgumentException("A car must have at least 2 doors.");
        }
        return doors;
    }

    public void setDoors(int doors) {
        if (doors < 2) {
            throw new IllegalArgumentException("A car must have at least 2 doors.");
        }
        this.doors = doors;
    }

    public boolean isConvertible() {
        return convertible;
    }

    public void setConvertible(boolean convertible) {
        this.convertible = convertible;
    }

    public List<String> getPreviousOwners() {
        return previousOwners;
    }

    public void setPreviousOwners(List<String> previousOwners) {
        this.previousOwners = previousOwners;
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
        boolean b = inputYear >= 1886 && inputYear <= currentYear;
        try {
            if (inputYear < 1886 || inputYear > currentYear) {
                throw new IllegalArgumentException("Invalid year: " + inputYear);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid year: " + e.getMessage());
        }
        return b;
    }

    @Override
    public boolean validName(String manufacturer) {
        try {
            boolean b = manufacturer != null && manufacturer.matches("^[A-Za-z\\s]+$");
        }
        catch (NullPointerException e) {
            throw new IllegalArgumentException("Manufacturer name cannot be null.");
        }
        return manufacturer != null && manufacturer.matches("^[A-Za-z\\s]+$");
    }

    @Override
    public void getHistory(Owner owner) {
        System.out.println("Ownership history for car owned by: " + owner.getName());
        if (previousOwners != null && !previousOwners.isEmpty()) {
            System.out.println("Previous owners:");
            for (String name : previousOwners) {
                System.out.println("- " + name);
            }
        }
    }

    @Override
    public List<Owner> getOwnerHistory() {
        return List.of();
    }

    @Override
    public String toString() {
        return "Car{" +
                "manufacturer='" + getManufacturer() + '\'' +
                ", wheels=" + getWheels() +
                ", fuelType=" + getFuelType() +
                ", year=" + getYear() +
                ", doors=" + doors +
                ", convertible=" + convertible +
                '}';
    }





}
