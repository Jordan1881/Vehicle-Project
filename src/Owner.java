// Owner.java

public class Owner {
    private static String name;
    private String id;
    private String phone;

    public Owner(String name, String id, String phone) {
        if (!name.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Name must contain only letters and spaces");
        }

        if (!id.matches("\\d+")) {
            throw new IllegalArgumentException("ID must contain only digits");
        }

        if (!phone.matches("^(\\+972|972|0)?5\\d{8}$")) {
            throw new IllegalArgumentException("Phone must be a valid Israeli number (e.g. 05XXXXXXXX or +9725XXXXXXXX).");
        }

        this.name = name;
        this.id = id;
        this.phone = phone;
    }

    public static String getName() {
        return name;
    }

    public void setName(String name) {
        if (!name.matches("^[A-Za-z\\s]+$")) {
            throw new IllegalArgumentException("Name must not contain digits or special characters.");
        }
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (!id.matches("\\d{9}")) {
            throw new IllegalArgumentException("ID must be numeric and exactly 9 digits.");
        }
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (!phone.matches("\\d{9,}")) {
            throw new IllegalArgumentException("Phone must contain only digits and be at least 9 digits.");
        }
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Owner{name='" + name + "', id='" + id + "', phone='" + phone + "'}";
    }
}
