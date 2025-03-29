import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VehicleInfoPanel extends JPanel {
    public VehicleInfoPanel() {
        setLayout(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        List<Vehicle> vehicles = VehicleManager.getAllVehicles();
        if (vehicles.isEmpty()) {
            textArea.setText("No vehicles in system.");
        } else {
            for (Vehicle v : vehicles) {
                textArea.append(v.toString() + "\n\n");
            }
        }

        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }
}