import java.util.List;

interface Maintainable {
    void addMaintenance(String desc);
    List<String> getMaintenanceHistory();
}