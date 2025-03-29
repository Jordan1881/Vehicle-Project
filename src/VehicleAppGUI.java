import javax.swing.*;
import java.awt.*;

public class VehicleAppGUI extends JFrame {
    private JPanel mainPanel;

    public VehicleAppGUI() {
        setTitle("Vehicle Management System");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        initializeComponents();
    }

    private void initializeComponents() {
        JLabel title = new JLabel("Welcome to Vehicle Manager!", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JButton addVehicleButton = new JButton("Add Vehicle");
        JButton showInfoButton = new JButton("Show Vehicle Info");
        JButton showOwnerHistoryButton = new JButton("Show Owner History");
        JButton showMaintenanceHistoryButton = new JButton("Show Maintenance History");
        JButton addMaintenanceButton = new JButton("Add Maintenance");
        JButton exitButton = new JButton("Exit");

        buttonPanel.add(addVehicleButton);
        buttonPanel.add(showInfoButton);
        buttonPanel.add(showOwnerHistoryButton);
        buttonPanel.add(showMaintenanceHistoryButton);
        buttonPanel.add(addMaintenanceButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.WEST);

        mainPanel = new JPanel(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        addVehicleButton.addActionListener(e -> switchPanel(new AddVehiclePanel()));
        showInfoButton.addActionListener(e -> switchPanel(new VehicleInfoPanel()));
        showOwnerHistoryButton.addActionListener(e -> switchPanel(new OwnerHistoryPanel()));
        showMaintenanceHistoryButton.addActionListener(e -> switchPanel(new MaintenanceHistoryPanel()));
        addMaintenanceButton.addActionListener(e -> switchPanel(new AddMaintenancePanel()));
        exitButton.addActionListener(e -> System.exit(0));
    }

    private void switchPanel(JPanel panel) {
        mainPanel.removeAll();
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VehicleAppGUI gui = new VehicleAppGUI();
            gui.setVisible(true);
        });
    }
}