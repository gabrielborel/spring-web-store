package borelgabriel.com.br.springwebstore.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "sell_purchase_virtual_store")
@SequenceGenerator(name = "sell_purchase_virtual_store_seq", sequenceName = "sell_purchase_virtual_store_seq", allocationSize = 1)
public class SellPurchaseVirtualStore implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sell_purchase_virtual_store_seq")
    private Long id;

    @ManyToOne(targetEntity = Person.class)
    @JoinColumn(
            name = "person_id",
            nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "person_fk")
    )
    private Person person;

    @ManyToOne(targetEntity = Address.class)
    @JoinColumn(
            name = "delivery_address_id",
            nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "delivery_address_fk")
    )
    private Address deliveryAddress;

    @ManyToOne(targetEntity = Address.class)
    @JoinColumn(
            name = "billing_address_id",
            nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "billing_address_fk")
    )
    private Address billingAddress;

    @Column(name = "total_value", nullable = false)
    private BigDecimal totalValue;

    @Column(name = "discount_value", nullable = false)
    private BigDecimal discountValue;

    @ManyToOne(targetEntity = PaymentMethod.class)
    @JoinColumn(
            name = "payment_method_id",
            nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "payment_method_fk")
    )
    private PaymentMethod paymentMethod;

    @OneToOne(targetEntity = SellInvoice.class)
    @JoinColumn(
            name = "sell_invoice_id",
            nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "sell_invoice_fk")
    )
    private SellInvoice sellInvoice;

    @ManyToOne(targetEntity = DiscountCode.class)
    @JoinColumn(
            name = "discount_code_id",
            nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "discount_code_fk")
    )
    private DiscountCode discountCode;

    @Column(name = "delivery_value", nullable = false)
    private BigDecimal deliveryValue;

    @Column(name = "delivery_day", nullable = false)
    private Integer deliveryDay;

    @Column(name = "sell_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date sellDate;

    @Column(name = "delivery_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date deliveryDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
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

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public SellInvoice getSellInvoice() {
        return sellInvoice;
    }

    public void setSellInvoice(SellInvoice sellInvoice) {
        this.sellInvoice = sellInvoice;
    }

    public DiscountCode getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(DiscountCode discountCode) {
        this.discountCode = discountCode;
    }

    public BigDecimal getDeliveryValue() {
        return deliveryValue;
    }

    public void setDeliveryValue(BigDecimal deliveryValue) {
        this.deliveryValue = deliveryValue;
    }

    public Integer getDeliveryDay() {
        return deliveryDay;
    }

    public void setDeliveryDay(Integer deliveryDay) {
        this.deliveryDay = deliveryDay;
    }

    public Date getSellDate() {
        return sellDate;
    }

    public void setSellDate(Date sellDate) {
        this.sellDate = sellDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SellPurchaseVirtualStore that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
