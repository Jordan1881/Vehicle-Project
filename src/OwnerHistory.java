import java.util.List;

public interface OwnerHistory {
    void getHistory(Owner owner);

    List<Owner> getOwnerHistory();
}