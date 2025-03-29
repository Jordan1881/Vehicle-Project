import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

public class AddVehiclePanel extends JPanel {

    private JComboBox<String> typeCombo;
    private JTextField manufacturerField, yearField, wheelsField;
    private JComboBox<FuelType> fuelCombo;

    public AddVehiclePanel() {
        setLayout(new GridLayout(6, 2, 10, 10));

        typeCombo = new JComboBox<>(new String[]{"Car", "Motorcycle", "Truck"});
        manufacturerField = new JTextField();
        yearField = new JTextField();
        wheelsField = new JTextField();
        fuelCombo = new JComboBox<>(FuelType.values());

        add(new JLabel("Vehicle Type:"));
        add(typeCombo);

        add(new JLabel("Manufacturer:"));
        add(manufacturerField);

        add(new JLabel("Year (e.g., 2020):"));
        add(yearField);

        add(new JLabel("Wheels:"));
        add(wheelsField);

        add(new JLabel("Fuel Type:"));
        add(fuelCombo);

        JButton addButton = new JButton("Add Vehicle");
        addButton.addActionListener(e -> handleAdd());

        add(new JLabel());
        add(addButton);



    }


    private void handleAdd() {
        try {
            String type = (String) typeCombo.getSelectedItem();
            String manufacturer = manufacturerField.getText();
            int year = Integer.parseInt(yearField.getText());
            int wheels = Integer.parseInt(wheelsField.getText());
            FuelType fuel = (FuelType) fuelCombo.getSelectedItem();

            Instant yearInstant = LocalDate.of(year, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant();

            Owner dummyOwner = new Owner("John Doe", "123456789", "0501234567");
            RegistrationInfo dummyReg = new RegistrationInfo(
                    LocalDate.now().minusYears(1),
                    LocalDate.now().plusYears(1),
                    LocalDate.now().minusMonths(6)
            );

            // Create a new vehicle based on the selected type
            Vehicle newVehicle;

            if ("Car".equals(type)) {
                newVehicle = new Car(wheels, manufacturer, fuel, yearInstant, dummyOwner, dummyReg, 4, false, new ArrayList<>());
            } else if ("Motorcycle".equals(type)) {
                newVehicle = new Motorcycle(wheels, manufacturer, fuel, yearInstant, dummyOwner, dummyReg, true);
            } else {
                newVehicle = new Truck(wheels, manufacturer, fuel, yearInstant, dummyOwner, dummyReg, 10000, false, 3000);
            }

            VehicleManager.addVehicle(newVehicle); // Assuming VehicleManager is a class that manages the list of vehicles

            JOptionPane.showMessageDialog(this, STR."""
                Vehicle added:
                \{newVehicle}""");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, STR."Error: \{ex.getMessage()}", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}