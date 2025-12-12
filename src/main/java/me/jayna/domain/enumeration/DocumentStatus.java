package me.jayna.domain.enumeration;

/**
 * The DocumentStatus enumeration.
 */
public enum DocumentStatus {
    DRAFT("Draft"),
    APPROVED("Approved"),
    ARCHIVED("Archived"),
    EXPIRED("Expired");

    private final String value;

    DocumentStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
