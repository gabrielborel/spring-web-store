package borelgabriel.com.br.springwebstore.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tracking_status")
@SequenceGenerator(name = "tracking_status_seq", sequenceName = "tracking_status_seq", allocationSize = 1)
public class TrackingStatus implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tracking_status_seq")
    private Long id;

    @Column(name = "distribution_center")
    private String distributionCenter;

    private String city;

    private String state;

    private String status;

    @ManyToOne(targetEntity = SellPurchaseVirtualStore.class)
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

    public String getDistributionCenter() {
        return distributionCenter;
    }

    public void setDistributionCenter(String distributionCenter) {
        this.distributionCenter = distributionCenter;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrackingStatus that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
