package borelgabriel.com.br.springwebstore.enums;

public enum ActiveDebtStatus {
    PENDING("Pending"),
    PAID("Paid"),
    OVERDUE("Overdue"),
    CANCELED("Canceled");

    private final String description;

    ActiveDebtStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
