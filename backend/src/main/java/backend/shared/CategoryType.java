package backend.shared;

public enum CategoryType {
    SPORTS,
    FINANCE,
    MOVIES;

    public static CategoryType getCategoryType(String categoryTypeStr) {
        for (CategoryType type : CategoryType.values()) {
            if (type.name().equalsIgnoreCase(categoryTypeStr)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant for category type: " + categoryTypeStr);
    }
}
