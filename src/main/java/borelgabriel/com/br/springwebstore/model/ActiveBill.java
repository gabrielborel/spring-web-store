package borelgabriel.com.br.springwebstore.model;

import borelgabriel.com.br.springwebstore.enums.ActiveBillStatus;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "billing")
@SequenceGenerator(name = "billing_seq", sequenceName = "billing_seq", allocationSize = 1)
public class ActiveBill implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "billing_seq")
    private Long id;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private ActiveBillStatus status;

    @Temporal(TemporalType.DATE)
    @Column(name = "due_date")
    private Date dueDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "total_value", nullable = false)
    private BigDecimal totalValue;

    @Column(name = "discount_value", nullable = false)
    private BigDecimal discountValue;

    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(name = "person_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "person_fk"))
    private Person person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ActiveBillStatus getStatus() {
        return status;
    }

    public void setStatus(ActiveBillStatus status) {
        this.status = status;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActiveBill billing)) return false;
        return Objects.equals(getId(), billing.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
