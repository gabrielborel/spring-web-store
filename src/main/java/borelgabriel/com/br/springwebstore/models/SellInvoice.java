package borelgabriel.com.br.springwebstore.models;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "sell_invoice")
@SequenceGenerator(name = "sell_invoice_seq", sequenceName = "sell_invoice_seq", allocationSize = 1)
public class SellInvoice implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sell_invoice_seq")
    private Long id;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String series;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String xml;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String pdf;

    @OneToOne(targetEntity = SellPurchaseVirtualStore.class)
    @JoinColumn(
            name = "sell_purchase_virtual_store_id",
            nullable = false,
            foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "sell_purchase_virtual_store_fk")
    )
    private SellPurchaseVirtualStore sellPurchaseVirtualStore;

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

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SellInvoice that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
