import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OwnerHistoryPanel extends JPanel {

    public OwnerHistoryPanel() {
        setLayout(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        List<Vehicle> vehicles = VehicleManager.getAllVehicles();

        if (vehicles.isEmpty()) {
            textArea.setText("No vehicles found.");
        } else {
            for (Vehicle v : vehicles) {
                textArea.append("Owner history for: " + v.getManufacturer() + "\n");

                // Check if the vehicle is an instance of OwnerHistory
                if (v instanceof OwnerHistory) {
                    OwnerHistory historyVehicle = (OwnerHistory) v;
                    List<Owner> history = historyVehicle.getOwnerHistory();

                    if (history.isEmpty()) {
                        textArea.append("  No previous owners.\n");
                    } else {
                        for (Owner owner : history) {
                            textArea.append("  • " + owner + "\n");
                        }
                    }
                } else {
                    textArea.append("  This vehicle doesn't track owner history.\n");
                }

                textArea.append("\n");
            }
        }

        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }
}