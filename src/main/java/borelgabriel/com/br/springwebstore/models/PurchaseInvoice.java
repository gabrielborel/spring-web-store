package borelgabriel.com.br.springwebstore.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "purchase_invoice")
@SequenceGenerator(name = "purchase_invoice_seq", sequenceName = "purchase_invoice_seq", allocationSize = 1)
public class PurchaseInvoice implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_invoice_seq")
    private Long id;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String serial;

    @Column(name = "total_value", nullable = false)
    private BigDecimal totalValue;

    @Column(name = "discount_value", nullable = false)
    private BigDecimal discountValue;

    @Column(name = "icms_value", nullable = false)
    private BigDecimal icmsValue;

    @Column(name = "puchase_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date purchaseDate;

    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(
            name = "person_id",
            nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "person_fk")
    )
    private Person person;

    @ManyToOne(targetEntity = ActiveBill.class)
    @JoinColumn(
            name = "bill_id",
            nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "bill_fk")
    )
    private ActiveBill bill;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PurchaseInvoice that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public BigDecimal getIcmsValue() {
        return icmsValue;
    }

    public void setIcmsValue(BigDecimal icmsValue) {
        this.icmsValue = icmsValue;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public ActiveBill getBill() {
        return bill;
    }

    public void setBill(ActiveBill bill) {
        this.bill = bill;
    }
}
