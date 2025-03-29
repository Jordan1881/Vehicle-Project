public enum FuelType {
    PETROL, DIESEL, ELECTRIC, HYBRID;

    public static FuelType fromStringIgnoreCase(String input) {
        for (FuelType type : FuelType.values()) {
            if (type.name().equalsIgnoreCase(input)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid fuel type: " + input);
    }
}