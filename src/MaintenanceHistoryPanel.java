import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MaintenanceHistoryPanel extends JPanel {
    public MaintenanceHistoryPanel() {
        setLayout(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        List<Vehicle> vehicles = VehicleManager.getAllVehicles();
        if (vehicles.isEmpty()) {
            textArea.setText("No vehicles found.");
        } else {
            for (Vehicle v : vehicles) {
                textArea.append("Maintenance for: " + v.getManufacturer() + "\n");
                if (v instanceof Maintainable m) {
                    for (String record : m.getMaintenanceHistory()) {
                        textArea.append(" - " + record + "\n");
                    }
                }
                textArea.append("\n");
            }
        }

        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }
}