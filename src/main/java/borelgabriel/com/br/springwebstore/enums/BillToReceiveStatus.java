package borelgabriel.com.br.springwebstore.enums;

public enum BillToReceiveStatus {
    PENDING("Pending"),
    PAID("Paid"),
    OVERDUE("Overdue"),
    CANCELED("Canceled");

    private final String description;

    BillToReceiveStatus(String description) {
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
