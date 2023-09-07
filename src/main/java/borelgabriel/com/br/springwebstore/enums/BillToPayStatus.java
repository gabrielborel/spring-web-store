package borelgabriel.com.br.springwebstore.enums;

public enum BillToPayStatus {
    PENDING("Pending"),
    PAID("Paid"),
    OVERDUE("Overdue"),
    CANCELED("Canceled");

    private final String description;

    BillToPayStatus(String description) {
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
