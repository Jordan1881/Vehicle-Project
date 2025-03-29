import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AddMaintenancePanel extends JPanel {
    private JComboBox<Vehicle> vehicleCombo;
    private JTextField maintenanceField;

    public AddMaintenancePanel() {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        vehicleCombo = new JComboBox<>();

        for (Vehicle v : VehicleManager.getAllVehicles()) {
            vehicleCombo.addItem(v);
        }

        maintenanceField = new JTextField();
        JButton addButton = new JButton("Add Maintenance Record");

        inputPanel.add(vehicleCombo);
        inputPanel.add(maintenanceField);
        inputPanel.add(addButton);

        add(inputPanel, BorderLayout.NORTH);

        addButton.addActionListener(e -> {
            try {
                Vehicle selected = (Vehicle) vehicleCombo.getSelectedItem();
                String record = maintenanceField.getText();

                if (selected instanceof Maintainable m) {
                    m.addMaintenance(record);
                    JOptionPane.showMessageDialog(this, "Maintenance added!");
                } else {
                    JOptionPane.showMessageDialog(this, "Vehicle is not maintainable.");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}