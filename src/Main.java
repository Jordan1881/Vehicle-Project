import java.io.PrintWriter;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static List<Vehicle> vehicles = new ArrayList<>();

    public static void addVehicle(){
        System.out.println("Please select the type of Vehicle to add:");
        System.out.println("1 - Car (requires doors and convertible flag)");
        System.out.println("2 - Motorcycle (requires passenger handles flag)");
        System.out.println("3 - Truck (requires max load capacity and trailer flag)");

        System.out.println("Enter your choice: ");
        int vehicleType = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter manufacturer: ");
        String manufacturer = scanner.nextLine();

        int year = 0;
        boolean validYear = false;


        do {
            try {
                System.out.print("Enter manufacturing year: ");
                year = Integer.parseInt(scanner.nextLine());

                if (year < 1886 || year > LocalDate.now().getYear()) {
                    throw new IllegalArgumentException("Year must be between 1886 and current year.");
                }

                validYear = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Please enter a valid year.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!validYear);

        // Convert int year to Instant (representing Jan 1st of that year)
        Instant yearInstant = LocalDate.of(year, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant();

        int wheels = 0;
        boolean validWheels = false;

        do {
            try {
                System.out.print("Enter number of wheels: ");
                wheels = Integer.parseInt(scanner.nextLine());

                if (wheels <= 0) {
                    throw new IllegalArgumentException("Number of wheels must be positive.");
                }

                validWheels = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format. Please enter a valid integer.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!validWheels);

        FuelType fuelType = null;
        boolean validFuel = false;

        do {
            try {
                System.out.println("Select fuel type:");
                System.out.println("1 - PETROL");
                System.out.println("2 - DIESEL");
                System.out.println("3 - ELECTRIC");
                System.out.println("4 - HYBRID");
                System.out.print("Enter fuel type number: ");
                int fuelOption = Integer.parseInt(scanner.nextLine());

                if (fuelOption < 1 || fuelOption > FuelType.values().length) {
                    throw new IllegalArgumentException("Invalid fuel option.");
                }

                fuelType = FuelType.values()[fuelOption - 1];
                validFuel = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } while (!validFuel);


        System.out.print("Enter owner's full name: ");
        String ownerName = scanner.nextLine();

        System.out.print("Enter owner's ID: ");
        String ownerId = scanner.nextLine();

        System.out.print("Enter owner's phone number: ");
        String ownerPhone = scanner.nextLine();

        Owner owner = new Owner(ownerName, ownerId, ownerPhone);

        System.out.print("Enter purchase date (YYYY-MM-DD): ");
        LocalDate purchaseDate = LocalDate.parse(scanner.nextLine());

        System.out.print("Enter license expiry date (YYYY-MM-DD): ");
        LocalDate licenseExpiry = LocalDate.parse(scanner.nextLine());

        System.out.print("Enter last maintenance date (YYYY-MM-DD): ");
        LocalDate lastMaintenance = LocalDate.parse(scanner.nextLine());

        RegistrationInfo registrationInfo = new RegistrationInfo(purchaseDate, licenseExpiry, lastMaintenance);
        switch (vehicleType) {
            case 1: // Car
                System.out.print("Enter number of doors: ");
                int doors = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Is it convertible? (true/false): ");
                boolean convertible = scanner.nextBoolean();
                scanner.nextLine();

                Car car = new Car(wheels, manufacturer, fuelType, yearInstant, owner, registrationInfo, doors, convertible, null);
                vehicles.add(car); // Add the car to the list of vehicles
                System.out.println("Car added: " + car);
                break;

            case 2: // Motorcycle
                System.out.print("Does it have passenger handles? (true/false): ");
                boolean hasPassengerHandles = scanner.nextBoolean();
                scanner.nextLine();

                Motorcycle motorcycle = new Motorcycle(wheels, manufacturer, fuelType, yearInstant, owner, registrationInfo, hasPassengerHandles);
                vehicles.add(motorcycle); // Add the motorcycle to the list of vehicles
                System.out.println("Motorcycle added: " + motorcycle);
                break;

            case 3: // Truck
                System.out.print("Enter max load capacity: ");
                double maxLoadCapacity = scanner.nextDouble();
                scanner.nextLine();

                double currentLoad = 0;
                boolean validLoad = false;

                do {
                    try {
                        System.out.print("Enter current load: ");
                        currentLoad = Double.parseDouble(scanner.nextLine());

                        if (currentLoad < 0 || currentLoad > maxLoadCapacity) {
                            throw new IllegalArgumentException("Current load must be between 0 and max load capacity.");
                        }

                        validLoad = true;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number format. Please enter a valid number.");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                } while (!validLoad);

                System.out.print("Does it have a trailer? (true/false): ");
                boolean hasTrailer = scanner.nextBoolean();
                scanner.nextLine();

                Truck truck = new Truck(wheels, manufacturer, fuelType, yearInstant, owner, registrationInfo, maxLoadCapacity, hasTrailer, currentLoad);
                vehicles.add(truck);
                System.out.println("Truck added: " + truck);
                break;

            default:
                System.out.println("Invalid Vehicle type selected.");
        }





    }
    public static void saveVehiclesToFile() {
        try (PrintWriter writer = new PrintWriter("vehicles.txt")) {
            for (Vehicle v : vehicles) {
                writer.println(v.toString());
            }
            System.out.println("Vehicles saved to file successfully.");
        } catch (Exception e) {
            System.out.println("Error saving vehicles: " + e.getMessage());
        }
    }
    public static void loadVehiclesFromFile() {
        System.out.println("\n=== Loaded Vehicles from File ===");

        try (Scanner fileScanner = new Scanner(new java.io.File("vehicles.txt"))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println("Error loading vehicles: " + e.getMessage());
        }
    }
    public static void showAllVehicles() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles available.");
            return;
        }

        System.out.println("\n=== Vehicle List ===");
        for (Vehicle v : vehicles) {
            System.out.println(v);
        }
    }
    public static Vehicle selectVehicleFromList() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles in the system.");
            return null;
        }

        System.out.println("\nSelect a vehicle by number:");
        for (int i = 0; i < vehicles.size(); i++) {
            System.out.println((i + 1) + " - " + vehicles.get(i).getManufacturer());
        }

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice < 1 || choice > vehicles.size()) {
                System.out.println("Invalid selection.");
                return null;
            }
            return vehicles.get(choice - 1);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return null;
        }
    }
    public static void handleShowVehicleInfo() {
        Vehicle v = selectVehicleFromList();
        if (v != null) showVehicleInfo(v);
    }

    public static void handleShowVehicleOwnerHistory() {
        Vehicle v = selectVehicleFromList();
        if (v != null) showVehicleHistory(v);
    }

    public static void handleShowVehicleMaintenanceHistory() {
        Vehicle v = selectVehicleFromList();
        if (v != null) showVehicleMaintenanceHistory(v);
    }

    public static void handleAddMaintenance() {
        Vehicle v = selectVehicleFromList();
        if (v != null) addMaintenance(v);
    }

    public static void showVehicleInfo(Vehicle vehicle) {
        if (vehicle instanceof Car) {
            ((Car) vehicle).printInfo();
        } else if (vehicle instanceof Motorcycle) {
            ((Motorcycle) vehicle).printInfo();
        } else if (vehicle instanceof Truck) {
            ((Truck) vehicle).printInfo();
        }
    }

    public static void showVehicleHistory(Vehicle vehicle) {
        if (vehicle instanceof Car) {
            ((Car) vehicle).getHistory(vehicle.getOwner());
        } else if (vehicle instanceof Motorcycle) {
            ((Motorcycle) vehicle).getHistory(vehicle.getOwner());
        } else if (vehicle instanceof Truck) {
            ((Truck) vehicle).getHistory(vehicle.getOwner());
        }
    }

    public static void showVehicleMaintenanceHistory(Vehicle vehicle) {
        if (vehicle instanceof Car) {
            List<String> history = ((Car) vehicle).getMaintenanceHistory();
            System.out.println("Maintenance history for Car: " + vehicle.getManufacturer());
            for (String record : history) {
                System.out.println(record);
            }
        } else if (vehicle instanceof Motorcycle) {
            List<String> history = ((Motorcycle) vehicle).getMaintenanceHistory();
            System.out.println("Maintenance history for Motorcycle: " + vehicle.getManufacturer());
            for (String record : history) {
                System.out.println(record);
            }
        } else if (vehicle instanceof Truck) {
            List<String> history = ((Truck) vehicle).getMaintenanceHistory();
            System.out.println("Maintenance history for Truck: " + vehicle.getManufacturer());
            for (String record : history) {
                System.out.println(record);
            }
        }
    }
    public static void addMaintenance(Vehicle vehicle) {
        System.out.print("Enter maintenance description: ");
        String desc = scanner.nextLine();

        if (vehicle instanceof Car) {
            ((Car) vehicle).addMaintenance(desc);
        } else if (vehicle instanceof Motorcycle) {
            ((Motorcycle) vehicle).addMaintenance(desc);
        } else if (vehicle instanceof Truck) {
            ((Truck) vehicle).addMaintenance(desc);
        }
    }




    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Vehicle Management System ===");
            System.out.println("1 - Add new vehicle");
            System.out.println("2 - Show all vehicles");
            System.out.println("3 - Show full vehicle info");
            System.out.println("4 - Show vehicle owner history");
            System.out.println("5 - Show maintenance history");
            System.out.println("6 - Add maintenance record");
            System.out.println("7 - Save vehicles to file");
            System.out.println("8 - Load vehicles from file");
            System.out.println("9 - Exit");

            System.out.print("Enter your choice: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    addVehicle();
                    break;
                case "2":
                    showAllVehicles();
                    break;
                case "3":
                    handleShowVehicleInfo();
                    break;
                case "4":
                    handleShowVehicleOwnerHistory();
                    break;
                case "5":
                    handleShowVehicleMaintenanceHistory();
                    break;
                case "6":
                    handleAddMaintenance();
                    break;
                case "7":
                    saveVehiclesToFile();
                    break;
                case "8":
                    loadVehiclesFromFile();
                    break;
                case "9":
                    exit = true;
                    System.out.println("Exiting program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }




    }
}