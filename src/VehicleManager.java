import java.util.ArrayList;
import java.util.List;

public class VehicleManager {
    private static final List<Vehicle> vehicles = new ArrayList<>();

    public static void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public static List<Vehicle> getAllVehicles() {
        return new ArrayList<>(vehicles); // to prevent external modification
    }
}